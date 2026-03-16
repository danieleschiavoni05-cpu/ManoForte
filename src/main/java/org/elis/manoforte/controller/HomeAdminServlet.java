package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.elis.manoforte.dao.jdbc.JdbcAdminDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/HomeAdmin")
public class HomeAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Object[]> citta = JdbcAdminDAO.getCitta();
        List<Object[]> professioni = JdbcAdminDAO.getProfessioni();

        request.setAttribute("citta", citta);
        request.setAttribute("professioni", professioni);

        request.getRequestDispatcher("/HomeAdmin.jsp").forward(request, response);
    }
}
