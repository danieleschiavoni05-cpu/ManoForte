package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class RichiestaServlet
 */
@WebServlet("/richiesta")
public class RichiestaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RichiestaServlet() {
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
		HttpSession session = request.getSession();
		Utente cliente = (Utente) session.getAttribute("utenteLoggato");
	

		// Recupero parametri dal form della pagina professionisti
		long idProfessionista = Long.parseLong(request.getParameter("idProfessionista"));
		String indirizzo = request.getParameter("indirizzo");

		// Prepariamo i dati per la nuova Richiesta
		Richiesta nuovaRichiesta = new Richiesta(
				null, // L'ID verrà generato dal DB (Auto-increment)
				LocalDate.now(), 
				LocalTime.now(), 
				LocalTime.now().plusHours(10), // Esempio: durata 1 ora
				indirizzo, 
				StatoRichiesta.IN_ATTESA_DI_CONFERMA, // Stato 1 = "Inviata/In Attesa"
				null, 
				idProfessionista
				);

		try {
			

			System.out.println("Richiesta salvata nel DB per il professionista ID: " + idProfessionista);

			// Reindirizziamo alla home dell'utente per vedere la lista aggiornata
			response.sendRedirect(request.getContextPath() + "/homeBase");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}