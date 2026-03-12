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
</head>

<body style="background: linear-gradient(180deg, #D7EDFF 0%, #A8D8FF 50%, #7BC2FF 100%);">


    <jsp:include page="/Navbar.jsp"/>



    <% List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioniList"); %>
    <% Map<Long, String> utenti = (Map<Long, String>) request.getAttribute("listaUtenti"); %>

<div class="carousel" 
     style="background: linear-gradient(180deg, #279AF1 0%, #4FB4FF 100%);">
    <div class="slide active" style="background-color:#279AF1">
        <div class="slide-content">
            <h1>Esperti Idraulici pronti per te</h1>
            <p>Risolvi ogni problema domestico con professionisti verificati.</p>
        </div>
    </div>
    <div class="slide" style="background-color:#279AF1">
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

<div class="container"
     style="background: linear-gradient(180deg, #A8E1FF 0%, #7BC2FF 100%);
            padding: 40px; border-radius: 15px; margin-top: 40px;">
    <h2 style="color:white;">Feedback Recenti</h2>
    <div class="recensioni-grid">
        <% if (recensioni != null && !recensioni.isEmpty() && utenti != null && !utenti.isEmpty()) {
                for (Recensione r : recensioni) { %>
                    <div class="rec-card"
                         style="background: linear-gradient(135deg, #E3F4FF 0%, #C7E9FF 100%);
                                padding:20px; border-radius:12px;">
                        <div class="stars" style="color:#1B82D1;">
                            <%
                               int numStelle = (r.getVoto() > 5) ? r.getVoto()/2 : r.getVoto();
                               for(int i=0; i<5; i++) { %>
                                    <%= (i < numStelle ? "★" : "☆") %>
                               <% } %>
                        </div>
                        <p style="font-style: italic; color:#003B73;">"<%= r.getDescrizione() %>"</p>
                        <div class="rec-meta" style="color:#003B73;">
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
                <div style="grid-column: 1/-1; text-align: center; padding: 50px;
                            background: linear-gradient(135deg, #C7E9FF, #A8D8FF);
                            border-radius: 15px; color:#003B73;">
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
<jsp:include page="/Footer.jsp"/>

</body>
</html>

