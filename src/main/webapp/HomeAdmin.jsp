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
</head>

<body style="background:#E6F4FF;">

<header style="
    background:#7F7F80;
    padding:20px;
    display:flex;
    justify-content:space-between;
    align-items:center;
">
    <a href="Homepage" style="color:white; font-size:24px; font-weight:bold; text-decoration:none;">ManoForte</a>

    <nav style="display:flex; gap:20px;">
        <a href="Homepage" style="color:white; text-decoration:none;">Home</a>
        <a href="ListaProfessionisti" style="color:#DFF1FF; text-decoration:none;">Esplora</a>
        <a href="HomeAdmin" style="color:#6EC6FF; font-weight:bold;">Admin</a>
    </nav>
</header>

<div style="
    background:#279AF1;
    padding:40px;
    text-align:center;
    color:white;
">
    <h1>Area Amministratore</h1>
    <p>Gestisci città e professioni della piattaforma</p>
</div>

<div style="
    max-width:900px;
    margin:auto;
    margin-top:40px;
    padding:30px;
    background:#F2F2F2;
    border-radius:15px;
">

    <!-- AGGIUNGI CITTÀ -->
    <h2 style="color:#3A3A3B;">Aggiungi una Città</h2>

    <form action="AggiungiCitta" method="post" 
          style="display:flex; gap:10px; margin-top:15px;">
        <input type="text" name="nomeCitta" placeholder="Nome città"
               style="padding:12px; border-radius:10px; border:none; flex:1;">
        <button type="submit"
                style="background:#279AF1; color:white; padding:12px 20px;
                       border:none; border-radius:10px; font-weight:bold;">
            Aggiungi
        </button>
    </form>

    <!-- LISTA CITTÀ -->
    <h3 style="margin-top:25px;">Città inserite:</h3>
    <ul>
        <% for (Object[] c : citta) { %>
            <li style="margin-bottom:10px;">
                <strong><%= c[1] %></strong>

                <!-- ELIMINA -->
                <a href="EliminaCitta?id=<%= c[0] %>" 
                   style="color:red; margin-left:10px; font-weight:bold;">
                    Elimina
                </a>

                <!-- MODIFICA -->
                <form action="ModificaCitta" method="post" style="display:inline; margin-left:10px;">
                    <input type="hidden" name="id" value="<%= c[0] %>">
                    <input type="text" name="nome" placeholder="Nuovo nome"
                           style="padding:5px; border-radius:5px; border:1px solid #CCC;">
                    <button type="submit"
                            style="background:#279AF1; color:white; border:none; padding:5px 10px; border-radius:5px;">
                        Modifica
                    </button>
                </form>
            </li>
        <% } %>
    </ul>

    <hr style="margin:40px 0; border:1px solid #CCC;">

    <!-- AGGIUNGI PROFESSIONE -->
    <h2 style="color:#3A3A3B;">Aggiungi una Professione</h2>

    <form action="AggiungiProfessione" method="post" 
          style="display:flex; gap:10px; margin-top:15px;">
        <input type="text" name="nomeProfessione" placeholder="Nome professione"
               style="padding:12px; border-radius:10px; border:none; flex:1;">
        <button type="submit"
                style="background:#6EC6FF; color:white; padding:12px 20px;
                       border:none; border-radius:10px; font-weight:bold;">
            Aggiungi
        </button>
    </form>

    <!-- LISTA PROFESSIONI -->
    <h3 style="margin-top:25px;">Professioni inserite:</h3>
    <ul>
        <% for (Object[] p : professioni) { %>
            <li style="margin-bottom:10px;">
                <strong><%= p[1] %></strong>

                <!-- ELIMINA -->
                <a href="EliminaProfessione?id=<%= p[0] %>" 
                   style="color:red; margin-left:10px; font-weight:bold;">
                    Elimina
                </a>

                <!-- MODIFICA -->
                <form action="ModificaProfessione" method="post" style="display:inline; margin-left:10px;">
                    <input type="hidden" name="id" value="<%= p[0] %>">
                    <input type="text" name="nome" placeholder="Nuovo nome"
                           style="padding:5px; border-radius:5px; border:1px solid #CCC;">
                    <button type="submit"
                            style="background:#6EC6FF; color:white; border:none; padding:5px 10px; border-radius:5px;">
                        Modifica
                    </button>
                </form>
            </li>
        <% } %>
    </ul>

</div>

<footer style="
    background:#7F7F80;
    padding:40px 20px;
    color:white;
    margin-top:60px;
    text-align:center;
">
    <span style="font-size:22px; font-weight:bold;">ManoForte</span>
    <p style="opacity:0.9;">Pannello amministratore</p>
</footer>

</body>
</html>
