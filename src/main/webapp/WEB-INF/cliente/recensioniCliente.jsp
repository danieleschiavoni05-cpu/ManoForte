<%@ page import="org.elis.manoforte.model.Recensione" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<% List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");%>

<div class="row section-title reviews mb-3">
  <h2>Recensioni</h2>
</div>
<div class="row d-flex align-items-center">
  <div class="justify-content-center" id="pendingTasks">
    <%if(recensioni!=null && !recensioni.isEmpty()){%>
    <%for(Recensione r:recensioni){%>
    <%=createCardReview(r)%>
    <%}%>
    <%}else{%>
    <p class="text-muted" id="noPending">Nessuna recensione effettuata! Quando qualcuno avra' finito il tuo prossimo lavoro effettuane una per vederla qui</p>
    <%}%>
  </div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content" style="background: var(--deep-steel); color: white;">
      <div class="modal-header modal-border">
        <h5 class="modal-title">Conferma Eliminazione</h5>
      </div>
      <form method="post" action="<%=request.getContextPath()%>/eliminazionerecensione">
        <input type="hidden" id="id_eliminare_recensione" name="id_recensione" value="">

        <div class="modal-body modal-border">
          Sei sicuro di voler eliminare definitivamente questa recensione? L'azione non è reversibile.
        </div>
        <div class="modal-footer" style="border-top: none;">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
          <button type="submit" class="btn btn-danger">Elimina</button>
        </div>
      </form>
    </div>
  </div>
</div>


<%! public String createCardReview(Recensione r) {
  StringBuilder card = new StringBuilder("<div class='news-card review-card mb-3' id='review-" + r.getId() + "'>" +
          "<div class='d-flex align-items-start gap-3 w-100'>" +
            "<div class='text flex-grow-1'>" +
              "<div class='d-flex justify-content-between align-items-center'>" +
                "<span class='titolo'>" + r.getProfessionista().getNome() + " " + r.getProfessionista().getCognome().charAt(0) + ".</span>" +
                "<div class='d-flex align-items-center gap-2'>" +
                  "<span class='subtitle' style='font-size: 0.75rem;'>" + r.getData() + "</span>" +
                  "<button class='btn btn-link p-0 text-danger' data-bs-toggle='modal' data-bs-target='#deleteModal' " +
                    "data-id='" + r.getId() + "' " +
                    "onclick='setDeleteId(" + r.getId() + ")' "+
                    "title='Elimina recensione'>" +
                  "<i class='fa-solid fa-trash-can' style='font-size: 0.85rem;'></i>" +
                  "</button>"+
                "</div>" +
             "</div>" +
             "<div class=\"star-rating\">");

  for(BigDecimal i = BigDecimal.ONE; i.compareTo(new BigDecimal(5)) <= 0; i = i.add(BigDecimal.ONE)){
    String check = (i.compareTo(r.getVoto()) <= 0) ? "fa-solid" : "fa-regular";
    card.append(String.format("<i class=\"%s fa-star\" style=\"color: var(--craft-gold);\"></i>", check));
  }

  card.append("</div>" + "<div class='description-box mt-2' style='padding: 10px; font-size: 0.9rem; border-left: 3px solid var(--craft-gold);'>")
          .append(r.getDescrizione()).append("</div>").append("</div>").append("</div>").append("</div>");
  return card.toString();
}%>
