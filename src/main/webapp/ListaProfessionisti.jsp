<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Professione" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Esplora Professioni - ManoForte</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-home.css">
</head>
<body>

<header>
    <a href="Homepage" class="logo">ManoForte</a>
    <nav>
        <a href="Homepage.jsp">Home</a>
        <a href="ListaProfessionisti" class="active">Esplora</a>
        <a href="login" style="color: var(--primary);">Area Riservata</a>
    </nav>
</header>

<div class="filter-section">
    <div class="container">
        <form class="search-box" action="ListaProfessionisti" method="get">
            <%
                String cerca = request.getParameter("cercaNome");
                if (cerca == null) cerca = "";
            %>
            <input type="text" name="cercaNome" placeholder="Cerca per nome..." value="<%= cerca %>">
            <button type="submit">Cerca</button>
        </form>
    </div>
</div>

<div class="container" style="min-height: 60vh;">
    <h2 style="margin-top: 40px;">Professioni Disponibili</h2>

    <div class="prof-grid">
        <%
            List<Professione> professioni = (List<Professione>) request.getAttribute("listaProfessionisti");
        %>

        <% if (professioni != null && !professioni.isEmpty()) { %>

            <% for (Professione p : professioni) { %>

                <div class="prof-card">

                    <h3><%= p.getNome() %></h3>

                    <p style="color: #666; font-size: 0.9rem;">
                        Nessuna descrizione disponibile.
                    </p>

                    <div class="prof-footer">
                        <a href="DettaglioProfessionista?id=<%= p.getId() %>" class="btn-contact">Vedi Profilo</a>
                    </div>
                </div>

            <% } %>

        <% } else { %>

            <div class="no-results">
                <h3>Nessun risultato trovato</h3>
                <a href="ListaProfessionisti" style="color: var(--primary);">Mostra tutti</a>
            </div>

        <% } %>
    </div>
</div>

<footer>
    <div class="footer-grid">
        <div class="footer-col">
            <span class="logo" style="color: white;">ManoForte</span>
            <p>Fiducia e qualità garantite.</p>
        </div>
    </div>
</footer>

</body>
</html>
