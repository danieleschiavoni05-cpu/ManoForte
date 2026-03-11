package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.jdbc.RecensioneDAOJDBC;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;

/**
 * Servlet implementation class RecensioniProfessionistiServlet
 */
@WebServlet("/RecensioniProfessionisti")
public class RecensioniProfessionistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecensioniProfessionistiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		Utente utenteLoggato=(Utente) session.getAttribute("utenteLoggato");
		
		if (utenteLoggato == null) {
	        response.sendRedirect(request.getContextPath() + "/login.jsp");
	        return;
	    }
		RecensioneDAO recensioneDao = new RecensioneDAOJDBC(DataSourceConfig.getDataSource());
		request.setAttribute("recensioni", recensioneDao.findAll());
		
	
		request.getRequestDispatcher( "/WEB-INF/recensioniPro.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
