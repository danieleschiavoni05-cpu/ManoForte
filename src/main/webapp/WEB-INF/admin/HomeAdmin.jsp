<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Utente" %>
<%@ page import="org.elis.manoforte.model.Citta" %>
<%@ page import="org.elis.manoforte.model.Professione" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard Admin | ManoForte</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home_professionista-style.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/spinning-background.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/professionista-style.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/modifica_professionista-style.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style-modificaProfilo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style-admin.css">

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

<jsp:include page="../includes/Navbar.jsp"/>

<div class="container container-home">

    <div class="row welcome-row-custom">
        <div class="col-12 d-flex justify-content-center align-items-end flex-wrap gap-3">

            <div class="profile-header-container ">
                <div class="welcome-text-container">
                    <h2 class="mb-1 display-6 fw-bold" style="color: var(--white-text); font-family: 'Poppins', sans-serif;">
                        Benvenuto ADMIN!</h2>
                </div>
            </div>

            <div class="button-row with-nav">
                <div class="form-check form-switch bg-dark p-2 rounded-3 text-white opacity-75">
                    <input class="form-check-input ms-0" type="checkbox"
                           id="disableEffect" <%=(effect!=null && effect.getValue().equals("true"))?"checked":""%>>
                    <label class="form-check-label ms-2" for="disableEffect">
                        Effettis
                    </label>
                </div>
            </div>

        </div>
    </div>


    <div class="row mt-3 mb-4">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <hr style="border-color: white; border-width: 1px; align-self: center">
        </div>
        <div class="col-md-2"></div>
    </div>

    <div class="row d-flex flex-row">
        <div class="col-md-10 gap-0 task-column">
            <div class="tab-content" id="pills-tabContent">
                <div class="tab-pane fade show active" id="city" role="tabpanel" aria-labelledby="pills-city" tabindex="0">
                    <%@include file="/WEB-INF/admin/homeTab/gestioneCitta.jsp"%>
                </div>
                <div class="tab-pane fade" id="professions" role="tabpanel" aria-labelledby="pills-professions" tabindex="0">
                    <%@include file="/WEB-INF/admin/homeTab/gestioneProfessioni.jsp"%>
                </div>
                <div class="tab-pane fade" id="vehicles" role="tabpanel" aria-labelledby="pills-vehicles" tabindex="0">
                    <%@include file="/WEB-INF/admin/homeTab/gestioneVeicoli.jsp"%>
                </div>
            </div>
        </div>

        <div class="col-md-2 ">
            <div class="card border-none shadow-sm p-3 border-radius-15 action-column text-center">
                <ul class="nav nav-pills flex-column align-items-center" id="pills-tab" role="tablist">
                    <li class="nav-item mb-2 w-100" role="presentation">
                        <button class="nav-link active w-100" id="pills-city" data-bs-toggle="pill" data-bs-target="#city" type="button" role="tab" aria-controls="tab-city" aria-selected="true">
                            Gestione citta
                        </button>
                    </li>
                    <li class="nav-item mb-2 w-100" role="presentation">
                        <button class="nav-link w-100" id="pills-professions" data-bs-toggle="pill" data-bs-target="#professions" type="button" role="tab" aria-controls="tab-professions" aria-selected="false">
                            Gestione professioni
                        </button>
                    </li>
                    <li class="nav-item mb-2 w-100" role="presentation">
                        <button class="nav-link w-100" id="pills-vehicles" data-bs-toggle="pill" data-bs-target="#vehicles" type="button" role="tab" aria-controls="tab-vehicles" aria-selected="false">
                            Gestione veicoli
                        </button>
                    </li>
                </ul>
                <hr class="w-100">
                <a href="<%=request.getContextPath()%>/logout" class="sidebar-link text-danger w-100 d-block">
                    Logout
                </a>
            </div>
        </div>
    </div>
</div>

<script src="<%=request.getContextPath()%>/js/homeAdmin-script.js"></script>

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
<jsp:include page="../includes/Footer.jsp"/>



</body>
</html>
