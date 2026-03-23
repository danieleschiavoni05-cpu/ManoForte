package org.elis.manoforte.controller.professionista;

import jakarta.servlet.ServletException;

import java.io.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import org.elis.manoforte.dao.definition.DisponibilitaDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcDisponibilitaDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.RichiestaDAOJDBC;
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
        RichiestaDAO richiestaDAO = new RichiestaDAOJDBC(DataSourceConfig.getDataSource());
        Utente user = (Utente) request.getSession().getAttribute("utenteLoggato");

        if(action.equals("add")) {

            LocalDate data = null;
            DayOfWeek giorno = null;

            TipoDisponibilita tipo = TipoDisponibilita.valueOf(request.getParameter("tipo"));
            String tipo_inserimento = request.getParameter("tipo_inserimento");

            if(tipo.equals(TipoDisponibilita.RICORSIVO)){
                giorno = DayOfWeek.from(LocalDate.parse(request.getParameter("giorno")));
            }else if(tipo.equals(TipoDisponibilita.SINGOLO)){
                if(request.getParameter("data")!=null && !request.getParameter("data").equals(""))
                    data = LocalDate.parse(request.getParameter("data"));
            }


            LocalTime ora_inizio = LocalTime.parse(request.getParameter("ora_inizio"));
            LocalTime ora_fine = LocalTime.parse(request.getParameter("ora_fine"));


            try {
                long id = utenteDAO.trovaIdProfessionistaPerEmail(user.getEmail());
                if(tipo.equals(TipoDisponibilita.SINGOLO) && tipo_inserimento.equals("ECCEZIONE")) {
                    tipo = TipoDisponibilita.ECCEZIONE;
                }

                Disponibilita disponibilita = new Disponibilita(
                        null, data, ora_inizio, ora_fine, id, tipo, giorno
                );

                if(tipo_inserimento.equals("ECCEZIONE") || tipo.equals(TipoDisponibilita.RICORSIVO)) {
                    Disponibilita db = disponibilitaDAO.checkSovrapposizione(disponibilita, user.getEmail());
                    if(db!=null&&db.getTipo().equals(disponibilita.getTipo())) {
                        Disponibilita union = new Disponibilita();
                        if(db.getOra_inizio().isBefore(disponibilita.getOra_inizio())) {
                            union.setOra_inizio(db.getOra_inizio());
                        }else union.setOra_inizio(disponibilita.getOra_inizio());

                        if(db.getOra_fine().isAfter(disponibilita.getOra_fine())) {
                            union.setOra_fine(db.getOra_fine());
                        }else union.setOra_fine(disponibilita.getOra_fine());

                        union.setData(db.getData());
                        union.setTipo(db.getTipo());
                        union.setId_utente(db.getId_utente());
                        union.setGiorno_settimana(giorno);

                        disponibilitaDAO.updateDisponibilitaById(union, db.getId());

                    }else disponibilitaDAO.inserisciDisponibilita(disponibilita);

                }else if(tipo_inserimento.equals("SINGOLO")) {
                    Disponibilita db = disponibilitaDAO.checkSovrapposizione(disponibilita, user.getEmail());
                    if(db!=null) {
                        Disponibilita union = new Disponibilita();

                        union.setData(db.getData());
                        union.setTipo(disponibilita.getTipo());
                        union.setId_utente(db.getId_utente());

                        LocalTime dbInizio = db.getOra_inizio();
                        LocalTime dbFine = db.getOra_fine();
                        LocalTime inizioDisp = disponibilita.getOra_inizio();
                        LocalTime fineDisp = disponibilita.getOra_fine();

                        if(!inizioDisp.isBefore(dbInizio)&&!fineDisp.isAfter(dbFine)) { // Dentro
                            response.sendRedirect("homeprofessionista#availability");
                            return;
                        }else if(inizioDisp.isBefore(dbInizio)&&!fineDisp.isAfter(dbFine)) { // Sx
                            union.setOra_inizio(inizioDisp);
                            union.setOra_fine(dbInizio);
                            disponibilitaDAO.inserisciDisponibilita(union);
                        }else if(!inizioDisp.isBefore(dbInizio)) { //Dx
                            union.setOra_inizio(dbFine);
                            union.setOra_fine(fineDisp);
                            disponibilitaDAO.inserisciDisponibilita(union);
                        }else{
                            Disponibilita prima = new Disponibilita(null, data, inizioDisp, dbInizio, id, tipo, giorno);
                            disponibilitaDAO.inserisciDisponibilita(prima);
                            Disponibilita dopo = new Disponibilita(null, data, dbFine, fineDisp, id, tipo, giorno);
                            disponibilitaDAO.inserisciDisponibilita(dopo);
                        }

                    }else disponibilitaDAO.inserisciDisponibilita(disponibilita);
                }

            }catch(SQLException e){
                e.printStackTrace();
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
                Disponibilita disp = disponibilitaDAO.findDisponibilitaById(id_disponibilita);
                if(!richiestaDAO.checkRequestByOra(disp)){
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
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }


        response.sendRedirect("homeprofessionista#availability");
    }
}
