
package org.elis.manoforte.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.model.Veicolo;
import org.elis.manoforte.utility.dto.DTOGenericResponse;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/EliminaVeicolo")
public class EliminaVeicolo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VeicoloDAO veicoloDao;

    @Override
    public void init() throws ServletException {
        veicoloDao = DaoFactory.getInstance().getVeicoloDAO();
    }

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
                veicoloDao.removeVeicolo(id);

                DTOGenericResponse dto = new DTOGenericResponse(true, "Veicolo rimosso con successo.");
                request.getSession().setAttribute("successoVeicolo", "Veicolo rimosso con successo.");

                outJson.println(mapper.writeValueAsString(dto));
                outJson.flush();
            } catch (Exception e) {
                e.printStackTrace();

                DTOGenericResponse dto = new DTOGenericResponse(false, "Impossibile eliminare il veicolo selezionato.");
                outJson.println(mapper.writeValueAsString(dto));
                outJson.flush();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}