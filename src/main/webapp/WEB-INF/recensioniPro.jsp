<%@page import="org.elis.manoforte.model.Recensione"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Le mie recensioni</h1>

<%
List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");

if(recensioni == null || recensioni.isEmpty()){
%>
    <p>Non hai ancora ricevuto recensioni.</p>
<%
} else {
    for(Recensione r : recensioni){
%>
    <div>
        <p>
            <strong>Da:</strong> <%= r.getRichiesta().getUtenteRichiedente().getUsername() %><br>
            <strong>Descrizione:</strong> <%= r.getDescrizione() %><br>
            <strong>Voto:</strong> <%= r.getVoto() %>/5
        </p>
        <hr>
    </div>
<%
    }
}
%>

<a href="<%=request.getContextPath()%>/HomeServlet">Torna indietro</a>
</body>
</html>