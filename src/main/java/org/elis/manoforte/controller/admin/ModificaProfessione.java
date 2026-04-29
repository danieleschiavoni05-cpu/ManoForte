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

@WebServlet("/ModificaProfessione")
public class ModificaProfessione extends HttpServlet {

    private static final long serialVersionUID = 1L;
	ProfessioneDAO professioneDao;

    public void init() throws ServletException{
        professioneDao = DaoFactory.getInstance().getProfessioneDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");

        if (nome != null && !nome.trim().isEmpty()) {
            try {
                professioneDao.modificaProfessione(id, nome.trim());

                DTOGenericResponse dto = new DTOGenericResponse(true, "Professione modificata.");
                request.getSession().setAttribute("successoProfessione", "Professione modificata con successo.");

                outJson.println(mapper.writeValueAsString(dto));

            } catch (Exception e) {
                e.printStackTrace();

                DTOGenericResponse dto = new DTOGenericResponse(false, "Errore nell'esecuzione della fetch.");
                outJson.println(mapper.writeValueAsString(dto));
            }
            outJson.flush();
        }

    }
}
