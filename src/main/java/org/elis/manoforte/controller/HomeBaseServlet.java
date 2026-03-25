package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class HomeBaseServlet
 */
@WebServlet("/homeBase")
public class HomeBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtenteDAO utenteDao;
	private ProfessioneDAO professioneDao;
	private RichiestaDAO richiestaDao;

	@Override
	public void init() throws ServletException{
		utenteDao = DaoFactory.getInstance().getUtenteDAO();
		professioneDao = DaoFactory.getInstance().getProfessioneDAO();
		richiestaDao = DaoFactory.getInstance().getRichiestaDAO();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeBaseServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        // 1. Istanzia il tuo DAO (o Service)
	        
	        Utente utenteSessione = (Utente) request.getSession().getAttribute("utenteLoggato");
	        String emailBase = utenteSessione.getEmail();
	        Long idBase=utenteDao.findIdByEmail(emailBase);
	        
	        // 2. Prendi la lista dei professionisti
	        List<Professione> listaProfessioni = professioneDao.getAllProfessioni();
	        List<Richiesta> listaRichiesta= richiestaDao. findRichiestaByIdCliente(idBase);
	        
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
		doGet(request, response);
	}

}
