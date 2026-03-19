<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="org.elis.manoforte.model.Utente, org.elis.manoforte.model.Professione, java.util.List"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Invia Richiesta | ManoForte</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/style-homeBase.css">
</head>
<body class="bg-light">

	<nav
		class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm mb-4">
		<div class="container">
			<a class="navbar-brand" href="#"><i
				class="fa-solid fa-handshake-angle me-2"></i>MANOFORTE</a>
		</div>
	</nav>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6">

				<%
				// Recupero il professionista (passato come singolo oggetto dalla Servlet)
				Utente pro = (Utente) request.getAttribute("listaProfessionisti");
				Utente utente = (Utente) request.getAttribute("utenteLoggato");

				if (pro != null) {
				%>

				<div class="card shadow border-0" style="border-radius: 15px;">
					<div class="card-header bg-white py-3 text-center border-0">
						<h4 class="fw-bold text-primary mb-0">Nuova Richiesta
							d'Intervento</h4>
					</div>

					<div class="card-body p-4">
						<div class="alert alert-secondary d-flex align-items-center mb-4">
							<i class="fa-solid fa-user-gear fa-2x me-3"></i>
							<div>
								<small class="text-muted d-block">Destinatario:</small> <strong><%=pro.getNome()%>
									<%=pro.getCognome()%></strong>
							</div>
						</div>

						<div>
							<small class="text-muted d-block">Richiedente:</small> <strong><%=utente.getNome()%>
								<%=utente.getCognome()%></strong>

						</div>
					</div>


					<form action="<%=request.getContextPath()%>/richiesta"
						method="post">
						<input type="hidden" name="emailProfessionista"
							value="<%=pro.getEmail()%>"> <input type="hidden"
							name="emailBase" value="<%=utente.getEmail()%>">

						<div class="mb-3">
							<label class="form-label fw-bold">Indirizzo
								dell'intervento</label>
							<div class="input-group">
								<span class="input-group-text"><i
									class="fa-solid fa-location-dot"></i></span> <input type="text"
									name="indirizzo" class="form-control"
									placeholder="Via Roma 10, Milano" required>
							</div>
						</div>

						<div class="mb-4">
							<label class="form-label fw-bold">Dettagli della
								richiesta</label>
							<textarea name="descrizione" class="form-control" rows="4"
								placeholder="Descrivi brevemente il guasto o l'intervento di cui hai bisogno..."
								required></textarea>
						</div>

						<%
						// Otteniamo la data di oggi nel formato ISO (yyyy-MM-dd) per il limite "min"
						String oggi = java.time.LocalDate.now().toString();
						%>

						<div class="mb-3">
							<label class="form-label fw-bold">Giorno dell'intervento</label>
							<div class="input-group">
								<span class="input-group-text"><i
									class="fa-solid fa-calendar-days"></i></span> <input type="date"
									name="giorni" class="form-control" min="<%=oggi%>"
									value="<%=oggi%>" required>
							</div>
							<div class="form-text">Seleziona una data disponibile (da
								oggi in poi).</div>
						</div>

						<div class="row mb-3">
							<div class="col-md-6">
								<label class="form-label fw-bold">Ora Inizio</label>
								<div class="input-group">
									<span class="input-group-text"><i
										class="fa-solid fa-clock"></i></span> <select name="ora_inizio"
										class="form-select" required>
										<option value="" disabled selected>Scegli ora...</option>
										<%
										for (int h = 0; h < 24; h++) {
											for (int m = 0; m < 60; m += 30) {
												String orario = String.format("%02d:%02d", h, m);
										%>
										<option value="<%=orario%>"><%=orario%></option>
										<%
										}
										}
										%>
									</select>
								</div>
							</div>

							<div class="col-md-6">
								<label class="form-label fw-bold">Ora Fine</label>
								<div class="input-group">
									<span class="input-group-text"><i
										class="fa-regular fa-clock"></i></span> <select name="ora_fine"
										class="form-select" required>
										<option value="" disabled selected>Scegli ora...</option>
										<%
										for (int h = 0; h < 24; h++) {
											for (int m = 0; m < 60; m += 30) {
												String orario = String.format("%02d:%02d", h, m);
										%>
										<option value="<%=orario%>"><%=orario%></option>
										<%
										}
										}
										%>
									</select>
								</div>
							</div>
						</div>

						<div class="d-grid gap-2">
							<button type="submit" class="btn btn-primary btn-lg rounded-pill">
								Invia richiesta a
								<%=pro.getNome()%>
							</button>
							<a href="<%=request.getContextPath()%>/homeBase"
								class="btn btn-link text-muted"> Annulla e torna alla home </a>
						</div>
					</form>
				</div>
			</div>

			<%
			} else {
			%>

			<div class="alert alert-danger text-center">
				<i class="fa-solid fa-triangle-exclamation me-2"></i> Errore:
				Professionista non trovato. <br> <a
					href="<%=request.getContextPath()%>/homeBase" class="alert-link">Torna
					alla Home</a>
			</div>

			<%
			}
			%>

		</div>
	</div>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>