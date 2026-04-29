function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

document.addEventListener('DOMContentLoaded', () => {

    const formAggiungiCitta = document.getElementById("aggiungiCittaForm");

    if(formAggiungiCitta!=null){
        formAggiungiCitta.addEventListener("submit", (e) =>{
            e.preventDefault();

            const erroreElement = document.getElementById("erroreCitta");

            let temp = new FormData(formAggiungiCitta);
            let formData = new URLSearchParams(temp);

            erroreElement.classList.add("d-none");
            erroreElement.innerHTML = "";

            fetch("AggiungiCitta",{
                method: "POST",
                body: formData
            }).then(response => response.json()).then(data => {
                console.log("Risposta dal server:", data);
                if(data.successo === false){
                    erroreElement.innerHTML = data.messaggio;
                    erroreElement.classList.remove("d-none");
                }else{
                    erroreElement.classList.add("d-none");
                    erroreElement.innerHTML = "";

                    window.location.href = getContextPath()+"/HomeAdmin#city";
                    window.location.reload();
                }
            }).catch(e => {
                console.error("Errore durante l'esecuzione della fetch: ", e);
                alert("Si è verificato un errore di connessione.");
            });
        })
    }

    const formAggiungiProfessione = document.getElementById("aggiungiProfessioneForm");

    if(formAggiungiProfessione!=null){
        formAggiungiProfessione.addEventListener("submit", (e) =>{
            e.preventDefault();

            const erroreElement = document.getElementById("erroreProfessione");

            let temp = new FormData(formAggiungiProfessione);
            let formData = new URLSearchParams(temp);

            erroreElement.classList.add("d-none");
            erroreElement.innerHTML = "";

            fetch("AggiungiProfessione",{
                method: "POST",
                body: formData,
            }).then(response => response.json()).then(data => {
                console.log("Risposta dal server:", data);
                if(data.successo === false){
                    erroreElement.innerHTML = data.messaggio;
                    erroreElement.classList.remove("d-none");
                }else{
                    erroreElement.classList.add("d-none");
                    erroreElement.innerHTML = "";

                    window.location.href = getContextPath()+"/HomeAdmin#professions";
                    window.location.reload();
                }
            }).catch(e => {
                console.error("Errore durante l'esecuzione della fetch: ", e);
                alert("Si è verificato un errore di connessione.");
            });
        })
    }

    const formAggiungiVeicolo = document.getElementById("aggiungiVeicoloForm");

    if(formAggiungiVeicolo!=null){
        formAggiungiVeicolo.addEventListener("submit", (e) =>{
            e.preventDefault();

            const erroreElement = document.getElementById("erroreVeicolo");

            let temp = new FormData(formAggiungiVeicolo);
            let formData = new URLSearchParams(temp);

            erroreElement.classList.add("d-none");
            erroreElement.innerHTML = "";

            fetch("AggiungiVeicolo",{
                method: "POST",
                body: formData,
            }).then(response => response.json()).then(data => {
                console.log("Risposta dal server:", data);
                if(data.successo === false){
                    erroreElement.innerHTML = data.messaggio;
                    erroreElement.classList.remove("d-none");
                }else{
                    erroreElement.classList.add("d-none");
                    erroreElement.innerHTML = "";

                    window.location.href = getContextPath()+"/HomeAdmin#vehicles";
                    window.location.reload();
                }
            }).catch(e => {
                console.error("Errore durante l'esecuzione della fetch: ", e);
                alert("Si è verificato un errore di connessione.");
            });
        })
    }

});
