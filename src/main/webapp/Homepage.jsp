<%@page import="org.elis.manoforte.model.Utente"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.elis.manoforte.model.Recensione" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.BigDecimal" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ManoForte - La tua mano di fiducia</title>

    <!-- Frameworks -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/professionista-style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/spinning-background.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home_professionista-style.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/homepage-style.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">

</head>

<body class="rotation">

    <% List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni"); %>

    <jsp:include page="WEB-INF/includes/Navbar.jsp"/>

    <main>
        <!-- HERO SECTION -->
        <section class="hero-section text-center">
            <div class="container">
                <h1>La tua casa, in mani sicure.</h1>
                <p class="lead">ManoForte è la piattaforma che ti connette con i migliori professionisti della tua zona. Dimentica lo stress, trova la tua mano di fiducia.</p>
                <div class="mt-5">
                    <form class="d-flex justify-content-center" action="ListaProfessionisti" method="get">
                        <div class="col-md-8">
                            <div class="input-group">
                                <input type="text" name="cercaNome" class="form-control" placeholder="Di quale professionista hai bisogno?">
                                <button class="btn custom-button btn-search" type="submit">Cerca</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>

        <!-- I VANTAGGI DI MANOFORTE -->
        <section class="content-section" style="background-color: var(--obsidian-base);">
            <div class="container text-center">
                <h2 class="section-title">I Vantaggi di ManoForte</h2>
                <div class="row g-4 mt-4">
                    <div class="col-md-3">
                        <div class="feature-card">
                            <div class="feature-icon"><i class="fas fa-bolt"></i></div>
                            <h3 class="feature-title">Interventi Rapidi</h3>
                            <p style="color: var(--light-silver);">Trova professionisti disponibili nella tua zona e risolvi il problema in tempi record.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="feature-card">
                            <div class="feature-icon"><i class="fas fa-award"></i></div>
                            <h3 class="feature-title">Qualità Certificata</h3>
                            <p style="color: var(--light-silver);">Solo esperti qualificati e con recensioni verificate. La qualità è il nostro primo obiettivo.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="feature-card">
                            <div class="feature-icon"><i class="fas fa-lock"></i></div>
                            <h3 class="feature-title">Pagamenti Sicuri</h3>
                            <p style="color: var(--light-silver);">Paga solo a lavoro concluso e approvato, attraverso la nostra piattaforma sicura e protetta.</p>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="feature-card">
                            <div class="feature-icon"><i class="fas fa-headset"></i></div>
                            <h3 class="feature-title">Supporto Dedicato</h3>
                            <p style="color: var(--light-silver);">Il nostro team di assistenza è sempre a tua disposizione per qualsiasi dubbio o necessità.</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- RECENSIONI -->
        <section class="content-section">
            <div class="container">
                <h2 class="section-title text-center">La parola ai nostri clienti</h2>
                <div class="row g-4 mt-5">
                    <%if(recensioni == null || recensioni.isEmpty()){%>
                        <div class="col-12 text-center">
                            <p style="color: var(--light-silver);">Ancora nessuna recensione. Sii il primo a condividere la tua esperienza!</p>
                        </div>
                    <%}else{%>
                        <%for(Recensione r : recensioni){%>
                            <div class="col-md-4">
                                <div class="detail-card h-100">
                                    <div class="star-rating mb-3">
                                        <%for(int i = 1; i <= 5; i++){%>
                                            <i class="<%= (BigDecimal.valueOf(i).compareTo(r.getVoto()) <= 0) ? "fas fa-star" : "far fa-star" %>" style="color: var(--craft-gold);"></i>
                                        <%}%>
                                    </div>
                                    <p class="fst-italic" style="color: var(--light-silver);">
                                        "<%= (r.getDescrizione() != null && !r.getDescrizione().isEmpty()) ? r.getDescrizione() : "Ottimo servizio!" %>"
                                    </p>
                                    <div class="author mt-3" style="color: var(--light-silver);">
                                        Cliente per <%=(r.getProfessionista() != null) ?
                                            (r.getProfessionista().getNome() + " " + r.getProfessionista().getCognome().charAt(0))+"."
                                            : "un nostro professionista"%>
                                    </div>
                                </div>
                            </div>
                        <%}%>
                    <%}%>
                </div>
            </div>
        </section>
    </main>

    <jsp:include page="WEB-INF/includes/Footer.jsp"/>

    <script src="<%=request.getContextPath()%>/js/random_color.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
