package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;

@WebServlet("/ModificaProfessione")
public class ModificaProfessione extends HttpServlet {

    private static final long serialVersionUID = 1L;
	ProfessioneDAO professioneDao;

    public void init() throws ServletException{
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");

        if (nome != null && !nome.trim().isEmpty()) {
            try {
                professioneDao.modificaProfessione(id, nome.trim());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        response.sendRedirect("HomeAdmin#professions");
    }
}
