
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
      <p class="text-muted" id="noPending">Nessuna recensione ricevuta! Lavora di pi&ugrave;!</p>
    <%}%>
  </div>
</div>

<%!public String createCardReview(Recensione r) {

    return "<div class='news-card review-card mb-3' id='review-" + r.getId() + "'>" +
              "<div class='d-flex align-items-start gap-3 w-100'>" +
                "<div class='icon-container'>" +
                  "<i class='bi bi-chat-quote-fill'></i>" +
                "</div>" +
                "<div class='text flex-grow-1'>" +
                  "<div class='d-flex justify-content-between align-items-center'>" +
                    "<span class='titolo'>" + r.getCliente().getNome() + " " + r.getCliente().getCognome().charAt(0) + ".</span>" +
                    "<span class='subtitle' style='font-size: 0.75rem;'>" + r.getData() + "</span>" +
                  "</div>" +
                  "<div class='text-muted'>"+ r.getVoto() +"/5.0 </div>"+
                  "<div class='description-box mt-2' style='padding: 10px; font-size: 0.9rem; border-left: 3px solid var(--craft-gold);'>" +
                    r.getDescrizione() +
                  "</div>" +
                "</div>" +
              "</div>"+
            "</div>";
  }%>
