<div class="row">
  <div class="col-md-9 gap-2 task-column">
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
  </div>
  <div class="col-md-3 ">
    <div class="card border-none shadow-sm p-3 border-radius-15 action-column">
      <h5 class="fw-bold mb-3 px-2">Azioni rapide</h5>
      <a href="<%=request.getContextPath()%>/" class="sidebar-link mb-2">
        ---------------
      </a>
      <hr>
      <a href="<%=request.getContextPath()%>/logout" class="sidebar-link text-danger">
        Logout
      </a>
    </div>
  </div>
</div>