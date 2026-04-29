
package org.elis.manoforte.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.model.Veicolo;

import java.io.IOException;

@WebServlet("/EliminaVeicolo")
public class EliminaVeicolo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VeicoloDAO veicoloDao;

    @Override
    public void init() throws ServletException {
        veicoloDao = DaoFactory.getInstance().getVeicoloDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                veicoloDao.removeVeicolo(id);
                request.getSession().setAttribute("successo", "Veicolo rimosso con successo.");
            } catch (Exception e) {
                e.printStackTrace();
                
                // Messaggio specifico per l'utente associato
                String msgErrore = "Impossibile eliminare il veicolo selezionato. " +
                        "Ci sono ancora professionisti registrati con questo veicolo.";
                request.getSession().setAttribute("errore", msgErrore);
            }
        }

        response.sendRedirect("HomeAdmin#vehicles");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}