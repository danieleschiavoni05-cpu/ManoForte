<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.dao.definition.AdminDAO" %>

<%
    List<Object[]> citta = AdminDAO.getCitta();
    List<Object[]> professioni = AdminDAO.getProfessioni();
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Home Admin - ManoForte</title>

    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/css/HomeAdmin.css">
</head>

<body>

<jsp:include page="/Navbar.jsp"/>

<div class="homeadmin-container">

    <div class="homeadmin-header">
        <h1>Area Amministratore</h1>
        <p>Gestisci città e professioni della piattaforma</p>
    </div>

    <div class="admin-card">

        <h2>Aggiungi una Città</h2>

        <form action="AggiungiCitta" method="post" class="admin-form">
            <input type="text" name="nomeCitta" placeholder="Nome città">
            <button type="submit" class="admin-btn-primary">Aggiungi</button>
        </form>

        <h3>Città inserite:</h3>
        <ul class="admin-list">
            <% for (Object[] c : citta) { %>
                <li class="admin-list-item">
                    <strong><%= c[1] %></strong>

                    <a href="EliminaCitta?id=<%= c[0] %>" class="admin-delete-link">
                        Elimina
                    </a>

                    <form action="ModificaCitta" method="post" class="admin-inline-form">
                        <input type="hidden" name="id" value="<%= c[0] %>">
                        <input type="text" name="nome" placeholder="Nuovo nome">
                        <button type="submit" class="admin-btn-small">Modifica</button>
                    </form>
                </li>
            <% } %>
        </ul>

        <hr class="admin-hr">

        <h2>Aggiungi una Professione</h2>

        <form action="AggiungiProfessione" method="post" class="admin-form">
            <input type="text" name="nomeProfessione" placeholder="Nome professione">
            <button type="submit" class="admin-btn-primary">Aggiungi</button>
        </form>

        <h3>Professioni inserite:</h3>
        <ul class="admin-list">
            <% for (Object[] p : professioni) { %>
                <li class="admin-list-item">
                    <strong><%= p[1] %></strong>

                    <a href="EliminaProfessione?id=<%= p[0] %>" class="admin-delete-link">
                        Elimina
                    </a>

                    <form action="ModificaProfessione" method="post" class="admin-inline-form">
                        <input type="hidden" name="id" value="<%= p[0] %>">
                        <input type="text" name="nome" placeholder="Nuovo nome">
                        <button type="submit" class="admin-btn-small">Modifica</button>
                    </form>
                </li>
            <% } %>
        </ul>

    </div>

</div>

<jsp:include page="/Footer.jsp"/>

</body>
</html>
