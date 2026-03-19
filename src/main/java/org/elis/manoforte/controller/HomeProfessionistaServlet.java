package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.dao.jdbc.JdbcCittaDAO;
import org.elis.manoforte.dao.jdbc.JdbcVeicoloDAO;
import org.elis.manoforte.dao.jdbc.RecensioneDAOJDBC;
import org.elis.manoforte.dao.jdbc.RichiestaDAOJDBC;
import org.elis.manoforte.exception.NessunValoreTrovatoException;
import org.elis.manoforte.model.*;
import org.elis.manoforte.utility.DataSourceConfig;
import org.elis.manoforte.utility.Utility;

import javax.sql.DataSource;

@WebServlet("/homeprofessionista")
public class HomeProfessionistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HomeProfessionistaServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Utente loggedUser = (Utente)session.getAttribute("utenteLoggato");

        if(loggedUser == null){
            response.sendRedirect(request.getContextPath()+"/login");
            return;
        }else if(loggedUser.getRuolo()!=Ruolo.PROFESSIONISTA){
            response.sendRedirect(request.getContextPath()+"/"+Utility.getUserHomePage(loggedUser));
            return;
        }


        DataSource ds = DataSourceConfig.getDataSource();
        RichiestaDAO richiestaDAO = new RichiestaDAOJDBC(ds);
        RecensioneDAO recensioneDAO = new RecensioneDAOJDBC(ds);
        CittaDAO cittaDAO = new JdbcCittaDAO(DataSourceConfig.getDataSource());
        VeicoloDAO veicoloDAO = new JdbcVeicoloDAO(DataSourceConfig.getDataSource());

        try{
            List<CardRichiesta> richiesteInAttesa = richiestaDAO.getRichiesteByEmailProfessionistaAndStato(loggedUser.getEmail(), StatoRichiesta.IN_ATTESA_DI_CONFERMA);
            List<CardRichiesta> richiesteInCorso = richiestaDAO.getRichiesteByEmailProfessionistaAndStato(loggedUser.getEmail(), StatoRichiesta.IN_CORSO);
            List<CardRichiesta> richiesteComplete = richiestaDAO.getRichiesteByEmailProfessionistaAndStato(loggedUser.getEmail(), StatoRichiesta.COMPLETA);

            List<CardRecensione> recensioni = recensioneDAO.getRecensioneByEmailProfessionistaLimit(loggedUser.getEmail(), 4);

            // Attibuti per le mie richieste
            request.setAttribute("richiesteInAttesa", richiesteInAttesa);
            request.setAttribute("richiesteInCorso", richiesteInCorso);
            request.setAttribute("richiesteComplete", richiesteComplete);
            // Atrributi per le mie recensioni
            request.setAttribute("recensioni", recensioni);
            // Attributi per la modifica del profilo
            request.setAttribute("citta", cittaDAO.getAllCitta());
            request.setAttribute("veicoli", veicoloDAO.getAllVeicolo());

        }catch(SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath()+"/errorPage");
            return;
        }catch(NessunValoreTrovatoException e){
            e.printStackTrace();
            request.setAttribute("errore", e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/professionista/homeprofessionista.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        doGet(request, response);
    }
}
