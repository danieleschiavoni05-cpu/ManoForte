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
        
        // Recupera l'utente loggato dalla sessione
        Utente user = (Utente) request.getSession().getAttribute("utenteLoggato");

        // -----------------------------------------------------------
        // LOGICA DI AGGIUNTA DISPONIBILITÀ (Action = "add")
        // -----------------------------------------------------------
        if(action != null && action.equals("add")) {

            LocalDate data = null;
            DayOfWeek giorno = null;

            // Recupera i parametri dal form
            // Tipo: RICORSIVO (ogni settimana) o SINGOLO (una data specifica)
            TipoDisponibilita tipo = TipoDisponibilita.valueOf(request.getParameter("tipo"));
            // Tipo Inserimento: SINGOLO o ECCEZIONE (sembra ridondante o specifica ulteriore logica)
            String tipo_inserimento = request.getParameter("tipo_inserimento");

            // Parsing della data o del giorno della settimana in base al tipo
            if(tipo.equals(TipoDisponibilita.RICORSIVO)){
                // Se ricorsivo, ci aspettiamo un giorno della settimana (es. "MONDAY")
                giorno = DayOfWeek.valueOf(request.getParameter("giorno").toUpperCase());
            } else if(tipo.equals(TipoDisponibilita.SINGOLO)){
                // Se singolo, ci aspettiamo una data specifica (YYYY-MM-DD)
                if(request.getParameter("data") != null && !request.getParameter("data").equals(""))
                    data = LocalDate.parse(request.getParameter("data"));
            }

            // Parsing degli orari di inizio e fine
            LocalTime ora_inizio = LocalTime.parse(request.getParameter("ora_inizio"));
            LocalTime ora_fine = LocalTime.parse(request.getParameter("ora_fine"));

            try {
                // Se è un inserimento SINGOLO marcato come ECCEZIONE, cambia il tipo
                if(tipo.equals(TipoDisponibilita.SINGOLO) && tipo_inserimento.equals("ECCEZIONE")) {
                    tipo = TipoDisponibilita.ECCEZIONE;
                }

                // Crea l'oggetto Disponibilita da inserire
                Disponibilita disponibilita = new Disponibilita(
                        null, data, ora_inizio, ora_fine, user, tipo, giorno
                );

                // --- GESTIONE SOVRAPPOSIZIONI E UNIONI ---
                
                // CASO 1: Inserimento RICORSIVO
                if(tipo.equals(TipoDisponibilita.RICORSIVO)) {
                    // Per le disponibilità ricorsive, la data è sempre null
                    disponibilita.setData(null);
                    
                    Disponibilita db = disponibilitaDao.checkSovrapposizione(disponibilita, user.getEmail());
                    
                    if(db != null && db.getTipo().equals(TipoDisponibilita.RICORSIVO)) {
                        // Se esiste già una disp. ricorsiva per quel giorno, unisci gli orari
                        if(disponibilita.getOra_inizio().isBefore(db.getOra_inizio())) {
                            db.setOra_inizio(disponibilita.getOra_inizio());
                        }
                        if(disponibilita.getOra_fine().isAfter(db.getOra_fine())) {
                            db.setOra_fine(disponibilita.getOra_fine());
                        }
                        disponibilitaDao.updateDisponibilitaById(db);
                    } else {
                        // Altrimenti, inserisci la nuova disponibilità ricorsiva
                        disponibilitaDao.inserisciDisponibilita(disponibilita);
                    }

                // CASO 2: Inserimento SINGOLO (Non eccezione)
                } else if(tipo_inserimento.equals("SINGOLO")) {
                    
                    // Controlla sovrapposizioni
                    Disponibilita db = disponibilitaDao.checkSovrapposizione(disponibilita, user.getEmail());
                    
                    if(db != null) {
                        // Logica complessa di suddivisione/intersezione intervalli
                        // Sembra gestire il caso in cui la nuova disp sia "dentro", a "sinistra" o a "destra" di quella esistente

                        Disponibilita union = new Disponibilita();
                        union.setData(db.getData());
                        union.setTipo(disponibilita.getTipo());
                        union.setUtente(db.getUtente());

                        LocalTime dbInizio = db.getOra_inizio();
                        LocalTime dbFine = db.getOra_fine();
                        LocalTime inizioDisp = disponibilita.getOra_inizio();
                        LocalTime fineDisp = disponibilita.getOra_fine();

                        if(!inizioDisp.isBefore(dbInizio) && !fineDisp.isAfter(dbFine)) {
                            // Caso: La nuova disponibilità è completamente INCLUSA in quella esistente.
                            // Non fare nulla, l'utente è già disponibile.
                            response.sendRedirect("homeprofessionista#availability");
                            return;

                        } else if(inizioDisp.isBefore(dbInizio) && !fineDisp.isAfter(dbFine)) {
                            // Caso: La nuova inizia PRIMA e finisce DENTRO quella esistente.
                            // Crea disponibilità per la parte "mancante" a sinistra.
                            union.setOra_inizio(inizioDisp);
                            union.setOra_fine(dbInizio);
                            disponibilitaDao.inserisciDisponibilita(union);

                        } else if(!inizioDisp.isBefore(dbInizio)) {
                            // Caso: La nuova inizia DENTRO e finisce DOPO quella esistente.
                            // Crea disponibilità per la parte "mancante" a destra.
                            union.setOra_inizio(dbFine);
                            union.setOra_fine(fineDisp);
                            disponibilitaDao.inserisciDisponibilita(union);

                        } else {
                            // Caso: La nuova INGLOBA completamente quella esistente (inizia prima e finisce dopo).
                            // Crea due disponibilità: una prima e una dopo quella esistente.
                            Disponibilita prima = new Disponibilita(null, data, inizioDisp, dbInizio, user, tipo, giorno);
                            disponibilitaDao.inserisciDisponibilita(prima);

                            Disponibilita dopo = new Disponibilita(null, data, dbFine, fineDisp, user, tipo, giorno);
                            disponibilitaDao.inserisciDisponibilita(dopo);
                        }
                        disponibilitaDao.updateDisponibilitaById(db);

                    } else {
                        // Nessuna sovrapposizione -> Inserisci nuova disponibilità singola
                        disponibilitaDao.inserisciDisponibilita(disponibilita);
                    }
                }

            } catch(SQLException e){
                e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
            }
            
        // -----------------------------------------------------------
        // LOGICA DI RIMOZIONE DISPONIBILITÀ (Action = "remove")
        // -----------------------------------------------------------
        } else if(action != null && action.equals("remove")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("H:mm");

            // Recupera parametri
            Long id_disponibilita = Long.parseLong(request.getParameter("id_disponibilita"));
            Boolean ricorrenza = request.getParameter("ricorrenza") != null; // Checkbox o param
            
            // Parsing orari un po' contorto (formatta e riparsa) per gestire formati diversi?
            LocalTime ora = LocalTime.parse(dtf.format(LocalTime.parse(request.getParameter("ora"), dtf2)));
            LocalDate data = LocalDate.parse(request.getParameter("data"));

            try{
                Disponibilita disp = disponibilitaDao.findDisponibilitaById(id_disponibilita);
                
                // Controlla se c'è una richiesta attiva in quell'ora prima di rimuovere
                if(!richiestaDao.checkDisponibilitaByOra(disp)){

                    if(ricorrenza){
                        // Elimina l'intera regola ricorrente
                        disponibilitaDao.deleteDisponiblitaById(id_disponibilita);
                    } else {
                        // Se la disponibilità originale era RICORRENTE, crea un'ECCEZIONE
                        if(disp.getTipo().equals(TipoDisponibilita.RICORSIVO)){
                            Disponibilita disponibilita = new Disponibilita(
                                    null, data, ora, ora.plusHours(1), user, TipoDisponibilita.ECCEZIONE, null
                            );
                            disponibilitaDao.inserisciDisponibilita(disponibilita);
                        } else {
                            // Se era una disponibilità singola, rimuovila semplicemente
                            disponibilitaDao.removeDisponibilitaByDataOraEmail(data, ora, user.getEmail());
                        }
                    }
                }

            } catch(Exception e){
                e.printStackTrace();
            }
        }

        // Redirect finale alla pagina del professionista (tab availability)
        response.sendRedirect("homeprofessionista#availability");
    }
}
