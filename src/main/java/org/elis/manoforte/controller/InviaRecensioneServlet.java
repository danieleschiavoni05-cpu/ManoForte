package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.time.LocalDate;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
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

	private UtenteDAO utenteDao;
	private RichiestaDAO richiestaDao;
	private RecensioneDAO recensioneDao;

	@Override
	public void init() throws ServletException{
		utenteDao = DaoFactory.getInstance().getUtenteDAO();
		richiestaDao = DaoFactory.getInstance().getRichiestaDAO();
		recensioneDao = DaoFactory.getInstance().getRecensioneDAO();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InviaRecensioneServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		    HttpSession session = request.getSession();
		    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

		    if (utenteLoggato == null) {
		        response.sendRedirect(request.getContextPath() + "/login.jsp");
		        return;
		    }

		    try {
		    	
		        // Recupero parametri
		        long idRichiesta = Long.parseLong(request.getParameter("idRichiesta"));
		        String descrizione = request.getParameter("descrizione");
		        int voto = Integer.parseInt(request.getParameter("voto"));
		        // Se possibile, genera la data internamente invece di riceverla dal form
		        LocalDate data = LocalDate.now(); 
		        
		        long idProfessionista = Long.parseLong(request.getParameter("id_professionista"));
		        Utente utenteSessione = (Utente) request.getSession().getAttribute("utenteLoggato");
		        String emailBase = utenteSessione.getEmail();
		        Long idBase=utenteDao.findIdByEmail(emailBase);


		        Richiesta richiesta = richiestaDao.getRichiestaById(idRichiesta);

		        // Controllo di business e di proprietà (il cliente che recensisce deve essere quello della richiesta)
		        if (richiesta != null && 
		            StatoRichiesta.COMPLETA.equals(richiesta.getStatoRichiesta()) &&
		            richiesta.getCliente().getId().equals(idBase)) {

		            Recensione rec = new Recensione(idRichiesta, descrizione, voto, data, richiesta.getCliente(), richiesta.getProfessionista());
		            recensioneDao.inserisciRecensione(rec);
		            session.setAttribute("messaggioSuccesso", "Recensione inviata con successo!");
		            
		        } else {
		            session.setAttribute("messaggioErrore", "Impossibile inviare la recensione: stato non valido.");
		        }

		    
		    } catch (Exception e) {
		        session.setAttribute("messaggioErrore", "Errore durante il salvataggio.");
		        e.printStackTrace();
		    }

		    response.sendRedirect(request.getContextPath() + "/homeBase");
		}
	}