package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;

import java.io.*;
import java.nio.file.Files;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcVeicoloDAO;
import org.elis.manoforte.exception.DatiErratiException;
import org.elis.manoforte.model.Ruolo;
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
        String path = "C:/immagini";
        File file = new File(path);
        if (!file.exists()) {
            Files.createDirectory(file.toPath());
        }
        try(DataOutputStream fos = new DataOutputStream(new FileOutputStream(file.toPath()+"/test.txt"))){
            fos.writeChars("hello world");
            fos.flush();
        }


        HttpSession session = request.getSession();
        Utente loggedUser = (Utente)session.getAttribute("utenteLoggato");

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

            if(utente.getRuolo().equals(Ruolo.PROFESSIONISTA)){
                VeicoloDAO  veicoloDAO = new JdbcVeicoloDAO(DataSourceConfig.getDataSource());
                utente.setVeicoli(veicoloDAO.getVeicoliByEmailProfessionista(utente.getEmail()));
            }

            HttpSession session = request.getSession();
            session.setAttribute("utenteLoggato", utente);

            String destination = request.getContextPath() + "/" + Utility.getUserHomePage(utente);
            
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("last_visited_url".equals(c.getName())) {
                        // Se esiste il cookie, la destinazione diventa l'URL salvato
                        destination = java.net.URLDecoder.decode(c.getValue(), "UTF-8");
                        
                        // Eliminiamo il cookie
                        c.setMaxAge(0);
                        c.setPath("/");
                        response.addCookie(c);
                        break;
                    }
                }
            }

            // 3. Reindirizzamento finale
            response.sendRedirect(destination);

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
