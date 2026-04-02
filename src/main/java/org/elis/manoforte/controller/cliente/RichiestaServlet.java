package org.elis.manoforte.controller.cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.elis.manoforte.dao.definition.*;
import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class RichiestaServlet
 */
@WebServlet("/richiesta")
public class RichiestaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProfessioneDAO professioneDao;
	UtenteDAO utenteDao;
	DisponibilitaDAO disponibilitaDao;
	RichiestaDAO richiestaDao;

	@Override
	public void init() throws ServletException{
		professioneDao = DaoFactory.getInstance().getProfessioneDAO();
		utenteDao = DaoFactory.getInstance().getUtenteDAO();
		disponibilitaDao = DaoFactory.getInstance().getDisponibilitaDAO();
		richiestaDao = DaoFactory.getInstance().getRichiestaDAO();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RichiestaServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Utente utenteSessione = (Utente) request.getSession().getAttribute("utenteLoggato");
		
		if(utenteSessione ==null) {
			String uri = request.getRequestURI();
		    String queryString = request.getQueryString();
		    String urlCompleto = (queryString == null) ? uri : uri + "?" + queryString;

		    // 2. Creiamo il Cookie (codificandolo in Base64 o URL-encoding per sicurezza)
		    String urlEncoded = java.net.URLEncoder.encode(urlCompleto, "UTF-8");
		    Cookie backUrlCookie = new Cookie("last_visited_url", urlEncoded);
		    
		    backUrlCookie.setMaxAge(600); // Scade dopo 10 minuti
		    backUrlCookie.setPath("/");   // Disponibile in tutto il sito
		    
		    response.addCookie(backUrlCookie);

		    // 3. Vai al Login
		    response.sendRedirect(request.getContextPath() +  "/login");
		    return;
		    
		    
		}else {
		
		String emailProfessionista = request.getParameter("emailPro");
		
		
		System.out.println("Faccio la richiesta verso: [" + emailProfessionista + "]");
        
		
        try {
            // Usiamo sempre il database, addio liste statiche "Database.utentiRegistrati"
            Utente risultato = utenteDao.getUtentebyEmail(emailProfessionista);
            List<Disponibilita> disponibilitaUtente= disponibilitaDao.findDisponibilitaByEmailProfessionista(emailProfessionista);
            
            System.out.println("Faccio la richiesta verso: [" + risultato.getNome() + "]");
            
            request.setAttribute("nomeProfessione", emailProfessionista);
            request.setAttribute("listaProfessionisti", risultato);
            request.setAttribute("DisponibiltaUtente", disponibilitaUtente);
            request.setAttribute("utenteLoggato", utenteSessione);
            

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Impossibile recuperare i professionisti.");
        }

        request.getRequestDispatcher("/WEB-INF/richiesta.jsp").forward(request, response);
    }
}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente cliente = (Utente) session.getAttribute("utenteLoggato");
		
		try {
			// Recupero parametri dal form della pagina professionisti
			String emailProfessionista=request.getParameter("emailProfessionista");
			Long idProfessionista = utenteDao.findIdByEmail( emailProfessionista);
			String indirizzo = request.getParameter("indirizzo");
			String descrizione = request.getParameter("descrizione");
			String ora_inizioString =request.getParameter("ora_inizio");
			String ora_fineString=request.getParameter("ora_fine");
			String giornoString=request.getParameter("giorni");
			LocalDate giorno=null;
			LocalDate giornoOggi=LocalDate.now(); 
			
			
			LocalTime ora_inizio = null;
		    LocalTime ora_fine = null;
			if (ora_inizioString != null && !ora_inizioString.isEmpty()) {
			     ora_inizio = LocalTime.parse(ora_inizioString);
			    // Ora puoi usare ora_inizio come oggetto LocalTime
			}
			if (ora_fineString != null && !ora_fineString.isEmpty()) {
			     ora_fine = LocalTime.parse(ora_fineString);
			    // Ora puoi usare ora_inizio come oggetto LocalTime
			}
			
			if (ora_inizio != null && ora_fine != null) {
			    // Verifichiamo che i minuti siano 00 o 30
			    if (ora_inizio.getMinute() % 30 != 0 || ora_fine.getMinute() % 30 != 0) {
			        throw new Exception("L'orario deve essere a intervalli di 30 minuti.");
			    }
			    
			    // Bonus: Verifica che l'ora di fine sia dopo l'ora di inizio
			    if (!ora_fine.isAfter(ora_inizio)) {
			        throw new Exception("L'orario di fine deve essere successivo a quello di inizio.");
			    }
			}
			
			if (giornoString != null && !giornoString.isEmpty()) {
			    giorno = LocalDate.parse(giornoString); // Converte la stringa "yyyy-MM-dd"
			    
			    // Controllo sicurezza: la data non deve essere antecedente a oggi
			    if (giorno.isBefore(giornoOggi)) {
			        throw new Exception("Non puoi richiedere un intervento per una data passata!");
			    }
			} else {
			    // Se non viene scelta una data, potresti voler usare oggi come default
			    giorno = giornoOggi; 
			}

			Utente professionista = utenteDao.getUtentebyEmail(emailProfessionista);

			// Prepariamo i dati per la nuova Richiesta
			Richiesta nuovaRichiesta = new Richiesta(
					null, // L'ID verrà generato dal DB (Auto-increment)
					giorno, 
					ora_inizio,
					ora_fine,
					indirizzo, 
					StatoRichiesta.IN_ATTESA_DI_CONFERMA, 
					descrizione,
					cliente,
					professionista
					);
			
			System.out.println("--- Dettagli Nuova Richiesta ---");
			System.out.println("Data: " + nuovaRichiesta.getData());
			System.out.println("Ora Inizio: " + nuovaRichiesta.getOra_inizio());
			System.out.println("Ora Fine: " + nuovaRichiesta.getOra_fine());
			System.out.println("Indirizzo: " + nuovaRichiesta.getIndirizzo());
			System.out.println("ID Cliente: " + nuovaRichiesta.getCliente());
			System.out.println("ID Professionista: " + nuovaRichiesta.getProfessionista());
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