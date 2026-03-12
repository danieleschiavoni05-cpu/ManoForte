<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ page import="org.elis.manoforte.model.*" %>

<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Professionista | ManoForte</title>

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home_professionista-style.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/color-var.css">
    </head>
<body>

    <% Utente utente = (Utente) request.getSession().getAttribute("utenteLoggato");%>
    <% List<CardRichiesta> richiesteInAttesa = (List<CardRichiesta>) request.getAttribute("richiesteInAttesa");%>
    <% List<CardRichiesta> richiesteInCorso = (List<CardRichiesta>) request.getAttribute("richiesteInCorso");%>
    <% List<CardRichiesta> richiesteCompletate = (List<CardRichiesta>) request.getAttribute("richiesteComplete");%>
    <% List<CardRecensione> recensioni = (List<CardRecensione>) request.getAttribute("recensioni");%>

    <%@include file="/includes/header.jsp"%>

    <div class="container container-home">
        <div class="row welcome-row">
            <div class="col-md-10 welcome-text">
                <h2>Benvenuto <%=utente.getNome()%> <%=utente.getCognome()%>!</h2>
                <p>Cosa vuoi fare oggi?</p>
            </div>
            <div class="col-md-2">
                <button type="button" class="button-edit">Modifica profilo</button>
            </div>

        </div>
        <div class="row mt-2 mb-4">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <hr style="border-color: white; border-width: 1px; align-self: center">
            </div>
            <div class="col-md-2"></div>
        </div>
        <div class="row">
            <div class="col-md-9 gap-2 task-column">
                <div class="row section-title pending">
                    <h2>Richieste in attesa</h2>
                </div>
                <div class="row">
                    <div class="news-grid">
                        <%if(richiesteInAttesa!=null && !richiesteInAttesa.isEmpty()){
                            for(CardRichiesta r:richiesteInAttesa){ %>
                                <%=createCard(request.getContextPath(), r)%>
                            <%}%>
                        <%}else{%>
                            <p>Nessuna nuova richiesta!</p>
                        <%}%>
                    </div>
                </div>
                <div class="row mt-1 mb-1">
                    <div class="col-md-2"></div>
                    <div class="col-md-8">
                        <hr style="border-color: black; border-width: 1px; align-self: center">
                    </div>
                    <div class="col-md-2"></div>
                </div>
                <div class="row mt-2 section-title running">
                    <h2>Richieste in corso</h2>
                </div>
                <div class="row mt-2">
                    <div class="news-grid">
                        <%if(richiesteInCorso!=null && !richiesteInCorso.isEmpty()){
                            for(CardRichiesta r:richiesteInCorso){ %>
                                <%=createCard(request.getContextPath(), r)%>
                            <%}
                        }else{%>
                            <p>Nessuna richiesta in corso!</p>
                        <%}%>
                    </div>
                </div>
                <div class="row mt-1 mb-1">
                    <div class="col-md-2"></div>
                    <div class="col-md-8">
                        <hr style="border-color: black; border-width: 1px; align-self: center">
                    </div>
                    <div class="col-md-2"></div>
                </div>
                <div class="row mt-3 section-title completed">
                    <h2>Richieste completate</h2>
                </div>
                <div class="row mt-2">
                    <div class="news-grid">
                        <%if(richiesteCompletate!=null && !richiesteCompletate.isEmpty()){
                            for(CardRichiesta r: richiesteCompletate){%>
                                <%=createCard(request.getContextPath(), r)%>
                            <%}
                        }else{%>
                            <p>Nessuna richiesta completata!</p>
                        <%}%>
                    </div>
                </div>
            </div>
            <div class="col-md-3 ">
                <div class="card border-none shadow-sm p-3 border-radius-15 action-column">
                    <h5 class="fw-bold mb-3 px-2">Azioni rapide</h5>
                    <a href="<%=request.getContextPath()%>/ModificaProfilo" class="sidebar-link mb-2">
                        <i class="fa-solid fa-pen-to-square me-2"></i> Modifica Dati
                    </a>
                    <hr>
                    <a href="<%=request.getContextPath()%>/logout" class="sidebar-link text-danger">
                        <i class="fa-solid fa-arrow-left me-2"></i> Logout
                    </a>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/includes/footer.jsp"%>

    <%!public String createCard(String path, CardRichiesta r) {
        return "<div class=\"news-card\">" +
                "<div class=\"text\">" +
                "<span class=\"titolo\">Richiesta di " + r.getCliente().getNome() + " " + r.getCliente().getCognome().charAt(0) + ".</span>" +
                "<span class=\"subtitle\">Data: " + r.getData() + "</span>" +
                "</div>" +
                "<div class=\"button-info-container\">" +
                "<form action=\""+ path +"/homeprofessionista\" method=\"post\" style=\"margin: 0; width: 100%; height: 100%;\">"+
                "" +
                "<input type=\"hidden\" name=\"id_richiesta\" value=\"" + r.getId() + "\">"+
                "<button type=\"submit\" class=\"button-info-arrow\">" +
                "+" +
                "</button>" +
                "</form>"+
                "</div>" +
                "</div>";
    }%>

    <script src="<%=request.getContextPath()%>/js/random_color.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>