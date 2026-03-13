package org.elis.manoforte.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.definition.AdminDAO;

@WebServlet("/AggiungiCitta")
public class AggiungiCitta extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String nome = request.getParameter("nomeCitta");

        if (nome != null && !nome.trim().isEmpty()) {
            AdminDAO.aggiungiCitta(nome.trim());
        }

        response.sendRedirect("HomeAdmin.jsp");
    }
}
