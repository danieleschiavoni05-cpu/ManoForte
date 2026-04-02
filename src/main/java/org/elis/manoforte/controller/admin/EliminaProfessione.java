package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;

@WebServlet("/EliminaProfessione")
public class EliminaProfessione extends HttpServlet {
    private ProfessioneDAO professioneDao;

    @Override
    public void init() throws ServletException{
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        try {
            professioneDao.removeProfessione(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("HomeAdmin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        try {
            professioneDao.removeProfessione(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("HomeAdmin");
    }
}
