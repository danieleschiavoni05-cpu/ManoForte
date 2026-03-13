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

<body style="background: #E6F4FF;">

<header style="
    background: #7F7F80;
    padding: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
">
    <a href="Homepage" class="logo" style="color:white; font-size:24px; font-weight:bold; text-decoration:none;">ManoForte</a>

    <nav style="display:flex; gap:20px;">
        <a href="Homepage.jsp" style="color:white; text-decoration:none;">Home</a>
        <a href="ListaProfessionisti" style="color:#DFF1FF; text-decoration:none; font-weight:bold;">Esplora</a>
        <a href="login" style="color:#DFF1FF;">Area Riservata</a>
    </nav>
</header>

<div class="filter-section" 
     style="background: #279AF1; padding:30px 0;">
    <div class="container">
        <form class="search-box" action="ListaProfessionisti" method="get" 
              style="display:flex; gap:10px; justify-content:center;">

            <%
                String cerca = request.getParameter("cercaNome");
                if (cerca == null) cerca = "";
            %>

            <input type="text" 
                   name="cercaNome" 
                   placeholder="Cerca per nome..." 
                   value="<%= cerca %>"
                   style="
                       padding:12px 15px;
                       border-radius:10px;
                       border:none;
                       width:280px;
                       background: #FFFFFF;
                       color:#2A2A2A;
                       font-size:1rem;
                   ">

            <button type="submit"
                    style="
                        background:#6EC6FF;
                        color:white;
                        padding:12px 20px;
                        border:none;
                        border-radius:10px;
                        font-size:1rem;
                        cursor:pointer;
                        font-weight:bold;
                    ">
                Cerca
            </button>

        </form>
    </div>
</div>

<div class="container" 
     style="min-height: 60vh; background: #F2F2F2;
            padding:40px; border-radius:15px; margin-top:40px;">
    <h2 style="margin-top: 20px; color:#3A3A3B;">Professioni Disponibili</h2>

    <div class="prof-grid" style="display:grid; grid-template-columns:repeat(auto-fill, minmax(250px,1fr)); gap:20px; margin-top:30px;">
        <%
            List<Professione> professioni = (List<Professione>) request.getAttribute("listaProfessionisti");
        %>

        <% if (professioni != null && !professioni.isEmpty()) { %>

            <% for (Professione p : professioni) { %>

                <div class="prof-card"
                     style="background: #6EC6FF;
                            padding:20px; border-radius:12px; color:white;">

                    <h3><%= p.getNome() %></h3>

                    <p style="color:#F0F8FF; font-size: 0.9rem;">
                        Nessuna descrizione disponibile.
                    </p>

                    <div class="prof-footer" style="margin-top:15px;">
                        <a href="DettaglioProfessionista?id=<%= p.getId() %>" 
                           style="background:#279AF1; color:white; padding:10px 15px; border-radius:8px; text-decoration:none;">
                           Vedi Profilo
                        </a>
                    </div>
                </div>

            <% } %>

        <% } else { %>

            <div class="no-results"
                 style="grid-column:1/-1; text-align:center; padding:40px;
                        background: #7F7F80;
                        border-radius:12px; color:white;">
                <h3>Nessun risultato trovato</h3>
                <a href="ListaProfessionisti" style="color:#6EC6FF; font-weight:bold;">Mostra tutti</a>
            </div>

        <% } %>
    </div>
</div>

<jsp:include page="/Footer.jsp"/>

</body>
</html>


