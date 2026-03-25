package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class RecensioniProfessionistiServlet
 */
@WebServlet("/RecensioniProfessionisti")
public class RecensioniProfessionistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	UtenteDAO utenteDao;
	RecensioneDAO recensioneDao;

	@Override
	public void init() throws ServletException{
		utenteDao = DaoFactory.getInstance().getUtenteDAO();
		recensioneDao = DaoFactory.getInstance().getRecensioneDAO();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecensioniProfessionistiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Meglio false se verifichi solo l'esistenza
        Utente utenteLoggato = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;
        
        

        if (utenteLoggato == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        try {
        // Inizializzo i DAO
        

        // Recupero tutte le recensioni
        List<Recensione> recensioni = recensioneDao.findAll();
        
        
        List<Utente> tuttiUtenti;
		
			tuttiUtenti = utenteDao.findAllProfessionisti();
			for(Utente u : tuttiUtenti) {
				String emailProfessionista = u.getEmail();
				Long idProfessionista=utenteDao.findIdByEmail(emailProfessionista);
				request.setAttribute("idProfessionista", idProfessionista);
				
			}
			
			
			 request.setAttribute("listaUtenti", tuttiUtenti);
			 request.setAttribute("recensioni", recensioni);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        

        request.getRequestDispatcher("/WEB-INF/recensioniPro.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
