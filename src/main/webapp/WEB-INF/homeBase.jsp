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
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard Utente | ManoForte</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/style-homeBase.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">

    <style>
        :root {
            --ph-bg-main: #000000;
            --ph-bg-card: #1b1b1b;
            --ph-bg-input: #282828;
            --ph-orange: #ff9900;
            --ph-orange-hover: #ffad33;
            --ph-text-main: #ffffff;
            --ph-text-muted: #999999;
            --ph-border: #333333;
        }

        body {
            background-color: var(--ph-bg-main);
            color: var(--ph-text-main);
            font-family: Arial, sans-serif;
        }

        /* Navbar Style */
        .navbar {
            background-color: var(--ph-bg-main) !important;
            border-bottom: 1px solid var(--ph-border);
        }
        .navbar-brand {
            font-weight: bold;
            color: var(--ph-text-main) !important;
        }
        .navbar-brand i {
            color: var(--ph-orange);
        }

        /* Hero Section */
        .hero-section {
            padding: 60px 0;
            background: linear-gradient(180deg, #1b1b1b 0%, #000000 100%);
            border-bottom: 1px solid var(--ph-border);
            margin-bottom: 30px;
        }

        /* Cards */
        .card {
            background-color: var(--ph-bg-card);
            border: 1px solid var(--ph-border);
            color: var(--ph-text-main);
        }

        .sidebar-link {
            display: block;
            color: var(--ph-text-main);
            text-decoration: none;
            padding: 10px;
            border-radius: 4px;
            transition: 0.2s;
        }
        .sidebar-link:hover {
            background-color: var(--ph-bg-input);
            color: var(--ph-orange);
        }

        /* Tabella */
        .table-dark-gemini {
            color: var(--ph-text-main);
        }
        .table-dark-gemini thead th {
            background: var(--ph-bg-input);
            color: var(--ph-orange);
            border-bottom: 2px solid var(--ph-orange);
            text-transform: uppercase;
        }
        .table-dark-gemini td {
            border-color: var(--ph-border);
        }

        /* Badge */
        .badge-gemini {
            padding: 5px 10px;
            border-radius: 4px;
            font-weight: bold;
            font-size: 0.7rem;
        }
        .badge-confirm { background: #443500; color: #ffcc00; }
        .badge-process { background: #441a00; color: #ff6600; }
        .badge-complete { background: #1a4400; color: #66ff00; }
        .badge-muted { background: #333; color: #ccc; }

        /* Pulsanti */
        .btn-review, .btn-gemini-submit {
            background-color: var(--ph-orange) !important;
            border: none !important;
            color: #000 !important;
            font-weight: bold;
            text-transform: uppercase;
        }
        .btn-review:hover, .btn-gemini-submit:hover {
            background-color: var(--ph-orange-hover) !important;
        }

        /* Modale */
        .gemini-modal {
            background-color: var(--ph-bg-card);
            border: 1px solid var(--ph-orange);
        }
        .dark-input {
            background-color: var(--ph-bg-input);
            border: 1px solid var(--ph-border);
            color: white !important;
        }
        .dark-input:focus {
            border-color: var(--ph-orange);
            box-shadow: 0 0 5px rgba(255, 153, 0, 0.5);
        }

        /* Helpers */
        .text-accent { color: var(--ph-orange) !important; }
        .text-main { color: var(--ph-text-main) !important; }
        .border-bottom-dark { border-bottom: 1px solid var(--ph-border); }
    </style>
</head>
<body style="background-color: var(--obsidian-base); color: var(--light-silver); margin: 0;">
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

    <jsp:include page="includes/Navbar.jsp"/>

	<header class="hero-section text-center" style="margin-top: 2rem;">
		<div class="container">
			<h1 class="display-4 fw-bold" style="color: var(--craft-gold);">
				Benvenuto
				<%=nomeUtente%></h1>
			<p class="lead" style="color: var(--muted-silver);">Che cosa hai rotto oggi?</p>
		</div>
	</header>

	<div class="container">
		<div class="row">
			<div class="col-lg-3 mb-4">
				<div class="card shadow-sm p-3 border-radius-15" style="background-color: var(--deep-steel); border: 1px solid var(--steel-variant);">
					<h5 class="fw-bold mb-3 px-2" style="color: var(--craft-gold);">Azioni rapide</h5>
					<a href="<%=request.getContextPath()%>/RecensioniProfessionisti"
						class="sidebar-link mb-2 text-decoration-none" style="color: var(--light-silver);"> <i
						class="fa-solid fa-star me-2" style="color: var(--craft-gold);"></i> Vedi Recensioni
					</a> <a href="<%=request.getContextPath()%>/ModificaProfilo"
						class="sidebar-link mb-2 text-decoration-none" style="color: var(--light-silver);"> <i
						class="fa-solid fa-pen-to-square me-2" style="color: var(--craft-gold);"></i> Modifica Dati
					</a>
				</div>
			</div>

			<div class="col-lg-9">
				<div class="row g-4">

					<div class="col-md-6 col-9">
						<div class="card h-100 profession-card p-3" style="background-color: var(--deep-steel); border: 1px solid var(--steel-variant);">
							<div class="card-body text-center">
								<div class="icon-box mx-auto" style="color: var(--craft-gold); font-size: 2rem; margin-bottom: 1rem;">
									<i class="fa-solid fa-briefcase"></i>
								</div>
								<h5 class="card-title fw-bold" style="color: var(--light-silver);">PROFESSIONISTI</h5>
								<p class="card-text small" style="color: var(--muted-silver);">Trova i migliori della
									tua zona.</p>
								<a href="<%=request.getContextPath()%>/lista_professioni"
									class="btn btn-sm rounded-pill px-4 mt-2" style="background-color: var(--craft-gold); color: var(--obsidian-base); font-weight: bold;">
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
			<div class="card shadow-sm border-radius-15 profession-card container" style="background-color: var(--deep-steel); border: 1px solid var(--steel-variant);">
				<div class="card-header bg-transparent py-3 border-bottom-dark" style="border-bottom: 1px solid var(--steel-variant);">
					<h5 class="fw-bold mb-0 text-main" style="color: var(--light-silver);">
						<i class="fa-solid fa-clipboard-list me-2 text-accent" style="color: var(--craft-gold);"></i>Le Mie
						Richieste
					</h5>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-dark-gemini align-middle" style="color: var(--light-silver);">
							<thead>
								<tr>
									<th style="background-color: transparent; color: var(--craft-gold); border-bottom: 1px solid var(--steel-variant);">Descrizione</th>
									<th style="background-color: transparent; color: var(--craft-gold); border-bottom: 1px solid var(--steel-variant);">Indirizzo</th>
									<th style="background-color: transparent; color: var(--craft-gold); border-bottom: 1px solid var(--steel-variant);">Data e Ora</th>
									<th style="background-color: transparent; color: var(--craft-gold); border-bottom: 1px solid var(--steel-variant);">Stato</th>
									<th class="text-center" style="background-color: transparent; color: var(--craft-gold); border-bottom: 1px solid var(--steel-variant);">Azioni</th>
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
									<td style="background-color: transparent; border-bottom: 1px solid var(--steel-variant);">
										<div class="fw-bold text-main" style="color: var(--light-silver);"><%= r.getDescrizione() %></div>
										<small class="text-muted" style="color: var(--muted-silver) !important;">ID: #<%= r.getId() %></small>
									</td>
									<td style="background-color: transparent; border-bottom: 1px solid var(--steel-variant);"><small class="text-muted" style="color: var(--muted-silver) !important;"> <i
											class="fa-solid fa-location-dot me-1"></i><%= r.getIndirizzo() %>
									</small></td>
									<td style="background-color: transparent; border-bottom: 1px solid var(--steel-variant);">
										<div class="small text-main" style="color: var(--light-silver);"><%= r.getData() %></div>
										<div class="small text-muted" style="color: var(--muted-silver) !important;"><%= r.getOra_inizio() %>
											-
											<%= r.getOra_fine() %></div>
									</td>
									<td style="background-color: transparent; border-bottom: 1px solid var(--steel-variant);"><span class="badge-gemini <%= badgeClass %>"> <%= r.getStatoRichiesta() %>
									</span></td>
									<td class="text-center" style="background-color: transparent; border-bottom: 1px solid var(--steel-variant);">
										<% if (StatoRichiesta.COMPLETA.equals(r.getStatoRichiesta())) { %>
										<button type="button" class="btn btn-review btn-sm"
                                                data-bs-toggle="modal" data-bs-target="#modalRecensione"
                                                onclick="preparaModale('<%= r.getId() %>', '<%= r.getProfessionista() %>')" style="background-color: var(--craft-gold); color: var(--obsidian-base); font-weight: bold;">
											<i class="fa-solid fa-star me-1"></i>Recensisci
										</button> <% } else { %> <span class="text-muted small italic" style="color: var(--muted-silver) !important;">In
											attesa...</span> <% } %>
									</td>
								</tr>
								<% } } else { %>
								<tr>
									<td colspan="5" class="text-center py-5 text-muted" style="background-color: transparent; border-bottom: 1px solid var(--steel-variant); color: var(--muted-silver) !important;">
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
			<div class="modal-content gemini-modal" style="background-color: var(--deep-steel); border: 1px solid var(--steel-variant);">
				<div class="modal-header border-bottom-dark" style="border-bottom: 1px solid var(--steel-variant);">
					<h5 class="modal-title text-main" style="color: var(--light-silver);">Lascia una recensione</h5>
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
							<label class="form-label text-muted" style="color: var(--muted-silver) !important;">Voto</label> <select
								name="voto" class="form-select dark-input" required style="background-color: var(--obsidian-base); color: var(--light-silver); border: 1px solid var(--steel-variant);">
								<option value="5">⭐⭐⭐⭐⭐ (Eccellente)</option>
								<option value="4">⭐⭐⭐⭐ (Ottimo)</option>
								<option value="3">⭐⭐⭐ (Buono)</option>
								<option value="2">⭐⭐ (Sufficiente)</option>
								<option value="1">⭐ (Scarso)</option>
							</select>
						</div>
						<div class="mb-3">
							<label class="form-label text-muted" style="color: var(--muted-silver) !important;">La tua esperienza</label>
							<textarea name="descrizione" class="form-control dark-input"
								rows="4" placeholder="Descrivi il servizio ricevuto..." required style="background-color: var(--obsidian-base); color: var(--light-silver); border: 1px solid var(--steel-variant);"></textarea>
						</div>
					</div>
					<div class="modal-footer border-top-dark" style="border-top: 1px solid var(--steel-variant);">
						<button type="button" class="btn btn-link text-muted text-decoration-none"
							data-bs-dismiss="modal" style="color: var(--muted-silver) !important;">Annulla</button>
						<button type="submit" class="btn btn-gemini-submit" style="background-color: var(--craft-gold); color: var(--obsidian-base); font-weight: bold;">Invia
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

    <jsp:include page="includes/Footer.jsp"/>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
