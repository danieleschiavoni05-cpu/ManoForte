package org.elis.manoforte.controller;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.jdbc.JdbcAdminDAO;

@WebServlet("/AggiungiProfessione")
public class AggiungiProfessione extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("utenteLoggato") == null) {
	        response.sendRedirect("Login");
	        return;
	    }

	    String nome = request.getParameter("nomeProfessione");

	    if (nome != null && !nome.trim().isEmpty()) {
	        JdbcAdminDAO.aggiungiProfessione(nome.trim());
	    }

	    response.sendRedirect("HomeAdmin");
	}

}
