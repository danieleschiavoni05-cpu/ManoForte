<%@page import="org.elis.manoforte.model.Utente"%>
<%@page import="org.elis.manoforte.model.Professione"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lista Professionisti | ManoForte</title>

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
        <div class="container pb-5">
            <div class="text-center mb-5">
                <h1 class="fw-bold" style="color: #ffffff;">I Nostri <span style="color: var(--craft-gold);">Professionisti</span></h1>
                <p style="color: rgba(255,255,255,0.8);">Seleziona un esperto per inviare una richiesta d'intervento</p>
            </div>
            
            <div class="row mb-4 justify-content-center">
    <div class="col-md-8">
        <div class="card p-3" style="background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1); border-radius: 15px;">
            <div class="row g-3">
                <div class="col-md-6">
                    <label class="form-label text-white-50 small">Cerca per nome</label>
                    <div class="input-group">
                        <span class="input-group-text bg-transparent border-secondary text-white-50"><i class="fa-solid fa-magnifying-glass"></i></span>
                        <input type="text" id="filterNome" class="form-control bg-transparent text-white border-secondary" placeholder="Es. Mario Rossi...">
                    </div>
                </div>
                <div class="col-md-6">
                    <label class="form-label text-white-50 small">Stato Disponibilità</label>
                    <select id="filterDisponibilita" class="form-select bg-transparent text-white border-secondary">
                        <option value="all" class="text-dark">Tutti i professionisti</option>
                        <option value="disponibile" class="text-dark">Disponibile Ora</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>

            <div class="row g-4">
            <%
            List<Utente> listaProfessionisti = (List<Utente>) request.getAttribute("listaProfessionisti");
            List<Professione> listaProfessioni = (List<Professione>) request.getAttribute("listaProfessioni");
            %>
            <%
            if (listaProfessionisti != null && !listaProfessionisti.isEmpty()) {
                for (Utente u : listaProfessionisti) {
                    // Supponiamo che u.isDisponibile() restituisca un boolean. 
                    // Se il metodo ha un nome diverso, cambialo qui sotto.
                    
            %>
                <div class="col-md-6 col-lg-4 professionista-item" 
                     data-nome="<%= u.getNome().toLowerCase() %> <%= u.getCognome().toLowerCase() %>" 
                     data-disponibile="<%= disponibile %>">
                    
                    <div class="review-card h-100 p-4 shadow-sm">
                        <div class="card-body">
                            <div class="d-flex align-items-center mb-3">
                                <div class="position-relative">
                                    <div class="profile-avatar m-0 me-3" style="width: 55px; height: 55px; font-size: 1.5rem; transform: none;">
                                        <i class="fa-solid fa-user-tie"></i>
                                    </div>
                                    <span class="position-absolute bottom-0 end-0 border border-2 border-dark rounded-circle" 
                                          style="width: 15px; height: 15px; background-color: <%= disponibile ? "#2ecc71" : "#e74c3c" %>; margin-right: 15px;">
                                    </span>
                                </div>
                                
                                <div class="ms-1">
                                    <h5 class="fw-bold mb-0" style="color: #ffffff;">
                                        <%= u.getNome() %> <%= u.getCognome() %>
                                    </h5>
                                    <small style="color: var(--craft-gold); font-weight: 600; text-transform: uppercase; font-size: 0.7rem; letter-spacing: 1px;">
                                        <%= disponibile ? "Disponibile ora" : "Non disponibile" %>
                                    </small>
                                </div>
                            </div>

                        <div class="mb-3">
                            <% 
                            if (u.getProfessione() != null && listaProfessioni != null) {
                                for (Long id : u.getProfessione()) {
                                    for (Professione p : listaProfessioni) {
                                        if (p.getId() == id.longValue()) { %>
                                            <span class="badge bg-info text-dark me-1"><%= p.getNome() %></span>
                                        <% break; }
                                    }
                                } 
                                %>
                            </div>

                            <div class="d-grid mt-4">
                                <a href="<%=request.getContextPath()%>/richiesta?emailPro=<%= u.getEmail() %>" 
                                   class="login-register-button text-center" style="text-decoration: none; padding: 10px; font-size: 0.85rem;">
                                    Invia Richiesta
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            <%
                }
            } else {
            %>
                <div class="col-12 text-center py-5">
                    <div class="no-data p-5">
                        <i class="fa-solid fa-user-slash fa-3x mb-3" style="color: var(--craft-gold);"></i>
                        <h3 class="text-white">Nessun professionista trovato</h3>
                        <p class="text-white opacity-50">Non ci sono esperti disponibili per questa categoria al momento.</p>
                        <a href="<%=request.getContextPath()%>/homeBase" class="btn-back d-inline-block mt-3">
                            <i class="fa-solid fa-chevron-left me-2"></i>Torna indietro
                        </a>
                    </div>
                </div>
            <%
            }
            %>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/includes/Footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
document.addEventListener('DOMContentLoaded', function() {
    const inputNome = document.getElementById('filterNome');
    const selectDisponibilita = document.getElementById('filterDisponibilita');
    const items = document.querySelectorAll('.professionista-item');

    function filtra() {
        const queryNome = inputNome.value.toLowerCase();
        const queryDisp = selectDisponibilita.value;

        items.forEach(item => {
            const nomePro = item.getAttribute('data-nome');
            const isDisponibile = item.getAttribute('data-disponibile') === 'true';

            // Logica del filtro
            const matchNome = nomePro.includes(queryNome);
            const matchDisp = (queryDisp === 'all') || (queryDisp === 'disponibile' && isDisponibile);

            if (matchNome && matchDisp) {
                item.style.display = 'block';
            } else {
                item.style.display = 'none';
            }
        });
    }

    inputNome.addEventListener('input', filtra);
    selectDisponibilita.addEventListener('change', filtra);
});
</script>
</body>
</html>