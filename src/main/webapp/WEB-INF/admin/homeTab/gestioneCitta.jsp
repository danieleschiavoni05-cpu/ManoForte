<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Citta" %>


<% List<Citta> citta = (List<Citta>) request.getAttribute("citta"); %>

<style>
  #aggiungiCittaForm input::placeholder {
    color: var(--muted-silver);
    opacity: 1;
  }

  #aggiungiCittaForm input {
    color: var(--light-silver);
    background-color: var(--obsidian-base);
    border: 1px solid var(--steel-variant);
  }

</style>

<div class="row section-title mb-4 align-items-center">
    <div class="col-md-12 mb-2">
        <h2 style="color: var(--white-text);">Gestione Citt&agrave;</h2>
    </div>
</div>

<div class="row">
    <div class="col-12">
        <div>
          <form method="post" id="aggiungiCittaForm" class="d-flex gap-3 align-items-center justify-content-center mb-4 flex-column">
            <div style="flex: 1; min-width: 500px; flex-direction: row; display: flex; ">
              <input type="text" name="nomeCitta" class="form-control" placeholder="Nome citt&agrave;" style="margin-right: 5px;">
                <button type="submit" class="btn-login" style="margin-left: 5px;">Aggiungi</button>
            </div>

            <p class="d-none text-danger small mt-1 mb-0" style="padding-left: 5px;" id="erroreCitta">Errore</p>
          </form>

            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
              <%if (citta != null) {%>
                <%for (Citta c : citta) { %>
                  <div class="col">
                      <div class="news-card h-100 flex-column justify-content-between align-items-stretch" style="padding: 20px; background-color: var(--obsidian-base); border: 1px solid var(--steel-variant); border-radius: 16px; ">
                        <div class="text-center mb-4 mt-2">
                            <h4 class="titolo" style="font-size: 1.5rem; margin: 0; color: var(--light-silver) !important;"><%= c.getNome() %></h4>
                        </div>
                        <div class="d-flex gap-2 w-100 mt-auto">
                          <button type="button" class="btn btn-sm w-50" style="background-color: var(--steel-variant); color: var(--white-text);"
                                  data-bs-toggle="modal" data-bs-target="#modalModificaCitta"
                                  onclick="preparaModaleModificaCitta('<%= c.getId() %>', '<%= c.getNome().replace("'", "\\'") %>')">
                              <i class="fas fa-edit"></i> Modifica
                          </button>
                          <a href="EliminaCitta?id=<%= c.getId() %>" class="btn btn-sm btn-outline-danger w-50"
                             onclick="return confirm('Sei sicuro di voler eliminare questa città? L\'operazione fallirà se ci sono utenti associati.');">
                              <i class="fas fa-trash"></i> Elimina
                          </a>
                        </div>
                      </div>
                  </div>
              <% } } else { %>
                  <div class="col-12">
                      <p class="text-muted">Nessuna città trovata.</p>
                  </div>
              <% } %>
            </div>
        </div>
    </div>
</div>

<!-- Modale Modifica Città -->
<div class="modal fade" id="modalModificaCitta" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content" style="background-color: var(--deep-steel); border: 1px solid var(--steel-variant);">
            <div class="modal-header" style="border-bottom: 1px solid var(--steel-variant);">
                <h5 class="modal-title" style="color: var(--craft-gold);">Modifica Citt&agrave;</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="ModificaCitta" method="post">
                <div class="modal-body">
                    <input type="hidden" name="id" id="modalModificaCittaId">
                    <div class="mb-3">
                        <label class="form-label" style="color: var(--muted-silver);">Nome Citt&agrave;</label>
                        <input type="text" name="nome" id="modalModificaCittaNome" class="form-control" required style="background-color: var(--obsidian-base); color: var(--light-silver); border: 1px solid var(--steel-variant);">
                    </div>
                </div>
                <div class="modal-footer" style="border-top: 1px solid var(--steel-variant);">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="background-color: var(--steel-variant); border: none;">Annulla</button>
                    <button type="submit" class="btn custom-button">Salva Modifiche</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function preparaModaleModificaCitta(id, nomeAttuale) {
        document.getElementById('modalModificaCittaId').value = id;
        document.getElementById('modalModificaCittaNome').value = nomeAttuale;
    }
</script>

<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
