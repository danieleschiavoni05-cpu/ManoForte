package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.time.LocalDate;

import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.RecensioneDAOJDBC;
import org.elis.manoforte.dao.jdbc.RichiestaDAOJDBC;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;

/**
 * Servlet implementation class InviaRecensioneServlet
 */
@WebServlet("/InviaRecensione")
public class InviaRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InviaRecensioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		Utente utenteLoggato=(Utente) session.getAttribute("utenteLoggato");
		
		if (utenteLoggato == null) {
	        response.sendRedirect(request.getContextPath() + "/login.jsp");
	        return;
	    }

	    try {
	        // 2. Recupero e parsing parametri
	        Long idRichiesta = Long.parseLong(request.getParameter("idRichiesta"));
	        String descrizione = request.getParameter("descrizione");
	        int voto = Integer.parseInt(request.getParameter("voto"));
	        LocalDate data = LocalDate.parse(request.getParameter("campoData"));
	        Long id_cliente = Long.parseLong(request.getParameter("id_cliente"));
	        Long id_professionista = Long.parseLong(request.getParameter("id_professionista"));

	        // 3. Inizializzazione DAO
	        RichiestaDAO richiestaDao = new RichiestaDAOJDBC(DataSourceConfig.getDataSource());
	        RecensioneDAO recensioneDao = new RecensioneDAOJDBC(DataSourceConfig.getDataSource());

	        // 4. Verifica della richiesta (metodo diretto invece del loop)
	        Richiesta richiesta = richiestaDao.getRichiestaById(idRichiesta);

	        // 5. Logica di business: si può recensire solo se la richiesta è COMPLETA
	        if (richiesta != null && StatoRichiesta.COMPLETA.equals(richiesta.getStatoRichiesta())) {
	            
	            Recensione rec = new Recensione(idRichiesta, descrizione, voto, data, id_cliente, id_professionista);
	            
	            // Uso dell'istanza del DAO
	            recensioneDao.inserisciRecensione(rec);
	            
	            // Opzionale: impostare un messaggio di successo
	            session.setAttribute("messaggio", "Recensione inviata con successo!");
	        }

	    } catch (Exception e) {
	        // Gestione errore formattazione dati
	        e.printStackTrace();
	    }

	    // 6. Redirect finale
	    response.sendRedirect(request.getContextPath() + "/HomeServlet");
	}
}