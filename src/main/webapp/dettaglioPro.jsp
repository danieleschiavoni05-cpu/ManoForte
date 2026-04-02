<%@page import="org.elis.manoforte.model.Recensione"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Utente" %>
<%@ page import="org.elis.manoforte.model.Professione" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="org.elis.manoforte.utility.Utility" %>
<%@ page import="java.math.RoundingMode" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Professionisti: <%= request.getAttribute("nomeProfessione") %></title>
    
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-home.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body {
            background-color: #121212; /* Sfondo scuro per far risaltare il design */
            color: var(--light-silver);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
        }

        .page-header {
            background: linear-gradient(135deg, var(--deep-steel) 0%, #1a1a1a 100%);
            padding: 60px 20px;
            text-align: center;
            border-bottom: 3px solid var(--craft-gold);
        }

        .page-header h1 {
            font-size: 2.5rem;
            margin-bottom: 10px;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        .prof-list-container {
            max-width: 1000px;
            margin: 50px auto;
            padding: 0 20px;
        }

        .prof-item {
            display: flex;
            align-items: center;
            background: var(--obsidian-base);
            border: 1px solid var(--steel-variant);
            border-radius: 16px;
            padding: 25px;
            margin-bottom: 25px;
            transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
            box-shadow: 0 4px 6px rgba(0,0,0,0.3);
        }

        .prof-item:hover {
            transform: translateY(-5px);
            border-color: var(--craft-gold);
            box-shadow: 0 12px 20px rgba(0,0,0,0.5);
        }

        .avatar-circle {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, var(--craft-gold), #b8860b);
            color: var(--obsidian-base);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            font-weight: 800;
            margin-right: 30px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.2);
            flex-shrink: 0;
        }

        .prof-info {
            flex-grow: 1;
        }

        .prof-info h3 {
            margin: 0;
            color: #ffffff;
            font-size: 1.6rem;
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .rating-badge {
            background: rgba(255, 193, 7, 0.1);
            color: #ffc107;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.9rem;
            font-weight: 600;
            border: 1px solid rgba(255, 193, 7, 0.3);
        }

        .prof-info .category-label {
            display: block;
            color: var(--craft-gold);
            font-size: 0.85rem;
            font-weight: bold;
            text-transform: uppercase;
            letter-spacing: 1.5px;
            margin-top: 8px;
        }

        .prof-actions {
            margin-left: 20px;
        }

        .btn-contatta {
            display: inline-block;
            background: transparent;
            border: 2px solid var(--craft-gold);
            color: var(--craft-gold);
            padding: 12px 25px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: bold;
            text-transform: uppercase;
            font-size: 0.85rem;
            transition: all 0.3s ease;
        }

        .btn-contatta:hover {
            background: var(--craft-gold);
            color: var(--obsidian-base);
            box-shadow: 0 0 15px rgba(212, 175, 55, 0.4);
        }

        .no-data {
            text-align: center;
            background: var(--obsidian-base);
            padding: 60px;
            border-radius: 20px;
            border: 1px dashed var(--steel-variant);
        }
    </style>
</head>
<body>
    <% List<Utente> professionisti = (List<Utente>) request.getAttribute("listaProfessionisti"); %>
    <% List<Recensione> tutteRecensioni = (List<Recensione>) request.getAttribute("recensioni"); %>

    <jsp:include page="/WEB-INF/includes/Navbar.jsp" />

    <header class="page-header">
        <h1 style="color: white;">Esperti in <span style="color: var(--craft-gold);"><%= request.getAttribute("nomeProfessione") %></span></h1>
        <p style="color: var(--light-silver); font-size: 1.1rem; opacity: 0.8;">Qualità e affidabilità al tuo servizio</p>
    </header>

    <div class="prof-list-container">
        <%if(professionisti != null && !professionisti.isEmpty()){%>
            <%for(Utente u : professionisti){ %>
                <%List<Recensione> recensioniProfessionista = tutteRecensioni.stream().filter(recensione -> recensione.getProfessionista().equals(u)).toList();%>
                <%BigDecimal media = Utility.calcolaMedia(recensioniProfessionista).setScale(1, RoundingMode.DOWN);%>
                <%String iniziali = (u.getNome().charAt(0)+". "+u.getCognome().charAt(0)+".").toUpperCase();%>

                <div class="prof-item">
                    <div class="avatar-circle">
                        <%= iniziali %>
                    </div>

                    <div class="prof-info">
                        <h3>
                            <%= u.getNome() %> <%= u.getCognome() %>
                            <span class="rating-badge">
                                <i class="fa-solid fa-star"></i>
                                <%if(!recensioniProfessionista.isEmpty()){%>
                                    <%=media%> <span style="font-size: 0.75rem; opacity: 0.7;">(<%= recensioniProfessionista.size() %>)</span>
                                <%}else{%>
                                    Nuova Mano
                                <%}%>
                            </span>
                        </h3>
                        <span class="category-label">
                            <i class="fa-solid fa-tools" style="margin-right: 5px;"></i>
                            <%= request.getAttribute("nomeProfessione") %>
                        </span>
                    </div>
                </div>
            <%}%>
        <%}else{%>
            <div class="no-data">
                <i class="fa-solid fa-user-slash" style="font-size: 4rem; color: var(--steel-variant); margin-bottom: 20px;"></i>
                <h2 style="color: white;">Nessun professionista trovato</h2>
                <p>Non ci sono ancora esperti registrati per questa categoria.</p>
                <a href="Homepage" style="color: var(--craft-gold); text-decoration: none; font-weight: bold;">Torna alla Home</a>
            </div>
        <%}%>
    </div>

    <jsp:include page="/WEB-INF/includes/Footer.jsp" />

</body>
</html>