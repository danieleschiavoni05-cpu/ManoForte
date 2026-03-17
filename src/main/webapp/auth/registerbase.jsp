

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.elis.manoforte.model.Citta"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrazione Utente Base</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/registrazione_professionista.css">
    
</head>
<body>

    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-12 col-md-10 col-lg-8 col-xl-7">
                
                <div class="register-card">
                    
                    <div class="text-center mb-5">
                        <h1 class="display-6 fw-bold text-primary">Crea il tuo Account</h1>
                        <p class="text-muted">Compila i campi sottostanti per registrarti come Utente Base</p>
                    </div>

                    <form action="<%=request.getContextPath()%>/registerBase" method="post">

                        <div class="row g-4">
                            <div class="col-md-6">
                                <label class="form-label">Nome</label>
                                <input type="text" class="form-control form-control-lg" placeholder="es. Mario" name="campoNome" required>
                            </div>
                            
                            <div class="col-md-6">
                                <label class="form-label">Cognome</label>
                                <input type="text" class="form-control form-control-lg" placeholder="es. Rossi" name="campoCognome" required>
                            </div>

                            <div class="col-12">
                                <label class="form-label">Codice Fiscale</label>
                                <input type="text" class="form-control form-control-lg text-uppercase" placeholder="Inserisci il tuo codice fiscale" name="campoCodiceFiscale" required>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Email Professionale</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white"><i class="fa-solid fa-envelope text-primary"></i></span>
                                    <input type="email" class="form-control form-control-lg" placeholder="nome@esempio.it" name="campoEmail" required>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Password</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white"><i class="fa-solid fa-lock text-primary"></i></span>
                                    <input type="password" class="form-control form-control-lg" placeholder="********" name="campoPassword" required>
                                </div>
                            </div>
                            
                            <div class="col-md-6">
                                <label class="form-label">Conferma Password</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white"><i class="fa-solid fa-lock text-primary"></i></span>
                                    <input type="password" class="form-control form-control-lg" placeholder="********" name="campoConfermaPassword" required>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Città di Residenza</label>
                                <select name="campoCitta" class="form-select form-select-lg" required>
                                    <option value="" disabled selected>Seleziona...</option>
                                    <%
                                        List<Citta> listaCitta = (List<Citta>) request.getAttribute("listaCitta");
                                        if (listaCitta != null) {
                                            for (Citta c : listaCitta) {
                                    %>
                                        <option value="<%= c.getId() %>"><%= c.getNome() %></option>
                                    <% 
                                            }
                                        } 
                                    %>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Data di Nascita</label>
                                <input type="date" class="form-control form-control-lg" name="campoData" required>
                            </div>

                            <div class="col-12 mt-5">
                                <button type="submit" class="btn btn-primary btn-lg w-100 py-3 fw-bold shadow-sm">
                                    REGISTRATI ORA
                                </button>
                            </div>
                        </div>

                        <hr class="my-5">

                        <div class="text-center">
                            <p class="text-secondary">
                                Hai già un account? <a href="<%=request.getContextPath()%>/login" class="text-decoration-none fw-bold">Accedi qui</a>
                            </p>
                            <a href="<%=request.getContextPath()%>/Homepage.jsp" class="btn btn-link text-decoration-none text-muted mt-2">
                                <i class="fa-solid fa-arrow-left me-2"></i>Torna alla Home
                            </a>
                        </div>

                    </form>
                </div> </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>