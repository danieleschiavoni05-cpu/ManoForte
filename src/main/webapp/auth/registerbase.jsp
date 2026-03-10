<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- IMPORT NECESSARI PER FAR FUNZIONARE IL CODICE JAVA SOTTO --%>
<%@ page import="java.util.List" %>
<%@ page import="org.elis.manoforte.model.Citta"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrazione</title>
<style type="text/css">
/* Sfondo scuro profondo coordinato */
body {
	font-family: 'Inter', -apple-system, sans-serif;
	background-color: #0f172a;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	margin: 0;
	padding: 20px;
	color: #f1f5f9;
}

/* Card con effetto Glassmorphism */
form {
	background: rgba(30, 41, 59, 0.7);
	backdrop-filter: blur(10px);
	padding: 30px;
	border-radius: 20px;
	border: 1px solid rgba(255, 255, 255, 0.1);
	width: 100%;
	max-width: 450px;
	box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
}

h1 {
	font-size: 1.8rem;
	text-align: center;
	background: linear-gradient(to right, #818cf8, #2dd4bf);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	margin-bottom: 25px;
	font-weight: 800;
	text-transform: uppercase;
}

div {
	margin-bottom: 15px;
}

label {
	display: block;
	font-size: 0.75rem;
	text-transform: uppercase;
	letter-spacing: 0.1em;
	color: #94a3b8;
	margin-bottom: 5px;
}

/* Input e Select stilizzati */
input[type="text"], 
input[type="email"], 
input[type="password"], 
input[type="date"],
.select-neon {
	width: 100%;
	padding: 10px 14px;
	background: #1e293b;
	border: 2px solid #334155;
	border-radius: 10px;
	color: white;
	font-size: 0.95rem;
	box-sizing: border-box;
	transition: all 0.3s ease;
}

/* Stile specifico per il menu a tendina */
.select-neon {
	appearance: none;
	background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke='%2394a3b8'%3E%3Cpath stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M19 9l-7 7-7-7'%3E%3C/path%3E%3C/svg%3E");
	background-repeat: no-repeat;
	background-position: right 12px center;
	background-size: 16px;
	cursor: pointer;
}

.select-neon option {
	background-color: #1e293b;
	color: white;
}

input:focus, .select-neon:focus {
	outline: none;
	border-color: #818cf8;
	box-shadow: 0 0 0 4px rgba(129, 140, 248, 0.2);
}

input[type="date"]::-webkit-calendar-picker-indicator {
	filter: invert(1);
}

/* Bottone Neon */
input[type="submit"] {
	width: 100%;
	padding: 14px;
	margin-top: 15px;
	border: none;
	border-radius: 10px;
	background: linear-gradient(135deg, #6366f1 0%, #a855f7 100%);
	color: white;
	font-weight: 700;
	font-size: 1rem;
	cursor: pointer;
	text-transform: uppercase;
	transition: transform 0.2s, box-shadow 0.2s;
}

input[type="submit"]:hover {
	transform: translateY(-2px);
	box-shadow: 0 0 20px rgba(99, 102, 241, 0.4);
}

a {
	display: block;
	text-align: center;
	color: #94a3b8;
	text-decoration: none;
	font-size: 0.85rem;
	margin-top: 15px;
	transition: color 0.2s;
}

a:hover {
	color: #2dd4bf;
}

br {
	display: none;
}
</style>
</head>
<body>
	<h1>REGISTRAZIONE</h1>
	<form action="<%=request.getContextPath()%>/registerBase" method="post">
		<div>
			<input type="text" placeholder="nome" name="campoNome">
		</div>
		<div>
			<input type="text" placeholder="cognome" name="campoCognome">
		</div>
		
		<div>
			<input type="text" placeholder="codiceFiscale" name="campoCodiceFiscale">
		</div>
		
		<div>
			<input type="email" placeholder="email" name="campoEmail">
		</div>

		<div>
			<input type="password" placeholder="password" name="campoPassword">
		</div>

		<div>
			<label>Seleziona Città</label> 
			<select name="campoCitta" class="select-neon">
				<option value="" disabled selected>Scegli una città...</option>
				<%
					// Recuperiamo la lista passata dalla servlet
					List<Citta> listaCitta = (List<Citta>) request.getAttribute("listaCitta");
					if (listaCitta != null) {
						for (Citta c : listaCitta) {
				%>
					<option value="<%= c.getId() %>"><%= c.getNome() %></option>
				<% 
				}} %>
			</select>
		</div>

		<div>
			<label>Data di Nascita</label>
			<input type="date" name="campoData">
		</div>

		<div>
			<input type="submit" value="Registrati come Utente Base">
		</div>

		<a href="<%=request.getContextPath()%>/login">Vai alla pagina di login</a>
		<a href="<%=request.getContextPath()%>/index.jsp">Torna alla home</a>
	</form>
</body>
</html>