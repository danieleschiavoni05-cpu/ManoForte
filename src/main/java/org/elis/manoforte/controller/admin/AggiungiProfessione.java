package org.elis.manoforte.controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ProfessioneDAO;
import org.elis.manoforte.utility.dto.DTOGenericResponse;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

@WebServlet("/AggiungiProfessione")
public class AggiungiProfessione extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private ProfessioneDAO professioneDAO;

    @Override
    public void init()throws ServletException{
        professioneDAO = DaoFactory.getInstance().getProfessioneDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        String nome = request.getParameter("nomeProfessione");

        if(nome==null || nome.isBlank()){
            DTOGenericResponse dto = new DTOGenericResponse(false, "Inserire una professione.");
            outJson.println(mapper.writeValueAsString(dto));
            outJson.flush();
            return;
        }

        try {
            professioneDAO.addProfessione(nome);

            DTOGenericResponse dto = new DTOGenericResponse(true, "Professione inserita.");
            request.getSession().setAttribute("successoProfessione", "Professione inserita con successo.");

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
