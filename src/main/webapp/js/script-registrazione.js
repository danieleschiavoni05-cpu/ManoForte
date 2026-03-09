function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById("formRegistrazione");
    const containerErrori = document.getElementById("containerErrori");
    const listaErrori = document.getElementById("listaErrori");
    const mainRow = document.getElementById("mainRow");

    form.addEventListener("submit", (e) => {
        e.preventDefault();

        containerErrori.classList.add("d-none");
        listaErrori.innerHTML = "";

        let temp = new FormData(form);
        let formData = new URLSearchParams(temp);

        fetch("registrazioneprofessionista",{
            method: "POST",
            body: formData,
        }).then(response => response.json()).then(data => {
            console.log("Risposta dal server:", data);
            if(data.successo === false){
                data.listaErrori.forEach(errore => {
                    const li = document.createElement("li");
                    li.textContent = errore;
                    listaErrori.appendChild(li);
                })

                containerErrori.classList.remove("d-none")
                mainRow.classList.remove("mb-4");
            }else{
                window.location.href = getContextPath()+"/login";
            }
        }).catch(e => {
            console.error("Errore durante l'esecuzione della fetch: ", e);
            alert("Si è verificato un errore di connessione.");
        });
    });
});