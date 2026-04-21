
<%@ page import="org.elis.manoforte.model.Utente" %>
<%@ page import="org.elis.manoforte.model.Ruolo" %>
<%@ page import="org.elis.manoforte.utility.Utility" %>

<%
    Utente user = (Utente) session.getAttribute("utenteLoggato");
    Ruolo ruolo = (user != null) ? user.getRuolo() : null;
%>

<nav class="navbar">
    <div class="nav-container">
        <div class="nav-left-group">
            <a href="Homepage" class="brand">ManoForte</a>
            <ul class="nav-links">
                <%if(user!=null){%>
                    <li><a href="<%=request.getContextPath()%>/<%=Utility.getUserHomePage(user)%>">Il mio profilo</a></li>
                    <%if(ruolo == Ruolo.UTENTE_BASE){%>
                        
                        
                        <li><a href="<%=request.getContextPath()%>/professionisti">Effettua una richiesta</a></li>
                       
                       
                    <%}else if(ruolo == Ruolo.PROFESSIONISTA){%>
                        <li><a href="<%=request.getContextPath()%>/homeprofessionista#requests">I miei lavori</a></li>
                        <li><a href="<%=request.getContextPath()%>/homeprofessionista#reviews">Le mie recensioni</a></li>
                        <li><a href="<%=request.getContextPath()%>/homeprofessionista#availability">La mia agenda</a></li>
                    <%}if(ruolo == Ruolo.ADMIN){%>
                        <li><a href="<%=request.getContextPath()%>/HomeAdmin">Dashboard</a></li>
                    <%}%>
                <%}else{%>
                    <li><a href="<%=request.getContextPath()%>/ListaProfessionisti">Lista professionisti</a></li>
                    <li><a href="<%=request.getContextPath()%>/ChiSiamo.jsp">Chi Siamo</a></li>
                <%}%>
            </ul>
        </div>
        <div>
            <%if(user == null){%>
                <a href="login" class="btn-login">Accedi</a>
            <%}else{%>
                <span style="margin-right: 15px; color: var(--light-silver);">Ciao, <strong><%= user.getNome() %></strong> <strong><%= user.getCognome() %></strong></span>
                <a href="logout" class="btn-login">Logout</a>
            <%}%>
        </div>
    </div>
</nav>