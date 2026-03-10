package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.utility.DataSourceConfig;

import java.io.IOException;

import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class ModificaProfiloServlet
 */
@WebServlet("/ModificaProfilo")
public class ModificaProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificaProfiloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente utenteSessione = (Utente) request.getSession().getAttribute("utenteLoggato");

		// 2. Controllo sicurezza: se non c'è nessuno in sessione, rimanda al login
		if (utenteSessione == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}


		try {

			request.setAttribute("utenteLoggato", utenteSessione);
			request.getRequestDispatcher("/WEB-INF/ModificaProfilo.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("errore.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteDAO dao = new JdbcUtenteDAO(DataSourceConfig.getDataSource());

		try {
			// Recupero l'utente dalla sessione per sicurezza
			Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");

			if (utenteLoggato == null) {
				response.sendRedirect("login.jsp");
				return;
			}

			String nuovoNome = request.getParameter("nome");
			String idCittaParam = request.getParameter("id_citta");

			if (nuovoNome != null && idCittaParam != null) {
				utenteLoggato.setNome(nuovoNome);
				utenteLoggato.setId_citta(Long.parseLong(idCittaParam));

				dao.update(utenteLoggato);

				// Aggiorno l'oggetto in sessione
				request.getSession().setAttribute("utenteLoggato", utenteLoggato);
				response.sendRedirect(request.getContextPath() + "/HomeServlet");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("errore.jsp");
		}
	}
}