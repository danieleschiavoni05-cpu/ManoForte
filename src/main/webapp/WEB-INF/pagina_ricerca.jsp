<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.elis.manoforte.model.Utente, org.elis.manoforte.model.Professione, java.util.List"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ricerca Professioni | ManoForte</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-modificaProfilo.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
</head>

<body>

    <jsp:include page="/WEB-INF/includes/Navbar.jsp" />

    <main class="main-content py-5">
        <div class="container">
            
            <header class="text-center mb-5">
                <% String nomeCercato = (String) request.getAttribute("nomeCercato"); %>
                <h1 class="fw-bold text-white">
                    <% if (nomeCercato != null && !nomeCercato.isEmpty()) { %>
                        Risultati per: <span style="color: var(--craft-gold);"><%= nomeCercato %></span>
                    <% } else { %>
                        Tutte le <span style="color: var(--craft-gold);">Professioni</span>
                    <% } %>
                </h1>
                <p class="text-white opacity-75">Scegli una categoria o trova l'esperto giusto per te</p>
            </header>

            <div class="row g-4 mb-5 justify-content-center">
                <% 
                List<Professione> listaProfessioni = (List<Professione>) request.getAttribute("listaProfessioni");
                if (listaProfessioni != null && !listaProfessioni.isEmpty()) {
                    for (Professione p : listaProfessioni) {
                %>
                                    <% 
                    }
                } else if (nomeCercato != null) { 
                %>
                    <div class="col-12 text-center p-5">
                        <div class="no-data">
                            <i class="fa-solid fa-magnifying-glass-blur fa-3x mb-3" style="color: var(--craft-gold);"></i>
                            <h3 class="text-white">Nessuna professione trovata</h3>
                            
                        </div>
                    </div>
                <% } %>
            </div>

            <% 
            List<Utente> professionisti = (List<Utente>) request.getAttribute("listaProfessionisti");
            if (professionisti != null && !professionisti.isEmpty()) { 
            %>
                <div class="mt-5">
                    <h2 class="text-white mb-4"><i class="fa-solid fa-users-gear me-2" style="color: var(--craft-gold);"></i>Esperti Disponibili</h2>
                    
                    <div class="row g-4">
                        <% for (Utente u : professionisti) { %>
                            <div class="col-md-6 col-lg-4">
                                <div class="review-card p-4 h-100 d-flex flex-column">
                                    <div class="d-flex align-items-center mb-4">
                                        <div class="profile-avatar m-0 me-3" style="width: 60px; height: 60px; font-size: 1.5rem; transform: none; background: rgba(212, 175, 55, 0.1); border: 1px solid var(--craft-gold);">
                                            <i class="fa-solid fa-user-tie" style="color: var(--craft-gold);"></i>
                                        </div>
                                        <div>
                                            <h5 class="text-white fw-bold mb-1"><%= u.getNome() %> <%= u.getCognome() %></h5>
                                            <span class="prof-id-tag">Professionista Verificato</span>
                                        </div>
                                    </div>
                                    
                                    <p class="text-white opacity-75 small mb-4">
                                        Specializzato negli interventi richiesti e pronto ad aiutarti con la massima professionalità.
                                    </p>

                                    <div class="mt-auto">
                                        <a href="<%=request.getContextPath()%>/richiesta?emailPro=<%= u.getEmail() %>" class="login-register-button w-100 text-center text-decoration-none d-block py-2">
                                            <i class="fa-solid fa-paper-plane me-2"></i>Invia Richiesta
                                        </a>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                </div>
            <% } else if (nomeCercato != null && !nomeCercato.isEmpty()) { %>
                 <div class="alert alert-info bg-dark border-secondary text-white text-center mt-4">
                    <i class="fa-solid fa-circle-info me-2"></i> Non ci sono ancora professionisti iscritti in questa categoria.
                 </div>
            <% } %>

        </div>
    </main>

    <jsp:include page="/WEB-INF/includes/Footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>