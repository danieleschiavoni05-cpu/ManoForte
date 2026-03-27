<%@page import="org.elis.manoforte.model.Utente"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Professione" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Esplora Professioni - ManoForte</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-home.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <style>
        .prof-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .prof-card {
            background: var(--obsidian-base);
            padding: 20px;
            border-radius: 12px;
            color: var(--light-silver);
            border: 1px solid var(--steel-variant);
        }
        .prof-card h3 { color: var(--craft-gold); }
        .prof-card p { color: var(--muted-silver); font-size: 0.9rem; }
        .prof-footer a {
            background: var(--craft-gold);
            color: var(--obsidian-base);
            padding: 10px 15px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            transition: 0.3s;
        }
        .prof-footer a:hover { background: var(--gold-variant); }
        .no-results {
            grid-column: 1 / -1;
            text-align: center;
            padding: 40px;
            background: var(--deep-steel);
            border-radius: 12px;
            color: var(--light-silver);
        }
        .no-results a { color: var(--craft-gold); font-weight: bold; }
        .filter-section {
            background: #279AF1; 
            padding: 30px 0;
            border-bottom: 1px solid var(--steel-variant);
        }
    </style>
</head>

<body>

<jsp:include page="WEB-INF/includes/Navbar.jsp"/>

<div class="filter-section">
    <div class="container">
        <form class="search-box" action="ListaProfessionisti" method="get" style="display:flex; gap:10px; justify-content:center;">
            <%
                String cerca = request.getParameter("nome"); // Uniformato al Controller
                if (cerca == null) cerca = "";
            %>
            <input type="text" name="nome" placeholder="Cerca per nome..." value="<%= cerca %>">
            <button type="submit">Cerca</button>
        </form>
    </div>
</div>

<div class="container">
    <h2 style="margin-top: 20px;">Professioni Disponibili</h2>

    <div class="prof-grid">
        <%
            List<Professione> professioni = (List<Professione>) request.getAttribute("listaProfessionisti");
        %>

        <% if (professioni != null && !professioni.isEmpty()) { %>
            <% for (Professione p : professioni) { %>
                <div class="prof-card">
                    <h3><%= p.getNome() %></h3>
                    <p>Esplora i professionisti specializzati in questa categoria.</p>
                    <div class="prof-footer" style="margin-top:15px;">
                        <a href="<%=request.getContextPath()%>/DettagliProfessionista?nome=<%=p.getNome()%>">Vedi Profilo</a>
                    </div>
                </div>
            <% } %>
        <% } else { %>
            <div class="no-results">
                <h3>Nessun risultato trovato</h3>
                <p>Prova a cambiare i filtri di ricerca.</p>
                <a href="ListaProfessionisti">Mostra tutti</a>
            </div>
        <% } %>
    </div>
</div>

<jsp:include page="WEB-INF/includes/Footer.jsp"/>

</body>
</html>