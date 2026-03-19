<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.elis.manoforte.model.Recensione" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ManoForte - Trova la tua mano di fiducia</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-home.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
</head>

<body>

    <jsp:include page="/includes/Navbar.jsp"/>

    <% List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioniList"); %>
    <% Map<Long, String> utenti = (Map<Long, String>) request.getAttribute("listaUtenti"); %>

    <div class="carousel">
        <div class="slide active">
            <div class="slide-content">
                <h1>Esperti Idraulici pronti per te</h1>
                <p>Risolvi ogni problema domestico con professionisti verificati.</p>
            </div>
        </div>
        <div class="slide">
            <div class="slide-content">
                <h1>Elettricisti Certificati</h1>
                <p>Manutenzione sicura e certificata per la tua casa.</p>
            </div>
        </div>

        <div class="search-container">
            <form class="search-box" action="ListaProfessionisti" method="get">
                <input type="text" name="cercaNome" placeholder="Cosa stai cercando? (es. Idraulico)" required>
                <button type="submit">Trova Esperto</button>
            </form>
        </div>
    </div>

    <div class="container">
        <h2>Scegli per Categoria</h2>
        <div class="categories-grid">
            <a href="ListaProfessionisti?cercaNome=Idraulico" class="cat-item">
                <span class="cat-icon">🔧</span>
                <span>Idraulici</span>
            </a>
            <a href="ListaProfessionisti?cercaNome=Elettricista" class="cat-item">
                <span class="cat-icon">⚡</span>
                <span>Elettricisti</span>
            </a>
            <a href="ListaProfessionisti?cercaNome=Pulizia" class="cat-item">
                <span class="cat-icon">🧹</span>
                <span>Pulizie</span>
            </a>
            <a href="ListaProfessionisti?cercaNome=Pittore" class="cat-item">
                <span class="cat-icon">🎨</span>
                <span>Pittori</span>
            </a>
        </div>
    </div>

    <div class="container">
        <h2>Feedback Recenti</h2>
        <div class="recensioni-grid">
            <% if (recensioni != null && !recensioni.isEmpty() && utenti != null && !utenti.isEmpty()) {
                    for (Recensione r : recensioni) { %>
                        <div class="rec-card">
                            <div class="stars">
                                <%
                                   int numStelle = (r.getVoto() > 5) ? r.getVoto()/2 : r.getVoto();
                                   for(int i=0; i<5; i++) { %>
                                        <%= (i < numStelle ? "★" : "☆") %>
                                   <% } %>
                            </div>
                            <p>"<%= r.getDescrizione() %>"</p>
                            <div class="rec-meta">
                                👤 <strong>
                                    <%= r.getId_cliente() != null ? utenti.get(r.getId_cliente()) : "Cliente" %>
                                </strong>
                                per <strong>
                                    <%= r.getId_professionista() != null ? utenti.get(r.getId_professionista()) : "Professionista" %>
                                </strong>
                            </div>
                        </div>
                  <% }
                } else { %>
                    <div class="empty-recensioni">
                        <p>Nessuna recensione disponibile.</p>
                    </div>
                <% } %>
        </div>
    </div>

    <script>
        let currentSlide = 0;
        const slides = document.querySelectorAll('.slide');
        setInterval(() => {
            slides[currentSlide].classList.remove('active');
            currentSlide = (currentSlide + 1) % slides.length;
            slides[currentSlide].classList.add('active');
        }, 5000);
    </script>
    <jsp:include page="/includes/Footer.jsp"/>

</body>
</html>
