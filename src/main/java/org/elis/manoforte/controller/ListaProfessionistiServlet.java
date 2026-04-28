package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elis.manoforte.dao.definition.*;

import org.elis.manoforte.model.Immagine;
import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Recensione;
import org.elis.manoforte.model.Utente;

/**
 * Servlet implementation class DettagliProfessionistaServlet
 */
@WebServlet("/listaProfessionisti")
public class ListaProfessionistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UtenteDAO utenteDao;
    private RecensioneDAO recensioneDao;
    private ProfessioneDAO professioneDao;
	private ImmagineDAO immagineDao;

    @Override
    public void init() throws ServletException{
        utenteDao = DaoFactory.getInstance().getUtenteDAO();
        recensioneDao = DaoFactory.getInstance().getRecensioneDAO();
        professioneDao =DaoFactory.getInstance().getProfessioneDAO();
		immagineDao = DaoFactory.getInstance().getImmagineDAO();
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
			List<Immagine> immaginiDb = immagineDao.findAllProfessionistiProPic();
	        List<Utente> tuttiUtenti;
			Map<Long, String> immagini = new HashMap<>();
			
			tuttiUtenti = utenteDao.findAllProfessionisti();
			for(Utente u : tuttiUtenti) {
				for(Immagine img:immaginiDb){
					if(img.getUtente().getId().equals(u.getId())){
						immagini.put(u.getId(), img.getPercorso());
						break;
					}
				}
			}
            // Usiamo sempre il database, addio liste statiche "Database.utentiRegistrati"
            List<Utente> risultato = utenteDao.findAllProfessionistibyProfessione(nomeProfessione);
            List<Professione> professioni= professioneDao.getAllProfessioni();

            request.setAttribute("nomeProfessione", nomeProfessione);
            request.setAttribute("listaProfessioni", professioni);
            request.setAttribute("listaProfessionisti", risultato);
            request.setAttribute("recensioni", recensioni);
			request.setAttribute("immagini", immagini);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Impossibile recuperare i professionisti.");
        }

        request.getRequestDispatcher("listaProfessionisti.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
