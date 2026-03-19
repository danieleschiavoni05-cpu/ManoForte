package org.elis.manoforte.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.RecensioneDAOJDBC;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;

@WebServlet("/Homepage")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String task = request.getParameter("task");
        if (task != null && !task.trim().isEmpty()) {
            response.sendRedirect("ListaProfessionisti?cercaNome=" + task);
            return;
        }

        UtenteDAO utenteDao = new JdbcUtenteDAO(DataSourceConfig.getDataSource());
        RecensioneDAO recensioneDao = new RecensioneDAOJDBC(DataSourceConfig.getDataSource());
        
        try {
        	// ... dentro il try della HomeServlet ...

        	List<Recensione> recensioni = recensioneDao.findAllwithConditions();
        	List<Utente> professionisti = utenteDao.findAllProfessionisti();

        	// 1. Creiamo la mappa
        	Map<Long, Utente> mappaProfessionisti = new HashMap<>();

        	if (professionisti != null) {
        	    for (Utente u : professionisti) {
        	        // 2. RECUPERO ID: Usiamo il tuo metodo DAO per ottenere l'ID tramite l'email
        	        Long idProfessionista = utenteDao.trovaIdProfessionistaPerEmail(u.getEmail());
        	        
        	        if (idProfessionista != null) {
        	            // 3. ASSOCIAZIONE: Chiave = ID (Long), Valore = Oggetto Utente
        	            mappaProfessionisti.put(idProfessionista, u);
        	        }
        	    }
        	}

        	// 4. PASSIAMO I DATI ALLA JSP
        	request.setAttribute("recensioni", recensioni);
        	request.setAttribute("mappaProfessionisti", mappaProfessionisti);

        	// Debug rapido in console per sicurezza
        	System.out.println("Mappa creata con " + mappaProfessionisti.size() + " professionisti");
        	
            request.setAttribute("recensioni", recensioni);
            request.setAttribute("mappaProfessionisti", mappaProfessionisti);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("Homepage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}