package org.elis.manoforte.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.dao.definition.*;
import org.elis.manoforte.model.*;
import org.elis.manoforte.utility.Utility;
import org.elis.manoforte.utility.dto.DTODettagliProfessionista;
import org.elis.manoforte.utility.dto.DTOGenericResponse;
import org.elis.manoforte.utility.dto.DTOResponseDettagliRichiesta;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;


@WebServlet("/dettagliProfessionista")
public class DettagliProfessionistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RecensioneDAO recensioneDao;
    private CittaDAO cittaDao;
    private UtenteDAO utenteDao;
    private VeicoloDAO veicoloDAO;
    private ProfessioneDAO professioneDao;
    private ImmagineDAO immagineDao;


    public DettagliProfessionistaServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        recensioneDao = DaoFactory.getInstance().getRecensioneDAO();
        cittaDao = DaoFactory.getInstance().getCittaDAO();
        utenteDao = DaoFactory.getInstance().getUtenteDAO();
        veicoloDAO = DaoFactory.getInstance().getVeicoloDAO();
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();
        immagineDao = DaoFactory.getInstance().getImmagineDAO();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        Long id_professionista = Long.valueOf(request.getParameter("id"));
        BigDecimal media = new BigDecimal(request.getParameter("media"));

        try{
            Utente professionista = utenteDao.findById(id_professionista);
            List<Veicolo> veicoli = veicoloDAO.getVeicoliByEmailProfessionista(professionista.getEmail());
            List<Professione> professioni = professioneDao.findProfessioniByIdProfessionista(id_professionista);
            Immagine immagine = immagineDao.getImmagineByIdUtente(id_professionista);
            Citta citta = cittaDao.getCittaById(professionista.getCitta().getId());

            DTODettagliProfessionista dto = new DTODettagliProfessionista();
            dto.setNomeCompleto(professionista.getNome()+" "+professionista.getCognome());
            dto.setMediaVoto(media);
            dto.setTariffa(professionista.getTariffa());
            if(immagine == null){
                dto.setImmagine(null);
            }else{
                dto.setImmagine(immagine.getPercorso());
            }
            dto.setProfessioni(professioni);
            dto.setVeicoli(veicoli);
            dto.setCitta(citta);

            response.setStatus(HttpServletResponse.SC_OK);
            outJson.write(mapper.writeValueAsString(dto));

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

    }
}
