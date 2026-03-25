package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Professione;

import java.io.IOException;
import java.util.List;

@WebServlet("/HomeAdmin")
public class HomeAdminServlet extends HttpServlet {
    private CittaDAO cittaDao;
    private ProfessioneDAO professioneDao;

    @Override
    public void init() throws ServletException{
        cittaDao = DaoFactory.getInstance().getCittaDAO();
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Citta> citta = null;
        List<Professione> professioni = null;

        try {
            citta = cittaDao.getAllCitta();
            professioni = professioneDao.getAllProfessioni();

            request.setAttribute("citta", citta);
            request.setAttribute("professioni", professioni);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("/HomeAdmin.jsp").forward(request, response);
    }
}
