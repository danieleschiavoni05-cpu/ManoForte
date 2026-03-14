
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Professione" %>
<%@ page import="org.elis.manoforte.model.Citta" %>
<%@ page import="org.elis.manoforte.model.Veicolo" %>
<%@ page import="org.elis.manoforte.model.Utente" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/modifica_professionista-style.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Professionista | ManoForte</title>
</head>
<body>

<% Utente utente = (Utente) request.getAttribute("utenteLoggato");%>
<% List<String> messaggi = (List<String>) request.getAttribute("messages"); %>
<% String errore = (String) request.getAttribute("errore");%>
<% List<Citta> citta = (List<Citta>) request.getAttribute("citta"); %>
<% List<Veicolo> veicoli = (List<Veicolo>) request.getAttribute("veicoli");%>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-12 task-column">
            <h2>
                MODIFICA PROFILO
            </h2>
            <form method="post" id="formModificaProfiloProfessionista">
                <div class="row g-3 <%=(errore!=null||messaggi!=null)?' ':"mb-4"%>" id="mainRow">
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

                    <div class="col-md-10">
                        <label class="section-title" for="codice_fiscale">Codice fiscale/Partita IVA</label>
                        <input type="text" class="form-control" id="codice_fiscale" name="codice_fiscale" value="<%=utente.getCodiceFiscale()%>" maxlength="16" placeholder="Inserire un codice fiscale/partita IVA">
                    </div> <%-- Codice fiscale --%>

                    <div class="col-12">
                        <label class="section-title" for="citta">Città di residenza</label>
                        <select class="form-select" name="citta" id="citta">
                            <%if(citta!=null && !citta.isEmpty()){%>
                                <%for(Citta c: citta){%>
                                    <%if(c.getId()==utente.getIdCitta()){%>
                                        <option selected value="<%=c.getId()%>"><%=c.getNome()%></option>
                                    <%}else{%>
                                        <option value="<%=c.getId()%>"><%=c.getNome()%></option>
                                    <%}%>
                                <%}%>
                            <%}%>
                        </select>
                    </div> <%-- Citta --%>

                    <div class="col-12">
                        <label class="section-title">Veicoli utilizzati (lasciare libero se non si utilizzano veicoli)</label>
                        <div class="checkboxes-container shadow-sm">
                            <% if(veicoli!=null) { %>
                                <% for(Veicolo veicolo:veicoli){ %>
                                    <label class="veicolo-label" for="veicolo_<%=veicolo.getNome()%>">
                                        <%=veicolo.getNome()%>
                                        <%if(utente.getVeicoli()!=null&&utente.getVeicoli().contains(veicolo.getId())){%>
                                            <input checked type="checkbox" value="<%=veicolo.getId()%>" name="veicolo" id="veicolo_<%=veicolo.getNome()%>">
                                        <%}else{%>
                                            <input type="checkbox" value="<%=veicolo.getId()%>" name="veicolo" id="veicolo_<%=veicolo.getNome()%>">
                                        <%}%>
                                    </label>
                                <%}%>
                            <%}%>
                        </div>
                    </div> <%-- Veicoli --%>

                    <div class="col-md-12">
                        <label class="section-title" for="tariffa">Inserire una tariffa oraria</label>
                        <div class="input-group mb-0 w-50">
                            <input type="number" name="tariffa" id="tariffa" min="0" class="form-control" value="<%=utente.getTariffa()%>">
                            <span class="input-group-text">€/h</span>
                        </div>
                    </div> <%-- Tariffa --%>

                    <div class="col-md-6">
                        <label class="section-title" for="nuova_password">Nuova password</label>
                        <input type="password" class="form-control" id="nuova_password" name="nuova_password" placeholder="Inserire una nuova password (lasciare vuoto per non modificare)">
                    </div> <%-- Nuova Password --%>

                    <div class="col-md-6">
                        <label class="section-title" for="conferma_password">Conferma password</label>
                        <input type="password" class="form-control" id="conferma_password" name="conferma_password" placeholder="Reinserire la password">
                    </div> <%-- Conferma Password --%>

                    <div class="col-md-6">
                        <label class="section-title" for="password_attuale">Password attuale</label>
                        <input type="password" class="form-control" id="password_attuale" name="password_attuale" placeholder="Inserire la password attuale">
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
                <button type="submit" class="login-register-button">Registrati</button>
            </form>

            <hr style="color: white;">
            <div class="text-center">
                <p class="mb-0">Hai già un account? <a href="<%=request.getContextPath()%>/login">Accedi ora!</a></p>
                <p class="mt-1">Sei un cliente? <a href="<%=request.getContextPath()%>/registazionecliente">Registrati qui!</a></p>
            </div>

        </div>
    </div>
</div>

<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
<script src="<%=request.getContextPath()%>/js/script-edit.js"></script>
</body>
</html>

