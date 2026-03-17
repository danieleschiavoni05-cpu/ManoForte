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

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/registrazione_professionista.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/professionista-style.css">

</head>
<body>

	<% List<String> messaggi = (List<String>) request.getAttribute("messages"); %>
	<% String errore = (String) request.getAttribute("errore");%>
	<% List<Citta> citta = (List<Citta>) request.getAttribute("listaCitta"); %>

	<div class="container my-5">
		<div class="row justify-content-center">
			<div class="col-12 col-md-10 col-lg-8 col-xl-7">

				<div class="register-card">

					<div class="text-center mb-5">
						<h1 class="display-6 fw-bold text-primary">Crea il tuo
							Account</h1>
						<p class="text-muted">Compila i campi sottostanti per
							registrarti come Utente Base</p>
					</div>

					<form method="post" id="formRegistrazioneUtenteBase">
						<div
							class="row g-3 <%=(errore!=null||messaggi!=null)?' ':"mb-4"%>"
							id="mainRow">
							<div class="col-md-6">
								<label class="section-title" for="nome">Nome</label> <input
									type="text" class="form-control" id="nome" name="campoNome"
									placeholder="Inserire un nome">
							</div>
							<%-- Nome --%>

							<div class="col-md-6">
								<label class="section-title" for="cognome">Cognome</label> <input
									type="text" class="form-control" id="cognome" name="campoCognome"
									placeholder="Inserire un cognome">
							</div>
							<%-- Cognome --%>

							<div class="col-md-8">
								<label class="section-title" for="email">Email</label> <input
									type="email" class="form-control" id="email" name="campoEmail"
									placeholder="Inserire un indirizzo email">
							</div>
							<%-- Email --%>

							<div class="col-md-4">
								<label class="section-title" for="data">Data</label> <input
									type="date" class="form-control" id="data" name="campoData">
							</div>
							<%-- Data di nascita --%>

							<div class="col-md-10">
								<label class="section-title" for="codice_fiscale">Codice
									fiscale</label> <input type="text" class="form-control"
									id="campoCodiceFiscale" name="campoCodiceFiscale" maxlength="16"
									placeholder="Inserire un codice fiscale/partita IVA">
							</div>
							
							<div class="col-12">
                        <label class="section-title" for="citta">Città di residenza</label>
                        <select class="form-select" name="campoCitta" id="citta">
                            <option selected disabled>Scegliere una città</option>
                            <%if(citta!=null && !citta.isEmpty()){%>
                                <%for(Citta c: citta){%>
                                    <option value="<%=c.getId()%>"><%=c.getNome()%></option>
                                <%}%>
                            <%}%>
                        </select>
                    </div> <%-- Citta --%>
							

							<div class="col-md-6">
								<label class="section-title" for="password">Password</label> <input
									type="password" class="form-control" id="password"
									name="campoPassword" placeholder="Inserire una password">
							</div>
							<%-- Password --%>

							<div class="col-md-6">
								<label class="section-title" for="conferma_password">Conferma
									password</label> <input type="password" class="form-control"
									id="conferma_password" name="campoConfermaPassword"
									placeholder="Reinserire la password">
							</div>
							<%-- Conferma password --%>

							<div class="col-12 mt-4 d-none" id="containerErrori">
								<div class="alert alert-danger text-center shadow-sm"
									role="alert">
									<ul id="listaErrori" class="list-unstyled mb-0 fw-bold"></ul>
								</div>
							</div>
							<%-- Lista errori --%>

							<div class="col-12 mt-4 d-none" id="containerSuccesso">
								<div class="alert alert-successo text-center shadow-sm"
									role="alert">
									<ul id="listaSuccessi" class="list-unstyled mb-0 fw-bold"></ul>
								</div>
							</div>
							<%-- Lista successo --%>

						</div>
						<button type="submit" class="login-register-button">Registrati</button>
					</form>

					<hr class="my-5">

					<div class="text-center">
						<p class="text-secondary">
							Hai già un account? <a href="<%=request.getContextPath()%>/login"
								class="text-decoration-none fw-bold">Accedi qui</a>
						</p>
						<a href="<%=request.getContextPath()%>/Homepage.jsp"
							class="btn btn-link text-decoration-none text-muted mt-2"> <i
							class="fa-solid fa-arrow-left me-2"></i>Torna alla Home
						</a>
					</div>

					
				</div>
			</div>
		</div>
	</div>

	
<script src="<%=request.getContextPath()%>/js/script-registrazione_edit.js"></script>
<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
</body>
</html>