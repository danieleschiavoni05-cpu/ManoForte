<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.elis.manoforte.model.Utente, org.elis.manoforte.model.Professione, java.util.List"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Invia Richiesta | ManoForte</title>

    <link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/color-var.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style-recensioniPro.css?v=<%=System.currentTimeMillis()%>">
</head>

<body>

    <jsp:include page="/WEB-INF/includes/Navbar.jsp" />

    <main class="main-content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">

                    <%
                    // Recupero il professionista e l'utente loggato
                    Utente pro = (Utente) request.getAttribute("listaProfessionisti");
                    Utente utente = (Utente) request.getAttribute("utenteLoggato");

                    if (pro != null && utente != null) {
                    %>

                    <div class="profile-card shadow">
                        <div class="profile-header">
                            <div class="profile-avatar">
                                <i class="fa-solid fa-paper-plane"></i>
                            </div>
                            <h2 class="fw-bold">Nuova Richiesta</h2>
                            <p class="text-white opacity-75">Compila i dettagli per l'intervento</p>
                        </div>

                        <div class="row g-3 mb-4">
                            <div class="col-12">
                                <div style="background: rgba(212, 175, 55, 0.05); padding: 15px; border-radius: 12px; border: 1px solid rgba(212, 175, 55, 0.2);">
                                    <div class="d-flex align-items-center mb-2">
                                        <i class="fa-solid fa-user-gear me-2" style="color: var(--craft-gold);"></i>
                                        <span class="section-title m-0">Destinatario</span>
                                    </div>
                                    
                                    <strong class="text-white"><%=pro.getNome()%> <%=pro.getCognome()%></strong>
                                </div>
                            </div>
                        </div>
						

						<form action="<%=request.getContextPath()%>/richiesta" method="post">
                            <input type="hidden" name="emailProfessionista" value="<%=pro.getEmail()%>"> 
                            <input type="hidden" name="emailBase" value="<%=utente.getEmail()%>">

                            <div class="mb-3">
                                <label class="section-title">Indirizzo dell'intervento</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-dark border-secondary text-white"><i class="fa-solid fa-location-dot"></i></span> 
                                    <input type="text" name="indirizzo" class="form-control" placeholder="Via Roma 10, Milano" required>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label class="section-title">Dettagli della richiesta</label>
                                <textarea name="descrizione" class="form-control" rows="4" placeholder="Descrivi il guasto o l'intervento..." required></textarea>
                            </div>

                            <% String oggi = java.time.LocalDate.now().toString(); %>

                            <div class="mb-3">
                                <label class="section-title">Giorno dell'intervento</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-dark border-secondary text-white"><i class="fa-solid fa-calendar-days"></i></span> 
                                    <input type="date" name="giorni" class="form-control" min="<%=oggi%>" value="<%=oggi%>" required>
                                </div>
                            </div>

                            <div class="row mb-4">
                                <div class="col-md-6">
                                    <label class="section-title">Ora Inizio</label>
                                    <select name="ora_inizio" class="form-select" required>
                                        <option value="" disabled selected>Scegli ora...</option>
                                        <% for (int h = 0; h < 24; h++) {
                                            for (int m = 0; m < 60; m += 30) {
                                                String orario = String.format("%02d:%02d", h, m);
                                        %>
                                        <option value="<%=orario%>"><%=orario%></option>
                                        <% } } %>
                                    </select>
                                </div>

                                <div class="col-md-6">
                                    <label class="section-title">Ora Fine</label>
                                    <select name="ora_fine" class="form-select" required>
                                        <option value="" disabled selected>Scegli ora...</option>
                                        <% for (int h = 0; h < 24; h++) {
                                            for (int m = 0; m < 60; m += 30) {
                                                String orario = String.format("%02d:%02d", h, m);
                                        %>
                                        <option value="<%=orario%>"><%=orario%></option>
                                        <% } } %>
                                    </select>
                                </div>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="login-register-button">
                                    Invia richiesta a <%=pro.getNome()%>
                                </button>
                                <a href="<%=request.getContextPath()%>/homeBase" class="btn-back text-center"> 
                                    <i class="fa-solid fa-xmark me-2"></i>Annulla 
                                </a>
                            </div>
                        </form>
                    </div>

                    <% } else { %>

                    <div class="no-data text-center p-5">
                        <i class="fa-solid fa-triangle-exclamation fa-3x mb-3" style="color: var(--craft-gold);"></i> 
                        <h3 class="text-white">Errore: Professionista non trovato</h3>
                        <a href="<%=request.getContextPath()%>/homeBase" class="btn-back mt-3">Torna alla Home</a>
                    </div>

                    <% } %>

                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/includes/Footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>