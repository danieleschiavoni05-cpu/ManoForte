package org.elis.manoforte.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcProfessioneDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;

/**
 * Servlet implementation class professionisti
 */
@WebServlet("/professionisti")
public class professionisti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public professionisti() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeProfessione = request.getParameter("nome");
		UtenteDAO utente= new JdbcUtenteDAO(DataSourceConfig.getDataSource());
		ProfessioneDAO professione=new JdbcProfessioneDAO(DataSourceConfig.getDataSource());
        
        try {
            // Usiamo sempre il database, addio liste statiche "Database.utentiRegistrati"
            List<Utente> risultato = utente.findAllProfessionistibyProfessione(nomeProfessione);
            List<Professione> professioni= professione.getAllProfessioni();

            request.setAttribute("nomeProfessione", nomeProfessione);
            request.setAttribute("listaProfessioni", professioni);
            request.setAttribute("listaProfessionisti", risultato);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Impossibile recuperare i professionisti.");
        }

        request.getRequestDispatcher("/WEB-INF/professionisti.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
