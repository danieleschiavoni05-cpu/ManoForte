<%@page import="java.util.Map"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="org.elis.manoforte.model.*"%>
<%@page import="java.util.List"%>
<%@ page import="org.elis.manoforte.utility.Utility" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!--Creare mappa(id_prof-nome_img) poi fare controllo per stampare l' img -->
<%
    Cookie effect = null;
    Cookie[] cookies = request.getCookies();
    if(cookies!=null){
      for(Cookie c:cookies){
        if(c.getName().equals("effect")){
          effect = c;
          break;
        }
      }
    }
  %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Professionisti | ManoForte</title>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/spinning-background.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home_professionista-style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/professionista-style.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/paginaProfessionisti-style.css">
	
</head>

  <body class="rotation">
	<jsp:include page="/WEB-INF/includes/Navbar.jsp" />

	<div class="container py-5">
		<div class="text-center mb-5 mt-4">
			<h1 class="fw-bold" style="color: white;">
				I NOSTRI <span class="text-primary-custom">PROFESSIONISTI</span>
			</h1>
			<p class="text-white-50">Esperti verificati pronti ad aiutarti</p>
		</div>
		<div class="button-row with-nav">
          <div class="form-check form-switch bg-dark p-2 rounded-3 text-white opacity-75">
            <input class="form-check-input ms-0" type="checkbox" id="disableEffect" <%=(effect!=null && effect.getValue().equals("true"))?"checked":""%>>
            <label class="form-check-label ms-2" for="disableEffect">
              Effettis
            </label>
          </div>
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
				Map<Long, String> propics = (Map<Long, String>) request.getAttribute("propics");

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

                        // 4. Propic
                        String propicUrl = Utility.DEFAULT_PROPIC_PATH;
                        if(propics!=null &&propics.get(u.getId()) != null) {
							propicUrl = "getImmagine?path=" + propics.get(u.getId());
                        }
            %>

			<div class="col-md-6 col-lg-4 pro-item" data-nome="<%= u.getNome().toLowerCase() %> <%= u.getCognome().toLowerCase() %>"
				data-citta="<%= nomeCitta.toLowerCase() %>" data-voto="<%= voto %>" data-tariffa="<%= tariffa %>"
				data-professioni="<%= sbProf.toString().trim() %>" data-giorni="lunedì martedì mercoledì giovedì venerdì">
				<div class="rec-card p-4">
					<div class="d-flex justify-content-between mb-3">
						<div class="d-flex align-items-center gap-3">
							<div class="profile-thumb">
								<img src="<%=propicUrl%>" alt="Foto profilo" class="img-fluid">
							</div>
							<div>
								<h5 class="fw-bold mb-0 text-primary-custom"><%= u.getNome() %> <%= u.getCognome() %></h5>
								<small class="text-white-50"><i class="fa-solid fa-map-pin me-1"></i><%= nomeCitta %></small>
							</div>
						</div>
						<span class="badge border text-primary-custom"
							style="font-size: 0.6rem; height: fit-content; background: rgba(99, 102, 241, 0.15); border-color: rgba(99, 102, 241, 0.3) !important;">PRO</span>
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
						</span> <small class="ms-2 text-white-50">(<%= String.format("%.1f", voto) %>)
						</small>
					</div>

					<div class="mt-auto">
						<p class="h4 fw-bold mb-3 text-white"><%= tariffa %>
							€ <small class="fs-6 fw-normal text-white-50">/ora</small>
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
				<h3 class="text-white-50">Nessun professionista disponibile</h3>
			</div>
			<% } %>
		</div>
	</div>

	<jsp:include page="/WEB-INF/includes/Footer.jsp" />
	
	<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

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

                // Se non c'è un campo giorni usiamo fallback (siccome non c'è nell'html ho aggiunto il null check)
                const valGiorno = fGiorno ? fGiorno.value : 'all';

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
                if(el) {
                    el.addEventListener('input', filterEngine);
                    el.addEventListener('change', filterEngine);
                }
            });
        });
    </script>
</body>
</html>