package org.elis.manoforte.controller.cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;

import org.elis.manoforte.dao.definition.UtenteDAO;


import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Utente;


/**
 * Servlet implementation class ricercaProfessioniServlet
 */
@WebServlet("/ricercaProfessioni")
public class RicercaProfessioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UtenteDAO utenteDao;
    private ProfessioneDAO professioneDao;

    @Override
    public void init() throws ServletException{
        utenteDao = DaoFactory.getInstance().getUtenteDAO(); 
        professioneDao =DaoFactory.getInstance().getProfessioneDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String nomeProfessione = request.getParameter("cercaNome");
        
        
        try {
            List<Professione> listaDaMostrare;
            List<Utente> professionisti = new ArrayList<>();

            // 1. CASO RICERCA: L'utente ha scritto qualcosa
            if (nomeProfessione != null && !nomeProfessione.trim().isEmpty()) {
                // Usiamo il metodo che restituisce la lista filtrata
                listaDaMostrare = professioneDao.findProfessioniByName(nomeProfessione.trim());
                
                // Se abbiamo trovato almeno una professione, prendiamo i professionisti della prima trovata
                if (!listaDaMostrare.isEmpty()) {
                    professionisti = utenteDao.findAllProfessionistibyProfessione(listaDaMostrare.get(0).getNome());
                }
                request.setAttribute("nomeCercato", nomeProfessione);
            } 
            // 2. CASO DEFAULT: Mostriamo tutto (es. quando apri la pagina per la prima volta)
            else {
                listaDaMostrare = professioneDao.getAllProfessioni();
                // Opzionale: se vuoi mostrare TUTTI i professionisti di tutte le categorie
                // professionisti = utenteDao.findAllProfessionisti(); 
            }

            // Passiamo i dati alla JSP
            request.setAttribute("listaProfessioni", listaDaMostrare);
            request.setAttribute("listaProfessionisti", professionisti);
            request.setAttribute("nomeProfessione", nomeProfessione);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore nel caricamento dei dati.");
        }

        request.getRequestDispatcher("/WEB-INF/pagina_ricerca.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
