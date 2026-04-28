package org.elis.manoforte.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Utente;

/**
 * Servlet Filter implementation class ProfessionistaFilter
 */
@WebFilter(urlPatterns = {
		"/gestisciDisponibilita",
		"/dettagliRichiesta",
		"/homeprofessionista",
		"/modificaProfiloProfessionista"
})
public class ProfessionistaFilter extends HttpFilter implements Filter {
       
	  @Override
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	  throws IOException, ServletException{
	      
	      HttpServletRequest req = (HttpServletRequest) request;
	      HttpServletResponse res = (HttpServletResponse) response;
	      
	      HttpSession session = req.getSession(false);
	      
	      boolean isProfessionista = false;

		  if(session != null) {
			  Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
			  if (utenteLoggato!=null && utenteLoggato.getRuolo() == Ruolo.PROFESSIONISTA) {
				  isProfessionista = true;
			  }
		  }
	      
	      if (isProfessionista) {
	          chain.doFilter(request, response);
	      } else {
	          res.sendRedirect(req.getContextPath() + "/login");
	      }
	  }

}
