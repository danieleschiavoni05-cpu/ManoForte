function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function executeFetch(servlet, data){

    const containerErrori = document.getElementById("containerErrori");
    const listaErrori = document.getElementById("listaErrori");
    const listaSuccessi = document.getElementById("listaSuccessi");
    const containerSuccesso = document.getElementById("containerSuccesso");
    const mainRow = document.getElementById("mainRow");

    containerErrori.classList.add("d-none");
    listaErrori.innerHTML = "";
    containerSuccesso.classList.add("d-none");
    listaSuccessi.innerHTML = "";

    fetch(servlet,{
        method: "POST",
        body: data,
    }).then(response => response.json()).then(data => {
        console.log("Risposta dal server:", data);
        if(data.successo === false){
            data.listaErrori.forEach(errore => {
                containerSuccesso.classList.add("d-none");
                const li = document.createElement("li");
                li.textContent = errore;
                listaErrori.appendChild(li);
            })

            containerErrori.classList.remove("d-none")
            mainRow.classList.remove("mb-4");
        }else{
            console.log(servlet);
            if(servlet==="modificaProfiloProfessionista"){
                const li = document.createElement("li");
                li.textContent = "Profilo modificato con successo.";
                listaSuccessi.appendChild(li);
                containerSuccesso.classList.remove("d-none");
                return getContextPath()+"/modificaProfiloProfessionista";
            }else if(servlet==="ModificaProfilo"){
                const li = document.createElement("li");
                li.textContent = "Profilo modificato con successo.";
                listaSuccessi.appendChild(li);
                containerSuccesso.classList.remove("d-none");
                return getContextPath()+"/homeBase";
            }else{
                console.log(getContextPath());
                let path= getContextPath()+"/login";
                window.location.href = path;
            }
        }
    }).catch(e => {
        console.error("Errore durante l'esecuzione della fetch: ", e);
        alert("Si è verificato un errore di connessione.");
    });
}

document.addEventListener('DOMContentLoaded', () => {

    const formRegistrazioneProfessionista = document.getElementById("formRegistrazioneProfessionista");
    const formModificaProfessionista = document.getElementById("formModificaProfiloProfessionista");
    const formRegistrazioneUtenteBase=document.getElementById("formRegistrazioneUtenteBase");
    const formModificaProfiloUtenteBase = document.getElementById("formModificaProfiloUtenteBase");


    let localDateNow = new Date();

    //Codice per impostare la data massima a un età maggiorenne
    //(toISOString() trasforma la data nel formato DD-MM-YYYYTHH:MM:SS; toString() mi restituirebbe Sat Mar 14 2026 HH:MM:SS GM)
    localDateNow.setFullYear(localDateNow.getFullYear() - 18);
    document.getElementById("data").setAttribute("max", localDateNow.toISOString().split('T')[0]);

    if(formRegistrazioneProfessionista!=null){
        formRegistrazioneProfessionista.addEventListener("submit", (e) => {
            e.preventDefault();

            let temp = new FormData(formRegistrazioneProfessionista);
            let formData = new URLSearchParams(temp);

            let path = executeFetch("registrazioneprofessionista", formData);
        });
    }else if(formModificaProfessionista!=null){
        formModificaProfessionista.addEventListener("submit", (e) => {
            e.preventDefault();

            let temp = new FormData(formModificaProfessionista);
            let formData = new URLSearchParams(temp);

            let path = executeFetch("modificaProfiloProfessionista", formData);
        })
    }else if(formRegistrazioneUtenteBase!=null){
        formRegistrazioneUtenteBase.addEventListener("submit", (e) => {
            e.preventDefault();

            let temp = new FormData(formRegistrazioneUtenteBase);
            let formData = new URLSearchParams(temp);

            let path = executeFetch("registrazionecliente", formData);
            console.log(path);
        })
    }else if(formModificaProfiloUtenteBase!=null){
        formModificaProfiloUtenteBase.addEventListener("submit", (e) => {
            e.preventDefault();

            let temp = new FormData(formModificaProfiloUtenteBase);
            let formData = new URLSearchParams(temp);

            let path = executeFetch("ModificaProfilo", formData);
            console.log(path);
        })
    }
});
