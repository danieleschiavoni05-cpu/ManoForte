package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.model.Citta;
import org.elis.manoforte.utility.dto.DTOGenericResponse;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

@WebServlet("/AggiungiCitta")
public class AggiungiCitta extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private CittaDAO cittaDao;

    @Override
    public void init()throws ServletException{
        cittaDao = DaoFactory.getInstance().getCittaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        String nome = request.getParameter("nomeCitta");

        if(nome==null || nome.isBlank()){
            DTOGenericResponse dto = new DTOGenericResponse(false, "Inserire una città.");
            outJson.println(mapper.writeValueAsString(dto));
            outJson.flush();
            return;
        }

        Citta c = new Citta();
        c.setNome(nome);
        try {
            cittaDao.inserisciCitta(c);

            DTOGenericResponse dto = new DTOGenericResponse(true, "Città inserita.");
            request.getSession().setAttribute("successoCitta", "Città inserita con successo.");

            outJson.println(mapper.writeValueAsString(dto));
        } catch (Exception e) {
            e.printStackTrace();

            DTOGenericResponse dto = new DTOGenericResponse(false, "Errore nell'esecuzione della fetch.");
            outJson.println(mapper.writeValueAsString(dto));
        }
        outJson.flush();
    }
}
