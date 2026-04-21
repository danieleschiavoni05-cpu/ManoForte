<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Utente" %>
<%@ page import="org.elis.manoforte.model.Citta" %>
<%@ page import="org.elis.manoforte.model.Professione" %>

<%
    List<Citta> citta = (List<Citta>) request.getAttribute("citta");
    List<Professione> professioni = (List<Professione>) request.getAttribute("professioni");
    Utente userSession = (Utente) session.getAttribute("utenteLoggato");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard Admin | ManoForte</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/spinning-background.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/professionista-style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/modifica_professionista-style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-modificaProfilo.css">
    <style>
    /* --- Dashboard Admin Styles --- */

:root {
    /* Assicurati che queste variabili siano definite, altrimenti usa i fallback */
    --deep-steel: #2c3e50;
    --craft-gold: #d4af37;
    --light-silver: #ecf0f1;
    --danger-red: #e74c3c;
    --success-green: #27ae60;
}

.homeadmin-container {
    max-width: 1000px;
    margin: 40px auto;
    padding: 20px;
}

.homeadmin-header {
    text-align: center;
    margin-bottom: 40px;
}

.homeadmin-header h1 {
    color: var(--craft-gold);
    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: 1px;
}

/* Card Principale */
.admin-card {
    background: var(--deep-steel);
    border-radius: 15px;
    padding: 30px;
    box-shadow: 0 10px 30px rgba(0,0,0,0.3);
    color: white;
}

.admin-card h2 {
    font-size: 1.5rem;
    margin-bottom: 20px;
    color: var(--craft-gold);
    border-bottom: 1px solid rgba(212, 175, 55, 0.3);
    padding-bottom: 10px;
}

.admin-card h3 {
    font-size: 1.1rem;
    margin-top: 30px;
    color: var(--light-silver);
}

/* Form di Aggiunta */
.admin-form {
    display: flex;
    gap: 10px;
    margin-bottom: 25px;
}

.admin-form input {
    flex-grow: 1;
    padding: 10px 15px;
    border-radius: 8px;
    border: 1px solid rgba(255,255,255,0.1);
    background: rgba(0,0,0,0.2);
    color: white;
}

.admin-btn-primary {
    background: var(--craft-gold);
    color: var(--deep-steel);
    border: none;
    padding: 10px 25px;
    border-radius: 8px;
    font-weight: bold;
    transition: transform 0.2s, opacity 0.2s;
}

.admin-btn-primary:hover {
    opacity: 0.9;
    transform: translateY(-2px);
}

/* Lista Elementi (Città/Professioni) */
.admin-list {
    list-style: none;
    padding: 0;
}

.admin-list-item {
    background: rgba(255,255,255,0.05);
    margin-bottom: 10px;
    padding: 15px 20px;
    border-radius: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    transition: background 0.3s;
}

.admin-list-item:hover {
    background: rgba(255,255,255,0.1);
}

.admin-list-item strong {
    font-size: 1.05rem;
    min-width: 150px;
}

/* Azioni (Modifica ed Elimina) */
.admin-actions {
    display: flex;
    align-items: center;
    gap: 20px;
}

.admin-delete-link {
    color: var(--danger-red);
    text-decoration: none;
    font-size: 0.9rem;
    font-weight: bold;
}

.admin-delete-link:hover {
    text-decoration: underline;
}

/* Form Modifica Inline */
.admin-inline-form {
    display: flex;
    gap: 5px;
    align-items: center;
}

.admin-inline-form input {
    padding: 5px 10px;
    font-size: 0.85rem;
    border-radius: 5px;
    border: none;
    background: rgba(255,255,255,0.9);
    width: 150px;
}

.admin-btn-small {
    background: #5dade2;
    color: white;
    border: none;
    padding: 5px 12px;
    border-radius: 5px;
    font-size: 0.8rem;
}

.admin-hr {
    border: 0;
    height: 1px;
    background-image: linear-gradient(to right, transparent, var(--craft-gold), transparent);
    margin: 40px 0;
    opacity: 0.5;
}

/* Alert Bootstrap Customization */
.alert {
    border: none;
    border-radius: 10px;
}
    </style>
  </head>
  

<body>

<jsp:include page="WEB-INF/includes/Navbar.jsp"/>

<div class="homeadmin-container">

    <div class="homeadmin-header">
        <h1>Area Amministratore</h1>
        <p>Gestisci città e professioni della piattaforma</p>
    </div>

    <%-- BLOCCO FEEDBACK: Visualizzazione Errori e Successi --%>
    <% 
        String errore = (String) session.getAttribute("errore");
        String successo = (String) session.getAttribute("successo");
        
        if (errore != null) { 
    %>
        <div class="alert alert-danger alert-dismissible fade show mb-4" role="alert" style="border-left: 5px solid #dc3545;">
            <i class="fa-solid fa-circle-exclamation me-2"></i>
            <strong>Attenzione:</strong> <%= errore %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <% 
            session.removeAttribute("errore");
        } 
        
        if (successo != null) { 
    %>
        <div class="alert alert-success alert-dismissible fade show mb-4" role="alert" style="border-left: 5px solid #198754;">
            <i class="fa-solid fa-check-double me-2"></i>
            <%= successo %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <% 
            session.removeAttribute("successo");
        } 
    %>

    <div class="admin-card">

        <h2>Aggiungi una Città</h2>
        <form action="AggiungiCitta" method="post" class="admin-form">
            <input type="text" name="nomeCitta" placeholder="Nome città" required>
            <button type="submit" class="admin-btn-primary">Aggiungi</button>
        </form>

        <h3>Città inserite:</h3>
        <ul class="admin-list">
            <% if (citta != null) {
                   for (Citta c : citta) { %>
                <li class="admin-list-item">
                    <strong><%= c.getNome() %></strong>
                    <div class="admin-actions">
                        <a href="EliminaCitta?id=<%= c.getId() %>" class="admin-delete-link" 
                           onclick="return confirm('Sei sicuro di voler eliminare questa città?L\'operazione fallirà se ci sono utenti associati.');">Elimina</a>
                        <form action="ModificaCitta" method="post" class="admin-inline-form">
                            <input type="hidden" name="id" value="<%= c.getId() %>">
                            <input type="text" name="nome" placeholder="Nuovo nome">
                            <button type="submit" class="admin-btn-small">Modifica</button>
                        </form>
                    </div>
                </li>
            <% } } %>
        </ul>

        <hr class="admin-hr">

        <h2>Aggiungi una Professione</h2>
        <form action="AggiungiProfessione" method="post" class="admin-form">
            <input type="text" name="nomeProfessione" placeholder="Nome professione" required>
            <button type="submit" class="admin-btn-primary">Aggiungi</button>
        </form>

        <h3>Professioni inserite:</h3>
        <ul class="admin-list">
            <% if (professioni != null) {
                   for (Professione p : professioni) { %>
                <li class="admin-list-item">
                    <strong><%= p.getNome() %></strong>
                    <div class="admin-actions">
                        <a href="EliminaProfessione?id=<%= p.getId() %>" class="admin-delete-link"
                           onclick="return confirm('Eliminare questa professione? L\'operazione fallirà se ci sono utenti associati.');">Elimina</a>
                        <form action="ModificaProfessione" method="post" class="admin-inline-form">
                            <input type="hidden" name="id" value="<%= p.getId() %>">
                            <input type="text" name="nome" placeholder="Nuovo nome">
                            <button type="submit" class="admin-btn-small">Modifica</button>
                        </form>
                    </div>
                </li>
            <% } } %>
        </ul>

    </div>
</div>

<jsp:include page="WEB-INF/includes/Footer.jsp"/>

</body>
</html>