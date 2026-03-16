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
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.RecensioneDAOJDBC;
import org.elis.manoforte.dao.jdbc.RichiestaDAOJDBC;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.model.Richiesta;
import org.elis.manoforte.model.StatoRichiesta;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;

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
		
		    HttpSession session = request.getSession();
		    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");

		    if (utenteLoggato == null) {
		        response.sendRedirect(request.getContextPath() + "/login.jsp");
		        return;
		    }

		    try {
		    	UtenteDAO utentedao = new JdbcUtenteDAO(DataSourceConfig.getDataSource()); 
		    	
		        // Recupero parametri
		        long idRichiesta = Long.parseLong(request.getParameter("idRichiesta"));
		        String descrizione = request.getParameter("descrizione");
		        int voto = Integer.parseInt(request.getParameter("voto"));
		        // Se possibile, genera la data internamente invece di riceverla dal form
		        LocalDate data = LocalDate.now(); 
		        
		        long idProfessionista = Long.parseLong(request.getParameter("id_professionista"));
		        Utente utenteSessione = (Utente) request.getSession().getAttribute("utenteLoggato");
		        String emailBase = utenteSessione.getEmail();
		        Long idBase=utentedao.trovaIdBasePerEmail(emailBase);
		        

		        RichiestaDAO richiestaDao = new RichiestaDAOJDBC(DataSourceConfig.getDataSource());
		        RecensioneDAO recensioneDao = new RecensioneDAOJDBC(DataSourceConfig.getDataSource());

		        Richiesta richiesta = richiestaDao.getRichiestaById(idRichiesta);

		        // Controllo di business e di proprietà (il cliente che recensisce deve essere quello della richiesta)
		        if (richiesta != null && 
		            StatoRichiesta.COMPLETA.equals(richiesta.getStatoRichiesta()) && 
		            richiesta.getId_cliente() == idBase) {

		            Recensione rec = new Recensione(idRichiesta, descrizione, voto, data, idBase, idProfessionista);
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