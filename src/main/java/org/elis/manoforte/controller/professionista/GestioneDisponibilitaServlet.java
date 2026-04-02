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

// Import dei DAO e Model
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.DisponibilitaDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.TipoDisponibilita;
import org.elis.manoforte.model.Utente;

/**
 * Servlet che gestisce le operazioni di Aggiunta e Rimozione delle disponibilità
 * orarie del professionista. Risponde all'URL /gestisciDisponibilita.
 */
@WebServlet("/gestisciDisponibilita")
public class GestioneDisponibilitaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DisponibilitaDAO disponibilitaDao;
    private UtenteDAO utenteDao;
    private RichiestaDAO richiestaDao;

    public GestioneDisponibilitaServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        disponibilitaDao = DaoFactory.getInstance().getDisponibilitaDAO();
        utenteDao = DaoFactory.getInstance().getUtenteDAO();
        richiestaDao = DaoFactory.getInstance().getRichiestaDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("");
        dispatcher.forward(request, response);
    }

    /**
     * Gestisce le richieste POST (Aggiunta o Rimozione disponibilità).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera l'azione richiesta ("add" o "remove")
        String action = request.getParameter("action");
        Utente utente = (Utente) request.getSession().getAttribute("utenteLoggato");

        if(action.equals("add")){
            String eccezione = request.getParameter("checkRicorsivo");
            LocalDate giorno = LocalDate.parse(request.getParameter("data"));
            LocalTime inizio = LocalTime.parse(request.getParameter("ora_inizio"));
            LocalTime fine = LocalTime.parse(request.getParameter("ora_fine"));


            Disponibilita newDisp = new Disponibilita();
            newDisp.setOra_inizio(inizio);
            newDisp.setOra_fine(fine);
            newDisp.setUtente(utente);

            if(eccezione!=null){
                newDisp.setGiorno_settimana(giorno.getDayOfWeek());
                newDisp.setTipo(TipoDisponibilita.RICORSIVO);
                newDisp.setData(null);
                Boolean checkEdit = false;

                try {

                    Disponibilita db = disponibilitaDao.checkSovrapposizione(newDisp, utente.getEmail());

                    if(db!=null){
                        if(newDisp.getOra_inizio().isBefore(db.getOra_inizio())){
                            db.setOra_inizio(newDisp.getOra_inizio());
                            checkEdit = true;
                        }

                        if(newDisp.getOra_fine().isAfter(db.getOra_fine())){
                            db.setOra_fine(newDisp.getOra_fine());
                            checkEdit = true;
                        }

                        if(checkEdit){
                            disponibilitaDao.updateDisponibilitaById(db);
                        }else{
                            disponibilitaDao.inserisciDisponibilita(newDisp);
                        }
                    }else disponibilitaDao.inserisciDisponibilita(newDisp);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                String tipo = request.getParameter("tipo_inserimento");

                newDisp.setData(giorno);
                newDisp.setGiorno_settimana(null);
                newDisp.setTipo((tipo.equals("SINGOLO")?TipoDisponibilita.SINGOLO:TipoDisponibilita.ECCEZIONE));

                try{

                    if(richiestaDao.checkDisponibilitaByOra(newDisp)){
                        Disponibilita db = disponibilitaDao.checkSovrapposizione(newDisp, utente.getEmail());
                        if(db!=null&&db.getTipo().equals(newDisp.getTipo())){
                            LocalTime dbInizio = db.getOra_inizio();
                            LocalTime dbFine = db.getOra_fine();
                            Boolean checkEdit = false;

                            if(inizio.isBefore(dbInizio)){
                                db.setOra_inizio(inizio);
                                checkEdit = true;
                            }
                            if(fine.isAfter(dbFine)){
                                db.setOra_fine(fine);
                                checkEdit = true;
                            }

                            if(checkEdit) disponibilitaDao.updateDisponibilitaById(db);

                        }else disponibilitaDao.inserisciDisponibilita(newDisp);

                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }else{
            try{
                Disponibilita disp = disponibilitaDao.findDisponibilitaById(Long.parseLong(request.getParameter("id_disponibilita")));
                if(richiestaDao.checkDisponibilitaByOra(disp)){
                    disponibilitaDao.deleteDisponiblitaById(disp.getId());
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        response.sendRedirect("homeprofessionista#availability");
    }
}
