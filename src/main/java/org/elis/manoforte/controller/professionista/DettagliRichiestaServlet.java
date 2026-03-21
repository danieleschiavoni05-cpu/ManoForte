package org.elis.manoforte.controller.professionista;

import jakarta.servlet.ServletException;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.dao.jdbc.JdbcCittaDAO;
import org.elis.manoforte.dao.jdbc.JdbcUtenteDAO;
import org.elis.manoforte.dao.jdbc.RichiestaDAOJDBC;
import org.elis.manoforte.model.*;
import org.elis.manoforte.utility.*;
import tools.jackson.databind.ObjectMapper;

@WebServlet("/dettagliRichiesta")
public class DettagliRichiestaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DettagliRichiestaServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");

        if(utenteLoggato == null){
            response.sendRedirect("login");
            return;
        }else if(utenteLoggato.getRuolo()!=Ruolo.PROFESSIONISTA){
            response.sendRedirect(Utility.getUserHomePage(utenteLoggato));
            return;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        Long id_richiesta = Long.valueOf(request.getParameter("id_richiesta"));

        RichiestaDAO richiestaDAO = new RichiestaDAOJDBC(DataSourceConfig.getDataSource());
        CittaDAO cittaDAO = new JdbcCittaDAO(DataSourceConfig.getDataSource());
        UtenteDAO utenteDAO = new JdbcUtenteDAO(DataSourceConfig.getDataSource());
        try{
            Richiesta richiesta = richiestaDAO.getRichiestaById(id_richiesta);

            if(richiesta!=null){
                Utente cliente = utenteDAO.findById(richiesta.getId_cliente());
                Citta citta = cittaDAO.getCittaById(cliente.getIdCitta());
                String nome = cliente.getNome()+" "+cliente.getCognome().charAt(0)+".";
                DTOResponseDettagliRichiesta risposta = new DTOResponseDettagliRichiesta(
                        richiesta, nome, citta.getNome());

                response.setStatus(HttpServletResponse.SC_OK);
                outJson.write(mapper.writeValueAsString(risposta));
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
                outJson.write("{\"errore\": \"Errore nel reperimento della richiesta.\"}");
            }

        }catch(Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            outJson.write("{\"error\": \"Errore interno del server\"}");
        }finally{
            outJson.flush();
            outJson.close();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        Long id_richiesta = Long.valueOf(request.getParameter("id"));
        String type = request.getParameter("type");

        RichiestaDAO richiestaDAO = new RichiestaDAOJDBC(DataSourceConfig.getDataSource());
        try{
            StatoRichiesta tipo;
            String messaggio;
            if(type.equals("accept")){
                tipo = StatoRichiesta.IN_CORSO;
                messaggio = "Richiesta accettata.";
            }else{
                tipo = StatoRichiesta.COMPLETA;
                messaggio = "Richiesta segnata come completata.";
            }

            richiestaDAO.updateStatoRichiesta(id_richiesta, tipo);

            DTOGenericResponse dtoResponse = new DTOGenericResponse(true, messaggio);
            response.setStatus(HttpServletResponse.SC_OK);
            outJson.write(mapper.writeValueAsString(dtoResponse));
        }catch(Exception e) {
            DTOGenericResponse dtoResponse = new DTOGenericResponse(false, "Impossibile aggiornare la richiesta.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            outJson.write(mapper.writeValueAsString(dtoResponse));
            throw new RuntimeException(e);
        }
    }
}
