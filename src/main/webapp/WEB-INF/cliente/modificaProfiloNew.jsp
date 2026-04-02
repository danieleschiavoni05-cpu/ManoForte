<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Citta" %>


<% List<String> messaggi = (List<String>) request.getAttribute("messages"); %>
<% String errore = (String) request.getAttribute("errore");%>
<% List<Citta> citta = (List<Citta>) request.getAttribute("citta"); %>


<h2>
  MODIFICA PROFILO
</h2>
<form method="post" id="formModificaProfiloProfessionista">
  <div class="row g-3" id="mainRow">
    <div class="col-md-6">
      <label class="section-title" for="nome">Nome</label>
      <input type="text" class="form-control" id="nome" name="nome" value="<%=utente.getNome()%>" placeholder="Inserire un nome">
    </div> <%-- Nome --%>

    <div class="col-md-6">
      <label class="section-title" for="cognome">Cognome</label>
      <input type="text" class="form-control" id="cognome" name="cognome" value="<%=utente.getCognome()%>" placeholder="Inserire un cognome">
    </div> <%-- Cognome --%>

    <div class="col-md-8">
      <label class="section-title" for="email">Email</label>
      <input disabled type="email" class="form-control" id="email" name="email" value="<%=utente.getEmail()%>" placeholder="Inserire un indirizzo email">
    </div> <%-- Email --%>

    <div class="col-md-4">
      <label class="section-title" for="data">Data</label>
      <input type="date" class="form-control" id="data" value="<%=utente.getDataNascita()%>" name="data_nascita">
    </div> <%-- Data di nascita --%>

    <div class="col-md-4">
      <label class="section-title" for="codice_fiscale">Codice fiscale</label>
      <input type="text" class="form-control" id="codice_fiscale" name="codice_fiscale" value="<%=utente.getCodiceFiscale()%>" maxlength="16" placeholder="Inserire un codice fiscale/partita IVA">
    </div> <%-- Codice fiscale --%>

    <div class="col-md-8">
      <label class="section-title" for="citta">Città di residenza</label>
      <select class="form-select vf" name="citta" id="citta">
        <%if(citta!=null && !citta.isEmpty()){%>
          <%for(Citta c: citta){%>
            <%if(c.getId().equals(utente.getCitta().getId())){%>
              <option selected value="<%=c.getId()%>"><%=c.getNome()%></option>
            <%}else{%>
              <option value="<%=c.getId()%>"><%=c.getNome()%></option>
            <%}%>
          <%}%>
        <%}%>
      </select>
    </div> <%-- Citta --%>

    <div class="col-md-6">
      <label class="section-title" for="nuova_password">Nuova password</label>
      <input type="password" class="form-control psw" id="nuova_password" name="nuova_password" placeholder="Inserire nuova password">
    </div> <%-- Nuova Password --%>

    <div class="col-md-6">
      <label class="section-title" for="conferma_password">Conferma password</label>
      <input type="password" class="form-control psw" id="conferma_password" name="conferma_password" placeholder="Conferma password">
    </div> <%-- Conferma Password --%>

    <div class="col-md-6">
      <label class="section-title" for="password_attuale">Password attuale</label>
      <input type="password" class="form-control psw" id="password_attuale" name="password_attuale" placeholder="Inserire la password attuale">
    </div> <%-- Vecchia password --%>

    <div class="col-12 mt-4 d-none" id="containerSuccesso">
      <div class="alert alert-success text-center shadow-sm" role="alert">
        <ul id="listaSuccessi" class="list-unstyled mb-0 fw-bold"></ul>
      </div>
    </div> <%-- Lista conferma --%>

    <div class="col-12 mt-4 d-none" id="containerErrori">
      <div class="alert alert-danger text-center shadow-sm" role="alert">
        <ul id="listaErrori" class="list-unstyled mb-0 fw-bold"></ul>
      </div>
    </div> <%-- Lista errori --%>

  </div>
  <button type="submit" class="login-register-button">Modifica profilo</button>
  <a href="<%=request.getContextPath()%>/homeBase" class="btn-back d-block text-center mt-4">
    Annulla e torna alla Home
  </a>
</form>



<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
<script src="<%=request.getContextPath()%>/js/script-registrazione_edit.js"></script>

