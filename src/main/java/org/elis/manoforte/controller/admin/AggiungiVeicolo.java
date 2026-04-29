package org.elis.manoforte.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.model.Veicolo;
import org.elis.manoforte.utility.dto.DTOGenericResponse;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AggiungiVeicolo")
public class AggiungiVeicolo extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private VeicoloDAO veicoloDao;

    @Override
    public void init()throws ServletException{
        veicoloDao = DaoFactory.getInstance().getVeicoloDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        String nome = request.getParameter("nomeVeicolo");

        if(nome==null || nome.isBlank()){
            DTOGenericResponse dto = new DTOGenericResponse(false, "Inserire un veicolo.");
            outJson.println(mapper.writeValueAsString(dto));
            outJson.flush();
            return;
        }

        Veicolo v = new Veicolo();
        v.setNome(nome);
        try {
            veicoloDao.inserisciVeicolo(v);

            DTOGenericResponse dto = new DTOGenericResponse(true, "Veicolo inserito.");
            request.getSession().setAttribute("successoVeicolo", "Veicolo inserito con successo.");

            outJson.println(mapper.writeValueAsString(dto));
            outJson.flush();
            return;

        } catch (Exception e) {
            e.printStackTrace();

            DTOGenericResponse dto = new DTOGenericResponse(false, "Errore nell'esecuzione della fetch.");
            outJson.println(mapper.writeValueAsString(dto));
            outJson.flush();
            return;
        }
    }
}
