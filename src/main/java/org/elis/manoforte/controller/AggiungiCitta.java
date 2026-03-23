package org.elis.manoforte.controller;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.jdbc.JdbcAdminDAO;

@WebServlet("/AggiungiCitta")
public class AggiungiCitta extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("utenteLoggato") == null) {
	        response.sendRedirect("Login");
	        return;
	    }

	    String nome = request.getParameter("nomeCitta");

	    if (nome != null && !nome.trim().isEmpty()) {
	        JdbcAdminDAO.aggiungiCitta(nome.trim());
	    }

	    response.sendRedirect("HomeAdmin");
	}
}