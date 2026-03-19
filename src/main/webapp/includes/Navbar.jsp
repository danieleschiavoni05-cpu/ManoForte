<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="org.elis.manoforte.model.Utente" %>
<%@ page import="org.elis.manoforte.model.Ruolo" %>

<%
    Utente user = (Utente) session.getAttribute("utenteLoggato");
    Ruolo ruolo = null;

    if (user != null) {
        ruolo = user.getRuolo();
    }
%>

<nav class="navbar">
    <div class="nav-container">
        <div class="nav-left-group">
            <a href="Homepage" class="brand">
                <div class="logo-icon"></div>
                ManoForte
            </a>

            <ul class="nav-links">
                <li><a href="Homepage">Home</a></li>
                <li><a href="ListaProfessionisti">Servizi</a></li>

                <% if (ruolo == Ruolo.UTENTE_BASE) { %>
                    <li><a href="ListaProfessionisti">Trova Professionisti</a></li>
                    <li><a href="MyRequests">Le mie richieste</a></li>
                    <li><a href="homeBase">Profilo</a></li>
                <% } %>

                <% if (ruolo == Ruolo.PROFESSIONISTA) { %>
                    <li><a href="ListaClienti">I miei Clienti</a></li>
                    <li><a href="homeprofessionista">Profilo</a></li>
                    <li><a href="professioniProfessionista">I miei lavori</a></li>
                <% } %>

                <% if (ruolo == Ruolo.ADMIN) { %>
                    <li><a href="GestioneProfessioni">Gestione Professionisti</a></li>
                    <li><a href="Dashboard">Dashboard</a></li>
                    <li><a href="ManageUsers">Gestione utenti</a></li>
                <% } %>
            </ul>
        </div>

        <div>
            <% if (user == null) { %>
                <a href="login" class="btn-login">Accedi</a>
            <% } else { %>
                <span style="margin-right: 15px; color: var(--light-silver);">Ciao, <strong><%= user.getNome() %></strong></span>
                <a href="logout" class="btn-login">Logout</a>
            <% } %>
        </div>
    </div>
</nav>