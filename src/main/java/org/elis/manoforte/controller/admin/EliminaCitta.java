
package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
@WebServlet("/EliminaCitta")
public class EliminaCitta extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CittaDAO cittaDAO;

    @Override
    public void init() throws ServletException {
        cittaDAO = DaoFactory.getInstance().getCittaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                cittaDAO.removeCitta(id);
                request.getSession().setAttribute("successo", "Città rimossa con successo.");
            } catch (Exception e) {
                e.printStackTrace();
                
                // Messaggio specifico per l'utente associato
                String msgErrore = "Impossibile eliminare la citta selezionata . " +
                        "Ci sono ancora professionisti registrati con questa citta.";
                request.getSession().setAttribute("errore", msgErrore);
            }
        }

        response.sendRedirect("HomeAdmin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}