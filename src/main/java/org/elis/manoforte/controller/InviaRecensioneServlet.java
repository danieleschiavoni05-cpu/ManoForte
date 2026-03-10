package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.time.LocalDate;

import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class InviaRecensioneServlet
 */
@WebServlet("/InviaRecensione")
public class InviaRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InviaRecensioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		Utente utenteLoggato=(Utente) session.getAttribute("utenteLoggato");
		
        Long id = Long.parseLong(request.getParameter("idRichiesta"));
        String descrizione = request.getParameter("descrizione");
        int voto = Integer.parseInt(request.getParameter("voto"));
        LocalDate data = LocalDate.parse(request.getParameter("campoData"));
        Long id_cliente= Long.parseLong(request.getParameter("id_cliente"));
        Long id_professionista= Long.parseLong(request.getParameter("id_professionista"));

        // Trova la richiesta
        Richiesta richiesta = null;
        for(Richiesta r : Database.richieste){
            if(r.getId() == id){
                richiesta = r;
                break;
            }
        }

        if (richiesta != null && richiesta.getStatoRichiesta().equals(StatoRichiesta.COMPLETA)) { 
            
            
            Recensione rec = new Recensione(id, descrizione, voto, data, id_cliente, id_professionista);
           
            Database.recensione.add(rec); 
            
            // 2. Aggiunta alla lista dell'utente professionista
            // ASSICURATI che getId_professionista() restituisca un oggetto Utente e non solo un Long
            if (richiesta.getIdProfessionista() != 0) {
                richiesta.getIdProfessionista().getRecensioni().add(rec);
            }
            
            // 3. Persistenza su DB reale
            RecensioneDao.inserisciRecensione(rec);
        }

        response.sendRedirect(request.getContextPath() + "/HomeServlet");
	}

}
