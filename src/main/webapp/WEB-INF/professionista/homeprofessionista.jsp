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
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/professionista-style.css">
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
                <a style="text-decoration: none;" href="<%=request.getContextPath()%>/modificaProfiloProfessionista">
                    <button type="button" class="button-edit">Modifica profilo</button>
                </a>
            </div>
        </div>

        <div class="row mt-2 mb-4">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <hr style="border-color: white; border-width: 1px; align-self: center">
            </div>
            <div class="col-md-2"></div>
        </div>

        <%@include file="/WEB-INF/professionista/richiesteProfessionista.jsp"%>

    </div>

    <%@include file="/includes/footer.jsp"%>

    <div class="modal fade" id="dettaglioModal" tabindex="-1" aria-labelledby="dettaglioModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content" style="background: rgba(255, 255, 255, 0.90); backdrop-filter: blur(15px); border-radius: 20px; border: 1px solid rgba(255, 255, 255, 0.6); box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);">

                <div class="modal-header" style="border-bottom: 1px solid rgba(0,0,0,0.1);">
                    <h5 class="modal-title section-title request-detail" id="dettaglioModalLabel" style="margin-bottom: 0; font-size: 1.5rem; border-left-color: var(--color1);">
                        Dettagli Richiesta
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body p-4">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="detail-group">
                                <span class="detail-label">Committente</span>
                                <div class="detail-value" id="committente">--</div>
                            </div>
                            <div class="detail-group">
                                <span class="detail-label">Città</span>
                                <div class="detail-value" id="citta">--</div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="row">
                                <div class="col-12">
                                    <div class="detail-group">
                                        <span class="detail-label">Data Intervento</span>
                                        <div class="detail-value" id="dataIntervento">--</div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="detail-group">
                                        <span class="detail-label">Ora inizio</span>
                                        <div class="detail-value" id="oraInizio">--</div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="detail-group">
                                        <span class="detail-label">Ora fine</span>
                                        <div class="detail-value" id="oraFine">--</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row mt-2">
                        <div class="col-12">
                            <div class="detail-group" style="border-bottom: none; margin-bottom: 0;">
                                <span class="detail-label">Descrizione del Lavoro</span>
                                <div class="description-box" id="descrizione">
                                    Caricamento dettagli in corso...
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer d-flex justify-content-between" style="border-top: 1px solid rgba(0,0,0,0.1);">
                    <p class="d-none" id="response"></p>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="border-radius: 10px;">Chiudi</button>
                    <button type="button" class="btn custom-button px-4 d-none" id="btnCompletato" data-id="" style="border-radius: 10px; color: white; font-weight: bold; border: none;">Segna come completo</button>
                    <button type="button" class="btn custom-button px-4 d-none" id="btnAccetta" data-id="" style="border-radius: 10px; color: white; font-weight: bold; border: none;">Accetta Lavoro</button>
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


    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/random_color.js"></script>
    <script src="<%=request.getContextPath()%>/js/dettagliRichieste.js"></script>
</body>
</html>
