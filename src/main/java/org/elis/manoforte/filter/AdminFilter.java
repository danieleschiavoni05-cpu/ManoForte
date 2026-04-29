package org.elis.manoforte.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Utente;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/AggiungiProfessione",
        "/EliminaCitta",
        "/EliminaProfessione",
        "/HomeAdmin",
        "/ModificaCitta",
        "/ModificaProfessione"
})
public class AdminFilter extends HttpFilter implements Filter {
       
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
  throws IOException, ServletException{
      
      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse res = (HttpServletResponse) response;
      
      HttpSession session = req.getSession(false);
      
      boolean isAdmin = false;

      if (session != null) {
          Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
          if (utenteLoggato!=null && utenteLoggato.getRuolo() == Ruolo.ADMIN) {
              isAdmin = true;
          }
      }
      
      if (isAdmin) {
          chain.doFilter(request, response);
      } else {
          res.sendRedirect(req.getContextPath() + "/login");
      }
  }
}