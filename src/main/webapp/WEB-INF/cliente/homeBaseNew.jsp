<%@page import="org.elis.manoforte.model.StatoRichiesta"%>
<%@page import="org.elis.manoforte.model.Richiesta"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="org.elis.manoforte.model.Utente"%>

  <%Utente utente = (Utente) session.getAttribute("utenteLoggato");%>

  <%
    Cookie effect = null;
    Cookie[] cookies = request.getCookies();
    if(cookies!=null){
      for(Cookie c:cookies){
        if(c.getName().equals("effect")){
          effect = c;
          break;
        }
      }
    }
  %>

<!DOCTYPE html>
<html lang="it">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard Utente | ManoForte</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/spinning-background.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home_professionista-style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/professionista-style.css">
  </head>

  <body class="rotation">
  <jsp:include page="../includes/Navbar.jsp"/>

  <div class="container container-home">

    <% if (session.getAttribute("messaggioSuccesso") != null) { %>
      <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
        <%= session.getAttribute("messaggioSuccesso") %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      </div>
      <%session.removeAttribute("messaggioSuccesso"); %>
    <%}%>

    <div class="row welcome-row">
      <div class="col-12 welcome-text text-start">
        <h2>Benvenuto <%=utente.getNome()%> <%=utente.getCognome()%>!</h2>
        <p>Gestisci le tue richieste e trova i migliori professionisti.</p>
      </div>
    </div>

    <div class="button-row with-nav">
      <div class="form-check form-switch bg-dark p-2 rounded-3 text-white opacity-75">
        <input class="form-check-input ms-0" type="checkbox" id="disableEffect" <%=(effect!=null && effect.getValue().equals("true"))?"checked":""%>>
        <label class="form-check-label ms-2" for="disableEffect">
          Effettis
        </label>
      </div>
    </div>

    <div class="row mt-2 mb-4">
      <div class="col-md-2"></div>
      <div class="col-md-8">
        <hr style="border-color: white; border-width: 1px; align-self: center">
      </div>
      <div class="col-md-2"></div>
    </div>

    <div class="row d-flex flex-row">
      <div class="col-md-10 gap-0 task-column">
        <div class="tab-content" id="pills-tabContent">
          <div class="tab-pane fade show active" id="requests" role="tabpanel" aria-labelledby="pills-requests" tabindex="0">
            <%@include file="/WEB-INF/cliente/richiesteCliente.jsp"%>
          </div>
          <div class="tab-pane fade" id="edit" role="tabpanel" aria-labelledby="pills-edit" tabindex="0">
            <%@include file="/WEB-INF/cliente/modificaProfiloNew.jsp"%>
          </div>
          <div class="tab-pane fade" id="reviews" role="tabpanel" aria-labelledby="pills-reviews" tabindex="0">
             <%--<%@include file="/WEB-INF/cliente/modificaProfiloNew.jsp"%>--%>
          </div>
        </div>
      </div>

      <div class="col-md-2">
        <div class="card border-none shadow-sm p-3 border-radius-15 action-column">
          <ul class="nav nav-pills mb-3 flex-column" id="pills-tab" role="tablist">

            <li class="nav-item" role="presentation">
              <button class="nav-link active w-100 text-start" id="pills-requests" data-bs-toggle="pill" data-bs-target="#requests" type="button" role="tab" aria-controls="requests" aria-selected="true">
                Le mie richieste
              </button>
            </li>
            <li class="nav-item mt-2">
              <button class="nav-link w-100 text-start" id="pills-reviews" data-bs-toggle="pill" data-bs-target="#reviews" type="button" role="tab" aria-controls="reviews" aria-selected="false">
                Recensioni
              </button>
            </li>
            <li class="nav-item">
              <button class="nav-link w-100 text-start" id="pills-edit" data-bs-toggle="pill" data-bs-target="#edit" type="button" role="tab" aria-controls="edit" aria-selected="false">
                Modifica profilo
              </button>
            </li>
          </ul>
          <hr style="border-color: var(--steel-variant);">
          <a href="<%=request.getContextPath()%>/logout" class="sidebar-link text-danger text-decoration-none px-3">
            Logout
          </a>
        </div>
      </div>

    </div>
  </div>

  <div class="modal fade" id="modalRecensione" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content" style="background-color: var(--deep-steel); border: 1px solid var(--steel-variant);">
        <div class="modal-header" style="border-bottom: 1px solid var(--steel-variant);">
          <h5 class="modal-title" style="color: var(--craft-gold);">Lascia una recensione</h5>
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

  <jsp:include page="../includes/Footer.jsp"/>

  <script src="<%=request.getContextPath()%>/js/random_color.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

  <script>

    function preparaModale(idRichiesta, idProfessionista) {
      document.getElementById('modalIdRichiesta').value = idRichiesta;
      document.getElementById('modalIdProfessionista').value = idProfessionista;
    }

    function changeTab() {
      const param = window.location.hash;
      if (param) {
        let tabId = "pills-" + param.substring(1);
        let tabElement = document.getElementById(tabId);
        if (tabElement) {
          let tab = new bootstrap.Tab(tabElement);
          tab.show();
        }
      }
    }

    window.addEventListener('DOMContentLoaded', changeTab);
    window.addEventListener('hashchange', changeTab);
  </script>
  </body>
</html>