<%@page import="org.elis.manoforte.model.Utente"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Professione" %>


<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Esplora Professioni - ManoForte</title>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css?v=1">
    
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ricercaProfessioni.css?v=1.2">
</head>

<body>

<jsp:include page="WEB-INF/includes/Navbar.jsp"/>

<div class="filter-section">
    <div class="container">
        <form class="search-box" action="listaProfessioni" method="get" style="display:flex; gap:10px; justify-content:center;">
            <% String cerca = request.getParameter("professioni");%>
            <% if (cerca == null) cerca = ""; %>
            <input type="text" name="professione" placeholder="Cerca per professione..." value="<%=cerca%>">
            <button type="submit">Cerca</button>
        </form>
    </div>
</div>

<div class="container">
    <h2 style="margin-top: 20px;">Professioni Disponibili</h2>

    <div class="prof-grid">
        <% List<Professione> professioni = (List<Professione>) request.getAttribute("listaProfessioni"); %>

        <% if (professioni != null && !professioni.isEmpty()) { %>
            <% for (Professione p : professioni) { %>
                <div class="prof-card">
                    <h3><%= p.getNome() %></h3>
                    <p>Esplora i professionisti specializzati in questa categoria.</p>
                    <div class="prof-footer" style="margin-top:15px;">
                        <a href="<%=request.getContextPath()%>/listaProfessionisti?nome=<%=p.getNome()%>">Vedi Profilo</a>
                    </div>
                </div>
            <% } %>
        <% } else { %>
            <div class="no-results">
                <h3>Nessun risultato trovato</h3>
                <p>Prova a cambiare i filtri di ricerca.</p>
                <a href="listaProfessioni">Mostra tutti</a>
            </div>
        <% } %>
    </div>
</div>

<jsp:include page="WEB-INF/includes/Footer.jsp"/>

	
	<script src="<%=request.getContextPath()%>/js/random_color.js"></script>

	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const url = new URL(window.location.href);
        const param = url.searchParams.get("professione");

        if (param === "" || param === null) {
            url.searchParams.delete("professione");
            window.history.replaceState({}, document.title, url.pathname + url.search);
        }
    </script>
</body>
</html>