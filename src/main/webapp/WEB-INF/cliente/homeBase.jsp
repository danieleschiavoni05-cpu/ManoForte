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

    <!-- Stili Esistenti del Progetto -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/spinning-background.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home_professionista-style.css">
</head>

<body class="rotation">
	<jsp:include page="../includes/Navbar.jsp"/>

	<% if (session.getAttribute("messaggioSuccesso") != null) { %>
	<div class="alert alert-success alert-dismissible fade show container mt-3" role="alert">
		<%= session.getAttribute("messaggioSuccesso") %>
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	</div>
	<% session.removeAttribute("messaggioSuccesso"); %>
	<% } %>

    <div class="container container-home">
        <div class="row">
            <div class="col-12">
                <div class="task-column">
                    <!-- Welcome Section -->
                    <div class="welcome-text text-center mb-5">
                        <h1 class="display-4 fw-bold">Benvenuto <%=nomeUtente%></h1>
                        <p class="lead">Gestisci le tue richieste e trova i migliori professionisti.</p>
                    </div>

                    <!-- Quick Actions & Main Card -->
                    <div class="row">
                        <div class="col-lg-3 mb-4">
                            <div class="action-column h-100">
                                <h5 class="section-title">Azioni</h5>
                                <a href="<%=request.getContextPath()%>/RecensioniProfessionisti" class="sidebar-link mb-2 text-decoration-none">Vedi Recensioni</a>
                                <a href="<%=request.getContextPath()%>/ModificaProfilo" class="sidebar-link mb-2 text-decoration-none">Modifica Dati</a>
                            </div>
                        </div>
                        <div class="col-lg-9">
                            <div class="news-card h-100 justify-content-around align-items-center">
                                <div class="text-center">
                                    <div class="fs-1" style="color: var(--craft-gold);"><i class="fa-solid fa-briefcase"></i></div>
                                    <h5 class="titolo mt-2">PROFESSIONISTI</h5>
                                    <p class="subtitle">Trova i migliori della tua zona.</p>
                                </div>
                                <a href="<%=request.getContextPath()%>/lista_professioni" class="btn btn-sm rounded-pill px-4 mt-2 custom-button">Esplora</a>
                            </div>
                        </div>
                    </div>

                    <!-- My Requests Table -->
                    <div class="mt-5">
                        <h2 class="section-title">Le Mie Richieste</h2>
                        <div class="table-responsive">
                            <table class="table align-middle">
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
                                    <%if(listaRichiesta != null && !listaRichiesta.isEmpty()){%>
                                        <%for(Richiesta r : listaRichiesta){%>
                                            <tr>
                                                <td>
                                                    <div class="fw-bold"><%= r.getDescrizione() %></div>
                                                    <small class="text-muted">ID: #<%= r.getId() %></small>
                                                </td>
                                                <td><small><i class="fa-solid fa-location-dot me-1"></i><%= r.getIndirizzo() %></small></td>
                                                <td>
                                                    <div class="small"><%= r.getData() %></div>
                                                    <div class="small text-muted"><%= r.getOra_inizio() %> - <%= r.getOra_fine() %></div>
                                                </td>
                                                <td>
                                                    <%-- Qui puoi usare una logica per colorare i badge in base allo stato --%>
                                                    <span class="badge bg-info"><%= r.getStatoRichiesta() %></span>
                                                </td>
                                                <td class="text-center">
                                                    <% if (StatoRichiesta.COMPLETA.equals(r.getStatoRichiesta())) { %>
                                                        <button type="button" class="btn btn-sm custom-button" data-bs-toggle="modal" data-bs-target="#modalRecensione"
                                                            onclick="preparaModale('<%= r.getId() %>', '<%= r.getProfessionista().getId() %>')">
                                                            Recensisci
                                                        </button>
                                                    <%}else{%>
                                                        <span class="text-muted small fst-italic">In attesa...</span>
                                                    <%}%>
                                                </td>
                                            </tr>
                                        <%}%>
                                    <%}else{%>
                                        <tr>
                                            <td colspan="5" class="text-center py-5 text-muted">Nessuna richiesta trovata.</td>
                                        </tr>
                                    <%}%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

	<!-- Modal Recensione -->
	<div class="modal fade" id="modalRecensione" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="background-color: var(--deep-steel); border: 1px solid var(--steel-variant);">
				<div class="modal-header" style="border-bottom: 1px solid var(--steel-variant);">
					<h5 class="modal-title">Lascia una recensione</h5>
					<button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<form action="<%=request.getContextPath()%>/InviaRecensione" method="POST">
					<div class="modal-body">
						<input type="hidden" name="idRichiesta" id="modalIdRichiesta">
						<input type="hidden" name="id_professionista" id="modalIdProfessionista">
						<input type="hidden" name="campoData" value="<%= java.time.LocalDate.now() %>">

						<div class="mb-3">
							<label class="form-label text-muted">Voto</label>
							<select name="voto" class="form-select" style="background-color: var(--obsidian-base); color: var(--light-silver); border: 1px solid var(--steel-variant);" required>
								<option value="5">⭐⭐⭐⭐⭐ (Eccellente)</option>
								<option value="4">⭐⭐⭐⭐ (Ottimo)</option>
								<option value="3">⭐⭐⭐ (Buono)</option>
								<option value="2">⭐⭐ (Sufficiente)</option>
								<option value="1">⭐ (Scarso)</option>
							</select>
						</div>
						<div class="mb-3">
							<label class="form-label text-muted">La tua esperienza</label>
							<textarea name="descrizione" class="form-control" rows="4" placeholder="Descrivi il servizio ricevuto..." style="background-color: var(--obsidian-base); color: var(--light-silver); border: 1px solid var(--steel-variant);" required></textarea>
						</div>
					</div>
					<div class="modal-footer" style="border-top: 1px solid var(--steel-variant);">
						<button type="button" class="btn btn-link text-muted text-decoration-none" data-bs-dismiss="modal">Annulla</button>
						<button type="submit" class="btn custom-button">Invia Recensione</button>
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

    <jsp:include page="../includes/Footer.jsp"/>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
