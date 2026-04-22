<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="org.elis.manoforte.model.*, java.util.List, java.time.format.TextStyle, java.util.Locale, java.util.stream.Collectors"%>

<%
Utente pro = (Utente) request.getAttribute("professionista");
Utente utente = (Utente) request.getAttribute("utenteLoggato");
List<Disponibilita> listaDisp = (List<Disponibilita>) request.getAttribute("listaDisponibilita");
%>
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
<title>Invia Richiesta | ManoForte</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<link rel="stylesheet" type="text/css"
	href="https://npmcdn.com/flatpickr/dist/themes/material_blue.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/spinning-background.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home_professionista-style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/professionista-style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/modifica_professionista-style.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/richiesta-style.css">
</head>
<body>

	<jsp:include page="/WEB-INF/includes/Navbar.jsp" />

	<main class="main-content mt-5">
		<div class="container">
		<div class="button-row with-nav">
          <div class="form-check form-switch bg-dark p-2 rounded-3 text-white opacity-75">
            <input class="form-check-input ms-0" type="checkbox" id="disableEffect" <%=(effect!=null && effect.getValue().equals("true"))?"checked":""%>>
            <label class="form-check-label ms-2" for="disableEffect">
              Effettis
            </label>
          </div>
        </div>
			<div class="row justify-content-center">
				<div class="col-12 col-lg-10">

					<%
					if (pro != null && utente != null) {
					%>



					<div
						class="profile-card shadow-lg border-0 rounded-4 overflow-hidden">
						<div class="profile-header text-center p-4 bg-light">
							<h2 class="fw-bold mb-1">Nuova Richiesta</h2>
							<p class="text-muted">
								Intervento con <strong><%=pro.getNome()%> <%=pro.getCognome()%></strong>
							</p>
						</div>

						<div class="p-4 p-md-5">
							<div class="availability-box mb-4">
								<span class="section-title mb-3 d-block fw-bold text-uppercase">
									<i class="fa-solid fa-calendar-days me-2 text-primary"></i>Calendario
									Disponibilità
								</span>
								<div class="row g-3">
									<%
									if (listaDisp != null && !listaDisp.isEmpty()) {
										for (Disponibilita d : listaDisp) {
											String badgeClass = "";
											String tipoTesto = "";
											String infoGiorno = "";

											if (d.getTipo() == TipoDisponibilita.RICORSIVO) {
										badgeClass = "bg-primary";
										tipoTesto = "Ogni settimana";
										infoGiorno = d.getGiorno_settimana().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
											} else if (d.getTipo() == TipoDisponibilita.SINGOLO) {
										badgeClass = "bg-success";
										tipoTesto = "Solo per oggi";
										infoGiorno = d.getData().toString();
											} else if (d.getTipo() == TipoDisponibilita.ECCEZIONE) {
										badgeClass = "bg-danger";
										tipoTesto = "Non disponibile";
										infoGiorno = d.getData().toString();
											}
									%>
									<div class="col-md-4">
										<div class="card h-100 border-0 shadow-sm p-3">
											<span class="badge <%=badgeClass%> mb-2 w-fit"><%=tipoTesto%></span>
											<strong class="text-capitalize text-dark"><%=infoGiorno%></strong>
											<%
											if (d.getTipo() != TipoDisponibilita.ECCEZIONE) {
											%>
											<small class="text-muted"><%=d.getOra_inizio().toString().substring(0, 5)%>
												- <%=d.getOra_fine().toString().substring(0, 5)%></small>
											<%
											} else {
											%>
											<small class="text-danger">Assenza programmata</small>
											<%
											}
											%>
										</div>
									</div>
									<%
									}
									}
									%>
								</div>
							</div>

							<div class="p-4 p-md-5">
								<form action="<%=request.getContextPath()%>/richiesta"
									method="post" id="formRichiesta">
									<input type="hidden" name="emailProfessionista"
										value="<%=pro.getEmail()%>"> <input type="hidden"
										name="ora_fine" id="oraFineHidden">

									<div class="row g-4">
										<div class="col-12">
											<label class="form-label fw-bold">Indirizzo
												Intervento</label> <input type="text" name="indirizzo"
												class="form-control"
												placeholder="Spiega il tuo indirizzo al professionista."
												required>
										</div>
									</div>


									<div class="col-12">
										<label class="form-label fw-bold">Descrizione</label>
										<textarea name="descrizione" class="form-control" rows="3"
											placeholder="Spiega il tuo problema al professionista."
											required></textarea>
									</div>

									<div class="row g-4">
										<div class="col-md-6">
											<label class="form-label fw-bold">Data Intervento</label>
											<div class="input-group">
												<span class="input-group-text  border-dark"><i
													class="fa-solid fa-calendar text-primary"></i></span> <input
													type="text" name="giorni" id="dataScelta"
													class="form-control  border-dark fw-bold"
													placeholder="Seleziona una data..." readonly required>
											</div>
										</div>

										<div class="col-md-6">
											<label class="form-label fw-bold">Durata Stimata</label>
											<div class="input-group">
												<span class="input-group-text  border-dark"><i
													class="fa-solid fa-hourglass-half text-primary"></i></span> <select
													id="durataOre" class="form-select border-dark fw-bold">
													<option value="1">1 Ora</option>
													<option value="2">2 Ore</option>
													<option value="3">3 Ore</option>
													<option value="4">4 Ore</option>
													<option value="5">5 Ore</option>
												</select>
											</div>
										</div>

										<div class="col-12">
											<label class="form-label fw-bold">Orario di Inizio</label>
											<div class="input-group">
												<span class="input-group-text  border-dark"><i
													class="fa-solid fa-clock text-primary"></i></span> <select
													name="ora_inizio" id="oraInizio"
													class="form-select border-dark fw-bold"
													style="color: #212529 !important; border-width: 2px"
													required>
													<option value="">Scegli prima una data...</option>
												</select>
											</div>
											<div id="orarioFineAnteprima" class="mt-3"></div>
										</div>
									</div>

									<div class="mt-5 text-center">
										<button type="submit"
											class="btn btn-primary btn-lg w-100 py-3 fw-bold shadow-sm"
											id="btnSubmit" disabled>INVIA RICHIESTA</button>
									</div>
								</form>
							</div>
						</div>
						<%
						}
						%>
					</div>
				</div>
			</div>
	</main>

	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
	<script src="https://npmcdn.com/flatpickr/dist/l10n/it.js"></script>
	
		<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>


	<script>
    // 1. Dati caricati da JSP
    const dispRicorsiva = {}; 
    const dispSingola = {};   
    const eccezioni = []; // Array di date disabilitate

    // Mappa giorni Java -> Indici Flatpickr (0=Domenica, 1=Lunedì...)
    const dayMap = { "sunday": 0, "monday": 1, "tuesday": 2, "wednesday": 3, "thursday": 4, "friday": 5, "saturday": 6 };
    const enabledDays = []; 

    <%if (listaDisp != null) {
	for (Disponibilita d : listaDisp) {
		String oraI = (d.getOra_inizio() != null) ? d.getOra_inizio().toString() : "";
		String oraF = (d.getOra_fine() != null) ? d.getOra_fine().toString() : "";

		if (d.getTipo() == TipoDisponibilita.RICORSIVO) {%>
                dispRicorsiva["<%=d.getGiorno_settimana().name().toLowerCase()%>"] = { inizio: "<%=oraI%>", fine: "<%=oraF%>" };
                enabledDays.push(dayMap["<%=d.getGiorno_settimana().name().toLowerCase()%>"]);
            <%} else if (d.getTipo() == TipoDisponibilita.SINGOLO) {%>
                dispSingola["<%=d.getData()%>"] = { inizio: "<%=oraI%>", fine: "<%=oraF%>" };
            <%} else if (d.getTipo() == TipoDisponibilita.ECCEZIONE) {%>
                eccezioni.push("<%=d.getData()%>"); 
            <%}
}
}%>

    // 2. Inizializzazione Calendario Flatpickr
    flatpickr("#dataScelta", {
        locale: "it",
        minDate: "today",
        dateFormat: "Y-m-d",
        disable: [
            function(date) {
                const dateStr = date.toISOString().split('T')[0];
                const dayIndex = date.getDay();
                if (eccezioni.includes(dateStr)) return true;
                if (dispSingola[dateStr]) return false;
                return !enabledDays.includes(dayIndex);
            }
        ],
        onChange: function(selectedDates, dateStr) {
            if (!dateStr) return;
            
            const dateObj = new Date(dateStr);
            const giornoSett = dateObj.toLocaleDateString('en-US', { weekday: 'long' }).toLowerCase();
            const disp = dispSingola[dateStr] || dispRicorsiva[giornoSett];

            popolaOrari(disp);
        }
    });

    // 3. Logica Form
    const selectInizio = document.getElementById('oraInizio');
    const selectDurata = document.getElementById('durataOre');
    const anteprimaFine = document.getElementById('orarioFineAnteprima');
    const oraFineHidden = document.getElementById('oraFineHidden');
    const btnSubmit = document.getElementById('btnSubmit');

    function popolaOrari(disp) {
        selectInizio.innerHTML = '<option value="" disabled selected>Seleziona orario...</option>';
        selectInizio.disabled = false;
        const hInizio = parseInt(disp.inizio.split(':')[0]);
        const hFine = parseInt(disp.fine.split(':')[0]);

        for (let h = hInizio; h < hFine; h++) {
            let hStr = h.toString().padStart(2, '0') + ":00";
            selectInizio.add(new Option(hStr, hStr));
        }
        aggiornaOrarioFine();
    }

    function aggiornaOrarioFine() {
        const dataVal = document.getElementById('dataScelta').value;
        if (!selectInizio.value || !dataVal) {
            anteprimaFine.innerHTML = "";
            oraFineHidden.value = "";
            btnSubmit.disabled = true;
            return;
        }

        const dateObj = new Date(dataVal);
        const giornoSett = dateObj.toLocaleDateString('en-US', { weekday: 'long' }).toLowerCase();
        const disp = dispSingola[dataVal] || dispRicorsiva[giornoSett];

        const hInizio = parseInt(selectInizio.value.split(':')[0]);
        const durata = parseInt(selectDurata.value);
        const hFine = hInizio + durata;
        const hLimite = parseInt(disp.fine.split(':')[0]);

        if (hFine > hLimite) {
            // Messaggio quando l'orario non è valido
            anteprimaFine.innerHTML = `
                <div class="alert alert-danger py-2 border-0 shadow-sm">
                    <i class="fa-solid fa-circle-exclamation me-2"></i>
                    L'orario scelto esce fuori dalla disponibilità del professionista
                </div>`;
            oraFineHidden.value = "";
            btnSubmit.disabled = true;
        } else {
            // Messaggio quando l'orario è valido
            const fineStr = hFine.toString().padStart(2, '0') + ":00";
            anteprimaFine.innerHTML = `
                <div class="alert alert-success py-2 border-0 shadow-sm">
                    <i class="fa-solid fa-circle-check me-2"></i>
                    Il lavoro può essere finito negli orari prestabiliti
                </div>`;
            
            // Salviamo comunque il valore preciso nel campo hidden per il database
            oraFineHidden.value = fineStr + ":00";
            btnSubmit.disabled = false;
        }
    }

    selectInizio.addEventListener('change', aggiornaOrarioFine);
    selectDurata.addEventListener('change', aggiornaOrarioFine);
    </script>

	<jsp:include page="/WEB-INF/includes/Footer.jsp" />
</body>
</html>