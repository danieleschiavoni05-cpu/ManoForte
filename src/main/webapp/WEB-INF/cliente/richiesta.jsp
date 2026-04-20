<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="org.elis.manoforte.model.*, java.util.List, java.util.stream.Collectors"%>

<%
    // Recupero dati
    Utente pro = (Utente) request.getAttribute("professionista");
    Utente utente = (Utente) request.getAttribute("utenteLoggato");
    List<Disponibilita> listaDisp = (List<Disponibilita>) request.getAttribute("listaDisponibilita");

    StringBuilder giorniLavoroSB = new StringBuilder();
    if (listaDisp != null) {
        for (int i = 0; i < listaDisp.size(); i++) {
            // Trasformiamo l'oggetto in stringa prima di usare toLowerCase()
            String giornoStr = String.valueOf(listaDisp.get(i).getGiorno_settimana()).toLowerCase();
            giorniLavoroSB.append(giornoStr);
            
            if (i < listaDisp.size() - 1) {
                giorniLavoroSB.append(",");
            }
        }
    }
    String giorniLavoro = giorniLavoroSB.toString();
%>

<!DOCTYPE html>
<html lang="it"><head>
    <meta charset="UTF-8">
    <title>Invia Richiesta | ManoForte</title>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/spinning-background.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/richiesta-style.css">
</head>
<body>

	<jsp:include page="/WEB-INF/includes/Navbar.jsp" />

	<main class="main-content">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-12">

					<% if (pro != null && utente != null) { %>

					<div class="profile-card shadow-lg">
						<div class="profile-header">
							<div class="profile-avatar">
								<i class="fa-solid fa-calendar-check"></i>
							</div>
							<h2 class="fw-bold mb-1">Nuova Richiesta</h2>
							<p class="text-muted">
								Intervento con <strong><%=pro.getNome()%> <%=pro.getCognome()%></strong>
							</p>
						</div>

						<div class="p-4 p-md-5">
							<div class="availability-box">
								<span class="section-title"><i
									class="fa-solid fa-clock me-2"></i>Orari di Lavoro</span>
								<div class="row g-2">
									<% if(listaDisp != null && !listaDisp.isEmpty()) { 
                                        for(Disponibilita d : listaDisp) { %>
									<div class="col-6 col-sm-4 col-md-3">
										<div class="day-item">
											<small class="text-warning d-block fw-bold"><%= d.getGiorno_settimana() %></small>
											<small><%= d.getOra_inizio().toString().substring(0,5) %>
												- <%= d.getOra_fine().toString().substring(0,5) %></small>
										</div>
									</div>
									<% } } %>
								</div>
							</div>

							<form action="<%=request.getContextPath()%>/richiesta"
								method="post" id="formRichiesta">
								<input type="hidden" name="emailProfessionista"
									value="<%=pro.getEmail()%>"> <input type="hidden"
									name="ora_fine" id="oraFineHidden">

								<div class="mb-4">
									<label class="section-title">Indirizzo Intervento</label> <input
										type="text" name="indirizzo" class="form-control"
										placeholder="Via, Civico, Città" required>
								</div>

								<div class="mb-4">
									<label class="section-title">Descrizione</label>
									<textarea name="descrizione" class="form-control" rows="3"
										required></textarea>
								</div>

								<div class="row g-3">
									<div class="col-md-6">
										<label class="section-title">Data Intervento</label> <input
											type="date" name="giorni" id="dataScelta"
											class="form-control" min="<%= java.time.LocalDate.now() %>"
											required>
										<div id="dataFeedback" class="info-label">Scegli un
											giorno lavorativo</div>
									</div>

									<div class="col-md-6">
										<label class="section-title">Durata Stimata</label> <select
											id="durataOre" class="form-select" required>
											<option value="1">1 Ora</option>
											<option value="2">2 Ore</option>
											<option value="3">3 Ore</option>
											<option value="4">4 Ore</option>
											<option value="5">5 Ore</option>
										</select>
									</div>

									<div class="col-md-12">
										<label class="section-title">Orario di Inizio</label> <select
											name="ora_inizio" id="oraInizio" class="form-select" required
											disabled>
											<option value="">Seleziona prima la data...</option>
										</select>
										<div id="orarioFineAnteprima"
											class="info-label text-info mt-2"></div>
									</div>
								</div>

								<div class="mt-5">
									<button type="submit" class="btn-submit" id="btnSubmit">INVIA
										RICHIESTA</button>
								</div>
							</form>
						</div>
					</div>

					<% } %>

				</div>
			</div>
		</div>
	</main>

	<script>
    // 1. Mappa delle disponibilità (Java -> JS)
    const disponibilità = {
        <% if(listaDisp != null) { 
            for(Disponibilita d : listaDisp) { 
                String giornoChiave = String.valueOf(d.getGiorno_settimana()).toLowerCase().trim();
        %>
            "<%= giornoChiave %>": { 
                inizio: "<%= d.getOra_inizio() %>", 
                fine: "<%= d.getOra_fine() %>" 
            },
        <% } } %>
    };

    const inputData = document.getElementById('dataScelta');
    const selectInizio = document.getElementById('oraInizio');
    const selectDurata = document.getElementById('durataOre');
    const anteprimaFine = document.getElementById('orarioFineAnteprima');
    const oraFineHidden = document.getElementById('oraFineHidden');

    // Funzione helper per ottenere il giorno in inglese (come le chiavi della mappa)
    function getGiornoInglese(dataValue) {
        const dataObj = new Date(dataValue);
        return dataObj.toLocaleDateString('en-US', { weekday: 'long' }).toLowerCase();
    }

    // 2. Evento Cambio Data
    inputData.addEventListener('change', function() {
        if(!this.value) return;
        
        const giornoSettimana = getGiornoInglese(this.value);
        const dispGiorno = disponibilità[giornoSettimana];

        selectInizio.innerHTML = '<option value="" disabled selected>Scegli orario...</option>';
        
        if (!dispGiorno) {
            alert("Il professionista non lavora di " + giornoSettimana);
            this.value = '';
            selectInizio.disabled = true;
            anteprimaFine.innerHTML = "";
            oraFineHidden.value = "";
            return;
        }

        selectInizio.disabled = false;
        
        // Popoliamo gli orari di inizio basandoci su HH:mm:ss o HH:mm
        const hInizio = parseInt(dispGiorno.inizio.substring(0,2));
        const hFine = parseInt(dispGiorno.fine.substring(0,2));

        for (let h = hInizio; h < hFine; h++) {
            let opt = document.createElement('option');
            let oraStr = h.toString().padStart(2, '0') + ":00";
            opt.value = oraStr;
            opt.textContent = oraStr;
            selectInizio.appendChild(opt);
        }
        
        // Se c'è già una durata, calcola la fine
        aggiornaOrarioFine();
    });

    // 3. Funzione Calcolo Orario Fine (CORRETTA)
    function aggiornaOrarioFine() {
        if (!selectInizio.value || !inputData.value) {
            oraFineHidden.value = "";
            return;
        }

        const giornoSettimana = getGiornoInglese(inputData.value);
        const dispGiorno = disponibilità[giornoSettimana];

        if (!dispGiorno) return;

        const limiteFineH = parseInt(dispGiorno.fine.substring(0,2));
        const inizioH = parseInt(selectInizio.value.split(':')[0]);
        const durata = parseInt(selectDurata.value);
        const fineH = inizioH + durata;

        if (fineH > limiteFineH) {
            anteprimaFine.innerHTML = `<span class="text-danger"><i class="fa-solid fa-triangle-exclamation"></i> Troppo tardi! Il professionista termina alle ${limiteFineH}:00</span>`;
            oraFineHidden.value = ""; // Svuota il valore per bloccare l'invio
            document.getElementById('btnSubmit').disabled = true;
        } else {
            // Formattiamo per MySQL: HH:mm:ss.000000
            const fineStr = fineH.toString().padStart(2, '0') + ":00:00.000000";
            anteprimaFine.innerHTML = `<span class="text-success"><i class="fa-solid fa-circle-check"></i> L'intervento terminerà alle ore <strong>${fineH}:00</strong></span>`;
            
            oraFineHidden.value = fineStr; // Valore salvato correttamente
            document.getElementById('btnSubmit').disabled = false;
        }
    }

    // Listener per ricalcolare in tempo reale
    selectInizio.addEventListener('change', aggiornaOrarioFine);
    selectDurata.addEventListener('change', aggiornaOrarioFine);

    // 4. Validazione finale
    document.getElementById('formRichiesta').addEventListener('submit', function(e) {
        if (!oraFineHidden.value || oraFineHidden.value === "") {
            e.preventDefault();
            alert("Per favore, seleziona una data e un orario validi entro i limiti di disponibilità.");
        }
    });
</script>

	<jsp:include page="/WEB-INF/includes/Footer.jsp" />
</body>
</html>