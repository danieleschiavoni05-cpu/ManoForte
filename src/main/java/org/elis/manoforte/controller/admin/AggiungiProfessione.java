package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;

@WebServlet("/AggiungiProfessione")
public class AggiungiProfessione extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private ProfessioneDAO professioneDAO;

    @Override
    public void init()throws ServletException{
        professioneDAO = DaoFactory.getInstance().getProfessioneDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String nome = request.getParameter("nomeProfessione");

        if (nome != null && !nome.trim().isEmpty()) {
            try {
                professioneDAO.addProfessione(nome);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        response.sendRedirect("HomeAdmin");
    }
}
