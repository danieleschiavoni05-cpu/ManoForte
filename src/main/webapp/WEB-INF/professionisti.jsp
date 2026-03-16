<%@page import="org.elis.manoforte.model.Utente"%>
<%@page import="org.elis.manoforte.model.Professione"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lista Professionisti | ManoForte</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style-homeBase.css">
</head>
<body class="bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm mb-4">
        <div class="container">
            <a class="navbar-brand" href="#"><i class="fa-solid fa-handshake-angle me-2"></i>MANOFORTE</a>
            <div class="ms-auto">
                <a href="<%=request.getContextPath()%>/homeBase" class="btn btn-outline-light btn-sm">
                    <i class="fa-solid fa-house me-1"></i> Home
                </a>
            </div>
        </div>
    </nav>

    <div class="container pb-5">
        <div class="text-center mb-5">
            <h1 class="fw-bold text-dark">I Nostri Professionisti</h1>
            <p class="text-muted">Seleziona un esperto per inviare una richiesta d'intervento</p>
        </div>

        <div class="row g-4">
        <%
        List<Utente> listaProfessionisti = (List<Utente>) request.getAttribute("listaProfessionisti");
        List<Professione> listaProfessioni = (List<Professione>) request.getAttribute("listaProfessioni");

        if (listaProfessionisti != null && !listaProfessionisti.isEmpty()) {
            for (Utente u : listaProfessionisti) {
        %>
            <div class="col-md-6 col-lg-4">
                <div class="card h-100 border-0 shadow-sm p-3" style="border-radius: 15px;">
                    <div class="card-body">
                        <div class="d-flex align-items-center mb-3">
                            <div class="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center" style="width: 50px; height: 50px;">
                                <i class="fa-solid fa-user-tie fa-lg"></i>
                            </div>
                            <div class="ms-3">
                                <h5 class="card-title fw-bold mb-0">
                                    <%= u.getNome() %> <%= u.getCognome() %>
                                </h5>
                                <small class="text-muted">Professionista verificato</small>
                            </div>
                        </div>

                        <div class="mb-3">
                            <% 
                            if (u.getProfessioni() != null && listaProfessioni != null) {
                                for (Long id : u.getProfessioni()) {
                                    for (Professione p : listaProfessioni) {
                                        if (p.getId() == id.longValue()) { %>
                                            <span class="badge bg-info text-dark me-1"><%= p.getNome() %></span>
                                        <% break; }
                                    }
                                }
                            } 
                            %>
                        </div>

                        <div class="d-grid mt-4">
                            <a href="<%=request.getContextPath()%>/richiesta?emailPro=<%= u.getEmail() %>" 
                               class="btn btn-primary rounded-pill">
                                Invia Richiesta
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        <%
            }
        } else {
        %>
            <div class="col-12 text-center py-5">
                <div class="alert alert-warning shadow-sm">
                    <i class="fa-solid fa-user-slash me-2"></i>
                    Nessun professionista trovato per questa categoria.
                </div>
                <a href="<%=request.getContextPath()%>/homeBase" class="btn btn-link">Torna indietro</a>
            </div>
        <%
        }
        %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>