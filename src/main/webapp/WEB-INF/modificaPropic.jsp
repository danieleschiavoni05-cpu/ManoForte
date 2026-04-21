<%@ page import="org.elis.manoforte.utility.Utility" %>
<% String propic = (String) request.getAttribute("propic"); %>

<div id="defaultImg" data-default-img="<%=Utility.DEFAULT_PROPIC_PATH%>"></div>

<h2>
    MODIFICA IMMAGINE PROFILO
</h2>

<form method="post" action="<%=request.getContextPath()%>/updateImage" enctype="multipart/form-data" id="formModificaPropicProfessionista">
    <div class="row g-3" id="mainRow">
        <div class="col-12 text-center mb-4 mt-2">
            <%if(propic==null){%>
                <img id="currentPropic" src="<%=Utility.DEFAULT_PROPIC_PATH%>" alt="Immagine Profilo Attuale" class="img-thumbnail rounded-circle shadow" style="width: 220px; height: 220px; object-fit: cover; border: 1px solid rgba(78, 115, 223, 0.3); background: rgba(15, 23, 42, 0.6); padding: 6px; transition: opacity 0.3s ease;">
            <%}else{%>
                <img id="currentPropic" src="getImmagine?path=<%=propic%>" alt="Immagine Profilo Attuale" class="img-thumbnail rounded-circle shadow" style="width: 220px; height: 220px; object-fit: cover; border: 1px solid rgba(78, 115, 223, 0.3); background: rgba(15, 23, 42, 0.6); padding: 6px; transition: opacity 0.3s ease;">
            <%}%>
        </div>

        <div class="col-md-12 mt-2">
            <label class="section-title" for="propicInput">Seleziona una nuova immagine</label>
            <input type="file" class="form-control custom-file-input" id="propicInput" name="propic" accept="image/*" required>
        </div>
    </div>
    <button type="submit" class="login-register-button mt-3 w-100">Carica immagine</button>
</form>

<div class="col-12 text-center mb-3 <%=propic==null?"d-none":""%>" id="deleteButton">
    <form method="post" action="<%=request.getContextPath()%>/deleteImage" id="formDeletePropic">
        <button type="submit" class="login-register-button btn btn-danger mt-0 w-100" style="background-color: var(--deep-steel);">
            Elimina immagine
        </button>
    </form>
</div>

<div class="col-12 mt-4 d-none" id="containerSuccessoPropic">
    <div class="alert alert-success text-center shadow-sm" role="alert">
        <ul id="listaSuccessiPropic" class="list-unstyled mb-0 fw-bold"></ul>
    </div>
</div> <%-- Lista conferma --%>

<div class="col-12 mt-4 d-none" id="containerErroriPropic">
    <div class="alert alert-danger text-center shadow-sm" role="alert">
        <ul id="listaErroriPropic" class="list-unstyled mb-0 fw-bold"></ul>
    </div>
</div> <%-- Lista errori --%>

<script>
    const defaultImg = "<%=Utility.DEFAULT_PROPIC_PATH%>";
</script>

<script src="<%=request.getContextPath()%>/js/imagePreview.js"></script>