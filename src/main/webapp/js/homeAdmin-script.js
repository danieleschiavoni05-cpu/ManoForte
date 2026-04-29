function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function addEditFetch(tab, url, formData){
    if(tab==="CITY"){
        const erroreElement = document.getElementById("erroreCitta");

        erroreElement.classList.add("d-none");
        erroreElement.innerHTML = "";

        fetch(url,{
            method: "POST",
            body: formData
        }).then(response => response.json())
            .then(data => {
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
            })
            .catch(e => {
            console.error("Errore durante l'esecuzione della fetch: ", e);
            alert("Si è verificato un errore di connessione.");
        });
    }else if(tab==="PROFESSIONS"){
        const erroreElement = document.getElementById("erroreProfessione");

        erroreElement.classList.add("d-none");
        erroreElement.innerHTML = "";

        fetch(url,{
            method: "POST",
            body: formData,
        }).then(response => response.json())
            .then(data => {
                console.log("Risposta dal server:", data);
                if(data.successo === false){
                    erroreElement.innerHTML = data.messaggio;
                    erroreElement.classList.remove("d-none");
                }else{
                    erroreElement.classList.add("d-none");
                    erroreElement.innerHTML = "";

                    window.location.href = getContextPath()+"/HomeAdmin#professions";
                    window.location.reload();
                }})
            .catch(e => {
                console.error("Errore durante l'esecuzione della fetch: ", e);
                alert("Si è verificato un errore di connessione.");
            });
    }else if(tab==="VEHICLES"){
        const erroreElement = document.getElementById("erroreVeicolo");

        erroreElement.classList.add("d-none");
        erroreElement.innerHTML = "";

        fetch(url,{
            method: "POST",
            body: formData,
        }).then(response => response.json())
            .then(data => {
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
            })
            .catch(e => {
                console.error("Errore durante l'esecuzione della fetch: ", e);
                alert("Si è verificato un errore di connessione.");
            });
    }
}

document.addEventListener('DOMContentLoaded', () => {

    const formAggiungiCitta = document.getElementById("aggiungiCittaForm");
    const formModificaCitta = document.getElementById("modificaCittaForm");
    const formEliminaCitta = document.getElementById("eliminaCittaForm");
    const btnCloseAlertCitta = document.getElementById("btnCloseAlertCitta");

    if(formAggiungiCitta!=null){
        formAggiungiCitta.addEventListener("submit", (e) =>{
            e.preventDefault();

            let temp = new FormData(formAggiungiCitta);
            let formData = new URLSearchParams(temp);

            addEditFetch("CITY", "AggiungiCitta", formData);
        })
    }

    if(formModificaCitta!=null){
        formModificaCitta.addEventListener("submit", (e) =>{
            e.preventDefault();

            let temp = new FormData(formModificaCitta);
            let formData = new URLSearchParams(temp);

            addEditFetch("CITY", "ModificaCitta", formData);
        })
    }

    if(formEliminaCitta!=null){
        formEliminaCitta.addEventListener("submit", (e) =>{
            e.preventDefault();

            const erroreElement = document.getElementById("erroreCitta");
            const containerErrore = document.getElementById("containerErroreCitta")

            console.log(erroreElement);
            console.log(containerErrore);

            let temp = new FormData(formEliminaCitta);
            let formData = new URLSearchParams(temp);

            containerErrore.classList.add("d-none");
            erroreElement.innerHTML = "";

            fetch("EliminaCitta",{
                method: "POST",
                body: formData
            }).then(response => response.json()).then(data => {
                console.log("Risposta dal server:", data);
                if(data.successo === false){
                    erroreElement.innerHTML = data.messaggio;
                    containerErrore.classList.remove("d-none");
                    containerErrore.classList.add("show")
                    bootstrap.Modal.getInstance(document.getElementById('modalEliminaCitta')).hide();
                }else{
                    containerErrore.classList.remove('show');
                    containerErrore.classList.add("d-none");
                    erroreElement.innerHTML = "";

                    window.location.href = getContextPath()+"/HomeAdmin#city";
                    window.location.reload();
                }
            }).catch(e => {
                console.log("Errore nell'esecuzione della fetch: ", e);
                erroreElement.innerHTML = "Si è verificato un errore di connessione.";
                containerErrore.classList.remove("d-none");
                containerErrore.classList.add("show")
            });
        })
    }

    if(btnCloseAlertCitta!=null){
        btnCloseAlertCitta.addEventListener('click', () => {
            const containerErrore = document.getElementById("containerErroreCitta");
            containerErrore.classList.remove('show');
            containerErrore.classList.add("d-none");
        })
    }

    const formAggiungiProfessione = document.getElementById("aggiungiProfessioneForm");
    const formModificaProfessione = document.getElementById("modificaProfessioneForm");
    const formEliminaProfessione = document.getElementById("eliminaProfessioneForm");
    const btnCloseAlertProfessione = document.getElementById("btnCloseAlertProfessione");

    if(formAggiungiProfessione!=null){
        formAggiungiProfessione.addEventListener("submit", (e) =>{
            e.preventDefault();

            let temp = new FormData(formAggiungiProfessione);
            let formData = new URLSearchParams(temp);

            addEditFetch("PROFESSIONS", "AggiungiProfessione", formData);
        })
    }

    if(formModificaProfessione!=null){
        formModificaProfessione.addEventListener("submit", (e) =>{
            e.preventDefault();

            let temp = new FormData(formModificaProfessione);
            let formData = new URLSearchParams(temp);

            addEditFetch("PROFESSIONS", "ModificaProfessione", formData);
        })
    }

    if(formEliminaProfessione!=null){
        formEliminaProfessione.addEventListener("submit", (e) =>{
            e.preventDefault();

            const erroreElement = document.getElementById("erroreProfessione");
            const containerErrore = document.getElementById("containerErroreProfessione")

            let temp = new FormData(formEliminaProfessione);
            let formData = new URLSearchParams(temp);

            containerErrore.classList.add("d-none");
            erroreElement.innerHTML = "";

            fetch("EliminaProfessione",{
                method: "POST",
                body: formData
            }).then(response => response.json()).then(data => {
                console.log("Risposta dal server:", data);
                if(data.successo === false){
                    erroreElement.innerHTML = data.messaggio;
                    containerErrore.classList.remove("d-none");
                    containerErrore.classList.add("show")
                    bootstrap.Modal.getInstance(document.getElementById('modalEliminaProfessione')).hide();
                }else{
                    containerErrore.classList.remove('show');
                    containerErrore.classList.add("d-none");
                    erroreElement.innerHTML = "";

                    window.location.href = getContextPath()+"/HomeAdmin#professions";
                    window.location.reload();
                }
            }).catch(e => {
                console.log("Errore nell'esecuzione della fetch: ", e);
                erroreElement.innerHTML = "Si è verificato un errore di connessione.";
                containerErrore.classList.remove("d-none");
                containerErrore.classList.add("show")
            });
        })
    }

    if(btnCloseAlertProfessione!=null){
        btnCloseAlertProfessione.addEventListener('click', () => {
            const containerErrore = document.getElementById("containerErroreProfessione");
            containerErrore.classList.remove('show');
            containerErrore.classList.add("d-none");
        })
    }

    const formAggiungiVeicolo = document.getElementById("aggiungiVeicoloForm");
    const formModificaVeicolo = document.getElementById("modificaVeicoloForm");
    const formEliminaVeicolo = document.getElementById("eliminaVeicoloForm");
    const btnCloseAlertVeicolo = document.getElementById("btnCloseAlertVeicolo");

    if(formAggiungiVeicolo!=null){
        formAggiungiVeicolo.addEventListener("submit", (e) =>{
            e.preventDefault();

            let temp = new FormData(formAggiungiVeicolo);
            let formData = new URLSearchParams(temp);

            addEditFetch("VEHICLES", "AggiungiVeicolo", formData);
        })
    }

    if(formModificaVeicolo!=null){
        formModificaVeicolo.addEventListener("submit", (e) =>{
            e.preventDefault();

            let temp = new FormData(formModificaVeicolo);
            let formData = new URLSearchParams(temp);

            addEditFetch("VEHICLES", "ModificaVeicolo", formData);
        })
    }

    if(formEliminaVeicolo!=null){
        formEliminaVeicolo.addEventListener("submit", (e) =>{
            e.preventDefault();

            const erroreElement = document.getElementById("erroreVeicolo");
            const containerErrore = document.getElementById("containerErroreVeicolo")

            let temp = new FormData(formEliminaVeicolo);
            let formData = new URLSearchParams(temp);

            containerErrore.classList.add("d-none");
            erroreElement.innerHTML = "";

            fetch("EliminaVeicolo",{
                method: "POST",
                body: formData
            }).then(response => response.json()).then(data => {
                console.log("Risposta dal server:", data);
                if(data.successo === false){
                    erroreElement.innerHTML = data.messaggio;
                    containerErrore.classList.remove("d-none");
                    containerErrore.classList.add("show")
                    bootstrap.Modal.getInstance(document.getElementById('modalEliminaVeicolo')).hide();
                }else{
                    containerErrore.classList.remove('show');
                    containerErrore.classList.add("d-none");
                    erroreElement.innerHTML = "";

                    window.location.href = getContextPath()+"/HomeAdmin#vehicles";
                    window.location.reload();
                }
            }).catch(e => {
                console.log("Errore nell'esecuzione della fetch: ", e);
                erroreElement.innerHTML = "Si è verificato un errore di connessione.";
                containerErrore.classList.remove("d-none");
                containerErrore.classList.add("show")
            });
        })
    }

    if(btnCloseAlertVeicolo!=null){
        btnCloseAlertVeicolo.addEventListener('click', () => {
            const containerErrore = document.getElementById("containerErroreVeicolo");
            containerErrore.classList.remove('show');
            containerErrore.classList.add("d-none");
        })
    }

});
