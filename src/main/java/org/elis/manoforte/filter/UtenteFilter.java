package org.elis.manoforte.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

import org.elis.manoforte.model.Ruolo;
import org.elis.manoforte.model.Utente;

/**
 * Servlet Filter implementation class UtenteFilter
 */
@WebFilter(urlPatterns = {
		"/homeBase",
		"/eliminaRichiesta",
		"/eliminazionerecensione",
		"/InviaRecensione",
		"/ModificaProfilo",
		"/professionisti",
		"/RecensioniProfessionisti",
		"/ricercaProfessioni",
		"/richiesta"
})
public class UtenteFilter extends HttpFilter implements Filter {
       
	@Override
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	  throws IOException, ServletException{
	      
	      HttpServletRequest req = (HttpServletRequest) request;
	      HttpServletResponse res = (HttpServletResponse) response;
	      
	      HttpSession session = req.getSession(false);
	      
	      boolean isUtente = false;

	      if (session != null) {
			  Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
	          if (utenteLoggato!=null && utenteLoggato.getRuolo() == Ruolo.UTENTE_BASE) {
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
