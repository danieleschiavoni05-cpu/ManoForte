package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;

@WebServlet("/EliminaProfessione")
public class EliminaProfessione extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProfessioneDAO professioneDAO;

    @Override
    public void init() throws ServletException {
        professioneDAO = DaoFactory.getInstance().getProfessioneDAO();
    }

    // Usiamo doGet perché il link <a> nella JSP invia una richiesta GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                professioneDAO.removeProfessione(id);
                request.getSession().setAttribute("successo", "Professione rimossa con successo.");
            } catch (Exception e) {
                e.printStackTrace();
                
                // Messaggio specifico per l'utente associato
                String msgErrore = "Impossibile eliminare la professione selezionata . " +
                        "Ci sono ancora professionisti registrati con questa qualifica.";
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