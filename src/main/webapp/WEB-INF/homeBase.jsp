<%@page import="org.elis.manoforte.model.StatoRichiesta"%>
<%@page import="org.elis.manoforte.model.Richiesta"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.elis.manoforte.model.Professione"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.manoforte.model.StatoRichiesta"%>
<%@page import="org.elis.manoforte.model.Recensione"%>
<%@page import="org.elis.manoforte.model.Utente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Utente</title>
<style>
    :root {
        --sidebar-bg: #1e293b;
        --main-bg: #0f172a;
        --accent: #38bdf8;
        --text-white: #f8fafc;
        --text-muted: #94a3b8;
        --card-bg: #1e293b;
    }

    body {
        font-family: 'Inter', system-ui, sans-serif;
        background-color: var(--main-bg);
        color: var(--text-white);
        margin: 0;
        display: flex;
        min-height: 100vh;
    }

    /* Sidebar Sinistra */
    .sidebar {
        width: 280px;
        background-color: var(--sidebar-bg);
        padding: 30px 20px;
        display: flex;
        flex-direction: column;
        border-right: 1px solid #334155;
    }

    .sidebar h2 {
        font-size: 1.2rem;
        color: var(--accent);
        text-transform: uppercase;
        letter-spacing: 1px;
        margin-bottom: 40px;
    }

    .nav-link {
        color: var(--text-white);
        text-decoration: none;
        padding: 12px 15px;
        border-radius: 8px;
        margin-bottom: 10px;
        transition: 0.3s;
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .nav-link:hover {
        background: rgba(56, 189, 248, 0.1);
        color: var(--accent);
    }

    .nav-link.active {
        background: var(--accent);
        color: var(--main-bg);
        font-weight: bold;
    }

    /* Area Contenuto Principale */
    .main-content {
        flex: 1;
        padding: 50px;
        overflow-y: auto;
    }

    .header-section {
        margin-bottom: 40px;
    }

    /* Grid Professioni Migliorata */
    .grid-container {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
        gap: 20px;
    }

    .profession-card {
        background: var(--card-bg);
        border: 1px solid #334155;
        border-radius: 16px;
        padding: 25px;
        text-align: center;
        transition: 0.3s;
        cursor: pointer;
        text-decoration: none;
        color: var(--text-white) !important;
    }

    .profession-card:hover {
        border-color: var(--accent);
        transform: translateY(-5px);
        background: #243147;
    }

    .profession-card i {
        font-size: 2rem;
        color: var(--accent);
        display: block;
        margin-bottom: 15px;
    }
</style><style>
    :root {
        --primary-color: #2563eb;
        --secondary-color: #64748b;
        --bg-color: #f8fafc;
        --card-bg: #ffffff;
        --text-color: #1e293b;
    }

    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: var(--bg-color);
        color: var(--text-color);
        line-height: 1.6;
        margin: 0;
        padding: 40px 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    h1 {
        color: var(--primary-color);
        margin-bottom: 10px;
    }

    .container {
        max-width: 800px;
        width: 100%;
    }

    /* Griglia Professioni */
    .professioni-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
        gap: 20px;
        padding: 0;
        list-style: none;
        margin: 30px 0;
    }

    .professioni-grid li a {
        display: block;
        background: var(--card-bg);
        padding: 20px;
        text-align: center;
        text-decoration: none;
        color: var(--primary-color);
        font-weight: 600;
        border-radius: 12px;
        box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s, box-shadow 0.2s;
        border: 1px solid #e2e8f0;
    }

    .professioni-grid li a:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
        background-color: var(--primary-color);
        color: white;
    }

    /* Pulsanti di Azione */
    .actions {
        display: flex;
        gap: 15px;
        margin-top: 30px;
        justify-content: center;
    }

    .btn {
        padding: 12px 24px;
        border-radius: 8px;
        text-decoration: none;
        font-weight: 500;
        transition: opacity 0.2s;
    }

    .btn-profilo {
        background-color: var(--primary-color);
        color: white;
    }

    .btn-back {
        background-color: var(--secondary-color);
        color: white;
    }

    .btn:hover {
        opacity: 0.9;
    }

    .empty-msg {
        text-align: center;
        color: var(--secondary-color);
        font-style: italic;
    }
</style>
</head>
<body>

	<h1>Benvenuto Utente</h1>
	<p>Scegli una professione:</p>

	<ul>
		<%
List<Professione> professioni = (List<Professione>) request.getAttribute("professioni");
List<Richiesta> richieste = (List<Richiesta>) request.getAttribute("richieste");

if (professioni == null || professioni.isEmpty()) {
%>
		<p>Nessuna professione disponibile.</p>
		<%
} else {
    for (Professione p : professioni) {
%>
		<li><a
			href="<%=request.getContextPath()%>/professionisti?nome=<%=p.getNome()%>">
				<%= p.getNome() %>
		</a></li>
		<%
    }
}
%>
	</ul>


	<a href="<%=request.getContextPath()%>/ModificaProfilo"
		class="btn-profilo"> Modifica Profilo </a>

	<a href="<%=request.getContextPath()%>/index.jsp">Torna alla home</a>

</body>
</html>