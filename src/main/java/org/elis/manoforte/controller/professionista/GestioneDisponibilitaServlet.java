package org.elis.manoforte.controller.professionista;

import jakarta.servlet.ServletException;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import org.elis.manoforte.dao.definition.DisponibilitaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcDisponibilitaDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.model.TipoDisponibilita;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;

@WebServlet("/gestisciDisponibilita")
public class GestioneDisponibilitaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GestioneDisponibilitaServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        DisponibilitaDAO disponibilitaDAO = new JdbcDisponibilitaDAO(DataSourceConfig.getDataSource());
        UtenteDAO utenteDAO = new JdbcUtenteDAO(DataSourceConfig.getDataSource());
        Utente user = (Utente) request.getSession().getAttribute("utenteLoggato");

        if(action.equals("add")) {

            LocalDate data = null;
            DayOfWeek giorno = null;

            TipoDisponibilita tipo = TipoDisponibilita.valueOf(request.getParameter("tipo"));

            if(tipo.equals(TipoDisponibilita.RICORSIVO)){
                giorno = DayOfWeek.from(LocalDate.parse(request.getParameter("giorno")));
            }else if(tipo.equals(TipoDisponibilita.SINGOLO)){
                if(request.getParameter("data")!=null && !request.getParameter("data").equals(""))
                    data = LocalDate.parse(request.getParameter("data"));
            }


            LocalTime ora_inizio = LocalTime.parse(request.getParameter("ora_inizio"));
            LocalTime ora_fine = LocalTime.parse(request.getParameter("ora_fine"));


            try{
                long id = utenteDAO.trovaIdProfessionistaPerEmail(user.getEmail());

                Disponibilita disponibilita = new Disponibilita(
                        null, data, ora_inizio, ora_fine, id, tipo, giorno
                );

                disponibilitaDAO.inserisciDisponibilita(disponibilita);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if(action.equals("remove")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("H:mm");

            Long id_disponibilita = Long.parseLong(request.getParameter("id_disponibilita"));
            Boolean ricorrenza = request.getParameter("ricorrenza")!=null;
            LocalTime ora = LocalTime.parse(dtf.format(LocalTime.parse(request.getParameter("ora"), dtf2)));
            LocalDate data = LocalDate.parse(request.getParameter("data"));

            try{
                if(ricorrenza){
                    disponibilitaDAO.deleteDisponiblitaById(id_disponibilita);
                }else{
                    if(disponibilitaDAO.checkRicorrenzaById(id_disponibilita)){
                        long id = utenteDAO.trovaIdProfessionistaPerEmail(user.getEmail());
                        Disponibilita disponibilita = new Disponibilita(
                                null, data, ora, ora.plusHours(1), id, TipoDisponibilita.ECCEZIONE, null
                        );
                        disponibilitaDAO.inserisciDisponibilita(disponibilita);
                    }else{
                        disponibilitaDAO.removeDisponibilitaByDataOraEmail(data, ora, user.getEmail());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("homeprofessionista#availability");
        dispatcher.forward(request, response);
    }
}
