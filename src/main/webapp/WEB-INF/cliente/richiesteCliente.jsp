<%@page import="org.elis.manoforte.model.Richiesta"%>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.StatoRichiesta" %>

<% List<Richiesta> richiesteInAttesa = (List<Richiesta>) request.getAttribute("richiesteInAttesa");%>
<% List<Richiesta> richiesteInCorso = (List<Richiesta>) request.getAttribute("richiesteInCorso");%>
<% List<Richiesta> richiesteCompletate = (List<Richiesta>) request.getAttribute("richiesteCompletate");%>
<% List<Richiesta> richiesteAnnullate = (List<Richiesta>) request.getAttribute("richiesteAnnullate");%>

<nav class="mb-4">
    <div class="nav nav-pills" id="nav-request" role="tablist">
        <a class="nav-item nav-link active" id="nav-pending-tab" data-bs-toggle="tab" href="#nav-pending" role="tab" aria-controls="nav-pending" aria-selected="true">
            In Attesa
        </a>
        <a class="nav-item nav-link" id="nav-running-tab" data-bs-toggle="tab" href="#nav-running" role="tab" aria-controls="nav-running" aria-selected="false">
            In Corso
        </a>
        <a class="nav-item nav-link" id="nav-completed-tab" data-bs-toggle="tab" href="#nav-completed" role="tab" aria-controls="nav-completed" aria-selected="false">
            Completate
        </a>
        <a class="nav-item nav-link" id="nav-canceled-tab" data-bs-toggle="tab" href="#nav-canceled" role="tab" aria-controls="nav-canceled" aria-selected="false">
            Annullate
        </a>
    </div>
</nav>

<div class="tab-content task-content-box" id="nav-tabContent">

    <div class="tab-pane fade show active" id="nav-pending" role="tabpanel" aria-labelledby="nav-pending-tab">
        <div class="row section-title pending mb-3">
            <h2>Richieste in attesa di conferma</h2>
        </div>
        <div class="row row-cols-12 g-1 flex-column">
            <%if(richiesteInAttesa!=null && !richiesteInAttesa.isEmpty()){
                for(Richiesta r:richiesteInAttesa){ %>
                    <div class="col"><%=createCardRequest(r)%></div>
                <%}%>
            <%}else{%>
                <p class="ms-3" style="color: var(--light-silver);" id="noPending">Nessuna richiesta in attesa.</p>
            <%}%>
        </div>
    </div>

    <div class="tab-pane fade" id="nav-running" role="tabpanel" aria-labelledby="nav-running-tab">
        <div class="row section-title running mb-3">
            <h2>Richieste in corso</h2>
        </div>
        <div class="row row-cols-12 g-4">
            <%if(richiesteInCorso!=null && !richiesteInCorso.isEmpty()){
                for(Richiesta r:richiesteInCorso){ %>
                    <div class="col"><%=createCardRequest(r)%></div>
                <%}%>
            <%}else{%>
                <p class="ms-3" style="color: var(--light-silver);" id="noRunning">Nessuna richiesta in corso.</p>
            <%}%>
        </div>
    </div>

    <div class="tab-pane fade" id="nav-completed" role="tabpanel" aria-labelledby="nav-completed-tab">
        <div class="row section-title completed mb-3">
            <h2>Richieste completate</h2>
        </div>
        <div class="row row-cols-12 g-4">
            <%if(richiesteCompletate!=null && !richiesteCompletate.isEmpty()){%>
                <%for(Richiesta r: richiesteCompletate){%>
                    <div class="col"><%=createCardRequest(r)%></div>
                <%}%>
            <%}else{%>
                <p class="ms-3" style="color: var(--light-silver);" id="noCompleted">Nessuna richiesta completata.</p>
            <%}%>
        </div>
    </div>
    
        <div class="tab-pane fade" id="nav-canceled" role="tabpanel" aria-labelledby="nav-canceled-tab">
        <div class="row section-title running mb-3">
            <h2>Richieste Annullate</h2>
        </div>
        <div class="row row-cols-12 g-4">
            <%if(richiesteAnnullate!=null && !richiesteAnnullate.isEmpty()){
                for(Richiesta r:richiesteAnnullate){ %>
                    <div class="col"><%=createCardRequest(r)%></div>
                <%}%>
            <%}else{%>
                <p class="ms-3" style="color: var(--light-silver);" id="noCanceled">Nessuna richiesta annullata.</p>
            <%}%>
        </div>
    </div>

</div>

<div class="modal fade" id="cancelRequestModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form action="<%=request.getContextPath()%>/eliminaRichiesta" method="POST"> <div class="modal-header">
                    <h5 class="modal-title text-danger">Annulla Richiesta</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Sei sicuro di voler annullare questa richiesta? Il professionista non potr� pi� vederla.
                    <input type="hidden" name="id_richiesta" id="id_eliminare">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                    <button type="submit" class="btn btn-danger">Conferma Annullamento</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="reviewModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form action="<%=request.getContextPath()%>/InviaRecensione" method="POST">
                <div class="modal-header">
                    <h5 class="modal-title">Raccontaci la tua esperienza</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="idRichiesta" id="modalRichiestaId">

                    <div class="mb-3 text-center">
                        <label class="form-label d-block">Voto</label>
                        <select name="voto" class="form-select w-50 mx-auto" required>
                            <option value="5">5 Stelle (Eccellente)</option>
                            <option value="4">4 Stelle (Ottimo)</option>
                            <option value="3">3 Stelle (Buono)</option>
                            <option value="2">2 Stelle (Sufficiente)</option>
                            <option value="1">1 Stella (Scarso)</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">La tua recensione</label>
                        <textarea name="descrizione" class="form-control" rows="4" placeholder="Com'e' andato il lavoro?" required></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
                    <button type="submit" class="btn btn-success text-white">Invia Recensione</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%!
    public String createCardRequest(Richiesta r) {
        String professionistaNome = (r.getProfessionista() != null) ? r.getProfessionista().getNome() + " " + r.getProfessionista().getCognome() : "Non assegnato";
        String badgeClass = "";
        String statoText = r.getStatoRichiesta().toString().replace("_", " ");

        // Gestione classi badge
        switch (r.getStatoRichiesta()) {
            case IN_ATTESA_DI_CONFERMA: badgeClass = "bg-warning text-dark"; break;
            case IN_CORSO: badgeClass = "bg-info text-dark"; break;
            case COMPLETA: badgeClass = "bg-success text-white"; break;
            case ANNULLATA: badgeClass = "bg-danger text-white"; break;
            default: badgeClass = "bg-secondary text-white";
        }

        String actionButton = "";

        if (r.getStatoRichiesta().name().equals("IN_ATTESA_DI_CONFERMA")) {
            actionButton = "<div class='mt-3 border-top pt-2 text-end'>" +
                    "<button class='btn btn-outline-danger btn-sm' data-bs-toggle='modal' data-bs-target='#cancelRequestModal' onclick='setDeleteId(" + r.getId() + ")'>" +
                    "<i class='fas fa-times me-1'></i> Annulla" +
                    "</button>" +
                    "</div>";
        }else if (r.getStatoRichiesta().name().equals("COMPLETA")) {
            actionButton = "<div class='mt-3 border-top pt-2 text-end'>" +
                    "<button class='btn btn-success btn-sm text-white' data-bs-toggle='modal' data-bs-target='#reviewModal' onclick='prepareReviewModal(" + r.getId() + ")'>" +
                    "<i class='fas fa-star me-1'></i> Lascia una Recensione" +
                    "</button>" +
                    "</div>";
        }

        return "<div class='detail-card h-100' id='richiesta-" + r.getId() + "'>" +
                "<div class='d-flex justify-content-between align-items-start'>" +
                "<div>" +
                "<div class='detail-label'>Professionista</div>" +
                "<div class='detail-value'>" + professionistaNome + "</div>" +
                "</div>" +
                "<span class='badge " + badgeClass + "'>" + statoText + "</span>" +
                "</div>" +

                "<div class='detail-group mt-3'>" +
                "<div class='detail-label'>Data e Ora</div>" +
                "<div class='detail-value'><i class='far fa-calendar-alt me-2'></i>" + r.getData() + " | " + r.getOra_inizio() + " - " + r.getOra_fine() + "</div>" +
                "</div>" +

                "<div class='detail-group'>" +
                "<div class='detail-label'>Indirizzo</div>" +
                "<div class='detail-value'><i class='fas fa-map-marker-alt me-2'></i>" + r.getIndirizzo() + "</div>" +
                "</div>" +

                "<div class='description-box'>" +
                "<div class='detail-label'>Descrizione richiesta</div>" +
                "<p class='mb-0'>" + r.getDescrizione() + "</p>" +
                "</div>" +
                actionButton +
                "</div>";
    }
%>