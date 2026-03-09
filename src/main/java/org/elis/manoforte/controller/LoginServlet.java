package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;

import java.io.IOException;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.exception.DatiErratiException;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;
import org.elis.manoforte.utility.Utility;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, IOException {
        HttpSession session = request.getSession();

        Utente loggedUser = (Utente)session.getAttribute("utenteLoggato");

        if(loggedUser != null){
            response.sendRedirect(request.getContextPath()+"/"+Utility.getUserHomePage(loggedUser));
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        DatiErratiException emptyError = new DatiErratiException();
        if(email==null || email.trim().isEmpty())
            emptyError.setErrEmail();
        if(password==null || password.trim().isEmpty())
            emptyError.setErrPassword();

        if(emptyError.checkErrors()){
            emptyError.printStackTrace();

            request.setAttribute("errore", "Inserire tutti i campi.");
            doGet(request, response);
            return;
        }

        UtenteDAO utenteDAO = new JdbcUtenteDAO(DataSourceConfig.getDataSource());

        try {
            Utente utente = utenteDAO.findByEmailPassword(email, password);

            HttpSession session = request.getSession();
            session.setAttribute("utenteLoggato", utente);

            response.sendRedirect(request.getContextPath()+"/"+Utility.getUserHomePage(utente));

        }catch(DatiErratiException e) {
            e.printStackTrace();

            request.setAttribute("errore", "Dati inseriti errati.");
            doGet(request, response);

        }catch(Exception e) {
            e.printStackTrace();

            request.setAttribute("errore", "Errore nel login.");
            doGet(request, response);

        }

    }
}
