<%@page import="org.elis.manoforte.model.Utente"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="org.elis.manoforte.model.Recensione"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ManoForte - Trova la tua mano di fiducia</title>
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
	href="<%=request.getContextPath()%>/css/style-home.css">

</head>

<body>

	<jsp:include page="WEB-INF/includes/Navbar.jsp" />


	<div class="carousel-wrapper" style="position: relative;">
		<div class="carousel"
			style="background: linear-gradient(180deg, #279AF1 0%, #4FB4FF 100%); min-height: 400px; display: flex; align-items: center; justify-content: center;">
			<div class="slide active">
				<div class="slide-content text-center text-white">
					<h1>Esperti Idraulici pronti per te</h1>
					<p>Risolvi ogni problema domestico con professionisti
						verificati.</p>
				</div>
			</div>

			<div class="slide">
				<div class="slide-content text-center text-white">
					<h1>Elettricisti Certificati</h1>
					<p>Manutenzione sicura e certificata per la tua casa.</p>
				</div>
			</div>
		</div>

		<div class="search-container">
			<form class="search-box"
				action="<%=request.getContextPath()%>/ricercaProfessioni"
				method="get">
				<input type="text" name="cercaNome"
					placeholder="Cosa stai cercando? (es. Idraulico)" required>
				<button type="submit">Trova Esperto</button>
			</form>
		</div>
	</div>

	<div class="container">
		<h2>Scegli per Categoria</h2>
		<div class="categories-grid">
			<a
				href="<%=request.getContextPath()%>/ricercaProfessioni?cercaNome=idraulico"
				class="cat-item"> <span class="cat-icon">🔧</span> <span>Idraulici</span>
			</a> <a
				href="<%=request.getContextPath()%>/ricercaProfessioni?cercaNome=Elettricista"
				class="cat-item"> <span class="cat-icon">⚡</span> <span>Elettricisti</span>
			</a> <a
				href="<%=request.getContextPath()%>/ricercaProfessioni?cercaNome=Pulizia"
				class="cat-item"> <span class="cat-icon">🧹</span> <span>Pulizie</span>
			</a> <a
				href="<%=request.getContextPath()%>/ricercaProfessioni?cercaNome=Pittore"
				class="cat-item"> <span class="cat-icon">🎨</span> <span>Pittori</span>
			</a>
		</div>
	</div>



	<section class="container"
		style="margin-top: 80px; margin-bottom: 80px;">
		<h2>Cosa dicono i nostri utenti</h2>

		<div class="recensioni-grid">
			<%
			Map<Long, Utente> mappaProf = (Map<Long, Utente>) request.getAttribute("mappaProfessionisti");
			List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");

			if (recensioni == null || recensioni.isEmpty()) {
			%>
			<div class="rec-card text-center w-100"
				style="grid-column: 1/-1; border-top: none; border: 1px dashed var(--muted-silver);">
				<i class="fa-solid fa-comment-slash fa-2x mb-3"
					style="color: var(--muted-silver);"></i>
				<p style="color: var(--muted-silver);">Ancora nessuna recensione
					disponibile.</p>
			</div>
			<%
			} else {
			// Mostriamo le ultime recensioni per mantenere la griglia pulita
			for (Recensione r : recensioni) {
				Utente prof = (mappaProf != null) ? mappaProf.get(r.getId_professionista()) : null;
			%>
			<div class="rec-card">
				<div class="stars">
					<%
					for (int i = 1; i <= 5; i++) {
					%>
					<i
						class="<%=(i <= r.getVoto()) ? "fa-solid fa-star" : "fa-regular fa-star"%>"></i>
					<%
					}
					%>
				</div>

				<div class="rec-content"
					style="font-style: italic; color: var(--white-text); margin-bottom: 15px;">
					"<%=(r.getDescrizione() != null && !r.getDescrizione().isEmpty())
		? r.getDescrizione()
		: "Servizio eccellente, altamente professionale."%>"
				</div>

				<div
					class="rec-meta d-flex align-items-center justify-content-between">
					<div>
						<i class="fa-solid fa-user-check me-2"
							style="color: var(--primary);"></i> <span
							style="font-weight: 600; color: var(--primary);"> <%=(prof != null) ? (prof.getNome() + " " + prof.getCognome()) : "Esperto ManoForte"%>
						</span>
					</div>
					<small style="font-size: 0.75rem; opacity: 0.7;">Verificato</small>
				</div>
			</div>
			<%
			}
			}
			%>
		</div>
	</section>



	<script>
        let currentSlide = 0;
        const slides = document.querySelectorAll('.slide');
        setInterval(() => {
            slides[currentSlide].classList.remove('active');
            currentSlide = (currentSlide + 1) % slides.length;
            slides[currentSlide].classList.add('active');
        }, 5000);
    </script>

	<jsp:include page="WEB-INF/includes/Footer.jsp" />
</body>
</html>