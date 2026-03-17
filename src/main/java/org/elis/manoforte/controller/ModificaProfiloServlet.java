package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import org.elis.manoforte.utility.DTOResponseRegistrazione;
import org.elis.manoforte.utility.DataSourceConfig;
import org.elis.manoforte.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcCittaDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.exception.DatiErratiException;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class ModificaProfiloServlet
 */
@WebServlet("/ModificaProfilo")
public class ModificaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificaProfiloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente utenteSessione = (Utente) request.getSession().getAttribute("utenteLoggato");
		CittaDAO cittaDao = new JdbcCittaDAO(DataSourceConfig.getDataSource());
		// 2. Controllo sicurezza: se non c'è nessuno in sessione, rimanda al login
		if (utenteSessione == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}


		try {
			List<Citta> citta = cittaDao.getAllCitta(); 
	        request.setAttribute("listaCitta", citta);
			request.setAttribute("utenteLoggato", utenteSessione);
			request.getRequestDispatcher("/WEB-INF/ModificaProfilo.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("errore.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 1. Controllo Sessione IMMEDIATO (prima di toccare la response)
	    Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");
	    if (utenteLoggato == null) {
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    // 2. Inizializzazione DAO e JSON (ora che siamo sicuri di essere loggati)
	    UtenteDAO dao = new JdbcUtenteDAO(DataSourceConfig.getDataSource());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    PrintWriter outJson = response.getWriter();
	    ObjectMapper mapper = new ObjectMapper();

	    try {
	        // Recupero parametri
	        String nuovoNome = request.getParameter("nome");
	        String nuovoCognome = request.getParameter("cognome");
	        String nuovoCF = request.getParameter("codiceFiscale");
	        String oldPassForm = request.getParameter("oldPassword");
	        String nuovaPass = request.getParameter("newPassword");
	        String confermaPass = request.getParameter("confirmPassword");

	        LocalDate dataNascitaStr = null;
	        if(request.getParameter("dataNascita") != null && !request.getParameter("dataNascita").isEmpty()) {
	            dataNascitaStr = LocalDate.parse(request.getParameter("dataNascita"));
	        }

	        Long idCittaStr = null;
	        if(request.getParameter("campoCitta") != null && !request.getParameter("campoCitta").isEmpty()) {
	            idCittaStr = Long.parseLong(request.getParameter("campoCitta"));
	        }
	        
	        
	        // 3. Validazione campi obbligatori
	        DatiErratiException emptyError = new DatiErratiException();
	        if(nuovoNome == null || nuovoNome.trim().isEmpty()) emptyError.setErrNome();
	        if(nuovoCognome == null || nuovoCognome.trim().isEmpty()) emptyError.setErrCognome();
	        if(dataNascitaStr == null) emptyError.setErrData();
	        if(nuovoCF == null || nuovoCF.trim().isEmpty()) emptyError.setErrCF();
	        if(oldPassForm == null || oldPassForm.trim().isEmpty()) emptyError.setErrPassword();

	        // Controllo coerenza password se l'utente sta provando a cambiarla
	        if(nuovaPass != null && !nuovaPass.trim().isEmpty()) {
	            if(confermaPass == null || !nuovaPass.equals(confermaPass)) {
	                emptyError.setErrConfermaPassword();
	            }
	        }

	        if(emptyError.checkEditErrors()){
	            emptyError.printStackTrace();
	            emptyError.buildEmptyErrorMessage();

	            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
	                    "Alcuni dei campi non sono stati compilati.", emptyError.getMessages());
	            outJson.print(mapper.writeValueAsString(risposta));
	            outJson.flush();
	            return;
	        }
	       
	        // 4. Logica di Business e Database
	        Utente utenteBase = Utility.checkInputEditUtenteBase(utenteLoggato, nuovoNome, nuovoCognome, 
	                            dataNascitaStr, nuovoCF, idCittaStr, nuovaPass, oldPassForm, confermaPass);
	            
	        dao.update(utenteBase);

	        // Aggiornamento Sessione
	        request.getSession().setAttribute("utenteLoggato", utenteBase);
	        
	        DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(true,
                    "Modifica completata con successo.", null);
            outJson.print(mapper.writeValueAsString(risposta));

        }catch(DatiErratiException e) {
            e.printStackTrace();
            e.buildErrorEditMessageBase();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Errore inserimento dati dell'utente.", e.getMessages());
            outJson.print(mapper.writeValueAsString(risposta));

        }catch(SQLException e) {
            e.printStackTrace();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Errore nel caricamento delle modifiche, riprovare più tardi.", null);
            outJson.print(mapper.writeValueAsString(risposta));

        }catch (Exception e){
            e.printStackTrace();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Errore imprevisto, riprovare.", null);
            outJson.print(mapper.writeValueAsString(risposta));
        }finally{
            outJson.flush();
        }
	}
}