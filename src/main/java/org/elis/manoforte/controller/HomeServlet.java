package org.elis.manoforte.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.RecensioneDAOJDBC;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.utility.DataSourceConfig;

@WebServlet("/Homepage")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String task = request.getParameter("task");
        if (task != null && !task.trim().isEmpty()) {
            response.sendRedirect("ListaProfessionisti?cercaNome=" + task);
            return;
        }

        RecensioneDAO recensioneDAO = new RecensioneDAOJDBC(DataSourceConfig.getDataSource());
        UtenteDAO utenteDAO = new JdbcUtenteDAO(DataSourceConfig.getDataSource());
        try {
            List<Recensione> recensioni = recensioneDAO.findAll();
            request.setAttribute("recensioniList", recensioni);
            Map<Long, String> listaUtenti = new HashMap<>();
            Map<Long, String> utenti = utenteDAO.findAllUsersMap();
            for(Recensione recensione : recensioni) {
                for(Map.Entry<Long, String> entry : utenti.entrySet()) {
                    if((entry.getKey().longValue() == recensione.getId_cliente()||
                       entry.getKey().longValue() == recensione.getId_professionista())){
                        if(!listaUtenti.containsKey(recensione.getId_cliente())) {
                            listaUtenti.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
            request.setAttribute("listaUtenti", listaUtenti);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("Homepage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}