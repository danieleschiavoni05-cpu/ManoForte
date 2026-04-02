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

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-modificaProfilo.css?v=<%=System.currentTimeMillis()%>">
</head>
<body style="margin: 0; padding: 0;">

    <jsp:include page="../includes/Navbar.jsp"/>

<% Utente utente = (Utente) request.getAttribute("utenteLoggato");%>
<% List<String> messaggi = (List<String>) request.getAttribute("messages"); %>
<% String errore = (String) request.getAttribute("errore");%>
<% List<Citta> citta = (List<Citta>) request.getAttribute("listaCitta"); %>

	<div class="container d-flex justify-content-center" style="margin-top: 2rem;">
		<div class="profile-card shadow">

			<div class="profile-header">
				<div class="profile-avatar">
					<i class="fa-solid fa-user"></i>
				</div>
				<h2 class="fw-bold">Modifica Profilo</h2>
				<p class="text-muted">Aggiorna le tue informazioni personali</p>
			</div>

			<form method="post" id="formModificaProfiloUtenteBase">
                <div class="row g-3" id="mainRow">
                    <div class="col-md-6">
                        <label class="section-title" for="nome">Nome</label>
                        <input type="text" class="form-control" id="nome" name="nome" value="<%=utente.getNome()%>" placeholder="Inserire un nome">
                    </div> <%-- Nome --%>

                    <div class="col-md-6">
                        <label class="section-title" for="cognome">Cognome</label>
                        <input type="text" class="form-control" id="cognome" name="cognome" value="<%=utente.getCognome()%>" placeholder="Inserire un cognome">
                    </div> <%-- Cognome --%>

                    <div class="col-md-8">
                        <label class="section-title" for="email">Email</label>
                        <input disabled type="email" class="form-control" id="email" name="email" value="<%=utente.getEmail()%>" placeholder="Inserire un indirizzo email">
                    </div> <%-- Email --%>

                    <div class="col-md-4">
                        <label class="section-title" for="data">Data</label>
                        <input type="date" class="form-control" id="data" value="<%=utente.getDataNascita()%>" name="dataNascita">
                    </div> <%-- Data di nascita --%>

                    <div class="col-md-10">
                        <label class="section-title" for="codice_fiscale">Codice fiscale/Partita IVA</label>
                        <input type="text" class="form-control" id="codice_fiscale" name="codiceFiscale" value="<%=utente.getCodiceFiscale()%>" maxlength="16" placeholder="Inserire un codice fiscale/partita IVA">
                    </div> <%-- Codice fiscale --%>

                    <div class="col-12">
                        <label class="section-title" for="citta">Città di residenza</label>
                        <select class="form-select"  name="campoCitta" id="citta">
                            <option selected disabled>Scegliere una città</option>
                            <%if(citta!=null && !citta.isEmpty()){%>
                                <%for(Citta c: citta){%>
                                    <option value="<%=c.getId()%>"><%=c.getNome()%></option>
                                <%}%>
                            <%}%>
                        </select>
                    </div> <%-- Citta --%>

                    <div class="col-md-6">
                        <label class="section-title" for="nuova_password">Nuova password</label>
                        <input type="password" class="form-control" id="nuova_password" name="newPassword" placeholder="Inserire una nuova password (lasciare vuoto per non modificare)">
                    </div> <%-- Nuova Password --%>

                    <div class="col-md-6">
                        <label class="section-title" for="conferma_password">Conferma password</label>
                        <input type="password" class="form-control" id="conferma_password" name="confirmPassword" placeholder="Reinserire la password">
                    </div> <%-- Conferma Password --%>

                    <div class="col-md-6">
                        <label class="section-title" for="password_attuale">Password attuale</label>
                        <input type="password" class="form-control" id="password_attuale" name="oldPassword" placeholder="Inserire la password attuale">
                    </div> <%-- Vecchia password --%>

                    <div class="col-12 mt-4 d-none" id="containerSuccesso">
                        <div class="alert alert-success text-center shadow-sm" role="alert">
                            <ul id="listaSuccessi" class="list-unstyled mb-0 fw-bold"></ul>
                        </div>
                    </div> <%-- Lista conferma --%>

                    <div class="col-12 mt-4 d-none" id="containerErrori">
                        <div class="alert alert-danger text-center shadow-sm" role="alert">
                            <ul id="listaErrori" class="list-unstyled mb-0 fw-bold"></ul>
                        </div>
                    </div> <%-- Lista errori --%>

                </div>
                <button type="submit" class="login-register-button">Modifica profilo</button>
            </form>
        </div>
    </div>


			<a href="<%=request.getContextPath()%>/homeBase"
				class="btn-back d-block text-center mt-4"> <i
				class="fa-solid fa-house-user me-2"></i>Annulla e torna alla Home
			</a>


    <jsp:include page="../includes/Footer.jsp"/>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
