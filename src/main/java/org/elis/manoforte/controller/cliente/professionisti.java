package org.elis.manoforte.controller.cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.definition.CittaDAO; // Assicurati che esista
import org.elis.manoforte.dao.definition.DisponibilitaDAO; // Assicurati che esista

import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.utility.Utility;

@WebServlet("/professionisti")
public class professionisti extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UtenteDAO utenteDao;
    private ProfessioneDAO professioneDao;
    private RecensioneDAO recensioneDao;
    private CittaDAO cittaDao;
    private DisponibilitaDAO disponibilitaDao;

    @Override
    public void init() throws ServletException {
        DaoFactory factory = DaoFactory.getInstance();
        utenteDao = factory.getUtenteDAO();
        professioneDao = factory.getProfessioneDAO();
        recensioneDao = factory.getRecensioneDAO();
        cittaDao = factory.getCittaDAO();
        disponibilitaDao = factory.getDisponibilitaDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeProfessione = request.getParameter("nome");
        
        try {
            List<Utente> professionistiFiltrati = utenteDao.findAllProfessionistibyProfessione(nomeProfessione);
            List<Professione> tutteLeProfessioni = professioneDao.getAllProfessioni();
            List<Citta> tutteLeCitta = cittaDao.getAllCitta();
            List<Disponibilita> disponibilitaOggi = disponibilitaDao.findDisponibilitaByData(LocalDate.now());

            Map<String, BigDecimal> mappaMedie = new HashMap<>();

            for (Utente p : professionistiFiltrati) {
                String emailPro = p.getEmail();
                long idPro = utenteDao.findIdByEmail(emailPro);
                
                List<Recensione> recensioni = recensioneDao.findByIdProfessionista(idPro);
                
                mappaMedie.put(emailPro, Utility.calcolaMedia(recensioni));
            }

            request.setAttribute("nomeProfessione", nomeProfessione);
            request.setAttribute("listaProfessionisti", professionistiFiltrati);
            request.setAttribute("listaProfessioni", tutteLeProfessioni);
            request.setAttribute("listaCitta", tutteLeCitta);
            request.setAttribute("mappaMedie", mappaMedie);
            request.setAttribute("disponibilita", disponibilitaOggi);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore nel caricamento dei dati.");
        }

        request.getRequestDispatcher("/WEB-INF/cliente/professionisti.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
