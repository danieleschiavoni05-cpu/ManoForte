<%@page import="java.time.LocalTime"%>
<%@page import="org.elis.manoforte.model.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lista Professionisti | ManoForte</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    
    <style>
        body { background-color: #121212; color: white; min-height: 100vh; }
        .filter-section { 
            background: rgba(255,255,255,0.05); 
            border-radius: 15px; 
            padding: 25px; 
            border: 1px solid rgba(255,255,255,0.1);
            backdrop-filter: blur(10px);
        }
        .professionista-card { 
            background: white; 
            border-radius: 15px; 
            transition: all 0.3s ease; 
            border: none; 
            color: #333; 
            overflow: hidden;
        }
        .professionista-card:hover { 
            transform: translateY(-8px); 
            box-shadow: 0 15px 30px rgba(0,0,0,0.4); 
        }
        .status-dot { 
            width: 12px; 
            height: 12px; 
            border-radius: 50%; 
            display: inline-block; 
        }
        .prof-tag { 
            background: rgba(39, 154, 241, 0.1); 
            color: #279AF1;
            padding: 4px 12px; 
            border-radius: 20px; 
            font-size: 0.75rem; 
            font-weight: bold;
            margin-right: 5px; 
        }
        .form-control, .form-select {
            border-radius: 8px;
        }
    </style>
</head>

<body>
    <jsp:include page="/WEB-INF/includes/Navbar.jsp" />

    <div class="container py-5">
        <div class="text-center mb-5">
            <h1 class="fw-bold text-white">I Nostri <span style="color: var(--craft-gold);">Professionisti</span></h1>
            <p class="text-white-50">Trova l'esperto perfetto filtrando per nome, città, voto o prezzo</p>
        </div>

        <div class="filter-section mb-5 shadow">
            <div class="row g-3">
                <div class="col-md-3">
                    <label class="small text-white-50 fw-bold mb-1">Cerca Nome</label>
                    <input type="text" id="fNome" class="form-control bg-dark text-white border-secondary" placeholder="Es. Mario Rossi...">
                </div>

                <div class="col-md-3">
                    <label class="small text-white-50 fw-bold mb-1">Città</label>
                    <select id="fCitta" class="form-select bg-dark text-white border-secondary">
                        <option value="all">Tutte le città</option>
                        <% 
                        List<Citta> listaCitta = (List<Citta>) request.getAttribute("listaCitta");
                        if(listaCitta != null) {
                            for(Citta c : listaCitta) { 
                        %>
                            <option value="<%= c.getNome().toLowerCase() %>"><%= c.getNome() %></option>
                        <% 
                            }
                        } 
                        %>
                    </select>
                </div>

                <div class="col-md-3">
                    <label class="small text-white-50 fw-bold mb-1">Valutazione Minima</label>
                    <select id="fVoto" class="form-select bg-dark text-white border-secondary">
                        <option value="0">Tutti i voti</option>
                        <option value="4">4+ Stelle ⭐</option>
                        <option value="3">3+ Stelle ⭐</option>
                        <option value="2">2+ Stelle ⭐</option>
                    </select>
                </div>

                <div class="col-md-3">
                    <label class="small text-white-50 fw-bold mb-1">Tariffa Max (€/h)</label>
                    <input type="number" id="fTariffa" class="form-control bg-dark text-white border-secondary" placeholder="Es. 50">
                </div>
            </div>
        </div>

        <div class="row g-4" id="listaContainer">
            <%
                List<Utente> listaPro = (List<Utente>) request.getAttribute("listaProfessionisti");
                List<Disponibilita> dispo = (List<Disponibilita>) request.getAttribute("disponibilita");
                LocalTime ora = LocalTime.now();
                java.time.DayOfWeek oggi = java.time.LocalDate.now().getDayOfWeek();

                if (listaPro != null && !listaPro.isEmpty()) {
                    for (Utente u : listaPro) {
                        // 1. Logica Disponibilità
                        boolean isDisponibile = false;
                        if (dispo != null) {
                            for (Disponibilita d : dispo) {
                                if (d.getGiorno_settimana() == oggi && !ora.isBefore(d.getOra_inizio()) && !ora.isAfter(d.getOra_fine())) {
                                    isDisponibile = true; 
                                    break;
                                }
                            }
                        }
                        
                        // 2. Recupero Nome Città da ID
                        String nomeCittaVisualizzato = "N/D";
                        if (listaCitta != null) {
                            for (Citta c : listaCitta) {
                                if (c.getId() == u.getIdCitta()) {
                                    nomeCittaVisualizzato = c.getNome();
                                    break;
                                }
                            }
                        }

                        // 3. Dati Mock (Sostituire con u.getTariffa() e u.getMediaVoti() se presenti)
                        double tariffaPro = 35.0; 
                        double votoPro = 4.2;    
            %>
            <div class="col-md-6 col-lg-4 pro-item" 
                 data-nome="<%= u.getNome().toLowerCase() %> <%= u.getCognome().toLowerCase() %>"
                 data-citta="<%= nomeCittaVisualizzato.toLowerCase() %>"
                 data-voto="<%= votoPro %>"
                 data-tariffa="<%= tariffaPro %>">
                
                <div class="professionista-card p-4 h-100 shadow-sm">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div>
                            <h5 class="fw-bold mb-0 text-dark"><%= u.getNome() %> <%= u.getCognome() %></h5>
                            <small class="text-muted"><i class="fa-solid fa-map-pin me-1"></i><%= nomeCittaVisualizzato %></small>
                        </div>
                        <div class="text-end">
                            <span class="status-dot" style="background-color: <%= isDisponibile ? "#2ecc71" : "#e74c3c" %>"></span>
                            <div class="small text-muted" style="font-size: 0.7rem;"><%= isDisponibile ? "Online" : "Offline" %></div>
                        </div>
                    </div>

                    <div class="mb-3 text-warning">
                        <% for(int i=1; i<=5; i++) { %>
                            <i class="<%= (i <= Math.round(votoPro)) ? "fa-solid" : "fa-regular" %> fa-star"></i>
                        <% } %>
                        <span class="text-muted small ms-1">(<%= votoPro %>)</span>
                    </div>

                    <div class="mb-4">
                        <div class="mb-2">
                            <span class="prof-tag">Professionista Verificato</span>
                        </div>
                        <p class="h5 fw-bold text-success mb-0"><%= tariffaPro %> €/ora</p>
                    </div>

                    <div class="d-grid pt-2">
                        <% if (isDisponibile) { %>
                            <a href="<%=request.getContextPath()%>/richiesta?emailPro=<%= u.getEmail() %>" 
                               class="btn btn-primary fw-bold py-2 shadow-sm" style="border-radius: 10px;">
                                <i class="fa-regular fa-paper-plane me-2"></i>Invia Richiesta
                            </a>
                        <% } else { %>
                            <button class="btn btn-secondary disabled py-2" style="border-radius: 10px; opacity: 0.6;">
                                <i class="fa-solid fa-clock me-2"></i>Non Disponibile
                            </button>
                        <% } %>
                    </div>
                </div>
            </div>
            <% 
                    } 
                } else { 
            %>
                <div class="col-12 text-center py-5">
                    <i class="fa-solid fa-magnifying-glass fa-3x mb-3 text-white-50"></i>
                    <h3 class="text-white-50">Nessun professionista trovato</h3>
                    <p class="text-muted">Prova a cambiare i filtri di ricerca</p>
                </div>
            <% } %>
        </div>
    </div>

    <jsp:include page="/WEB-INF/includes/Footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const fNome = document.getElementById('fNome');
            const fCitta = document.getElementById('fCitta');
            const fVoto = document.getElementById('fVoto');
            const fTariffa = document.getElementById('fTariffa');
            const items = document.querySelectorAll('.pro-item');

            const filterEngine = () => {
                const queryNome = fNome.value.toLowerCase().trim();
                const queryCitta = fCitta.value.toLowerCase();
                const queryVoto = parseFloat(fVoto.value);
                const queryTariffa = parseFloat(fTariffa.value) || Infinity;

                items.forEach(item => {
                    const dataNome = item.dataset.nome;
                    const dataCitta = item.dataset.citta;
                    const dataVoto = parseFloat(item.dataset.voto);
                    const dataTariffa = parseFloat(item.dataset.tariffa);

                    const matchNome = dataNome.includes(queryNome);
                    const matchCitta = (queryCitta === 'all' || dataCitta === queryCitta);
                    const matchVoto = dataVoto >= queryVoto;
                    const matchTariffa = dataTariffa <= queryTariffa;

                    if (matchNome && matchCitta && matchVoto && matchTariffa) {
                        item.classList.remove('d-none');
                    } else {
                        item.classList.add('d-none');
                    }
                });
            };

            // Eventi per il filtraggio in tempo reale
            fNome.addEventListener('input', filterEngine);
            fCitta.addEventListener('change', filterEngine);
            fVoto.addEventListener('change', filterEngine);
            fTariffa.addEventListener('input', filterEngine);
        });
    </script>
</body>
</html>