package org.elis.manoforte.controller.professionista;

import jakarta.servlet.ServletException;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.elis.manoforte.dao.definition.*;
import org.elis.manoforte.exception.DatiErratiException;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.model.Veicolo;
import org.elis.manoforte.utility.dto.DTOResponseRegistrazione;
import org.elis.manoforte.utility.Utility;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

@WebServlet("/modificaProfiloProfessionista")
public class ModificaProfiloProfessionistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UtenteDAO utenteDao;
    private VeicoloDAO veicoloDao;
    private CittaDAO cittaDao;
    private ProfessioneDAO professioneDao;

    public ModificaProfiloProfessionistaServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        utenteDao = DaoFactory.getInstance().getUtenteDAO();
        veicoloDao = DaoFactory.getInstance().getVeicoloDAO();
        cittaDao = DaoFactory.getInstance().getCittaDAO();
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/homeprofessionista#edit");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        LocalDate data_nascita = null;
        Long id_citta = null;
        BigDecimal tariffa = null;
        List<Long> veicoliIds = new ArrayList<>();
        List<Long> professioniIds = new ArrayList<>();


        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");

        if(request.getParameter("data_nascita")!=null && !request.getParameter("data_nascita").equals(""))
            data_nascita = LocalDate.parse(request.getParameter("data_nascita"));

        String codice_fiscale = request.getParameter("codice_fiscale");

        if(request.getParameter("citta")!=null && !request.getParameter("citta").equals(""))
            id_citta =  Long.parseLong(request.getParameter("citta"));

        String nuovaPassword = request.getParameter("nuovaPassword");
        String confermaPassword = request.getParameter("conferma_password");
        String password = request.getParameter("password_attuale");

        if(request.getParameterValues("professione")!=null)
            for(String str:request.getParameterValues("professione"))
                professioniIds.add(Long.parseLong(str));

        if(request.getParameterValues("veicolo")!=null)
            for(String str : request.getParameterValues("veicolo"))
                veicoliIds.add(Long.parseLong(str));

        if(request.getParameterValues("tariffa")!=null && !request.getParameter("tariffa").equals(""))
            tariffa = new BigDecimal(request.getParameter("tariffa"));

        DatiErratiException emptyError = new DatiErratiException();

        if(nome==null || nome.trim().isEmpty())
            emptyError.setErrNome();

        if(cognome==null || cognome.trim().isEmpty())
            emptyError.setErrCognome();

        if(id_citta==null || id_citta.toString().trim().isEmpty())
            emptyError.setErrCitta();

        if(data_nascita==null)
            emptyError.setErrData();

        if(professioniIds.isEmpty())
            emptyError.setErrProfessioni();

        if(codice_fiscale==null || codice_fiscale.trim().isEmpty())
            emptyError.setErrCF();

        if(nuovaPassword!=null){
            if(confermaPassword==null){
                emptyError.setErrConfermaPassword();
            }else if(!nuovaPassword.trim().isEmpty() && confermaPassword.trim().isEmpty()){
                emptyError.setErrConfermaPassword();
            }
        }

        if(password==null || password.trim().isEmpty())
            emptyError.setErrPassword();

        if(tariffa==null || tariffa.compareTo(BigDecimal.ZERO)<=0)
            emptyError.setErrTariffa();

        if(emptyError.checkEditErrors()){
            emptyError.printStackTrace();
            emptyError.buildEmptyErrorMessage();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Alcuni dei campi non sono stati compilati.", emptyError.getMessages());
            outJson.print(mapper.writeValueAsString(risposta));
            outJson.flush();
            return;
        }

        try {
            Utente utenteLoggato = (Utente) request.getSession().getAttribute("utenteLoggato");

            Citta citta = cittaDao.getCittaById(id_citta);
            List<Veicolo> veicolo = veicoloDao.getVeicoliByIds(veicoliIds);
            List<Professione> professione = professioneDao.getProfessioniListById(professioniIds);

            Utente professionista = Utility.checkInputEditProfessionista(
                    utenteLoggato, nome, cognome, data_nascita, codice_fiscale, professione,
                    tariffa, nuovaPassword, password, confermaPassword);

            professionista.setCitta(citta);
            professionista.setVeicolo(veicolo);
            professionista.setProfessione(professione);
            professionista.setId(utenteLoggato.getId());

            utenteDao.modificaProfessionista(professionista);
            veicoloDao.updateVeicoliProfessionista(utenteLoggato.getEmail(), veicolo);

            request.getSession().setAttribute("utenteLoggato", professionista);

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(true,
                    "Modifica completata con successo.", null);
            outJson.print(mapper.writeValueAsString(risposta));

        }catch(DatiErratiException e) {
            e.printStackTrace();
            e.buildErrorEditMessage();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Errore inserimento dati dell'utente.", e.getMessages());
            outJson.print(mapper.writeValueAsString(risposta));

        }catch(SQLException e) {
            e.printStackTrace();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Errore nel caricamento delle modifiche, riprovare più tardi.", null);
            outJson.print(mapper.writeValueAsString(risposta));

        }catch (Exception e){
            e.printStackTrace();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Errore imprevisto, riprovare.", null);
            outJson.print(mapper.writeValueAsString(risposta));
        }finally{
            outJson.flush();
        }

    }
}
