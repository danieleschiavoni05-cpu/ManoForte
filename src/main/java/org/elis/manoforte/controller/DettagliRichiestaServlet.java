package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcCittaDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.RichiestaDAOJDBC;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;
import org.elis.manoforte.utility.Utility;

@WebServlet("/dettagliRichiesta")
public class DettagliRichiestaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DettagliRichiestaServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");

        if(utenteLoggato == null){
            response.sendRedirect("login");
            return;
        }else if(utenteLoggato.getRuolo()!=Ruolo.PROFESSIONISTA){
            response.sendRedirect(Utility.getUserHomePage(utenteLoggato));
            return;
        }

        Long id_richiesta = Long.parseLong((String)request.getSession().getAttribute("id_richiesta"));

        RichiestaDAO richiestaDAO = new RichiestaDAOJDBC(DataSourceConfig.getDataSource());
        CittaDAO cittaDAO = new JdbcCittaDAO(DataSourceConfig.getDataSource());
        UtenteDAO utenteDAO = new JdbcUtenteDAO(DataSourceConfig.getDataSource());
        try{
            Richiesta richiesta = richiestaDAO.getRichiestaById(id_richiesta);
            Utente cliente = utenteDAO.findById(richiesta.getId_cliente());
            Citta citta = cittaDAO.getCittaById(cliente.getIdCitta());

            request.setAttribute("richiesta", richiesta);
            request.setAttribute("cliente", cliente);
            request.setAttribute("citta", citta);

        }catch(Exception e){
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/professionista/dettagliRichiesta.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().removeAttribute("id_richiesta");

        RequestDispatcher dispatcher = request.getRequestDispatcher("");
        dispatcher.forward(request, response);
    }
}
