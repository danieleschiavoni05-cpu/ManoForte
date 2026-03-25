package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.elis.manoforte.dao.definition.DaoFactory;

import org.elis.manoforte.utility.DTOResponseRegistrazione;
import org.elis.manoforte.utility.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.exception.DatiErratiException;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Utente;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/registerBase")
public class RegisterBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	CittaDAO cittaDao;
	UtenteDAO utenteDao;

	public void init() throws ServletException{
		cittaDao = DaoFactory.getInstance().getCittaDAO();
		utenteDao = DaoFactory.getInstance().getUtenteDAO();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterBaseServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();

	        Utente loggedUser = (Utente)session.getAttribute("utenteLoggato");

	        if(loggedUser != null){
	            response.sendRedirect(request.getContextPath()+Utility.getUserHomePage(loggedUser));
	            return;
	        }

		try {
	        List<Citta> citta = cittaDao.getAllCitta(); 
	        request.setAttribute("listaCitta", citta);
	        
	        // Assicurati che il percorso della JSP sia corretto (corrisponda alla cartella in WebContent/webapp)
	        request.getRequestDispatcher("/auth/registerbase.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect(request.getContextPath() + "/errore.jsp");
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)	 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    
		    PrintWriter outJson = response.getWriter();
			ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

		    try {
		        // 1. Recupero parametri
		        String nome = request.getParameter("campoNome");
		        String cognome = request.getParameter("campoCognome");
		        String email = request.getParameter("campoEmail");
		        String codice_fiscale = request.getParameter("campoCodiceFiscale");
		        String password = request.getParameter("campoPassword");
		        String confermapassword = request.getParameter("campoConfermaPassword");
		        
		        LocalDate data_nascita = null;
		        if(request.getParameter("campoData") != null && !request.getParameter("campoData").isEmpty()) {
		            data_nascita = LocalDate.parse(request.getParameter("campoData"));
		        }

		        Long id_citta = null;
		        if(request.getParameter("campoCitta") != null && !request.getParameter("campoCitta").isEmpty()) {
		            id_citta = Long.parseLong(request.getParameter("campoCitta"));
		        }

		        // 2. Validazione sintattica immediata
		        DatiErratiException emptyError = new DatiErratiException();
		        if(email == null || email.trim().isEmpty()) emptyError.setErrEmail();
		        if(nome == null || nome.trim().isEmpty()) emptyError.setErrNome();
		        if(cognome == null || cognome.trim().isEmpty()) emptyError.setErrCognome();
		        if(data_nascita == null) emptyError.setErrData();
				if(id_citta == null) emptyError.setErrCitta();
		        if(codice_fiscale == null || codice_fiscale.trim().isEmpty()) emptyError.setErrCF();
		        if(password == null || password.trim().isEmpty()) emptyError.setErrPassword();

		        if(emptyError.checkErrors()){
		            emptyError.buildEmptyErrorMessage();
		            outJson.print(mapper.writeValueAsString(new DTOResponseRegistrazione(false, "Campi obbligatori mancanti", emptyError.getMessages())));
		            return;
		        }

		        // 3. Validazione Business Logic (Utility)
		        Utente u = Utility.checkInputUtenteBase(email, password, nome, cognome, data_nascita, codice_fiscale, id_citta, confermapassword);

				Citta c = cittaDao.getCittaById(id_citta);

				u.setCitta(c);
		                 
		        // 4. Inserimento
		        utenteDao.inserisciUtente(u);

		        // 5. Risposta JSON (Il redirect lo farà il frontend)
		       outJson.print(mapper.writeValueAsString(new DTOResponseRegistrazione(true, "Registrazione completata con successo!", null)));
		       

		    } catch(DatiErratiException e) {
		        e.buildErrorMessage();
		        outJson.print(mapper.writeValueAsString(new DTOResponseRegistrazione(false, "Dati non validi", e.getMessages())));
		        
		    } catch(SQLException e) {
		        e.printStackTrace();
		        outJson.print(mapper.writeValueAsString(new DTOResponseRegistrazione(false, "Errore database (forse email o CF già esistenti)", null)));
		      
		    } catch (Exception e){
		        e.printStackTrace();
		        outJson.print(mapper.writeValueAsString(new DTOResponseRegistrazione(false, "Errore imprevisto", null)));
		     
		    } finally {
		        outJson.flush();
		        
		    }
		}
}