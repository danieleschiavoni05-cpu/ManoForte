package org.elis.manoforte.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.jdbc.JdbcAdminDAO;

@WebServlet("/ModificaCitta")
public class ModificaCitta extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");

        if (nome != null && !nome.trim().isEmpty()) {
            JdbcAdminDAO.modificaCitta(id, nome.trim());
        }

        response.sendRedirect("HomeAdmin");
    }
}
