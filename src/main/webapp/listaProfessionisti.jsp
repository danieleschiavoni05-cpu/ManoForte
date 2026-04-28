<%@page import="org.elis.manoforte.model.Recensione"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Utente" %>
<%@ page import="org.elis.manoforte.model.Professione" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="org.elis.manoforte.utility.Utility" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Professionisti: <%= request.getAttribute("nomeProfessione") %></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-home.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/listaProfessionisti.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

</head>
<body>
    <% List<Utente> professionisti = (List<Utente>) request.getAttribute("listaProfessionisti"); %>
    <% List<Recensione> tutteRecensioni = (List<Recensione>) request.getAttribute("recensioni"); %>
    <% Map<Long, String> immagini = (Map<Long, String>) request.getAttribute("immagini"); %>

    <jsp:include page="/WEB-INF/includes/Navbar.jsp" />

    <div class="main-content">
        <header class="page-header">
            <h1 style="color: white;">Esperti in <span style="color: var(--craft-gold);"><%= request.getAttribute("nomeProfessione") %></span></h1>
            <p style="color: var(--light-silver); font-size: 1.1rem; opacity: 0.8;">Qualità e affidabilità al tuo servizio</p>
        </header>

        <div class="prof-list-container">
            <%if(professionisti != null && !professionisti.isEmpty()){%>
                <%for(Utente u : professionisti){ %>
                    <%List<Recensione> recensioniProfessionista = tutteRecensioni.stream().filter(recensione -> recensione.getProfessionista().equals(u)).toList();%>
                    <%BigDecimal media = Utility.calcolaMedia(recensioniProfessionista).setScale(1, RoundingMode.DOWN);%>

                    <div class="prof-item">
                        <div class="avatar-circle">
                            <img id="profAvatar" src="getImmagine?path=<%=immagini.get(u.getId())%>" alt="Profile" class="img-fluid rounded-circle" style="width: 80px; height: 80px; object-fit: cover; border: 3px solid var(--craft-gold);">
                        </div>

                        <div class="prof-info">
                            <h3>
                                <%= u.getNome() %> <%= u.getCognome() %>
                                <span class="rating-badge">
                                    <i class="fa-solid fa-star"></i>
                                    <%if(!recensioniProfessionista.isEmpty()){%>
                                        <%=media%> <span style="font-size: 0.75rem; opacity: 0.7;">(<%= recensioniProfessionista.size() %>)</span>
                                    <%}else{%>
                                        Nuova Mano
                                    <%}%>
                                </span>
                            </h3>
                            <span class="category-label">
                                <i class="fa-solid fa-tools" style="margin-right: 5px;"></i>
                                <%= request.getAttribute("nomeProfessione") %>
                            </span>
                        </div>
                        <div class="prof-actions">
                            <button type="button" class="btn-contatta btn-apri-modal"
                                    data-id="<%= u.getId() %>"
                                    data-media="<%=media%>">
                                Visualizza Dettagli
                            </button>
                        </div>
                    </div>
                <%}%>
            <%}else{%>
                <div class="no-data">
                    <i class="fa-solid fa-user-slash" style="font-size: 4rem; color: var(--steel-variant); margin-bottom: 20px;"></i>
                    <h2 style="color: white;">Nessun professionista trovato</h2>
                    <p>Non ci sono ancora esperti registrati per questa categoria.</p>
                    <a href="Homepage" style="color: var(--craft-gold); text-decoration: none; font-weight: bold;">Torna alla Home</a>
                </div>
            <%}%>
        </div>
    </div>

    <div class="modal fade" id="dettagliProfessionista" tabindex="-1" aria-labelledby="dettagliProfessionista">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content" style="background: var(--deep-steel); border-radius: 20px; border: 1px solid var(--steel-variant); color: var(--light-silver);">

                <div class="modal-header" style="border-bottom: 1px solid var(--steel-variant);">
                    <h5 class="modal-title" id="professionalDetailModalLabel" style="color: var(--craft-gold); border-left: 4px solid var(--craft-gold); padding-left: 10px;">
                        Dettagli Professionista
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body p-4">
                    <div id="modalLoader" class="text-center p-5">
                        <div class="spinner-border text-warning" role="status"></div>
                        <p class="mt-2">Recupero informazioni dal database...</p>
                    </div>

                    <div id="modalContent" class="row d-none">
                        <div class="col-md-4 text-center mb-4 mb-md-0">
                            <img id="profAvatarDetails" src="" alt="Profile" class="img-fluid rounded-circle mb-3" style="width: 150px; height: 150px; object-fit: cover; border: 3px solid var(--craft-gold);">
                            <div class="detail-group">
                                <span class="detail-label" style="color: var(--muted-silver); font-size: 0.8rem;">Tariffa</span>
                                <div class="detail-value" id="profTariffa" style="color: var(--craft-gold); font-size: 1.4rem; font-weight: bold;">--</div>
                            </div>
                            <div class="detail-group">
                                <span class="detail-label" style="color: var(--muted-silver); font-size: 0.8rem;">Media</span>
                                <div class="detail-value" id="profMedia" style="color: var(--craft-gold); font-size: 1.4rem; font-weight: bold;">--</div>
                            </div>
                        </div>

                        <div class="col-md-8">
                            <div class="row">
                                <div class="col-12 mb-3">
                                    <span class="detail-label" style="color: var(--muted-silver); font-size: 0.8rem; display: block;">Nome Completo</span>
                                    <div class="detail-value" id="profNomeCompleto" style="color: var(--white-text); font-size: 1.2rem; font-weight: 500;">--</div>
                                </div>
                            </div>

                            <div class="mb-4">
                                <span class="detail-label" style="color: var(--muted-silver); font-size: 0.8rem; display: block;">Professioni</span>
                                <div id="listaProfessioni" class="readonly-container"></div>
                            </div>

                            <div class="mb-3">
                                <span class="detail-label" style="color: var(--muted-silver); font-size: 0.8rem; display: block;">Veicoli</span>
                                <div id="listaVeicoli" class="readonly-container"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer" style="border-top: 1px solid var(--steel-variant);">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="background-color: var(--steel-variant); border: none;">Chiudi</button>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/includes/Footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/dettagliProfessionista.js"></script>
</body>
</html>