<%@page import="org.elis.manoforte.model.Citta"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.elis.manoforte.model.Utente"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Modifica Profilo | ManoForte</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style-modificaProfilo.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">

</head>
<body style="margin: 0; padding: 0;">

    <jsp:include page="/includes/Navbar.jsp"/>

	<% Utente utente = (Utente) request.getAttribute("utenteLoggato"); %>

	<div class="container d-flex justify-content-center" style="margin-top: 2rem;">
		<div class="profile-card shadow">

			<div class="profile-header">
				<div class="profile-avatar">
					<i class="fa-solid fa-user"></i>
				</div>
				<h2 class="fw-bold">Modifica Profilo</h2>
				<p class="text-muted">Aggiorna le tue informazioni personali</p>
			</div>

			<form action="<%=request.getContextPath()%>/ModificaProfilo"
				method="POST">

				<div class="mb-3">
					<label class="form-label">Nome</label> <input type="text"
						class="form-control" name="nome" value="<%= utente.getNome() %>"
						required>
				</div>

				<div class="mb-3">
					<label class="form-label">Cognome</label> <input type="text"
						class="form-control" name="cognome"
						value="<%= utente.getCognome() %>" required>
				</div>

				<div class="mb-3">
					<label class="form-label">Codice Fiscale</label> <input type="text"
						class="form-control" name="codiceFiscale"
						value="<%= utente.getCodiceFiscale() %>" required>
				</div>



				<hr class="my-4">
				<h5 class="mb-3 text-primary">
					<i class="fa-solid fa-key me-2"></i>Sicurezza Account
				</h5>

				<div class="mb-3">
					<label class="form-label">Password Attuale</label>
					<div class="input-group">
						<span class="input-group-text"><i
							class="fa-solid fa-lock-open"></i></span> <input type="text"
							class="form-control" name="oldPassword"
							placeholder="Inserisci la password attuale"
							 required>
					</div>
					<div class="form-text">Necessaria per confermare l'identità.</div>
				</div>

				<div class="row">
					<div class="col-md-6 mb-3">
						<label class="form-label">Nuova Password</label>
						<div class="input-group">
							<span class="input-group-text"><i class="fa-solid fa-lock"></i></span>
							<input type="text" class="form-control" name="newPassword"
								id="newPassword" placeholder="Nuova password">
						</div>
					</div>
					<div class="col-md-6 mb-3">
						<label class="form-label">Conferma Nuova Password</label>
						<div class="input-group">
							<span class="input-group-text"><i
								class="fa-solid fa-check-double"></i></span> <input type="text"
								class="form-control" name="confirmPassword" id="confirmPassword"
								placeholder="Ripeti password">
						</div>
					</div>
				</div>
				<div class="form-text mb-4 text-muted">Lascia vuoti i campi
					"Nuova Password" se non vuoi cambiarla.</div>
					
					

				<div class="mb-4">
					<label class="form-label">Città</label> <select name="campoCitta"
						class="form-select" required>
						<%
                List<Citta> listaCitta = (List<Citta>) request.getAttribute("listaCitta");
                if (listaCitta != null) {
                    for (Citta c : listaCitta) {
                        // Verifico se è la città dell'utente per aggiungere 'selected'
                        
            %>
						<option value="<%= c.getId() %>"><%= c.getNome() %></option>
						<% 
                }} 
            %>
					</select>
				</div>

				<div class="mb-4">
					<label class="form-label">Data di Nascita</label> <input
						type="date" class="form-control" name="dataNascita"
						value="<%= utente.getDataNascita() %>" required>
				</div>

				<div class="d-grid gap-2">
					<button type="submit" class="btn btn-primary btn-save">
						<i class="fa-solid fa-floppy-disk me-2"></i>Salva Modifiche
					</button>
				</div>
			</form>

			<a href="<%=request.getContextPath()%>/homeBase"
				class="btn-back d-block text-center mt-4"> <i
				class="fa-solid fa-house-user me-2"></i>Annulla e torna alla Home
			</a>
		</div>
	</div>

    <jsp:include page="/includes/Footer.jsp"/>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>