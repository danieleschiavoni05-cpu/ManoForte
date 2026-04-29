package org.elis.manoforte.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.dao.definition.CittaDAO;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.VeicoloDAO;
import org.elis.manoforte.utility.dto.DTOGenericResponse;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ModificaVeicolo")
public class ModificaVeicolo extends HttpServlet {

    private static final long serialVersionUID = 1L;
	VeicoloDAO veicoloDao;

    public void init() throws ServletException{
        veicoloDao = DaoFactory.getInstance().getVeicoloDAO();
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
                veicoloDao.modificaVeicoloById(id, nome.trim());

                DTOGenericResponse dto = new DTOGenericResponse(true, "Veicolo modificato.");
                request.getSession().setAttribute("successoVeicolo", "Veicolo modificato con successo.");

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
