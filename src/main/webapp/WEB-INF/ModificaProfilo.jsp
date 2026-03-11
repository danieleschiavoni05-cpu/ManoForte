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

</head>
<body>

	<% Utente utente = (Utente) request.getAttribute("utenteLoggato"); %>

	<div class="container d-flex justify-content-center">
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
					<label class="form-label"><i
						class="fa-solid fa-signature me-2"></i>Nome</label> <input type="text"
						class="form-control form-control-lg" name="nome"
						value="<%= utente.getNome() %>"
						placeholder="Inserisci il tuo nome" required>
				</div>

				<div class="mb-4">
					<label class="form-label"><i class="fa-solid fa-city me-2"></i>Città
						(ID)</label> <input type="number" class="form-control form-control-lg"
						name="id_citta" value="<%= utente.getIdCitta() %>"
						placeholder="Inserisci ID città" required>
					<div class="form-text">Inserisci il codice numerico della tua
						nuova città.</div>
				</div>

				<div class="d-grid gap-2">
					<button type="submit" class="btn btn-primary btn-save">
						<i class="fa-solid fa-floppy-disk me-2"></i>Salva Modifiche
					</button>
				</div>
			</form>

			<a href="<%=request.getContextPath()%>/homeBase" class="btn-back d-block text-center mt-4"> 
				<i class="fa-solid fa-house-user me-2"></i>Annulla e torna alla Home
			</a>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>