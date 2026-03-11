<link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<nav class="navbar">
    <div class="nav-container">

        <div class="nav-left-group">
            <a href="/" class="brand">
                <div class="logo-icon"></div>
                <span class="brand-name">ManoForte</span>
            </a>

            <ul class="nav-links">
                <li><a href="#">Prodotti</a></li>
                <li><a href="#">Risorse</a></li>
                <li><a href="#">Prezzi</a></li>
            </ul>
        </div>

        <div class="nav-auth">
            <%if(request.getSession().getAttribute("utenteLoggato")==null){%>
                <a href="login" class="btn-login">Accedi</a>
            <%}else{%>
                <a href="logout" class="btn-login">Logout</a>
            <%}%>
        </div>

    </div>
</nav>