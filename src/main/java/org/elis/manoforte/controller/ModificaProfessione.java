package org.elis.manoforte.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.jdbc.JdbcAdminDAO;

@WebServlet("/ModificaProfessione")
public class ModificaProfessione extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("utenteLoggato") == null) {
	        response.sendRedirect("Login");
	        return;
	    }

	    int id = Integer.parseInt(request.getParameter("id"));
	    String nome = request.getParameter("nome");

	    if (nome != null && !nome.trim().isEmpty()) {
	        JdbcAdminDAO.modificaProfessione(id, nome.trim());
	    }

	    response.sendRedirect("HomeAdmin");
	}

}
