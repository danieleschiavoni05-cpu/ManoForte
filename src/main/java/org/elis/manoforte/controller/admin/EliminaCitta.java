
package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;

@WebServlet("/EliminaCitta")
public class EliminaCitta extends HttpServlet {
    private CittaDAO cittaDAO;

    @Override
    public void init()throws ServletException{
        cittaDAO = DaoFactory.getInstance().getCittaDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        try {
            cittaDAO.removeCitta(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("HomeAdmin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);

    }
}
