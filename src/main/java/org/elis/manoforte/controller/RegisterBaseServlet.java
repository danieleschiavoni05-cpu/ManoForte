package org.elis.manoforte.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.elis.manoforte.utility.DataSourceConfig;
import org.elis.manoforte.utility.Utility;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcCittaDAO;
import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/registerBase")
public class RegisterBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterBaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();

	        Utente loggedUser = (Utente)session.getAttribute("utenteLoggato");

	        if(loggedUser != null){
	            response.sendRedirect(request.getContextPath()+Utility.getUserHomePage(loggedUser));
	            return;
	        }
		
		
		CittaDAO cittaDao = new JdbcCittaDAO(DataSourceConfig.getDataSource());
		try {
	        List<Citta> citta = cittaDao.getAllCitta(); 
	        request.setAttribute("listaCitta", citta);
	        
	        // Assicurati che il percorso della JSP sia corretto (corrisponda alla cartella in WebContent/webapp)
	        request.getRequestDispatcher("/auth/registerbase.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect(request.getContextPath() + "/errore.jsp");
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UtenteDAO utenteDao= new JdbcUtenteDAO(DataSourceConfig.getDataSource());

		String nome = request.getParameter("campoNome");
		String cognome = request.getParameter("campoCognome");
		BigDecimal tariffa = new BigDecimal(0);
		String codice_fiscale = request.getParameter("campoCodiceFiscale");
		String email = request.getParameter("campoEmail");
		String password = request.getParameter("campoPassword");
		Long id_citta=Long.parseLong(request.getParameter("campoCitta"));
		LocalDate data_nascita = LocalDate.parse(request.getParameter("campoData"));
		Utente u=new Utente(email, password,nome, cognome,data_nascita, codice_fiscale, id_citta);

		try {
			utenteDao.inserisciUtente(u);
			response.sendRedirect(request.getContextPath() + "/login");
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("/primo-progetto/errore.jsp");
		}



	}

}
