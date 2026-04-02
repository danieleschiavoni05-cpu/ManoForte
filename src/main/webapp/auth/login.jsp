<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/registrazione_professionista.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/color-var.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/professionista-style.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/spinning-background.css">

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
</head>
<body class="rotation">

    <%String errore = (String) request.getAttribute("errore");%>
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
    <div class="button-row">
        <div class="form-check form-switch bg-dark p-2 rounded-3 text-white opacity-75">
            <input class="form-check-input ms-0" type="checkbox" id="disableEffect" <%=(effect!=null && effect.getValue().equals("true"))?"checked":""%>>
            <label class="form-check-label ms-2" for="disableEffect">
                Effettis
            </label>
        </div>
    </div>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-12">
            <h2>
                LOGIN
            </h2>
            <form action="login" method="post" id="formRegistrazione">
                <div class="row g-3 <%=(errore!=null)?' ':"mb-4"%>" id="mainRow">
                    <div class="col-12">
                        <label class="section-title" for="email">Email</label>
                        <input type="text" class="form-control" id="email" name="email" placeholder="Inserire un indirizzo email">
                    </div> <%-- Email --%>
                    <div class="col-12">
                        <label class="section-title" for="password">Password</label>
                        <input type="text" class="form-control" id="password" name="password" placeholder="Inserire una password">
                    </div> <%-- Password --%>
                    <%if(errore!=null){%>
                        <div class="col-12 mt-4" id="containerErrori">
                            <div class="alert alert-danger text-center shadow-sm" role="alert">
                                    <p class="mb-0" id="error"><%=errore%></p>
                            </div>
                        </div> <%-- Lista errori --%>
                    <%}%>
                </div>
                <button type="submit" class="login-register-button">Accedi</button>
            </form>
            <hr style="color: white;">
            <div class="text-center">
                <p class="mt-1 mb-0">Sei un cliente? <a href="<%=request.getContextPath()%>/registerBase">Registrati qui!</a></p>
                <p class="mt-1 mb-0">Sei un professionista? <a href="<%=request.getContextPath()%>/registrazioneprofessionista">Registrati qui!</a></p>
            </div>
        </div>
    </div>
</div>

<script src="<%=request.getContextPath()%>/js/random_color.js"></script>
</body>
</html>

