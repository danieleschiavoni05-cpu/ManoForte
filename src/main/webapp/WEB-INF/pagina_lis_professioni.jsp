<%@page import="org.elis.manoforte.model.StatoRichiesta"%>
<%@page import="org.elis.manoforte.model.Richiesta"%>
<%@page import="org.elis.manoforte.model.Professione"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.manoforte.model.Recensione"%>
<%@page import="org.elis.manoforte.model.Utente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
<title>Home | ManoForte</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/color-var.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style-recensioniPro.css?v=<%=System.currentTimeMillis()%>">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/header.css">
</head>

<body>

	<jsp:include page="/WEB-INF/includes/Navbar.jsp" />

	<main class="main-content">
		<div class="container py-5">
			<div class="row g-4">
				<%
                // Recupero la lista passata dalla Servlet
                List<Professione> professioni = (List<Professione>) request.getAttribute("listaProfessioni");
                
                if (professioni == null || professioni.isEmpty()) {
                %>
				<div class="col-12 text-center py-5">
					<div class="no-data p-5">
						<i class="fa-solid fa-circle-info fa-3x mb-3"
							style="color: var(--craft-gold);"></i>
						<h3 class="text-white">Nessuna professione disponibile</h3>
						<p class="text-white opacity-50">Stiamo aggiornando il nostro
							catalogo esperti.</p>
					</div>
				</div>
				<%
                } else {
                    // Ciclo per ogni professione trovata
                    for (Professione p : professioni) {
                %>
				<div class="col-md-6 col-xl-4">
					<div class="review-card h-100 p-4 text-center">
						<div class="icon-box mx-auto mb-3"
							style="width: 60px; height: 60px; background: rgba(212, 175, 55, 0.1); border-radius: 15px; display: flex; align-items: center; justify-content: center;">
							<i class="fa-solid fa-tools fa-2x"
								style="color: var(--craft-gold);"></i>
						</div>

						<h4 class="fw-bold mb-3" style="color: #fff;"><%= p.getNome() %></h4>

						<p class="mb-4"
							style="color: rgba(255, 255, 255, 0.7); font-size: 0.9rem;">
							Trova i migliori esperti in <strong><%= p.getNome() %></strong>
							pronti ad aiutarti nella tua zona.
						</p>

						<a
							href="<%=request.getContextPath()%>/professionisti?nome=<%= p.getNome() %>"
							class="login-register-button d-inline-block w-auto px-5 py-2"
							style="text-decoration: none; font-size: 0.8rem;"> Esplora </a>
					</div>
				</div>
				<%
                    } 
                } 
                %>
			</div>
		</div>
	</main>

	<jsp:include page="/WEB-INF/includes/Footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>