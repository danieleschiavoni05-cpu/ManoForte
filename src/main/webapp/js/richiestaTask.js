function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

document.addEventListener('DOMContentLoaded', () => {

    const formInvioRichiesta = document.getElementById("formRichiesta");

    if(formInvioRichiesta!=null){
        formInvioRichiesta.addEventListener("submit", (e) =>{
            e.preventDefault();

            const containerErrori = document.getElementById("containerErrori");
            const listaErrori = document.getElementById("listaErrori");

            let temp = new FormData(formInvioRichiesta);
            let formData = new URLSearchParams(temp);

            containerErrori.classList.add("d-none");
            listaErrori.innerHTML = "";

            fetch("richiesta",{
                method: "POST",
                body: formData,
            }).then(response => response.json()).then(data => {
                console.log("Risposta dal server:", data);
                if(data.successo === false){
                    const li = document.createElement("li");
                    li.textContent = data.messaggio;
                    listaErrori.appendChild(li);

                    containerErrori.classList.remove("d-none")
                }else{
                    window.location.href = getContextPath()+"/homeBase";
                }
            }).catch(e => {
                console.error("Errore durante l'esecuzione della fetch: ", e);
                alert("Si è verificato un errore di connessione.");
            });
        })
    }

});
