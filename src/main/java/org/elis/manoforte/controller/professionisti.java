package org.elis.manoforte.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;

import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.model.Utente;


import org.elis.manoforte.utility.MediaVoti;

/**
 * Servlet implementation class professionisti
 */
@WebServlet("/professionisti")
public class professionisti extends HttpServlet {
	private static final long serialVersionUID = 1L;

    UtenteDAO utenteDao;
    ProfessioneDAO professioneDao;

    public void init()throws ServletException{
        utenteDao = DaoFactory.getInstance().getUtenteDAO();
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public professionisti() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeProfessione = request.getParameter("nome");

		
		System.out.println("Cerco professionisti per: [" + nomeProfessione + "]");
		
		
        try {
            // Usiamo sempre il database, addio liste statiche "Database.utentiRegistrati"

            List<Utente> risultato = utenteDao.findAllProfessionistibyProfessione(nomeProfessione);
            List<Professione> professioni= professioneDao.getAllProfessioni();
             List<Disponibilita> disponibilitaPro=disponibilita.findDisponibilitaByData(LocalDate.now());
            List<Citta> citta = cittaDao.getAllCitta();
            
            request.setAttribute("listaCitta", citta);




    	System.out.println("Cerco professionisti per: [" + nomeProfessione + "]");


    	try {
    		// Usiamo sempre il database, addio liste statiche "Database.utentiRegistrati"
    		List<Utente> risultato = utente.findAllProfessionistibyProfessione(nomeProfessione);
    		List<Professione> professioni= professione.getAllProfessioni();
    		List<Disponibilita> disponibilitaPro=disponibilita.findDisponibilitaByData(LocalDate.now());
    		List<Citta> citta = cittaDao.getAllCitta();


    		Map<String, Double> mappaMedie = new HashMap<>();

    		for (Utente p : risultato) {
    		    String emailPro = p.getEmail();
    		    
    		    // Recuperiamo l'ID del professionista tramite l'email per fare la query delle recensioni
    		    long idProfessionista = utente.trovaIdProfessionistaPerEmail(emailPro);
    		    
    		    List<Recensione> recensioniPro = recensioneDao.findByIdProfessionista(idProfessionista);
    		    double media = MediaVoti.calcolaMedia(recensioniPro);
    		    
    		    // Salviamo la media associata all'email
    		    mappaMedie.put(emailPro, media);
    		}

    		request.setAttribute("listaCitta", citta);
    		request.setAttribute("mappaMedie", mappaMedie);
    		request.setAttribute("nomeProfessione", nomeProfessione);
    		request.setAttribute("listaProfessioni", professioni);
    		request.setAttribute("listaProfessionisti", risultato);
    		request.setAttribute("disponibilita", disponibilitaPro);

    	} catch (Exception e) {
    		e.printStackTrace();
    		request.setAttribute("errore", "Impossibile recuperare i professionisti.");
    	}

    	request.getRequestDispatcher("/WEB-INF/professionisti.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	doGet(request, response);
    }

}
