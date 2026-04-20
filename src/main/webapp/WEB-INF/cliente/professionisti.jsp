<%@page import="java.util.Map"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="org.elis.manoforte.model.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Professionisti | ManoForte</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/color-var.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/footer.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/spinning-background.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/home_professionista-style.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/professionista-style.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/modifica_professionista-style.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style-modificaProfilo.css">

<style>
:root {
	--primary: #d4af37; /* Oro */
	--obsidian-base: #121212;
	--muted-silver: #a0a0a0;
	--white-text: #ffffff;
}

body {
	background-color: var(--obsidian-base);
	color: var(--white-text);
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Container Filtri */
.filter-section {
	background: rgba(255, 255, 255, 0.03);
	backdrop-filter: blur(15px);
	border-radius: 20px;
	padding: 30px;
	border: 1px solid rgba(212, 175, 55, 0.15);
	margin-bottom: 50px;
}

.filter-label {
	font-size: 0.8rem;
	text-transform: uppercase;
	letter-spacing: 1px;
	color: var(--primary);
	margin-bottom: 8px;
	font-weight: 700;
}

.form-control, .form-select {
	background-color: rgba(0, 0, 0, 0.3) !important;
	border: 1px solid rgba(255, 255, 255, 0.1) !important;
	color: white !important;
	height: 45px;
}

.form-control:focus, .form-select:focus {
	border-color: var(--primary) !important;
	box-shadow: 0 0 0 0.25rem rgba(212, 175, 55, 0.1) !important;
}

/* Card Professionista */
.rec-card {
	background: #1a1a1a;
	border: 1px solid rgba(255, 255, 255, 0.05);
	border-radius: 18px;
	transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
	position: relative;
	overflow: hidden;
	height: 100%;
	display: flex;
	flex-direction: column;
}

.rec-card:hover {
	transform: translateY(-12px);
	border-color: var(--primary);
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.5);
}

.rec-card::before {
	content: "";
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 4px;
	background: linear-gradient(90deg, transparent, var(--primary),
		transparent);
}

.badge-profession {
	background: rgba(212, 175, 55, 0.1) !important;
	color: var(--primary) !important;
	border: 1px solid rgba(212, 175, 55, 0.3);
	font-size: 0.7rem;
	padding: 5px 10px;
}

.btn-contact {
	background: var(--primary);
	color: #000 !important;
	border: none;
	border-radius: 12px;
	padding: 12px;
	font-weight: 700;
	transition: 0.3s;
}

.btn-contact:hover {
	background: #f1c40f;
	transform: scale(1.02);
	box-shadow: 0 5px 15px rgba(212, 175, 55, 0.3);
}

.stars {
	color: var(--primary);
}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/includes/Navbar.jsp" />

	<div class="container py-5">
		<div class="text-center mb-5">
			<h1 class="fw-bold">
				I NOSTRI <span style="color: var(--primary);">PROFESSIONISTI</span>
			</h1>
			<p class="text-muted">Esperti verificati pronti ad aiutarti</p>
		</div>

		<div class="filter-section shadow">
			<div class="row g-4">
				<div class="col-md-4 col-lg-2">
					<label class="filter-label">Nome</label> <input type="text"
						id="fNome" class="form-control" placeholder="Cerca...">
				</div>

				<div class="col-md-4 col-lg-2">
					<label class="filter-label">Città</label> <select id="fCitta"
						class="form-select">
						<option value="all">Tutte</option>
						<% 
                        List<Citta> listaCitta = (List<Citta>) request.getAttribute("listaCitta");
                        if(listaCitta != null) {
                            for(Citta c : listaCitta) { 
                        %>
						<option value="<%= c.getNome().toLowerCase() %>"><%= c.getNome() %></option>
						<% } } %>
					</select>
				</div>

				<div class="col-md-4 col-lg-2">
					<label class="filter-label">Professione</label> <select
						id="fProfessione" class="form-select">
						<option value="all">Tutte</option>
						<% 
                        List<Professione> tutteLeProf = (List<Professione>) request.getAttribute("listaProfessioni");
                        if(tutteLeProf != null) {
                            for(Professione pr : tutteLeProf) { %>
						<option value="<%= pr.getNome().toLowerCase() %>"><%= pr.getNome() %></option>
						<% } } %>
					</select>
				</div>


				<div class="col-md-4 col-lg-2">
					<label class="filter-label">Valutazione</label> <select id="fVoto"
						class="form-select">
						<option value="0">Tutte</option>
						<option value="4">4+ Stelle</option>
						<option value="3">3+ Stelle</option>
					</select>
				</div>

				<div class="col-md-4 col-lg-2">
					<label class="filter-label">Prezzo Max</label> <input type="number"
						id="fTariffa" class="form-control" placeholder="€/h">
				</div>
			</div>
		</div>

		<div class="row g-4" id="listaContainer">
			<%
                List<Utente> listaPro = (List<Utente>) request.getAttribute("listaProfessionisti");
                Map<String, BigDecimal> medie = (Map<String, BigDecimal>) request.getAttribute("mappaMedie");

                if (listaPro != null && !listaPro.isEmpty()) {
                    for (Utente u : listaPro) {
                        
                        // 1. Nome Città
                        String nomeCitta = "N/D";
                        if (listaCitta != null && u.getCitta() != null) {
                            for (Citta c : listaCitta) {
                                if (c.getId().equals(u.getCitta().getId())) {
                                    nomeCitta = c.getNome();
                                    break;
                                }
                            }
                        }

                        // 2. Professioni
                        StringBuilder sbProf = new StringBuilder();
                        List<Professione> profs = u.getProfessione(); 
                        if (profs != null) {
                            for(Professione p : profs) sbProf.append(p.getNome().toLowerCase()).append(" ");
                        }

                        // 3. Tariffa e Voto
                        BigDecimal tariffa = u.getTariffa() != null ? u.getTariffa() : BigDecimal.ZERO;
                        BigDecimal voto = (medie != null && medie.get(u.getEmail()) != null) ? medie.get(u.getEmail()) : BigDecimal.ZERO;
            %>

			<div class="col-md-6 col-lg-4 pro-item"
				data-nome="<%= u.getNome().toLowerCase() %> <%= u.getCognome().toLowerCase() %>"
				data-citta="<%= nomeCitta.toLowerCase() %>" data-voto="<%= voto %>"
				data-tariffa="<%= tariffa %>"
				data-professioni="<%= sbProf.toString().trim() %>"
				data-giorni="lunedì martedì mercoledì giovedì venerdì">
				<div class="rec-card p-4">
					<div class="d-flex justify-content-between mb-3">
						<div>
							<h5 class="fw-bold mb-0" style="color: var(--primary);"><%= u.getNome() %>
								<%= u.getCognome() %></h5>
							<small class="text-muted"><i
								class="fa-solid fa-map-pin me-1"></i><%= nomeCitta %></small>
						</div>
						<span class="badge bg-dark border border-secondary text-primary"
							style="font-size: 0.6rem; height: fit-content;">PRO</span>
					</div>

					<div class="mb-3">
						<% if (profs != null) { for(Professione p : profs) { %>
						<span class="badge badge-profession rounded-pill me-1"><%= p.getNome() %></span>
						<% } } %>
					</div>

					<div class="mb-3">
						<span class="stars"> <% for(int i=1; i<=5; i++) { %> <i
							class="<%= (voto.doubleValue() >= i) ? "fa-solid" : "fa-regular" %> fa-star"></i>
							<% } %>
						</span> <small class="ms-2 text-muted">(<%= String.format("%.1f", voto) %>)
						</small>
					</div>

					<div class="mt-auto">
						<p class="h4 fw-bold mb-3"><%= tariffa %>
							€ <small class="fs-6 fw-normal text-muted">/ora</small>
						</p>
						<div class="d-grid">
							<a
								href="<%=request.getContextPath()%>/richiesta?emailPro=<%= u.getEmail() %>"
								class="btn btn-contact"> <i
								class="fa-regular fa-paper-plane me-2"></i>Invia Richiesta
							</a>
						</div>
					</div>
				</div>
			</div>

			<% } } else { %>
			<div class="col-12 text-center py-5">
				<h3 class="text-muted">Nessun professionista disponibile</h3>
			</div>
			<% } %>
		</div>
	</div>

	<jsp:include page="/WEB-INF/includes/Footer.jsp" />

	<script>
        document.addEventListener('DOMContentLoaded', () => {
            const fNome = document.getElementById('fNome');
            const fProf = document.getElementById('fProfessione');
            const fGiorno = document.getElementById('fGiorno');
            const fCitta = document.getElementById('fCitta');
            const fVoto = document.getElementById('fVoto');
            const fTariffa = document.getElementById('fTariffa');
            const items = document.querySelectorAll('.pro-item');

            const filterEngine = () => {
                const valNome = fNome.value.toLowerCase();
                const valProf = fProf.value;
                const valGiorno = fGiorno.value;
                const valCitta = fCitta.value;
                const valVoto = parseFloat(fVoto.value);
                const valTariffa = parseFloat(fTariffa.value) || Infinity;

                items.forEach(item => {
                    const d = item.dataset;
                    const match = d.nome.includes(valNome) &&
                                  (valProf === 'all' || d.professioni.includes(valProf)) &&
                                  (valGiorno === 'all' || d.giorni.includes(valGiorno)) &&
                                  (valCitta === 'all' || d.citta === valCitta) &&
                                  parseFloat(d.voto) >= valVoto &&
                                  parseFloat(d.tariffa) <= valTariffa;

                    item.style.display = match ? "block" : "none";
                });
            };

            [fNome, fProf, fGiorno, fCitta, fVoto, fTariffa].forEach(el => {
                el.addEventListener('input', filterEngine);
                el.addEventListener('change', filterEngine);
            });
        });
    </script>
</body>
</html>