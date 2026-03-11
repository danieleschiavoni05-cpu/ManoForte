<%@page import="org.elis.manoforte.model.Richiesta"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.manoforte.model.Recensione"%>
<%@page import="org.elis.manoforte.model.Utente"%>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Dashboard Professionista | ManoForte</title>

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home_professionista-style.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/color-var.css">
    </head>
<body>

    <% Utente utente = (Utente) request.getSession().getAttribute("utenteLoggato");%>
    <% List<Richiesta> richieste = (List<Richiesta>) request.getAttribute("richieste");%>
    <% List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");%>
    <% Map<Long, String> listaClienti = (Map<Long, String>) request.getAttribute("listaClienti");%>

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
                        <%for(int i=0; i<15; i++){%>
                        <div class="news-card">
                            <div class="text">
                                <span class="titolo">Nuova richiesta da Ciao</span>
                                <span class="subtitle">Per il 12-12-2000</span>
                            </div>
                            <div class="button-info-container">
                                <button type="button" class="button-info-arrow"> > </button>
                            </div>
                        </div>
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
                        <%for(int i=0; i<4; i++){%>
                        <div class="news-card">
                            <div class="text">
                                <span class="titolo">Nuova richiesta da Ciao</span>
                                <span class="subtitle">Per il 12-12-2000</span>
                            </div>
                            <div class="button-info-container">
                                <button type="button" class="button-info-arrow"> > </button>
                            </div>
                        </div>
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
                        <%if(richieste!=null){
                            for(Richiesta r:richieste){%>
                                <div class="news-card">
                                    <div class="text">
                                        <span class="titolo">Richiesta di <%=listaClienti.get(r.getId_cliente())%></span>
                                        <span class="subtitle">Per il 12-12-2000</span>
                                    </div>
                                    <div class="button-info-container">
                                        <button type="button" class="button-info-arrow"> > </button>
                                    </div>
                                </div>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>