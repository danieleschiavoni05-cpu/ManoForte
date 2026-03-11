<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="org.elis.manoforte.model.Utente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-modifcaProfilo.css">
</head>
<body>
	<%Utente utente=(Utente) request.getAttribute("utenteLoggato"); %>
	<form action="<%=request.getContextPath()%>/ModificaProfilo" method="POST">
    <input type="hidden" name="id" value="${utente.id}">
    
    <label>Nome:</label>
    <input type="text" name="nome" value="<%= utente.getNome()%>" required>
    
    <label>Città (ID):</label>
    <input type="number" name="id_citta" value="<%= utente.getIdCitta()%>" required>
    

    <button type="submit">Salva Modifiche</button>
</form>

</body>
</html>