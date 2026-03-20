<%@page import="org.elis.manoforte.model.Utente"%>
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

    <% 
	List<Utente> listaUtenti = (List<Utente>) request.getAttribute("listaUtenti");
	
    %>

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

<div class="container"
     style="background: linear-gradient(135deg, #279AF1 0%, #6EC6FF 50%, #A8E1FF 100%);
            padding: 40px; border-radius: 15px; margin-top: 40px;">
    <h2 style="color:white;">Scegli per Categoria</h2>
    <div class="categories-grid">
        <a href="ListaProfessionisti?cercaNome=Idraulico" class="cat-item" 
           style="background: linear-gradient(135deg, #1B82D1, #4FB4FF); color:white;">
            <span class="cat-icon">🔧</span>
            <span>Idraulici</span>
        </a>
        <a href="ListaProfessionisti?cercaNome=Elettricista" class="cat-item"
           style="background: linear-gradient(135deg, #1B82D1, #4FB4FF); color:white;">
            <span class="cat-icon">⚡</span>
            <span>Elettricisti</span>
        </a>
        <a href="ListaProfessionisti?cercaNome=Pulizia" class="cat-item"
           style="background: linear-gradient(135deg, #1B82D1, #4FB4FF); color:white;">
            <span class="cat-icon">🧹</span>
            <span>Pulizie</span>
        </a>
        <a href="ListaProfessionisti?cercaNome=Pittore" class="cat-item"
           style="background: linear-gradient(135deg, #1B82D1, #4FB4FF); color:white;">
            <span class="cat-icon">🎨</span>
            <span>Pittori</span>
        </a>
    </div>
</div>

<div class="container" style="margin-top: 40px; margin-bottom: 60px;">
    <h2 style="color:white; margin-bottom: 25px;">
        <i class="fa-solid fa-comments me-2"></i>Cosa dicono i nostri utenti
    </h2>
    
    <div class="recensioni-grid">
    <%
    Map<Long, Utente> mappaProf = (Map<Long, Utente>) request.getAttribute("mappaProfessionisti");
    List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");

    if (recensioni == null || recensioni.isEmpty()) {
    %>
        <div class="text-center p-5 bg-white rounded shadow-sm" style="border-radius: 15px;">
            <i class="fa-solid fa-comment-slash fa-3x mb-3 text-muted"></i>
            <h3>Ancora nessuna recensione</h3>
            <p class="text-muted">Sii il primo a lasciare un feedback!</p>
        </div>
    <%
    } else {
        for (Recensione r : recensioni) {
            Utente prof = (mappaProf != null) ? mappaProf.get(r.getId_professionista()) : null;
    %>
        <div class="review-card shadow-sm bg-white p-4 mb-3" style="border-radius: 15px; border-left: 6px solid #279AF1;">
            <div class="d-flex justify-content-between align-items-start">
                <div>
                    <div class="prof-name fw-bold" style="font-size: 1.2rem; color: #2c3e50;">
                        <i class="fa-solid fa-user-tie text-primary me-2"></i>
                        <%=(prof != null) ? (prof.getNome() + " " + prof.getCognome()) : "Professionista non trovato"%>
                    </div>
                    
                    <div class="star-rating my-2">
                        <%-- Ciclo per mostrare le icone delle stelle --%>
                        <% for(int i=1; i<=5; i++) { %>
                            <i class="<%= (i <= r.getVoto()) ? "fa-solid fa-star" : "fa-regular fa-star" %>" style="color: #ffc107;"></i>
                        <% } %>
                        <span class="ms-2 text-muted small">(<%= r.getVoto() %>/5)</span>
                    </div>
                </div>
                <div class="badge bg-primary rounded-pill">
                    Verified Feedback
                </div>
            </div>
            
            <div class="mt-3 p-3 bg-light rounded" style="font-style: italic; color: #444; border-radius: 10px;">
                <i class="fa-solid fa-quote-left me-2 text-muted"></i>
                <%= (r.getDescrizione() != null && !r.getDescrizione().isEmpty()) ? r.getDescrizione() : "Nessun commento testuale inserito." %>
                <i class="fa-solid fa-quote-right ms-2 text-muted"></i>
            </div>
        </div>
    <%
        }
    }
    %>
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
    

</body>
</html>
