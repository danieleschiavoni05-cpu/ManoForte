package org.elis.manoforte.controller.cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elis.manoforte.dao.definition.*;


import org.elis.manoforte.exception.NoImageException;
import org.elis.manoforte.model.*;

import org.elis.manoforte.utility.Utility;

@WebServlet("/professionisti")
public class professionisti extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UtenteDAO utenteDao;
    private ProfessioneDAO professioneDao;
    private RecensioneDAO recensioneDao;
    private CittaDAO cittaDao;
    private ImmagineDAO immagineDao;
   

    @Override
    public void init() throws ServletException {
        DaoFactory factory = DaoFactory.getInstance();
        utenteDao = factory.getUtenteDAO();
        professioneDao = factory.getProfessioneDAO();
        recensioneDao = factory.getRecensioneDAO();
        cittaDao = factory.getCittaDAO();
        immagineDao = factory.getImmagineDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeProfessione = request.getParameter("nome");
        
        try {
            List<Utente> professionisti = utenteDao.findAllProfessionisti();
            List<Professione> tutteLeProfessioni = professioneDao.getAllProfessioni();
            List<Citta> tutteLeCitta = cittaDao.getAllCitta();
            Map<String, BigDecimal> mappaMedie = new HashMap<>();
            Map<Long, String> propics = new HashMap<>();

            for (Utente p : professionisti) {
                String emailPro = p.getEmail();
                long idPro = utenteDao.findIdByEmail(emailPro);

                Immagine propic;
                try{
                    propic = immagineDao.getImmagineByIdUtente(p.getId());
                }catch(NoImageException e){
                    propic = null;
                }

                List<Professione> professioniDiQuestoUtente = professioneDao.findProfessioniByIdProfessionista(idPro);
                p.setProfessione(professioniDiQuestoUtente); 
                // -----------------------------------------------------------------

                List<Recensione> recensioni = recensioneDao.findByIdProfessionista(idPro);
                mappaMedie.put(emailPro, Utility.calcolaMedia(recensioni));
                propics.put(idPro, propic==null?null:propic.getPercorso());
            }

            request.setAttribute("nomeProfessione", nomeProfessione);
            request.setAttribute("listaProfessionisti", professionisti);
            request.setAttribute("listaProfessioni", tutteLeProfessioni); 
            request.setAttribute("listaCitta", tutteLeCitta);
            request.setAttribute("mappaMedie", mappaMedie);
            request.setAttribute("propics", propics);
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
