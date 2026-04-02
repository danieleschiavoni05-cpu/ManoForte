

<%@page import="org.elis.manoforte.model.Recensione"%>
<%@page import="org.elis.manoforte.model.Utente"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Recensioni Inviate | ManoForte</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/color-var.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style-recensioniPro.css?v=<%=System.currentTimeMillis()%>">
</head>

<body>

	<jsp:include page="/WEB-INF/includes/Navbar.jsp" />

	<main class="main-content">
		<header class="page-header text-center mb-5">
			<h1 class="fw-bold">
				Le mie <span style="color: var(--craft-gold);">recensioni</span>
			</h1>
			<p class="text-white">Ecco i feedback che hai lasciato ai nostri
				professionisti</p>
		</header>

		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-9">

					<%
                    List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");
                    List<Utente> listaUtenti = (List<Utente>) request.getAttribute("listaUtenti");
                    Long idProfessionista = (Long) request.getAttribute("idProfessionista");

                    if (recensioni == null || recensioni.isEmpty()) {
                    %>

					<div class="no-data text-center p-5">
						<i class="fa-solid fa-comment-slash fa-3x mb-3"
							style="color: var(--steel-variant);"></i>
						<h3 class="text-white">Ancora nessuna recensione</h3>
						<p class="text-white opacity-75">Le valutazioni che scriverai
							appariranno in questa sezione.</p>
					</div>

					<%
                    } else {
                    	for (Recensione r : recensioni) {
        					Utente prof = null;
        					if (listaUtenti != null) {
        						for (Utente u : listaUtenti) {
        					// CORREZIONE: confronta l'ID dell'utente con l'ID professionista della recensione
        					if (idProfessionista == r.getProfessionista().getId()) {
        						prof = u;
        						break;
        					}
        						}
        					}
        				%>


					<div class="profile-card mb-4">
						<div class="d-flex justify-content-between align-items-start">
							<div class="prof-info">
								<h3 class="mb-1"
									style="color: var(--craft-gold); font-size: 1.4rem;">
									<i class="fa-solid fa-user-tie me-2"></i>
									<%=(prof != null) ? (prof.getNome() + " " + prof.getCognome()) : "Professionista non trovato"%>
								</h3>
								<p class="mb-2 text-white opacity-75">
									<i class="fa-solid fa-envelope me-1"></i>
									<%=(prof != null) ? prof.getEmail() : "N/A"%>
								</p>

								<div class="star-rating">
									<% for (int i = 1; i <= 5; i++) { %>
									<i
										class="<%=(i <= r.getVoto()) ? "fa-solid" : "fa-regular"%> fa-star"
										style="color: var(--craft-gold);"></i>
									<% } %>
									<span class="ms-2 text-white small">(<%=r.getVoto()%>/5)
									</span>
								</div>
							</div>

							<div class="actions">
								<form action="eliminazionerecensione" method="POST"
									onsubmit="return confirm('Sei sicuro di voler eliminare questa recensione?');">
									<input type="hidden" name="idRecensione" value="<%=r.getId()%>">
									<button type="submit"
										class="btn btn-outline-danger btn-sm border-0">
										<i class="fa-solid fa-trash-can"></i> Elimina
									</button>
								</form>
							</div>
						</div>

						<div class="review-text mt-3 pt-3"
							style="border-top: 1px solid rgba(255, 255, 255, 0.1); color: white; font-style: italic;">
							<i class="fa-solid fa-quote-left me-2"
								style="color: var(--craft-gold); opacity: 0.5;"></i>
							<%=r.getDescrizione()%>
							<i class="fa-solid fa-quote-right ms-2"
								style="color: var(--craft-gold); opacity: 0.5;"></i>
						</div>
					</div>

					<%
                        } 
                    } 
                    %>

					<div class="text-center mt-5 mb-5">
						<a href="<%=request.getContextPath()%>/homeBase" class="btn-back">
							<i class="fa-solid fa-arrow-left me-2"></i>Torna alla Home
						</a>
					</div>

				</div>
			</div>
		</div>
	</main>

	<jsp:include page="/WEB-INF/includes/Footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>