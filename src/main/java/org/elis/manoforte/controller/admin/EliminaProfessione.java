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

@WebServlet("/EliminaProfessione")
public class EliminaProfessione extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProfessioneDAO professioneDAO;

    @Override
    public void init() throws ServletException {
        professioneDAO = DaoFactory.getInstance().getProfessioneDAO();
    }

    // Usiamo doGet perché il link <a> nella JSP invia una richiesta GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                professioneDAO.removeProfessione(id);

                DTOGenericResponse dto = new DTOGenericResponse(true, "Professione rimossa con successo.");
                request.getSession().setAttribute("successoProfessione", "Professione rimossa con successo.");

                outJson.println(mapper.writeValueAsString(dto));
            } catch (Exception e) {
                e.printStackTrace();

                DTOGenericResponse dto = new DTOGenericResponse(false, "Impossibile eliminare la professione selezionata.");
                outJson.println(mapper.writeValueAsString(dto));
            }
            outJson.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}