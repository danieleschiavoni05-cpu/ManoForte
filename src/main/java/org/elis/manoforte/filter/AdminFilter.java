package org.elis.manoforte.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter extends HttpFilter implements Filter {
       
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
  throws IOException, ServletException{
      
      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse res = (HttpServletResponse) response;
      
      HttpSession session = req.getSession(false);
      
      boolean isAdmin = false;

      if (session != null) {
          Object ruolo = session.getAttribute("ruolo");
          if (ruolo != null && ruolo.toString().equals("ADMIN")) {
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