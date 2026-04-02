<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.elis.manoforte.model.Professione"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.manoforte.model.Utente"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style-homeBase.css">


</head>
<body>

<%Utente u = (Utente) session.getAttribute("utenteLoggato");%>
<%List<Professione> professioni = (List<Professione>) request.getAttribute("listaProfessioni");%>
	
<div class="container py-5">
    <div class="row g-4">
        
        <%if (professioni == null || professioni.isEmpty()) {%>
            <div class="col-12 text-center py-5">
                <div class="alert alert-info">
                    <i class="fa-solid fa-circle-info me-2"></i> 
                    Nessuna professione disponibile al momento.
                </div>
            </div>
        <%}else{%>
            <%for (Professione p : professioni) {%>
                <div class="col-md-6 col-xl-4">
                    <div class="card h-100 profession-card p-3 shadow-sm">
                        <div class="card-body text-center">
                            <div class="icon-box mx-auto mb-3">
                                <i class="fa-solid fa-briefcase fa-2x text-primary"></i>
                            </div>
                            <h5 class="card-title fw-bold text-dark"><%= p.getNome() %></h5>
                            <p class="card-text text-muted small">
                                Trova i migliori esperti in <%= p.getNome() %> della tua zona.
                            </p>
                            <a href="<%=request.getContextPath()%>/professionisti?nome=<%= p.getNome() %>"
                               class="btn btn-outline-primary btn-sm rounded-pill px-4 mt-2">
                                Esplora
                            </a>
                        </div>
                    </div>
                </div>
            <%}%>
        <%}%>
    </div>
</div>
					
					
</body>
</html>