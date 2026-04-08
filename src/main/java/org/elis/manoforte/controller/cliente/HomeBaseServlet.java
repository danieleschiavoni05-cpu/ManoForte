package org.elis.manoforte.controller.cliente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.elis.manoforte.dao.definition.*;
import org.elis.manoforte.exception.NessunValoreTrovatoException;
import org.elis.manoforte.model.*;
import org.elis.manoforte.utility.Utility;

/**
 * Servlet implementation class HomeBaseServlet
 */
@WebServlet("/homeBase")
public class HomeBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtenteDAO utenteDao;
	private CittaDAO cittaDao;
	private RichiestaDAO richiestaDao;
	private RecensioneDAO recensioneDao;

	@Override
	public void init() throws ServletException{
		utenteDao = DaoFactory.getInstance().getUtenteDAO();
		cittaDao = DaoFactory.getInstance().getCittaDAO();
		richiestaDao = DaoFactory.getInstance().getRichiestaDAO();
		recensioneDao = DaoFactory.getInstance().getRecensioneDAO();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeBaseServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		Utente loggedUser = (Utente)session.getAttribute("utenteLoggato");

		if(loggedUser==null){
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}else if(loggedUser.getRuolo()!= Ruolo.UTENTE_BASE){
			response.sendRedirect(request.getContextPath()+"/"+ Utility.getUserHomePage(loggedUser));
		}

		try {
			loggedUser = utenteDao.reinizializzaUtente(loggedUser.getId());

			List<Richiesta> richiesteInAttesa = richiestaDao.getRichiesteByIdClienteAndStato(loggedUser.getId(), StatoRichiesta.IN_ATTESA_DI_CONFERMA);
			List<Richiesta> richiesteInCorso = richiestaDao.getRichiesteByIdClienteAndStato(loggedUser.getId(), StatoRichiesta.IN_CORSO);
	        List<Richiesta> richiesteCompletate= richiestaDao.getRichiesteByIdClienteAndStato(loggedUser.getId(), StatoRichiesta.COMPLETA);

			List<Recensione> recensioni = recensioneDao.getRecensioneByIdCliente(loggedUser.getId());

			// Dati utente loggato con attributi inizializzati
			request.setAttribute("loggedUser", loggedUser);
			// Attibuti per le mie richieste
			request.setAttribute("richiesteInAttesa", richiesteInAttesa);
	        request.setAttribute("richiesteInCorso", richiesteInCorso);
			request.setAttribute("richiesteCompletate", richiesteCompletate);
			// Attributi per la modifica del profilo
			request.setAttribute("citta", cittaDao.getAllCitta());
			// Attributi per le mie recensioni
			request.setAttribute("recensioni", recensioni);

	    }catch(SQLException e){
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/errorPage");
			return;
		}catch(NessunValoreTrovatoException e){
			e.printStackTrace();
			request.setAttribute("errore", e.getMessage());
			return;
		}catch(Exception e){
			e.printStackTrace();
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/cliente/homeBaseNew.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
