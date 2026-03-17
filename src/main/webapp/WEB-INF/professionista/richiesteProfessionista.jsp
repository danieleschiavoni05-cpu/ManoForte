<nav class="mb-4">
  <div class="nav nav-pills" id="nav-request" role="tablist">
    <a class="nav-item nav-link active" id="nav-pending-tab" data-bs-toggle="tab" href="#nav-pending" role="tab" aria-controls="nav-pending" aria-selected="true">
      Richieste in attesa
    </a>
    <a class="nav-item nav-link" id="nav-running-tab" data-bs-toggle="tab" href="#nav-running" role="tab" aria-controls="nav-running" aria-selected="false">
      Richieste in corso
    </a>
    <a class="nav-item nav-link" id="nav-completed-tab" data-bs-toggle="tab" href="#nav-completed" role="tab" aria-controls="nav-completed" aria-selected="false">
      Richieste completate
    </a>
  </div>
</nav>

<div class="tab-content task-content-box" id="nav-tabContent">
  <div class="tab-pane fade show active" id="nav-pending" role="tabpanel" aria-labelledby="nav-pending-tab">
    <div class="row section-title pending mb-3">
      <h2>Richieste in attesa</h2>
    </div>
    <div class="row">
      <div class="news-grid" id="pendingTasks">
        <%if(richiesteInAttesa!=null && !richiesteInAttesa.isEmpty()){
          for(CardRichiesta r:richiesteInAttesa){ %>
        <%=createCard(r)%>
        <%}
        }else{%>
        <p class="text-muted" id="noPending">Nessuna nuova richiesta!</p>
        <%}%>
      </div>
    </div>
  </div>
  <div class="tab-pane fade" id="nav-running" role="tabpanel" aria-labelledby="nav-running-tab">
    <div class="row section-title running mb-3">
      <h2>Richieste in corso</h2>
    </div>
    <div class="row">
      <div class="news-grid" id="runningTasks">
        <%if(richiesteInCorso!=null && !richiesteInCorso.isEmpty()){
          for(CardRichiesta r:richiesteInCorso){ %>
        <%=createCard(r)%>
        <%}
        }else{%>
        <p class="text-muted" id="noRunning">Nessuna richiesta in corso!</p>
        <%}%>
      </div>
    </div>
  </div>
  <div class="tab-pane fade" id="nav-completed" role="tabpanel" aria-labelledby="nav-completed-tab">
    <div class="row section-title completed mb-3">
      <h2>Richieste completate</h2>
    </div>
    <div class="row">
      <div class="news-grid" id="completedTasks">
        <%if(richiesteCompletate!=null && !richiesteCompletate.isEmpty()){
          for(CardRichiesta r: richiesteCompletate){%>
        <%=createCard(r)%>
        <%} }else{%>
        <p class="text-muted" id="noCompleted">Nessuna richiesta completata!</p>
        <%}%>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="dettaglioModal" tabindex="-1" aria-labelledby="dettaglioModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg">
    <div class="modal-content" style="background: var(--deep-steel); border-radius: 20px; border: 1px solid var(--steel-variant); box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3); color: var(--light-silver);">

      <div class="modal-header" style="border-bottom: 1px solid var(--steel-variant);">
        <h5 class="modal-title section-title request-detail" id="dettaglioModalLabel" style="margin-bottom: 0; font-size: 1.5rem; border-left-color: var(--craft-gold); color: var(--craft-gold);">
          Dettagli Richiesta
        </h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>

      <div class="modal-body p-4">
        <div class="row">
          <div class="col-md-6">
            <div class="detail-group" style="border-bottom: 1px solid var(--steel-variant);">
              <span class="detail-label" style="color: var(--muted-silver);">Committente</span>
              <div class="detail-value" id="committente" style="color: var(--white-text);">--</div>
            </div>
            <div class="detail-group" style="border-bottom: 1px solid var(--steel-variant);">
              <span class="detail-label" style="color: var(--muted-silver);">Città</span>
              <div class="detail-value" id="citta" style="color: var(--white-text);">--</div>
            </div>
          </div>

          <div class="col-md-6">
            <div class="row">
              <div class="col-12">
                <div class="detail-group" style="border-bottom: 1px solid var(--steel-variant);">
                  <span class="detail-label" style="color: var(--muted-silver);">Data Intervento</span>
                  <div class="detail-value" id="dataIntervento" style="color: var(--white-text);">--</div>
                </div>
              </div>
              <div class="col-6">
                <div class="detail-group" style="border-bottom: 1px solid var(--steel-variant);">
                  <span class="detail-label" style="color: var(--muted-silver);">Ora inizio</span>
                  <div class="detail-value" id="oraInizio" style="color: var(--white-text);">--</div>
                </div>
              </div>
              <div class="col-6">
                <div class="detail-group" style="border-bottom: 1px solid var(--steel-variant);">
                  <span class="detail-label" style="color: var(--muted-silver);">Ora fine</span>
                  <div class="detail-value" id="oraFine" style="color: var(--white-text);">--</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row mt-2">
          <div class="col-12">
            <div class="detail-group" style="border-bottom: none; margin-bottom: 0;">
              <span class="detail-label" style="color: var(--muted-silver);">Descrizione del Lavoro</span>
              <div class="description-box" id="descrizione" style="background-color: var(--obsidian-base); border-left: 5px solid var(--craft-gold); color: var(--light-silver);">
                Caricamento dettagli in corso...
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="modal-footer d-flex justify-content-between" style="border-top: 1px solid var(--steel-variant);">
        <p class="d-none" id="response"></p>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="border-radius: 10px; background-color: var(--steel-variant); border: none; color: var(--light-silver);">Chiudi</button>
        <button type="button" class="btn px-4 d-none" id="btnCompletato" data-id="" style="border-radius: 10px; background-color: var(--craft-gold); color: var(--obsidian-base); font-weight: bold; border: none; transition: 0.3s;">Segna come completo</button>
        <button type="button" class="btn px-4 d-none" id="btnAccetta" data-id="" style="border-radius: 10px; background-color: var(--craft-gold); color: var(--obsidian-base); font-weight: bold; border: none; transition: 0.3s;">Accetta Lavoro</button>
      </div>
    </div>
  </div>
</div>

<%!public String createCard(CardRichiesta r) {
  return "<div class=\"news-card\" id=\"richiesta-"+r.getId()+"\">" +
          "<div class=\"text\">" +
          "<span class=\"titolo\">Richiesta di " + r.getCliente().getNome() + " " + r.getCliente().getCognome().charAt(0) + ".</span>" +
          "<span class=\"subtitle\">Data: " + r.getData() + "</span>" +
          "</div>" +
          "<div class=\"button-info-container\">" +
          "<button type=\"button\" class=\"button-info-arrow btn-apri-modale\" "+
          "data-id=\""+r.getId()+"\">" +
          "</button>" +
          "</div>" +
          "</div>";
}%>