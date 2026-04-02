package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;

@WebServlet("/ModificaCitta")
public class ModificaCitta extends HttpServlet {

    CittaDAO cittaDao;

    public void init() throws ServletException{
        cittaDao = DaoFactory.getInstance().getCittaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");

        if (nome != null && !nome.trim().isEmpty()) {
            try {
                cittaDao.modificaCitta(id, nome.trim());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        response.sendRedirect("HomeAdmin");
    }
}
