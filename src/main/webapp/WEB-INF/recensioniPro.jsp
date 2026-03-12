<%@page import="org.elis.manoforte.model.Recensione"%>
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
	href="<%=request.getContextPath()%>/css/style-recensioniPro.css">

</head>
<body>

	<header class="header-section text-center">
		<div class="container">
			<h1>Le mie recensioni date</h1>
			<p class="text-muted">Storico dei feedback che hai lasciato ai
				professionisti</p>
		</div>
	</header>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8">

				<%
                List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");

                if(recensioni == null || recensioni.isEmpty()){
                %>
				<div class="empty-box text-center">
					<i class="fa-solid fa-pen-nib fa-3x mb-3 text-muted"></i>
					<h3>Nessuna recensione inviata</h3>
					<p class="text-muted">Non hai ancora valutato i servizi
						ricevuti. Le tue recensioni appariranno qui.</p>
				</div>
				<%
                } else {
                    for(Recensione r : recensioni){
                %>
				<div class="review-card shadow-sm">
					<div class="d-flex justify-content-between align-items-start">
						<div>
							<span class="prof-id-tag"> <i
								class="fa-solid fa-user-tie me-1"></i> Professionista ID: <%= r.getId_professionista() %>
							</span>
							<div class="star-rating">
								<% for(int i=1; i<=5; i++) { %>
								<i
									class="<%= i <= r.getVoto() ? "fa-solid" : "fa-regular" %> fa-star"></i>
								<% } %>
							</div>
						</div>
						<div class="text-muted small">
							<i class="fa-solid fa-check-double text-success"></i> Verificata
						</div>
					</div>

					<div class="review-body">
						"<%= r.getDescrizione() %>"
					</div>
				</div>
				<%
                    }
                }
                %>

				<div class="text-center">
					<a href="<%=request.getContextPath()%>/homeBase"
						class="btn-back d-block text-center mt-4"> <i
						class="fa-solid fa-house-user me-2"></i>Annulla e torna alla Home
					</a>
				</div>

			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>