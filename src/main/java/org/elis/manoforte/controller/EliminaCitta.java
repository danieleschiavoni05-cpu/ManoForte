
package org.elis.manoforte.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import org.elis.manoforte.dao.definition.AdminDAO;

@WebServlet("/EliminaCitta")
public class EliminaCitta extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        AdminDAO.eliminaCitta(id);

        response.sendRedirect("HomeAdmin.jsp");
    }
}
