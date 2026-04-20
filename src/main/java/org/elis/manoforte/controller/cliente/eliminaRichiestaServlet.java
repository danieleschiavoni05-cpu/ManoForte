package org.elis.manoforte.controller.cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.model.StatoRichiesta;

/**
 * Servlet implementation class eliminaRichiestaServlet
 */
@WebServlet("/eliminaRichiesta")
public class eliminaRichiestaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RichiestaDAO richiestaDao;

	@Override
	public void init() throws ServletException{
		richiestaDao = DaoFactory.getInstance().getRichiestaDAO();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public eliminaRichiestaServlet() {
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
		// TODO Auto-generated method stub
		String idParam = request.getParameter("id_richiesta");

	    if (idParam != null && !idParam.isEmpty()) {
	        try {
	            long idRichiesta = Long.parseLong(idParam);
	            

	            // 3. Chiamo il metodo void
	            richiestaDao.updateStatoRichiesta(idRichiesta, StatoRichiesta.ANNULLATA);

	            // 4. Feedback opzionale in sessione
	            request.getSession().setAttribute("messaggio", "Richiesta annullata.");

	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            // 1. Stampa lo stack trace standard (molto lungo, utile per la riga esatta)
	            e.printStackTrace();

	            // 2. LOG MIRATO: Scava per trovare la causa radice (Root Cause)
	            Throwable t = e;
	            while (t.getCause() != null) {
	                t = t.getCause();
	            }

	            System.out.println("============== ERRORE DB DETTAGLIATO ==============");
	            System.out.println("Messaggio: " + t.getMessage());
	            System.out.println("Tipo Eccezione: " + t.getClass().getName());
	            System.out.println("===================================================");

	            request.getSession().setAttribute("errore", "Errore: " + t.getMessage());
	        }
	    }

	    // 5. Ricarico la pagina delle recensioni
	    response.sendRedirect(request.getContextPath() + "/homeBase");
	}

	}

