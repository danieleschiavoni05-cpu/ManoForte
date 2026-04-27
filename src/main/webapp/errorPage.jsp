<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>

<%
    Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
    String title = "Oops! Qualcosa è andato storto.";
    String message = "Sembra che ci sia stato un problema inaspettato. Il nostro team è stato notificato.";

    if (statusCode != null) {
        title = "Oops! Errore " + statusCode;
        if (statusCode == 404) {
            message = "La pagina che stai cercando non esiste o è stata spostata.";
        }
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Errore | ManoForte</title>

    <!-- Stili Esistenti del Progetto -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/spinning-background.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home_professionista-style.css">

    <style>
        /* Override per centrare il contenuto della pagina di errore */
        .container-home {
            display: flex;
            align-items: center;
            justify-content: center;
            flex-grow: 1; /* Occupa tutto lo spazio verticale disponibile */
        }
    </style>
</head>

<body class="rotation">

    <jsp:include page="/WEB-INF/includes/Navbar.jsp"/>

    <div class="container container-home">
        <div class="row w-100">
            <div class="col-12">
                <div class="task-column text-center">

                    <div class="mb-4">
                        <i class="fas fa-exclamation-triangle fa-4x" style="color: var(--craft-gold);"></i>
                    </div>

                    <h1 class="display-4 fw-bold" style="color: var(--craft-gold);"><%= title %></h1>
                    <p class="lead" style="color: var(--light-silver);"><%= message %></p>
                    <p class="text-muted">Ci scusiamo per il disagio.</p>

                    <hr style="border-color: var(--steel-variant); margin: 30px 0;">

                    <a href="<%=request.getContextPath()%>/Homepage" class="btn custom-button btn-lg">Torna alla Homepage</a>

                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/includes/Footer.jsp"/>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
