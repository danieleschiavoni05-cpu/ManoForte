package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.elis.manoforte.dao.jdbc.JdbcProfessioneDAO;
import org.elis.manoforte.model.Professione;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/ListaProfessionisti")
public class ListaProfessionistiServlet extends HttpServlet {

    private JdbcProfessioneDAO professioneDAO;

    @Override
    public void init() throws ServletException {
        try {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL("jdbc:mysql://localhost:3306/progetto_java_web?useSSL=false&serverTimezone=UTC");
            ds.setUser("root");
            ds.setPassword("root"); 

            professioneDAO = new JdbcProfessioneDAO(ds);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Errore DataSource: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cerca = request.getParameter("cercaNome");
        if (cerca == null) cerca = "";
        String searchLower = cerca.toLowerCase();

        try {
            List<Professione> tutte = professioneDAO.getAllProfessioni();

            List<Professione> filtrate = tutte.stream()
                    .filter(p -> p.getNome().toLowerCase().contains(searchLower))
                    .collect(Collectors.toList());

            request.setAttribute("listaProfessionisti", filtrate);

            System.out.println("Professioni trovate: " + filtrate.size());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("listaProfessionisti", List.of());
        }

        request.getRequestDispatcher("/ListaProfessionisti.jsp")
               .forward(request, response);
    }
}
