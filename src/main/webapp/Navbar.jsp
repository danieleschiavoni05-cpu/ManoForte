<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.elis.manoforte.model.Utente" %>
<%@ page import="org.elis.manoforte.model.Ruolo" %>

<%
    Utente user = (Utente) session.getAttribute("user");
    Ruolo ruolo = null;

    if (user != null) {
        ruolo = user.getRuolo();
    }
%>

<nav style="background:#7F7F80; padding:15px; display:flex; justify-content:space-between; align-items:center; font-family:sans-serif;">
    
    <div style="font-size:22px; font-weight:bold;">
        <a href="Homepage" style="color:white; text-decoration:none;">ManoForte</a>
    </div>

    <ul style="list-style:none; display:flex; gap:25px; margin:0; padding:0;">

       
        <li><a href="Homepage" style="color:white; text-decoration:none;">Home</a></li>
        <li><a href="ListaProfessionisti.jsp" style="color:white; text-decoration:none;">Servizi</a></li>

        
        <% if (ruolo == Ruolo.UTENTE_BASE) { %>
            <li><a href="ListaProfessionisti" style="color:white; text-decoration:none;">Trova Professionisti</a></li>
        <% } %>

        <% if (ruolo == Ruolo.PROFESSIONISTA) { %>
            <li><a href="ListaClienti" style="color:white; text-decoration:none;">I miei Clienti</a></li>
        <% } %>

        <% if (ruolo == Ruolo.ADMIN) { %>
            <li><a href="GestioneProfessioni" style="color:white; text-decoration:none;">Gestione Professionisti</a></li>
        <% } %>

        
        <% if (ruolo == Ruolo.UTENTE_BASE) { %>
            <li><a href="MyRequests" style="color:white; text-decoration:none;">Le mie richieste</a></li>
            <li><a href="homeBase" style="color:white; text-decoration:none;">Profilo</a></li>
        <% } %>

       
        <% if (ruolo == Ruolo.PROFESSIONISTA) { %>
            <li><a href="homeprofessionista" style="color:white; text-decoration:none;">Profilo</a></li>
            <li><a href="professioniProfessionista" style="color:white; text-decoration:none;">I miei lavori</a></li>
        <% } %>

        
        <% if (ruolo == Ruolo.ADMIN) { %>
            <li><a href="Dashboard" style="color:white; text-decoration:none;">Dashboard</a></li>
            <li><a href="ManageUsers" style="color:white; text-decoration:none;">Gestione utenti</a></li>
        <% } %>

        
        <% if (user == null) { %>
            <li><a href="login" style="color:white; text-decoration:none;">Accedi</a></li>
        <% } else { %>
            <li><a href="Logout" style="color:white; text-decoration:none;">Logout</a></li>
        <% } %>

    </ul>

    
    <div style="color:white;">
        <% if (user != null) { %>
            Ciao, <strong><%= user.getNome() %></strong>
        <% } %>
    </div>

</nav>
