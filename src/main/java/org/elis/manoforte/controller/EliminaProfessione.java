package org.elis.manoforte.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.jdbc.JdbcAdminDAO;

@WebServlet("/EliminaProfessione")
public class EliminaProfessione extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        JdbcAdminDAO.eliminaProfessione(id);

        response.sendRedirect("HomeAdmin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        JdbcAdminDAO.eliminaProfessione(id);

        response.sendRedirect("HomeAdmin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
}
