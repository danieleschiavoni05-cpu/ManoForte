package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.jdbc.RecensioneDAOJDBC;
import org.elis.manoforte.utility.DataSourceConfig;

/**
 * Servlet implementation class EliminazioneRecensioneServlet
 */
@WebServlet("/eliminazionerecensione")
public class EliminazioneRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminazioneRecensioneServlet() {
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
	    // 1. Recupero l'id della recensione dalla JSP
	    String idParam = request.getParameter("idRecensione");

	    if (idParam != null && !idParam.isEmpty()) {
	        try {
	            long idRecensione = Long.parseLong(idParam);

	            // 2. Inizializzo il DAO
	            RecensioneDAO recensioneDao = new RecensioneDAOJDBC(DataSourceConfig.getDataSource());

	            // 3. Chiamo il metodo void
	            recensioneDao.delete(idRecensione);

	            // 4. Feedback opzionale in sessione
	            request.getSession().setAttribute("messaggio", "Recensione eliminata.");

	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            // Se il DB dà errore, finisci qui
	            e.printStackTrace();
	            request.getSession().setAttribute("errore", "Errore durante l'eliminazione.");
	        }
	    }

	    // 5. Ricarico la pagina delle recensioni
	    response.sendRedirect(request.getContextPath() + "/RecensioniProfessionisti");
	}

}
