package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;

import java.io.*;
import java.sql.SQLException;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.dao.jdbc.JdbcCittaDAO;
import org.elis.manoforte.dao.jdbc.JdbcProfessioneDAO;
import org.elis.manoforte.dao.jdbc.JdbcVeicoloDAO;
import org.elis.manoforte.exception.NessunValoreTrovatoException;
import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;
import org.elis.manoforte.utility.Utility;

@WebServlet("/modificaProfiloProfessionista")
public class ModificaProfiloProfessionistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ModificaProfiloProfessionistaServlet() {
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

        System.out.println(loggedUser);

        CittaDAO cittaDAO = new JdbcCittaDAO(DataSourceConfig.getDataSource());
        VeicoloDAO veicoloDAO = new JdbcVeicoloDAO(DataSourceConfig.getDataSource());
        try {
            request.setAttribute("utenteLoggato", loggedUser);
            request.setAttribute("citta", cittaDAO.getAllCitta());
            request.setAttribute("veicoli", veicoloDAO.getAllVeicolo());
        }catch(SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/errorpage");
            return;
        }catch(NessunValoreTrovatoException e){
            e.printStackTrace();
            request.setAttribute("errore", e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/professionista/modificaProfiloProfessionista.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        RequestDispatcher dispatcher = request.getRequestDispatcher("");
        dispatcher.forward(request, response);
    }
}
