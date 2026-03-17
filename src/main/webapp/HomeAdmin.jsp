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

        <!-- Aggiungi Città -->
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
                    <strong><%= c[1] %></strong>

                    <form action="EliminaCitta" method="post" class="admin-inline-form">
                        <input type="hidden" name="id" value="<%= c[0] %>">
                        <button type="submit" class="admin-delete-link">Elimina</button>
                    </form>

                    <form action="ModificaCitta" method="post" class="admin-inline-form">
                        <input type="hidden" name="id" value="<%= c[0] %>">
                        <input type="text" name="nome" placeholder="Nuovo nome">
                        <button type="submit" class="admin-btn-small">Modifica</button>
                    </form>
                </li>
            <% } } %>
        </ul>

        <hr class="admin-hr">

        <!-- Aggiungi Professione -->
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
                    <strong><%= p[1] %></strong>

                    <form action="EliminaProfessione" method="post" class="admin-inline-form">
                        <input type="hidden" name="id" value="<%= p[0] %>">
                        <button type="submit" class="admin-delete-link">Elimina</button>
                    </form>

                    <form action="ModificaProfessione" method="post" class="admin-inline-form">
                        <input type="hidden" name="id" value="<%= p[0] %>">
                        <input type="text" name="nome" placeholder="Nuovo nome">
                        <button type="submit" class="admin-btn-small">Modifica</button>
                    </form>
                </li>
            <% } } %>
        </ul>

    </div>
</div>

</body>
</html>