<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map" %>
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

<% Utente cliente = (Utente) request.getAttribute("cliente");%>
<% Richiesta richiesta = (Richiesta) request.getAttribute("richiesta");%>
<% Citta citta = (Citta) request.getAttribute("citta");%>

<%@include file="/includes/header.jsp"%>

<div class="container container-home">
    <div class="row welcome-row">
        <div class="col-12">
            <a href="<%=request.getContextPath()%>/homeprofessionista" style="text-decoration: none;">
                <button type="button" class="button-edit">
                    <i class="bi bi-arrow-left"></i> Torna indietro
                </button>
            </a>
        </div>
    </div>

    <div class="row justify-content-center">
        <div class="col-lg-10 detail-card">
            <div class="section-title request-detail">
                <h2>Dettagli Richiesta</h2>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="detail-group">
                        <span class="detail-label">Committente</span>
                        <div class="detail-value"><%=cliente.getNome()%> <%=cliente.getCognome()%></div>
                    </div>
                    <div class="detail-group">
                        <span class="detail-label">Città</span>
                        <div class="detail-value"><%=citta.getNome()%></div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="row">
                        <div class="col-12">
                            <div class="detail-group">
                                <span class="detail-label">Data Intervento</span>
                                <div class="detail-value"><%=richiesta.getData()%></div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="detail-group">
                                <span class="detail-label">Inizio</span>
                                <div class="detail-value"><%=richiesta.getOra_inizio()%></div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="detail-group">
                                <span class="detail-label">Fine</span>
                                <div class="detail-value"><%=richiesta.getOra_fine()%></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row mt-4">
                <div class="col-12">
                    <span class="detail-label">Descrizione del Lavoro</span>
                    <div class="description-box">
                        <p class="mb-0"><%=richiesta.getDescrizione()%></p>
                    </div>
                </div>
            </div>

            <div class="row mt-5">
                <div class="col-12 d-flex gap-3 justify-content-center">
                    <form action="dettagliRichiesta" method="POST">
                       <input type="hidden" name="id_richiesta" value="<%=richiesta.getId()%>">
                        <button type="submit" class="btn btn-success px-4 py-2 custom-button">Accetta Lavoro</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/includes/footer.jsp"%>

<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>