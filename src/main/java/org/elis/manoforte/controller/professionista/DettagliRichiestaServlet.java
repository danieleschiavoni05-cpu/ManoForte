package org.elis.manoforte.controller.professionista;

import jakarta.servlet.ServletException;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.RichiestaDAO;
import org.elis.manoforte.dao.definition.UtenteDAO;
import org.elis.manoforte.model.*;
import org.elis.manoforte.utility.*;
import org.elis.manoforte.utility.dto.DTOGenericResponse;
import org.elis.manoforte.utility.dto.DTOResponseDettagliRichiesta;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

@WebServlet("/dettagliRichiesta")
public class DettagliRichiestaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RichiestaDAO richiestaDao;
    private CittaDAO cittaDao;
    private UtenteDAO utenteDao;


    public DettagliRichiestaServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        richiestaDao = DaoFactory.getInstance().getRichiestaDAO();
        cittaDao = DaoFactory.getInstance().getCittaDAO();
        utenteDao = DaoFactory.getInstance().getUtenteDAO();
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
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        Long id_richiesta = Long.valueOf(request.getParameter("id_richiesta"));

        try{
            Richiesta richiesta = richiestaDao.getRichiestaById(id_richiesta);

            if(richiesta!=null){
                Utente cliente = richiesta.getCliente();
                Citta citta = cliente.getCitta();
                String nome = cliente.getNome()+" "+cliente.getCognome();
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
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        Long id_richiesta = Long.valueOf(request.getParameter("id"));
        String type = request.getParameter("type");

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

            richiestaDao.updateStatoRichiesta(id_richiesta, tipo);

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
