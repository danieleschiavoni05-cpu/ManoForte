package org.elis.manoforte.controller;

import java.io.IOException;
import java.util.List;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.model.Recensione;

@WebServlet("/Homepage")
public class HomeServlet extends HttpServlet {

    private UtenteDAO utenteDao;
    private RecensioneDAO recensioneDao;

    @Override
    public void init() throws ServletException{
        utenteDao = DaoFactory.getInstance().getUtenteDAO();
        recensioneDao = DaoFactory.getInstance().getRecensioneDAO();
    }

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
        	List<Recensione> recensioni = recensioneDao.findRecensioneLimit(3);
        	request.setAttribute("recensioni", recensioni);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("Homepage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
