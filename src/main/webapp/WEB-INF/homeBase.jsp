<%@page import="org.elis.manoforte.model.StatoRichiesta"%>
<%@page import="org.elis.manoforte.model.Richiesta"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.elis.manoforte.model.Professione"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.manoforte.model.Recensione"%>
<%@page import="org.elis.manoforte.model.Utente"%>

<%
Utente u = (Utente) session.getAttribute("utenteLoggato");
String nomeUtente = (u != null) ? u.getNome() : "Ospite";
List<Richiesta> listaRichiesta = (List<Richiesta>) request.getAttribute("listaRichiesta");
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
	<% if (session.getAttribute("messaggioSuccesso") != null) { %>
	<div
		class="alert alert-success alert-dismissible fade show container mt-3"
		role="alert">
		<%= session.getAttribute("messaggioSuccesso") %>
		<button type="button" class="btn-close" data-bs-dismiss="alert"
			aria-label="Close"></button>
	</div>
	<% session.removeAttribute("messaggioSuccesso"); %>
	<% } %>

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
					<li class="nav-item"><a class="nav-link active"
						href="<%=request.getContextPath()%>/lista_professioni">Pagina
							Professioni</a></li>
					<li class="nav-item"><a class="nav-link active"
						href="<%=request.getContextPath()%>/ModificaProfilo">Modifica
							Dati Profilo</a></li>
					</li>
					<li class="nav-item"><a class="nav-link active text-danger"
						href="<%=request.getContextPath()%>/Homepage.jsp">Esci alla
							home</a></li>
					</li>
					<li class="nav-item ms-lg-3">
					<a class="btn btn-danger btn-sm px-3 mt-1 mt-lg-0" 
           href="<%=request.getContextPath()%>/logout"
           onclick="return confirm('Sei sicuro di voler uscire?')">
            <i class="fa-solid fa-right-from-bracket me-1"></i> Logout
        </a>
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
			<p class="lead">Che cosa hai rotto oggi?</p>
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
					<a class="btn btn-danger btn-sm px-3 mt-1 mt-lg-0" 
           href="<%=request.getContextPath()%>/logout"
           onclick="return confirm('Sei sicuro di voler uscire?')">
            <i class="fa-solid fa-right-from-bracket me-1"></i> Logout
    </a>
				</div>
			</div>

			<div class="col-lg-9">
				<div class="row g-4">

					<div class="col-md-6 col-9">
						<div class="card h-100 profession-card p-3">
							<div class="card-body text-center">
								<div class="icon-box mx-auto">
									<i class="fa-solid fa-briefcase"></i>
								</div>
								<h5 class="card-title fw-bold text-dark">PROFESSIONISTI</h5>
								<p class="card-text text-muted small">Trova i migliori della
									tua zona.</p>
								<a href="<%=request.getContextPath()%>/lista_professioni"
									class="btn btn-outline-primary btn-sm rounded-pill px-4 mt-2">
									Esplora </a>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<div class="row mt-4">
		<div class="col-12">
			<div class="card shadow-sm border-radius-15 profession-card">
				<div class="card-header bg-transparent py-3 border-bottom-dark">
					<h5 class="fw-bold mb-0 text-main">
						<i class="fa-solid fa-clipboard-list me-2 text-accent"></i>Le Mie
						Richieste
					</h5>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-dark-gemini align-middle">
							<thead>
								<tr>
									<th>Descrizione</th>
									<th>Indirizzo</th>
									<th>Data e Ora</th>
									<th>Stato</th>
									<th class="text-center">Azioni</th>
								</tr>
							</thead>
							<tbody>
								<% 
                            if (listaRichiesta != null && !listaRichiesta.isEmpty()) {
                                for (Richiesta r : listaRichiesta) { 
                                    String badgeClass = "badge-muted"; 
                                    if (r.getStatoRichiesta() != null) {
                                        switch(r.getStatoRichiesta()) {
                                            case IN_ATTESA_DI_CONFERMA: badgeClass = "badge-confirm"; break;
                                            case IN_CORSO: badgeClass = "badge-process"; break;
                                            case COMPLETA: badgeClass = "badge-complete"; break;
                                        }
                                    }
                            %>
								<tr>
									<td>
										<div class="fw-bold text-main"><%= r.getDescrizione() %></div>
										<small class="text-muted">ID: #<%= r.getId() %></small>
									</td>
									<td><small class="text-muted"> <i
											class="fa-solid fa-location-dot me-1"></i><%= r.getIndirizzo() %>
									</small></td>
									<td>
										<div class="small text-main"><%= r.getData() %></div>
										<div class="small text-muted"><%= r.getOra_inizio() %>
											-
											<%= r.getOra_fine() %></div>
									</td>
									<td><span class="badge-gemini <%= badgeClass %>"> <%= r.getStatoRichiesta() %>
									</span></td>
									<td class="text-center">
										<% if (StatoRichiesta.COMPLETA.equals(r.getStatoRichiesta())) { %>
										<button type="button" class="btn btn-review btn-sm"
											data-bs-toggle="modal" data-bs-target="#modalRecensione"
											onclick="preparaModale('<%= r.getId() %>', '<%= r.getId_professionista() %>')">
											<i class="fa-solid fa-star me-1"></i>Recensisci
										</button> <% } else { %> <span class="text-muted small italic">In
											attesa...</span> <% } %>
									</td>
								</tr>
								<% } } else { %>
								<tr>
									<td colspan="5" class="text-center py-5 text-muted">
										Nessuna richiesta trovata.</td>
								</tr>
								<% } %>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modalRecensione" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content gemini-modal">
				<div class="modal-header border-bottom-dark">
					<h5 class="modal-title text-main">Lascia una recensione</h5>
					<button type="button" class="btn-close btn-close-white"
						data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<form action="<%=request.getContextPath()%>/InviaRecensione"
					method="POST">
					<div class="modal-body">
						<input type="hidden" name="idRichiesta" id="modalIdRichiesta">
						<input type="hidden" name="id_professionista"
							id="modalIdProfessionista"> <input type="hidden"
							name="campoData" value="<%= java.time.LocalDate.now() %>">

						<div class="mb-3">
							<label class="form-label text-muted">Voto</label> <select
								name="voto" class="form-select dark-input" required>
								<option value="5">⭐⭐⭐⭐⭐ (Eccellente)</option>
								<option value="4">⭐⭐⭐⭐ (Ottimo)</option>
								<option value="3">⭐⭐⭐ (Buono)</option>
								<option value="2">⭐⭐ (Sufficiente)</option>
								<option value="1">⭐ (Scarso)</option>
							</select>
						</div>
						<div class="mb-3">
							<label class="form-label text-muted">La tua esperienza</label>
							<textarea name="descrizione" class="form-control dark-input"
								rows="4" placeholder="Descrivi il servizio ricevuto..." required></textarea>
						</div>
					</div>
					<div class="modal-footer border-top-dark">
						<button type="button" class="btn btn-link text-muted"
							data-bs-dismiss="modal">Annulla</button>
						<button type="submit" class="btn btn-gemini-submit">Invia
							Recensione</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
function preparaModale(idRichiesta, idProfessionista) {
    document.getElementById('modalIdRichiesta').value = idRichiesta;
    document.getElementById('modalIdProfessionista').value = idProfessionista;
}
</script>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>