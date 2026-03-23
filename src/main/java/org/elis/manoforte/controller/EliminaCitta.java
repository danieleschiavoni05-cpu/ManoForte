package org.elis.manoforte.controller;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.jdbc.JdbcAdminDAO;

@WebServlet("/EliminaCitta")
public class EliminaCitta extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {

	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("utenteLoggato") == null) {
	        response.sendRedirect("Login");
	        return;
	    }

	    int id = Integer.parseInt(request.getParameter("id"));

	    if (JdbcAdminDAO.cittaUsata(id)) {
	        session.setAttribute("erroreCitta", "Impossibile eliminare: la città è collegata a utenti.");
	    } else {
	        JdbcAdminDAO.eliminaCitta(id);
	    }

	    response.sendRedirect("HomeAdmin");
	}


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request, response);
    }
}
