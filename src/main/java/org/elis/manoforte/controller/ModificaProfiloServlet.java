package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.utility.DataSourceConfig;

import java.io.IOException;
import java.util.List;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcCittaDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
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
	    UtenteDAO dao = new JdbcUtenteDAO(DataSourceConfig.getDataSource());

	    try {
	        // Recupero l'utente corrente dalla sessione (contiene i dati "vecchi" inclusa la pass attuale)
	        Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");

	        if (utenteLoggato == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        // 1. Recupero parametri anagrafici
	        String nuovoNome = request.getParameter("nome");
	        String nuovoCognome = request.getParameter("cognome");
	        String nuovoCF = request.getParameter("codiceFiscale");
	        String idCittaStr = request.getParameter("campoCitta");
	        String dataNascitaStr = request.getParameter("dataNascita");

	        // 2. Recupero parametri Password
	        String oldPassForm = request.getParameter("oldPassword"); // Quella inserita dall'utente
	        String nuovaPass = request.getParameter("newPassword");
	        String confermaPass = request.getParameter("confirmPassword");

	        // --- LOGICA DI CONTROLLO PASSWORD ---
	        
	        // Controllo A: La vecchia password deve essere corretta
	        if (!utenteLoggato.getPassword().equals(oldPassForm)) {
	            // Se la password non corrisponde, torniamo al form con un errore
	            request.setAttribute("errore", "La password attuale inserita non è corretta.");
	            doGet(request, response); // Ricarica la pagina tramite doGet per riavere la lista città
	            return;
	        }

	        // Controllo B: Se l'utente vuole cambiare password (nuovaPass non vuota)
	        if (nuovaPass != null && !nuovaPass.trim().isEmpty()) {
	            if (nuovaPass.equals(confermaPass)) {
	                // Se coincidono, la aggiorno nell'oggetto
	                utenteLoggato.setPassword(nuovaPass);
	            } else {
	                // Se non coincidono
	                request.setAttribute("errore", "Le nuove password non coincidono.");
	                doGet(request, response);
	                return;
	            }
	        }
	        // Se nuovaPass è vuota, l'oggetto mantiene la vecchia password già presente in utenteLoggato

	        // 3. Aggiornamento degli altri campi
	        if (nuovoNome != null && idCittaStr != null) {
	            utenteLoggato.setNome(nuovoNome);
	            utenteLoggato.setCognome(nuovoCognome);
	            utenteLoggato.setCodiceFiscale(nuovoCF);
	            
	            long idCitta = Long.parseLong(idCittaStr);
	            utenteLoggato.setId_citta(idCitta); 
	            // Aggiungi qui l'eventuale set della data di nascita se il tuo modello lo prevede

	            // 4. Salvataggio su Database
	            dao.update(utenteLoggato);

	            // 5. Aggiorno la sessione
	            request.getSession().setAttribute("utenteLoggato", utenteLoggato);
	            
	            // Redirect al successo
	            response.sendRedirect(request.getContextPath() + "/homeBase");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("errore.jsp");
	    }
	}
}