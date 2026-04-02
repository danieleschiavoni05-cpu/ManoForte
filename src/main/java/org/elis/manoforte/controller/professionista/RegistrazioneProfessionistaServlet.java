package org.elis.manoforte.controller.professionista;

import jakarta.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import org.elis.manoforte.dao.definition.*;
import org.elis.manoforte.exception.DatiErratiException;
import org.elis.manoforte.exception.NessunValoreTrovatoException;
import org.elis.manoforte.model.Professione;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.model.Veicolo;
import org.elis.manoforte.utility.dto.DTOResponseRegistrazione;
import org.elis.manoforte.utility.Utility;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

@WebServlet("/registrazioneprofessionista")
public class RegistrazioneProfessionistaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProfessioneDAO professioneDao;
    private CittaDAO cittaDao;
    private VeicoloDAO veicoloDao;
    private UtenteDAO utenteDao;

    public RegistrazioneProfessionistaServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();
        cittaDao = DaoFactory.getInstance().getCittaDAO();
        veicoloDao = DaoFactory.getInstance().getVeicoloDAO();
        utenteDao = DaoFactory.getInstance().getUtenteDAO();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, IOException {
        HttpSession session = request.getSession();

        Utente loggedUser = (Utente)session.getAttribute("utenteLoggato");

        if(loggedUser != null){
            response.sendRedirect(request.getContextPath()+Utility.getUserHomePage(loggedUser));
            return;
        }

        try {
            request.setAttribute("citta", cittaDao.getAllCitta());
            request.setAttribute("professioni", professioneDao.getAllProfessioni());
            request.setAttribute("veicoli", veicoloDao.getAllVeicolo());
        }catch(SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/errorpage");
            return;
        }catch(NessunValoreTrovatoException e){
            e.printStackTrace();
            request.setAttribute("errore", e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/registrazione_professionista.jsp");
        dispatcher.forward(request, response);
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
        Long citta = null;
        BigDecimal tariffa = null;
        List<Long> professioni = new ArrayList<>();
        List<Long> veicoli = new ArrayList<>();

        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        if(request.getParameter("data_nascita")!=null && !request.getParameter("data_nascita").equals(""))
            data_nascita = LocalDate.parse(request.getParameter("data_nascita"));
        String codice_fiscale = request.getParameter("codice_fiscale");
        if(request.getParameter("citta")!=null && !request.getParameter("citta").equals(""))
            citta =  Long.parseLong(request.getParameter("citta"));
        String password = request.getParameter("password");
        String confermaPassword = request.getParameter("conferma_password");
        if(request.getParameterValues("professioni")!=null)
            for(String str : request.getParameterValues("professioni"))
                professioni.add(Long.parseLong(str));
        if(request.getParameterValues("veicoli")!=null)
            for(String str : request.getParameterValues("veicoli"))
                veicoli.add(Long.parseLong(str));
        if(request.getParameterValues("tariffa")!=null && !request.getParameter("tariffa").equals(""))
            tariffa = new BigDecimal(request.getParameter("tariffa"));

        DatiErratiException emptyError = new DatiErratiException();

        if(email==null || email.trim().isEmpty())
            emptyError.setErrEmail();

        if(nome==null || nome.trim().isEmpty())
            emptyError.setErrNome();

        if(cognome==null || cognome.trim().isEmpty())
            emptyError.setErrCognome();

        if(data_nascita==null)
            emptyError.setErrData();

        if(codice_fiscale==null || codice_fiscale.trim().isEmpty())
            emptyError.setErrCF();

        if(password==null||confermaPassword==null ||
                confermaPassword.trim().isEmpty() || password.trim().isEmpty())
            emptyError.setErrPassword();

        if(professioni.isEmpty())
            emptyError.setErrProfessioni();

        if(tariffa==null||tariffa.compareTo(BigDecimal.ZERO)<=0)
            emptyError.setErrTariffa();


        if(emptyError.checkErrors()){
            emptyError.printStackTrace();
            emptyError.buildEmptyErrorMessage();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Alcuni dei campi non sono stati compilati", emptyError.getMessages());

            outJson.print(mapper.writeValueAsString(risposta));
            outJson.flush();
            return;
        }

        try {
            List<Veicolo> veicoloList = veicoloDao.getVeicoliByIds(veicoli);
            List<Professione> professioniDb = professioneDao.getProfessioniListById(professioni);
            Utente professionista = Utility.checkInputProfessionista(nome, cognome, email, data_nascita, codice_fiscale, professioniDb, tariffa, password, confermaPassword);
            professionista.setCitta(cittaDao.getCittaById(citta));
            professionista.setVeicolo(veicoloList);
            professionista.setProfessione(professioniDb);
            utenteDao.inserisciProfessionista(professionista);

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(true,
                    "Registrazione completata con successo.", null);
            outJson.print(mapper.writeValueAsString(risposta));

        }catch(DatiErratiException e) {
            e.printStackTrace();
            e.buildErrorMessage();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Errore inserimento dati dell'utente.", e.getMessages());
            outJson.print(mapper.writeValueAsString(risposta));

        }catch(SQLException e) {
            e.printStackTrace();

            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Errore inserimento nel database, riprovare più tardi.", null);
            outJson.print(mapper.writeValueAsString(risposta));

        }catch (Exception e){
            e.printStackTrace();
            DTOResponseRegistrazione risposta = new DTOResponseRegistrazione(false,
                    "Errore imprevisto, riprovare.", null);
            outJson.print(mapper.writeValueAsString(risposta));
        }

        outJson.flush();
    }
}
