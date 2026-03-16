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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<style>
.star-rating .fa-star {
	color: #ffc107;
	margin-right: 2px;
}

.review-card {
	background: #fff;
	border-radius: 12px;
	padding: 25px;
	margin-bottom: 20px;
	border-left: 6px solid #0d6efd;
	transition: transform 0.2s;
}

.review-card:hover {
	transform: translateY(-3px);
}

.prof-name {
	font-weight: 700;
	color: #212529;
	font-size: 1.2rem;
}

.prof-email {
	color: #6c757d;
	font-size: 0.9rem;
}

.review-text {
	font-style: italic;
	color: #444;
	margin-top: 15px;
	border-top: 1px solid #eee;
	pt-3;
}
</style>

</head>

<body class="bg-light">

	<header class="text-center py-5 bg-white shadow-sm mb-4">
		<div class="container">
			<h1 class="fw-bold text-primary">Le mie recensioni</h1>
			<p class="lead">Ecco i feedback che hai lasciato ai nostri
				professionisti</p>
		</div>
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

				<div class="text-center p-5 bg-white rounded shadow-sm">
					<i class="fa-solid fa-comment-slash fa-3x mb-3 text-muted"></i>
					<h3>Ancora nessuna recensione</h3>
					<p class="text-muted">Le valutazioni che scriverai appariranno
						in questa sezione.</p>

				</div>

				<%
				} else {
				for (Recensione r : recensioni) {
					Utente prof = null;
					if (listaUtenti != null) {
						for (Utente u : listaUtenti) {
					// CORREZIONE: confronta l'ID dell'utente con l'ID professionista della recensione
					if (idProfessionista == r.getId_professionista()) {
						prof = u;
						break;
					}
						}
					}
				%>

				<div class="review-card shadow-sm bg-white">
					<div class="d-flex justify-content-between align-items-start">
						<div>
							<div class="prof-name">
								<i class="fa-solid fa-user-tie text-primary me-2"></i>
								<%=(prof != null) ? (prof.getNome() + " " + prof.getCognome()) : "Professionista non trovato"%>
							</div>
							<div class="prof-email mb-2">
								<i class="fa-solid fa-envelope me-1"></i>
								<%=(prof != null) ? prof.getEmail() : "N/A"%>
							</div>

							<div class="star-rating">
								<%
								for (int i = 1; i <= 5; i++) {
								%>
								<i
									class="<%=(i <= r.getVoto()) ? "fa-solid" : "fa-regular"%> fa-star"></i>
								<%
								}
								%>
								<span class="ms-1 fw-bold text-muted small">(<%=r.getVoto()%>/5)
								</span>
							</div>
						</div>

						<div class="text-end">
							

							<div class="mt-2">
								<form action="eliminazionerecensione" method="POST"
									onsubmit="return confirm('Sei sicuro di voler eliminare questa recensione?');">
									<input type="hidden" name="idRecensione" value="<%=r.getId()%>">
									<button type="submit"
										class="btn btn-outline-danger btn-sm border-0">
										<i class="fa-solid fa-trash-can me-1"></i> Elimina
									</button>
								</form>
							</div>
						</div>
					</div>

					<div class="review-text pt-3 mt-3">
						<i class="fa-solid fa-quote-left text-primary opacity-25 me-2"></i>
						<%=r.getDescrizione()%>
						<i class="fa-solid fa-quote-right text-primary opacity-25 ms-2"></i>
					</div>
				</div>



				<%
                    } 
                } 
                %>

				<div class="text-center mt-5 mb-5">
					<a href="<%=request.getContextPath()%>/homeBase"
						class="btn btn-secondary px-4 py-2 shadow-sm"> <i
						class="fa-solid fa-arrow-left me-2"></i>Torna alla Home
					</a>
				</div>

			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>