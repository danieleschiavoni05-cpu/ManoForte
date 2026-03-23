package org.elis.manoforte.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.jdbc.JdbcAdminDAO;

@WebServlet("/EliminaProfessione")
public class EliminaProfessione extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("utenteLoggato") == null) {
	        response.sendRedirect("Login");
	        return;
	    }

	    String nome = request.getParameter("nome");
	    int id = Integer.parseInt(request.getParameter("id"));

	    if (JdbcAdminDAO.professioneUsata(nome)) {
	        session.setAttribute("erroreProfessione", "Impossibile eliminare: la professione è collegata a utenti.");
	    } else {
	        JdbcAdminDAO.eliminaProfessione(id);
	    }

	    response.sendRedirect("HomeAdmin");
	}

  

    
}
