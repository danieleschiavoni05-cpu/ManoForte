package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;

import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.DataSourceConfig;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/ListaProfessionisti")
public class ListaProfessionistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    private ProfessioneDAO professioneDao;

    @Override
    public void init() throws ServletException {
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String nomeProfessione = request.getParameter("nome");
        if (nomeProfessione == null) nomeProfessione = "";
        String nomeProfessionebasso = nomeProfessione.toLowerCase();
        UtenteDAO utente= new JdbcUtenteDAO(DataSourceConfig.getDataSource());
		ProfessioneDAO professione=new JdbcProfessioneDAO(DataSourceConfig.getDataSource());

        try {

            List<Professione> tutte = professioneDao.getAllProfessioni();


            List<Professione> filtrati = tutte.stream()
                    .filter(p -> p.getNome().toLowerCase().contains(nomeProfessionebasso))
                    .collect(Collectors.toList());

            request.setAttribute("listaProfessionisti", filtrati);
            request.setAttribute("listaUtenteProfessionisti", risultato);
           

        } catch (Exception e) {
            e.printStackTrace();
            
        }

        request.getRequestDispatcher("/ListaProfessionisti.jsp")
               .forward(request, response);
    }
}
