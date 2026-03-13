package org.elis.manoforte.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.definition.AdminDAO;

@WebServlet("/AggiungiProfessione")
public class AggiungiProfessione extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String nome = request.getParameter("nomeProfessione");

        if (nome != null && !nome.trim().isEmpty()) {
            AdminDAO.aggiungiProfessione(nome.trim());
        }

        response.sendRedirect("HomeAdmin.jsp");
    }
}
