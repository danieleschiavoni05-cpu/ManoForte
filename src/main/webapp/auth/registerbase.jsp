<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.elis.manoforte.model.Citta"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Registrazione Utente Base</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/registrazione_professionista.css">


</head>
<body>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6">
				<div class="register-card">

					<div class="text-center mb-4">
						<h2 class="fw-bold text-primary">Registrazione</h2>
						<p class="text-muted">Crea il tuo account Utente Base</p>
					</div>

					<form action="<%=request.getContextPath()%>/registerBase"
						method="post">

						<div class="row">
							<div class="col-md-6 mb-3">
								<label class="form-label">Nome</label> <input type="text"
									class="form-control" placeholder="es. Mario" name="campoNome"
									required>
							</div>
							<div class="col-md-6 mb-3">
								<label class="form-label">Cognome</label> <input type="text"
									class="form-control" placeholder="es. Rossi"
									name="campoCognome" required>
							</div>
						</div>

						<div class="mb-3">
							<label class="form-label">Codice Fiscale</label> <input
								type="text" class="form-control" placeholder="Codice Fiscale"
								name="campoCodiceFiscale" required>
						</div>

						<div class="mb-3">
							<label class="form-label">Email</label>
							<div class="input-group">
								<span class="input-group-text"><i
									class="fa-solid fa-envelope"></i></span> <input type="email"
									class="form-control" placeholder="nome@esempio.it"
									name="campoEmail" required>
							</div>
						</div>

						<div class="mb-3">
							<label class="form-label">Password</label>
							<div class="input-group">
								<span class="input-group-text"><i
									class="fa-solid fa-lock"></i></span> <input type="password"
									class="form-control" placeholder="********"
									name="campoPassword" required>
							</div>
						</div>

						<div class="mb-3">
							<label class="form-label">Città</label> <select name="campoCitta"
								class="form-select" required>
								<option value="" disabled selected>Scegli una città...</option>
								<%
                                List<Citta> listaCitta = (List<Citta>) request.getAttribute("listaCitta");
                                if (listaCitta != null) {
                                    for (Citta c : listaCitta) {
                            %>
								<option value="<%= c.getId() %>"><%= c.getNome() %></option>
								<% 
                            }} %>
							</select>
						</div>

						<div class="mb-4">
							<label class="form-label">Data di Nascita</label> <input
								type="date" class="form-control" name="campoData" required>
						</div>

						<div class="d-grid gap-2">
							<button type="submit" class="btn btn-primary btn-lg">Registrati</button>
						</div>

						<hr class="my-4">

						<div class="text-center">
							<p class="mb-1">
								Hai già un account? <a
									href="<%=request.getContextPath()%>/login"
									class="text-decoration-none">Accedi</a>
							</p>
							<a href="<%=request.getContextPath()%>/Homepage.jsp"
								class="btn btn-outline-secondary btn-sm"> <i
								class="fa-solid fa-house me-1"></i>Torna alla home
							</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>