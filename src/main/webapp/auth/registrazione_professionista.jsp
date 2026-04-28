
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Professione" %>
<%@ page import="org.elis.manoforte.model.Citta" %>
<%@ page import="org.elis.manoforte.model.Veicolo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/registrazione_professionista.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/spinning-background.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/professionista-style.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrazione professionista</title>
</head>
<body>

<% List<String> messaggi = (List<String>) request.getAttribute("messages"); %>
<% String errore = (String) request.getAttribute("errore");%>
<% List<Professione> professioni = (List<Professione>) request.getAttribute("professioni"); %>
<% List<Citta> citta = (List<Citta>) request.getAttribute("citta"); %>
<% List<Veicolo> veicoli = (List<Veicolo>) request.getAttribute("veicoli");%>

<%
    Cookie effect = null;
    Cookie[] cookies = request.getCookies();
    if(cookies!=null){
        for(Cookie c:cookies){
            if(c.getName().equals("effect")){
                effect = c;
                System.out.println(effect.getValue());
                break;
            }
        }
    }
%>
<div class="button-row">
    <div class="form-check form-switch bg-dark p-2 rounded-3 text-white opacity-75">
        <input class="form-check-input ms-0" type="checkbox" id="disableEffect" <%=(effect!=null && effect.getValue().equals("true"))?"checked":""%>>
        <label class="form-check-label ms-2" for="disableEffect">
            Effettis
        </label>
    </div>
</div>

<div class="container mb-5 mt-5">
    <div class="row justify-content-center">
        <div class="col-md-12">
            <h2>
                REGISTRAZIONE PROFESSIONISTA
            </h2>
            <form method="post" id="formRegistrazioneProfessionista">
                <div class="row g-3 <%=(errore!=null||messaggi!=null)?' ':"mb-4"%>" id="mainRow">
                    <div class="col-md-6">
                        <label class="section-title" for="nome">Nome</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="Inserire un nome">
                    </div> <%-- Nome --%>

                    <div class="col-md-6">
                        <label class="section-title" for="cognome">Cognome</label>
                        <input type="text" class="form-control" id="cognome" name="cognome"  placeholder="Inserire un cognome">
                    </div> <%-- Cognome --%>

                    <div class="col-md-8">
                        <label class="section-title" for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Inserire un indirizzo email">
                    </div> <%-- Email --%>

                    <div class="col-md-4">
                        <label class="section-title" for="data">Data</label>
                        <input type="date" class="form-control" id="data" name="data_nascita">
                    </div> <%-- Data di nascita --%>

                    <div class="col-md-10">
                        <label class="section-title" for="codice_fiscale">Codice fiscale/Partita IVA</label>
                        <input type="text" class="form-control" id="codice_fiscale" name="codice_fiscale" maxlength="16" placeholder="Inserire un codice fiscale/partita IVA">
                    </div> <%-- Email --%>

                    <div class="col-12">
                        <label class="section-title" for="citta">Città di residenza</label>
                        <select class="form-select" name="citta" id="citta">
                            <option selected disabled>Scegliere una città</option>
                            <%if(citta!=null && !citta.isEmpty()){%>
                                <%for(Citta c: citta){%>
                                    <option value="<%=c.getId()%>"><%=c.getNome()%></option>
                                <%}%>
                            <%}%>
                        </select>
                    </div> <%-- Citta --%>

                    <div class="col-12">
                        <label class="section-title">Professioni praticate</label>
                        <div class="checkboxes-container shadow-sm">
                            <% if(professioni!=null) { %>
                                <% for(Professione professione:professioni){ %>
                                    <label class="checkbox-label" for="professione_<%=professione.getNome()%>">
                                        <%=professione.getNome()%>
                                        <input type="checkbox" value="<%=professione.getId()%>" name="professioni" id="professione_<%=professione.getNome()%>">
                                    </label>
                                <%}%>
                            <%}%>
                        </div>
                    </div> <%-- Professioni --%>

                    <div class="col-12">
                        <label class="section-title">Veicoli utilizzati (lasciare libero se non si utilizzano veicoli)</label>
                        <div class="checkboxes-container shadow-sm">
                            <% if(veicoli!=null) { %>
                                <% for(Veicolo veicolo:veicoli){ %>
                                    <label class="checkbox-label" for="veicolo_<%=veicolo.getNome()%>">
                                        <%=veicolo.getNome()%>
                                        <input type="checkbox" value="<%=veicolo.getId()%>" name="veicoli" id="veicolo_<%=veicolo.getNome()%>">
                                    </label>
                                <%}%>
                            <%}%>
                        </div>
                    </div> <%-- Veicoli --%>

                    <div class="col-md-12">
                        <label class="section-title" for="tariffa">Inserire una tariffa oraria</label>
                        <div class="input-group mb-0 w-50">
                            <input type="number" name="tariffa" id="tariffa" min="0" step="0.25" class="form-control">
                            <span class="input-group-text">&euro;/h</span>
                        </div>
                    </div> <%-- Tariffa --%>

                    <div class="col-md-6">
                        <label class="section-title" for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Inserire una password">
                    </div> <%-- Password --%>

                    <div class="col-md-6">
                        <label class="section-title" for="conferma_password">Conferma password</label>
                        <input type="password" class="form-control" id="conferma_password" name="conferma_password" placeholder="Reinserire la password">
                    </div> <%-- Conferma password --%>

                    <div class="col-12 mt-4 d-none" id="containerErrori">
                        <div class="alert alert-danger text-center shadow-sm" role="alert">
                            <ul id="listaErrori" class="list-unstyled mb-0 fw-bold"></ul>
                        </div>
                    </div> <%-- Lista errori --%>

                    <div class="col-12 mt-4 d-none" id="containerSuccesso">
                        <div class="alert alert-successo text-center shadow-sm" role="alert">
                            <ul id="listaSuccessi" class="list-unstyled mb-0 fw-bold"></ul>
                        </div>
                    </div> <%-- Lista successo --%>

                </div>
                <button type="submit" class="login-register-button">Registrati</button>
            </form>
            <hr style="color: white;">
            <div class="text-center">
                <p class="mb-0">Hai già un account? <a href="<%=request.getContextPath()%>/login">Accedi ora!</a></p>
                <p class="mt-1">Sei un cliente? <a href="<%=request.getContextPath()%>/registrazionecliente">Registrati qui!</a></p>
            </div>
        </div>
    </div>
</div>

<script src="<%=request.getContextPath()%>/js/script-registrazione_edit.js"></script>
<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
</body>
</html>
