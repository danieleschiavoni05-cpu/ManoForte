package org.elis.manoforte.controller.cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.elis.manoforte.dao.definition.*;
import org.elis.manoforte.model.Disponibilita;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.dto.DTOGenericResponse;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

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
		    // 1. Recupero i dati dal DB
		    Utente risultato = utenteDao.getUtentebyEmail(emailProfessionista);
		    List<Disponibilita> disponibilitaUtente = disponibilitaDao.findDisponibilitaByEmailProfessionista(emailProfessionista);
		    
		    // DEBUG: Controlla in console se i dati arrivano davvero
		    System.out.println("Professionista trovato: " + (risultato != null ? risultato.getNome() : "NULL"));
		    System.out.println("Disponibilità trovate: " + (disponibilitaUtente != null ? disponibilitaUtente.size() : "0"));

		    // 2. SETTO GLI ATTRIBUTI (Usa questi nomi esatti per far funzionare la JSP)
		    request.setAttribute("professionista", risultato); 
		    request.setAttribute("listaDisponibilita", disponibilitaUtente);
		    request.setAttribute("utenteLoggato", utenteSessione); // Assicurati che utenteSessione non sia null

		} catch (Exception e) {
		    e.printStackTrace();
		    request.setAttribute("errore", "Impossibile recuperare i dati.");
		}

        request.getRequestDispatcher("/WEB-INF/cliente/richiesta.jsp").forward(request, response);
    }
}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter outJson = response.getWriter();
		ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

		HttpSession session = request.getSession();
		Utente cliente = (Utente) session.getAttribute("utenteLoggato");

		try {
			// Recupero parametri dal form della pagina professionisti
			String emailProfessionista=request.getParameter("emailProfessionista");
			String indirizzo = request.getParameter("indirizzo");
			String descrizione = request.getParameter("descrizione");
			String ora_inizioString =request.getParameter("ora_inizio");
			String ora_fineString=request.getParameter("ora_fine");
			String giornoString=request.getParameter("giorni");
			LocalDate giorno=null;
			LocalDate giornoOggi=LocalDate.now();

			if(indirizzo==null || indirizzo.isBlank()){
				DTOGenericResponse dto = new DTOGenericResponse(false, "Inserire un indirizzo");
				outJson.print(mapper.writeValueAsString(dto));
				outJson.flush();
				return;
			}

			if(descrizione==null || descrizione.isBlank()){
				DTOGenericResponse dto = new DTOGenericResponse(false, "Inserire una descrizione");
				outJson.print(mapper.writeValueAsString(dto));
				outJson.flush();
				return;
			}

			LocalTime ora_inizio = null;
		    LocalTime ora_fine = null;
			if (ora_inizioString != null && !ora_inizioString.isEmpty()) {
			     ora_inizio = LocalTime.parse(ora_inizioString);
			}
			if (ora_fineString != null && !ora_fineString.isEmpty()) {
			     ora_fine = LocalTime.parse(ora_fineString);
			}
			
			if (ora_inizio != null && ora_fine != null) {
			    if (ora_inizio.getMinute() % 30 != 0 || ora_fine.getMinute() % 30 != 0) {
			        throw new Exception("L'orario deve essere a intervalli di 30 minuti.");
			    }

			    if (!ora_fine.isAfter(ora_inizio)) {
			        throw new Exception("L'orario di fine deve essere successivo a quello di inizio.");
			    }
			}
			
			if (giornoString != null && !giornoString.isEmpty()) {
			    giorno = LocalDate.parse(giornoString);
			    

			    if (giorno.isBefore(giornoOggi)) {
			        throw new Exception("Non puoi richiedere un intervento per una data passata!");
			    }
			} else {
			    giorno = giornoOggi; 
			}

			Utente professionista = utenteDao.getUtentebyEmail(emailProfessionista);

			Richiesta nuovaRichiesta = new Richiesta(null, giorno, ora_inizio, ora_fine, indirizzo,
					StatoRichiesta.IN_ATTESA_DI_CONFERMA, descrizione, cliente, professionista );
			
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

			DTOGenericResponse dto = new DTOGenericResponse(true, "Richiesta inviata con successo.");
			outJson.println(mapper.writeValueAsString(dto));
			outJson.flush();
			return;

		} catch (Exception e) {
			e.printStackTrace();

			DTOGenericResponse dto = new DTOGenericResponse(false, "Errore durante il completamente dell'operazione. Riprova più tardi.");
			outJson.println(mapper.writeValueAsString(dto));
			outJson.flush();
			return;
		}
	}
}