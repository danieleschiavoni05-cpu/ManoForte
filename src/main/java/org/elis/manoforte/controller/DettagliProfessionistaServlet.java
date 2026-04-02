package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.RecensioneDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;

import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class DettagliProfessionistaServlet
 */
@WebServlet("/DettagliProfessionista")
public class DettagliProfessionistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UtenteDAO utenteDao;
    private RecensioneDAO recensioneDao;
    private ProfessioneDAO professioneDao;

    @Override
    public void init() throws ServletException{
        utenteDao = DaoFactory.getInstance().getUtenteDAO();
        recensioneDao = DaoFactory.getInstance().getRecensioneDAO();
        professioneDao =DaoFactory.getInstance().getProfessioneDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nomeProfessione = request.getParameter("nome");
		
		try {
	        // Recupero tutte le recensioni
	        List<Recensione> recensioni = recensioneDao.findAll();
	        List<Utente> tuttiUtenti;
			
			tuttiUtenti = utenteDao.findAllProfessionisti();
			for(Utente u : tuttiUtenti) {
				String emailProfessionista = u.getEmail();
				Long idProfessionista=utenteDao.findIdByEmail(emailProfessionista);
				request.setAttribute("idProfessionista", idProfessionista);
			}
            // Usiamo sempre il database, addio liste statiche "Database.utentiRegistrati"
            List<Utente> risultato = utenteDao.findAllProfessionistibyProfessione(nomeProfessione);
            List<Professione> professioni= professioneDao.getAllProfessioni();

            request.setAttribute("nomeProfessione", nomeProfessione);
            request.setAttribute("listaProfessioni", professioni);
            request.setAttribute("listaProfessionisti", risultato);
            request.setAttribute("recensioni", recensioni);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Impossibile recuperare i professionisti.");
        }

        request.getRequestDispatcher("dettaglioPro.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
