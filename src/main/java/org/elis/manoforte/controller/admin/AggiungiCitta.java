package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.model.Citta;

@WebServlet("/AggiungiCitta")
public class AggiungiCitta extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private CittaDAO cittaDao;

    @Override
    public void init()throws ServletException{
        cittaDao = DaoFactory.getInstance().getCittaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String nome = request.getParameter("nomeCitta");

        if (nome != null && !nome.trim().isEmpty()) {
            Citta c = new Citta();
            c.setNome(nome);
            try {
                cittaDao.inserisciCitta(c);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        response.sendRedirect("HomeAdmin");
    }
}
