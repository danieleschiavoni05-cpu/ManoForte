<%@page import="java.util.List"%>
<%@ page import="org.elis.manoforte.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Professionista | ManoForte</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home_professionista-style.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/spinning-background.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/color-var.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/professionista-style.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/modifica_professionista-style.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style-modificaProfilo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/disponibilita-professionista.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/footer.css">


</head>
<body class="rotation">

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


<% Utente utente = (Utente) request.getAttribute("utenteLoggato");%>

<%@include file="/WEB-INF/includes/Navbar.jsp"%>

<div class="container container-home">
    <div class="row welcome-row">
        <div class="col-12 welcome-text">
            <h2>Benvenuto <%=utente.getNome()%> <%=utente.getCognome()%>!</h2>
            <p>Cosa vuoi fare oggi?</p>
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
                   <%@include file="/WEB-INF/professionista/homeTab/richiesteProfessionista.jsp"%>
                </div>
                <div class="tab-pane fade" id="edit" role="tabpanel" aria-labelledby="pills-edit" tabindex="0">
                    <%@include file="/WEB-INF/professionista/homeTab/modificaProfiloProfessionista.jsp"%>
                </div>
                <div class="tab-pane fade" id="availability" role="tabpanel" aria-labelledby="pills-availability" tabindex="0">
                    <%@include file="/WEB-INF/professionista/homeTab/disponibilitaProfessionista.jsp"%>
                </div>
                <div class="tab-pane fade" id="reviews" role="tabpanel" aria-labelledby="pills-reviews" tabindex="0">
                    <%@include file="/WEB-INF/professionista/homeTab/recensioniProfessionista.jsp"%>
                </div>
                <div class="tab-pane fade" id="propic" role="tabpanel" aria-labelledby="pills-propic" tabindex="0">
                    <%--<%@include file="/WEB-INF/professionista/recensioniProfessionista.jsp"%>--%>
                </div>
            </div>
        </div>

        <div class="col-md-2 ">
            <div class="card border-none shadow-sm p-3 border-radius-15 action-column">
                <ul class="nav nav-pills mb-3 flex-column" id="pills-tab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="pills-requests" data-bs-toggle="pill" data-bs-target="#requests" type="button" role="tab" aria-controls="tab-requests" aria-selected="true">
                            Le mie richieste
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="pills-availability" data-bs-toggle="pill" data-bs-target="#availability" type="button" role="tab" aria-controls="tab-availability" aria-selected="false">
                            Disponibilità
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="pills-reviews" data-bs-toggle="pill" data-bs-target="#reviews" type="button" role="tab" aria-controls="tab-reviews" aria-selected="false">
                            Recensioni
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="pills-edit" data-bs-toggle="pill" data-bs-target="#edit" type="button" role="tab" aria-controls="tab-edit" aria-selected="false">
                            Modifica profilo
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="pills-propic" data-bs-toggle="pill" data-bs-target="#propic" type="button" role="tab" aria-controls="tab-propic" aria-selected="false">
                            Foto profilo
                        </button>
                    </li>
                </ul>
                <hr>
                <a href="<%=request.getContextPath()%>/logout" class="sidebar-link text-danger">
                    Logout
                </a>
            </div>
        </div>
    </div>

</div>

<%@include file="/WEB-INF/includes/Footer.jsp"%>



<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
<script src="<%=request.getContextPath()%>/js/dettagliRichieste.js"></script>
<script src="<%=request.getContextPath()%>/js/aggiungiDisponibilita.js"></script>

<script>
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
