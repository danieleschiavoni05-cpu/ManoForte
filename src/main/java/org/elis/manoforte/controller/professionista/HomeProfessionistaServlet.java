package org.elis.manoforte.controller.professionista;

import jakarta.servlet.ServletException;

import java.io.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import org.elis.manoforte.dao.definition.*;
import org.elis.manoforte.exception.NessunValoreTrovatoException;
import org.elis.manoforte.model.*;
import org.elis.manoforte.utility.Utility;

@WebServlet("/homeprofessionista")
public class HomeProfessionistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RichiestaDAO richiestaDao;
    private RecensioneDAO recensioneDao;
    private  DisponibilitaDAO disponibilitaDao;
    private CittaDAO cittaDao;
    private VeicoloDAO veicoloDao;

    public HomeProfessionistaServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        richiestaDao = DaoFactory.getInstance().getRichiestaDAO();
        recensioneDao = DaoFactory.getInstance().getRecensioneDAO();
        cittaDao = DaoFactory.getInstance().getCittaDAO();
        veicoloDao = DaoFactory.getInstance().getVeicoloDAO();
        disponibilitaDao = DaoFactory.getInstance().getDisponibilitaDAO();
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

        try{
            List<CardRichiesta> richiesteInAttesa = richiestaDao.getRichiesteByEmailProfessionistaAndStato(loggedUser.getEmail(), StatoRichiesta.IN_ATTESA_DI_CONFERMA);
            List<CardRichiesta> richiesteInCorso = richiestaDao.getRichiesteByEmailProfessionistaAndStato(loggedUser.getEmail(), StatoRichiesta.IN_CORSO);
            List<CardRichiesta> richiesteComplete = richiestaDao.getRichiesteByEmailProfessionistaAndStato(loggedUser.getEmail(), StatoRichiesta.COMPLETA);

            List<CardRecensione> recensioni = recensioneDao.getRecensioneByEmailProfessionistaLimit(loggedUser.getEmail(), 4);

            List<Citta> citta = cittaDao.getAllCitta();
            List<Veicolo> veicolo = veicoloDao.getAllVeicolo();

            List<Disponibilita> disponibilita = disponibilitaDao.findDisponibilitaByEmailProfessionistaAndTipo(loggedUser.getEmail(), TipoDisponibilita.SINGOLO);
            Map<LocalDate, List<Disponibilita>> disponibilitaSingole = disponibilita.stream()
                    .collect(Collectors.groupingBy(d -> d.getData()));

            disponibilita = disponibilitaDao.findDisponibilitaByEmailProfessionistaAndTipo(loggedUser.getEmail(), TipoDisponibilita.RICORSIVO);
            Map<DayOfWeek, List<Disponibilita>> disponibilitaRicorsive = disponibilita.stream()
                    .collect(Collectors.groupingBy(d -> d.getGiorno_settimana()));

            disponibilita = disponibilitaDao.findDisponibilitaByEmailProfessionistaAndTipo(loggedUser.getEmail(), TipoDisponibilita.ECCEZIONE);
            Map<LocalDate, List<Disponibilita>> disponibilitaEccezioni = disponibilita.stream()
                    .collect(Collectors.groupingBy(d -> d.getData()));

            List<Richiesta> richieste = richiestaDao.getRichiesteListByEmailProfessionistaAndStato(loggedUser.getEmail(), StatoRichiesta.IN_CORSO);
            Map<LocalDate, List<Richiesta>> richiesteRicevute = richieste.stream()
                    .collect(Collectors.groupingBy(r -> r.getData()));

            // Attibuti per le mie richieste
            request.setAttribute("richiesteInAttesa", richiesteInAttesa);
            request.setAttribute("richiesteInCorso", richiesteInCorso);
            request.setAttribute("richiesteComplete", richiesteComplete);
            // Atrributi per le mie recensioni
            request.setAttribute("recensioni", recensioni);
            // Attributi per la modifica del profilo
            request.setAttribute("citta", citta);
            request.setAttribute("veicoli", veicolo);
            // Attributi disponibilità
            request.setAttribute("disponibilitaSingole", disponibilitaSingole);
            request.setAttribute("disponibilitaRicorrenti", disponibilitaRicorsive);
            request.setAttribute("disponibilitaEccezioni", disponibilitaEccezioni);
            request.setAttribute("richiesteRicevute", richiesteRicevute);

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
