package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcProfessioneDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.RichiestaDAOJDBC;
import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;

/**
 * Servlet implementation class HomeBaseServlet
 */
@WebServlet("/homeBase")
public class HomeBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeBaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
	        // 1. Istanzia il tuo DAO (o Service)
	        UtenteDAO utentedao = new JdbcUtenteDAO(DataSourceConfig.getDataSource()); 
	        ProfessioneDAO professionedao = new JdbcProfessioneDAO(DataSourceConfig.getDataSource());
	        RichiestaDAO richiestadao = new RichiestaDAOJDBC(DataSourceConfig.getDataSource());
	        
	        Utente utenteSessione = (Utente) request.getSession().getAttribute("utenteLoggato");
	        String emailBase = utenteSessione.getEmail();
	        Long idBase=utentedao.trovaIdBasePerEmail(emailBase);
	        
	        // 2. Prendi la lista dei professionisti
	        List<Professione> listaProfessioni = professionedao.getAllProfessioni();
	        List<Richiesta> listaRichiesta= richiestadao. findRichiestaByIdCliente(idBase);
	        
	        // 3. Passa la lista alla JSP con un nome chiave ("pro")
	        request.setAttribute("professioni", listaProfessioni);
	        request.setAttribute("listaRichiesta", listaRichiesta);
	        
	    } catch (Exception e) {
	        e.printStackTrace(); // Gestione errore base
	    }

	    // 4. Vai alla pagina JSP
	    request.getRequestDispatcher("/WEB-INF/homeBase.jsp").forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
