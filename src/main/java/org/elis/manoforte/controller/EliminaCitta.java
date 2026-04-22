package org.elis.manoforte.controller;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.jdbc.JdbcAdminDAO;

@WebServlet("/EliminaCitta")
public class EliminaCitta extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        // Controllo login
        if (session == null || session.getAttribute("utenteLoggato") == null) {
            response.sendRedirect("Login");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            // Controllo se la città è collegata ad utenti
            if (JdbcAdminDAO.cittaUsata(id)) {
                session.setAttribute("erroreCitta",
                        "Impossibile eliminare: la città è collegata a uno o più utenti.");
            } else {
                JdbcAdminDAO.eliminaCitta(id);
            }

        } catch (NumberFormatException e) {
            session.setAttribute("erroreCitta", "ID città non valido.");
        } catch (Exception e) {
            session.setAttribute("erroreCitta", "Errore interno durante l'eliminazione.");
            e.printStackTrace();
        }

        response.sendRedirect("HomeAdmin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request, response);
    }
}
