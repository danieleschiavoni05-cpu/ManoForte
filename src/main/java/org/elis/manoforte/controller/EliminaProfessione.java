package org.elis.manoforte.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.definition.AdminDAO;

@WebServlet("/EliminaProfessione")
public class EliminaProfessione extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        AdminDAO.eliminaProfessione(id);

        response.sendRedirect("HomeAdmin.jsp");
    }
}
