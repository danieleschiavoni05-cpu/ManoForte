<%@page import="org.elis.manoforte.model.Utente"%>
<%@page import="org.elis.manoforte.model.Professione"%>
<%@page import="org.elis.manoforte.model.Recensione"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista servizi</title>
</head>
<body>

<h1>Professionisti e Servizi Disponibili</h1>

    <%
        // Recuperiamo la lista di tutti i professionisti passata dalla Servlet
        List<Utente> listaProfessionisti = (List<Utente>) request.getAttribute("listaUtenti");

        if (listaProfessionisti != null && !listaProfessionisti.isEmpty()) {
            for (Utente u : listaProfessionisti) {
    %>
        <div class="card-professionista">
            <h3><%= u.getNome() %> <%= u.getCognome() %></h3>
            
            <p><strong>Competenze:</strong> 
                <% 
                for(Professione p : u.getProfessioni()) { 
                %>
                    [<%= p.getNome() %>] 
                <% } %>
            </p>

            <div class="form-richiesta">
                <form action="<%=request.getContextPath()%>/richiesta" method="post">
                    <input type="hidden" name="nomePro" value="<%= u.getNome() %>"> <input type="hidden" name="usernamePro" value="<%= u.getNome() %>">

                    <label>Scegli il servizio:</label>
                    <select name="nomeProfessione" required>
                        <option value="">-- Seleziona --</option>
                        <% for(Professione p : u.getProfessioni()) { %>
                            <option value="<%= p.getNome() %>"><%= p.getNome() %></option>
                        <% } %>
                    </select>

                    <br>
                    <label>Cosa ti serve?</label><br>
                    <textarea name="descrizione" placeholder="Es: Ho bisogno di riparare il rubinetto..." required></textarea>
                    
                    <br>
                    <input type="submit" class="btn-invia" value="Invia richiesta a <%= u.getNome() %>">
                </form>
            </div>
        </div>
    <%
            }
        } else {
    %>
        <p>Al momento non ci sono professionisti disponibili.</p>
    <%
        }
    %>

    <hr>
    <a href="<%=request.getContextPath()%>/index.jsp">Torna alla home</a>

</body>
</html></html>