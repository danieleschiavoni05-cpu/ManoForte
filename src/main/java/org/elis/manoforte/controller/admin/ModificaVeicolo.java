package org.elis.manoforte.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.VeicoloDAO;

import java.io.IOException;

@WebServlet("/ModificaVeicolo")
public class ModificaVeicolo extends HttpServlet {

    private static final long serialVersionUID = 1L;
	VeicoloDAO veicoloDao;

    public void init() throws ServletException{
        veicoloDao = DaoFactory.getInstance().getVeicoloDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");

        if (nome != null && !nome.trim().isEmpty()) {
            try {
                veicoloDao.modificaVeicoloById(id, nome.trim());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        response.sendRedirect("HomeAdmin#vehicles");
    }
}
