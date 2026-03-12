<%@page import="org.elis.manoforte.model.StatoRichiesta"%>
<%@page import="org.elis.manoforte.model.Richiesta"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.elis.manoforte.model.Professione"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.manoforte.model.Recensione"%>
<%@page import="org.elis.manoforte.model.Utente"%>

<%
// Recupero l'utente loggato dalla sessione o dall'attributo
Utente u = (Utente) session.getAttribute("utenteLoggato");
String nomeUtente = (u != null) ? u.getNome() : "Ospite";
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Dashboard Utente | ManoForte</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/style-homeBase.css">


</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
		<div class="container">
			<a class="navbar-brand" href="#"><i
				class="fa-solid fa-handshake-angle me-2"></i>MANOFORTE</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link active"
						href="<%=request.getContextPath()%>/Homepage.jsp">Home</a></li>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<header class="hero-section text-center">
		<div class="container">
			<h1 class="display-4 fw-bold">
				Benvenuto
				<%=nomeUtente%></h1>
			<p class="lead">Di quale professionista hai bisogno oggi?</p>
		</div>
	</header>

	<div class="container">
		<div class="row">
			<div class="col-lg-3 mb-4">
				<div class="card border-none shadow-sm p-3 border-radius-15">
					<h5 class="fw-bold mb-3 px-2">Azioni rapide</h5>
					<a href="<%=request.getContextPath()%>/RecensioniProfessionisti"
						class="sidebar-link mb-2"> <i
						class="fa-solid fa-star me-2 text-warning"></i> Vedi Recensioni
					</a> <a href="<%=request.getContextPath()%>/ModificaProfilo"
						class="sidebar-link mb-2"> <i
						class="fa-solid fa-pen-to-square me-2"></i> Modifica Dati
					</a>
					<hr>
					<a href="<%=request.getContextPath()%>/Homepage.jsp"
						class="sidebar-link text-danger"> <i
						class="fa-solid fa-arrow-left me-2"></i> Esci alla Home
					</a>
				</div>
			</div>

			<div class="col-lg-9">
				<div class="row g-4">
					<%
					List<Professione> professioni = (List<Professione>) request.getAttribute("professioni");
					if (professioni == null || professioni.isEmpty()) {
					%>
					<div class="col-12 text-center py-5">
						<div class="alert alert-info">
							<i class="fa-solid fa-circle-info me-2"></i> Nessuna professione
							disponibile al momento.
						</div>
					</div>
					<%
					} else {
					for (Professione p : professioni) {
					%>
					<div class="col-md-6 col-xl-4">
						<div class="card h-100 profession-card p-3">
							<div class="card-body text-center">
								<div class="icon-box mx-auto">
									<i class="fa-solid fa-briefcase"></i>
								</div>
								<h5 class="card-title fw-bold text-dark"><%=p.getNome()%></h5>
								<p class="card-text text-muted small">
									Trova i migliori esperti in
									<%=p.getNome()%>
									della tua zona.
								</p>
								<a
									href="<%=request.getContextPath()%>/professionisti<%=p.getNome()%>"
									class="btn btn-outline-primary btn-sm rounded-pill px-4 mt-2">
									Esplora </a>
							</div>
						</div>
					</div>
					<%
					}
					}
					%>
				</div>
			</div>
		</div>
	</div>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>