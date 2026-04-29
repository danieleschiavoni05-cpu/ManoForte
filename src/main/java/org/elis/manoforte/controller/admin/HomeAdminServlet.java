package org.elis.manoforte.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Veicolo;

import java.io.IOException;
import java.util.List;

@WebServlet("/HomeAdmin")
public class HomeAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	private CittaDAO cittaDao;
    private ProfessioneDAO professioneDao;
    private VeicoloDAO veicoloDao;

    @Override
    public void init() throws ServletException{
        cittaDao = DaoFactory.getInstance().getCittaDAO();
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();
        veicoloDao = DaoFactory.getInstance().getVeicoloDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        try {
            List<Citta> citta = cittaDao.getAllCitta();
            List<Professione> professioni = professioneDao.getAllProfessioni();
            List<Veicolo> veicoli = veicoloDao.getAllVeicolo();

            request.setAttribute("citta", citta);
            request.setAttribute("professioni", professioni);
            request.setAttribute("veicoli", veicoli);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("WEB-INF/admin/HomeAdmin.jsp").forward(request, response);
    }
}
