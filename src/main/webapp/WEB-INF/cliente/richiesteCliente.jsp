<%@page import="org.elis.manoforte.model.Richiesta"%>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.StatoRichiesta" %>

<% List<Richiesta> richiesteInAttesa = (List<Richiesta>) request.getAttribute("richiesteInAttesa");%>
<% List<Richiesta> richiesteInCorso = (List<Richiesta>) request.getAttribute("richiesteInCorso");%>
<% List<Richiesta> richiesteCompletate = (List<Richiesta>) request.getAttribute("richiesteCompletate");%>

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

</div>

<%!
    public String createCardRequest(Richiesta r) {
        String professionistaNome = (r.getProfessionista() != null) ? r.getProfessionista().getNome() + " " + r.getProfessionista().getCognome() : "Non assegnato";
        String badgeClass = "";
        String statoText = r.getStatoRichiesta().toString().replace("_", " ");

        switch (r.getStatoRichiesta()) {
            case IN_ATTESA_DI_CONFERMA:
                badgeClass = "bg-warning text-dark";
                break;
            case IN_CORSO:
                badgeClass = "bg-info text-dark";
                break;
            case COMPLETA:
                badgeClass = "bg-success text-white";
                break;
            default:
                badgeClass = "bg-secondary text-white";
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
               "</div>";
    }
%>