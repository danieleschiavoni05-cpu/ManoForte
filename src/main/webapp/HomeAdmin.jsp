<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Utente" %>
<%@ page import="org.elis.manoforte.model.Ruolo" %>

<%
    List<Object[]> citta = (List<Object[]>) request.getAttribute("citta");
    List<Object[]> professioni = (List<Object[]>) request.getAttribute("professioni");
    Utente userSession = (Utente) session.getAttribute("utenteLoggato");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Home Admin - ManoForte</title>
   <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/HomeAdmin.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
    
</head>

<body>

<jsp:include page="/includes/Navbar.jsp"/>

<div class="homeadmin-container">

    <div class="homeadmin-header">
        <h1>Area Amministratore</h1>
        <p>Gestisci città e professioni della piattaforma</p>
    </div>

    <div class="admin-card">

        <h2>Aggiungi una Città</h2>
        <form action="AggiungiCitta" method="post" class="admin-form">
            <input type="text" name="nomeCitta" placeholder="Nome città" required>
            <button type="submit" class="admin-btn-primary">Aggiungi</button>
        </form>

        <h3>Città inserite:</h3>
        <ul class="admin-list">
            <% if (citta != null) {
                   for (Object[] c : citta) { %>
                <li class="admin-list-item">
                    <!-- Mostro solo il nome, non l'ID -->
                    <strong><%= c[1] %></strong>
                    <div class="admin-actions">
                        <a href="EliminaCitta?id=<%= c[0] %>" class="admin-delete-link">Elimina</a>
                        <form action="ModificaCitta" method="post" class="admin-inline-form">
                            <input type="hidden" name="id" value="<%= c[0] %>">
                            <input type="text" name="nome" placeholder="Nuovo nome">
                            <button type="submit" class="admin-btn-small">Modifica</button>
                        </form>
                    </div>
                </li>
            <% } } %>
        </ul>

        <hr class="admin-hr">

        <h2>Aggiungi una Professione</h2>
        <form action="AggiungiProfessione" method="post" class="admin-form">
            <input type="text" name="nomeProfessione" placeholder="Nome professione" required>
            <button type="submit" class="admin-btn-primary">Aggiungi</button>
        </form>

        <h3>Professioni inserite:</h3>
        <ul class="admin-list">
            <% if (professioni != null) {
                   for (Object[] p : professioni) { %>
                <li class="admin-list-item">
                    <!-- Mostro solo il nome -->
                    <strong><%= p[1] %></strong>
                    <div class="admin-actions">
                        <a href="EliminaProfessione?id=<%= p[0] %>" class="admin-delete-link">Elimina</a>
                        <form action="ModificaProfessione" method="post" class="admin-inline-form">
                            <input type="hidden" name="id" value="<%= p[0] %>">
                            <input type="text" name="nome" placeholder="Nuovo nome">
                            <button type="submit" class="admin-btn-small">Modifica</button>
                        </form>
                    </div>
                </li>
            <% } } %>
        </ul>

    </div>
</div>

</body>
</html>