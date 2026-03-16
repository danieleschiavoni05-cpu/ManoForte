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
import java.util.List;

import org.elis.manoforte.dao.definition.DisponibilitaDAO;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcDisponibilitaDAO;
import org.elis.manoforte.dao.jdbc.JdbcProfessioneDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.RichiestaDAOJDBC;
import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;

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
		
		Utente utenteSessione = (Utente) request.getSession().getAttribute("utenteLoggato");
		
		String emailProfessionista = request.getParameter("emailPro");
		UtenteDAO utente= new JdbcUtenteDAO(DataSourceConfig.getDataSource());
		DisponibilitaDAO disponibilita=new JdbcDisponibilitaDAO(DataSourceConfig.getDataSource());
		
		
		System.out.println("Faccio la richiesta verso: [" + emailProfessionista + "]");
		ProfessioneDAO professionedao = new JdbcProfessioneDAO(DataSourceConfig.getDataSource());
        
		
        try {
            // Usiamo sempre il database, addio liste statiche "Database.utentiRegistrati"
            Utente risultato = utente.getUtentebyEmail(emailProfessionista);
            List<Disponibilita> disponibilitautente= disponibilita.findDisponibilitaByEmailProfessionista(emailProfessionista);
            
            System.out.println("Faccio la richiesta verso: [" + risultato.getNome() + "]");
            
            request.setAttribute("nomeProfessione", emailProfessionista);
            request.setAttribute("listaProfessionisti", risultato);
            request.setAttribute("disponibilita", disponibilita);
            request.setAttribute("DisponibiltaUtente", disponibilitautente);
            request.setAttribute("utenteLoggato", utenteSessione);
            

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Impossibile recuperare i professionisti.");
        }

        request.getRequestDispatcher("/WEB-INF/richiesta.jsp").forward(request, response);
    }
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente cliente = (Utente) session.getAttribute("utenteLoggato");
		 RichiestaDAO richiestaDao = new RichiestaDAOJDBC(DataSourceConfig.getDataSource());
		 UtenteDAO utenteDao = new JdbcUtenteDAO(DataSourceConfig.getDataSource());
		
		try {
			// Recupero parametri dal form della pagina professionisti
			String emailBase=request.getParameter("emailBase");
			String emailProfessionista=request.getParameter("emailProfessionista");
			long idCliente = utenteDao.trovaIdBasePerEmail(emailBase);
			long idProfessionista = utenteDao.trovaIdProfessionistaPerEmail( emailProfessionista);
			String indirizzo = request.getParameter("indirizzo");
			String descrizione = request.getParameter("descrizione");

			// Prepariamo i dati per la nuova Richiesta
			Richiesta nuovaRichiesta = new Richiesta(
					null, // L'ID verrà generato dal DB (Auto-increment)
					LocalDate.now(), 
					LocalTime.now(), 
					LocalTime.now().plusHours(1), 
					indirizzo, 
					StatoRichiesta.IN_ATTESA_DI_CONFERMA, 
					descrizione, 
					idProfessionista,
					idCliente
					);
			
			System.out.println("--- Dettagli Nuova Richiesta ---");
			System.out.println("Data: " + nuovaRichiesta.getData());
			System.out.println("Ora Inizio: " + nuovaRichiesta.getOra_inizio());
			System.out.println("Ora Fine: " + nuovaRichiesta.getOra_fine());
			System.out.println("Indirizzo: " + nuovaRichiesta.getIndirizzo());
			System.out.println("ID Cliente: " + nuovaRichiesta.getId_cliente());
			System.out.println("ID Professionista: " + nuovaRichiesta.getId_professionista());
			System.out.println("Descrizione: " + nuovaRichiesta.getDescrizione());
			System.out.println("--------------------------------");
			
			richiestaDao.inserisciRichiesta(nuovaRichiesta);
			
			System.out.println("Richiesta salvata con successo!");
			
			
			response.sendRedirect(request.getContextPath() + "/homeBase");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}