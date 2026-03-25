package org.elis.manoforte.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

import org.elis.manoforte.model.Ruolo;

/**
 * Servlet Filter implementation class UtenteFilter
 */
@WebFilter("/UtenteFilter")
public class UtenteFilter extends HttpFilter implements Filter {
       
	@Override
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	  throws IOException, ServletException{
	      
	      HttpServletRequest req = (HttpServletRequest) request;
	      HttpServletResponse res = (HttpServletResponse) response;
	      
	      HttpSession session = req.getSession(false);
	      
	      boolean isUtente = false;

	      if (session != null) {
	          Ruolo ruolo = (Ruolo) session.getAttribute("ruolo");
	          if (ruolo == Ruolo.UTENTE_BASE) {
	              isUtente = true;
	          }
	      }
	      
	      if (isUtente) {
	          chain.doFilter(request, response);
	      } else {
	          res.sendRedirect(req.getContextPath() + "/login");
	      }
	  }

}
