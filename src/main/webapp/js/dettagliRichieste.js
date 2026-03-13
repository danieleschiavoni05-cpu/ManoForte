

document.addEventListener('DOMContentLoaded', () => {
    const detailsButtons = document.querySelectorAll(".btn-apri-modale");
    const acceptButtons = document.querySelectorAll("#btnAccetta");
    const completeButtons = document.querySelectorAll("#btnCompletato");

    const pendingTasks = document.getElementById("pendingTasks");
    const runningTasks = document.getElementById("runningTasks");
    const completedTasks = document.getElementById("completedTasks");

    const response = document.getElementById("response");

    const modal = document.getElementById('dettaglioModal');
    const modalBootstrap = bootstrap.Modal.getOrCreateInstance(modal);

    detailsButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            const idRichiesta = button.getAttribute("data-id");

            console.log(response);
            response.classList.add('d-none');
            reset(true);

            document.getElementById("btnAccetta").classList.add('d-none');
            document.getElementById("btnCompletato").classList.add('d-none');

            modalBootstrap.show();

            fetch('dettagliRichiesta?id_richiesta='+idRichiesta, {
                method: 'GET',
            }).then(response => {
                if(!response.ok) {
                    throw new Error(`Errore del server: `+response.status);
                }
                return response.json();
            }).then(datiJson=>{
                let richiesta = datiJson.richiesta;
                document.getElementById('committente').innerText = datiJson.committente;
                document.getElementById('citta').innerText = datiJson.citta;
                document.getElementById('dataIntervento').innerText = richiesta.data;
                document.getElementById('oraInizio').innerText = richiesta.ora_inizio.substring(0,5);
                document.getElementById('oraFine').innerText = richiesta.ora_fine.substring(0,5);
                document.getElementById('descrizione').innerText = richiesta.descrizione;

                if(richiesta.statoRichiesta==="IN_ATTESA_DI_CONFERMA"){
                    document.getElementById("btnAccetta").classList.remove('d-none');
                    document.getElementById("btnAccetta").setAttribute('data-id',richiesta.id);
                }else if(richiesta.statoRichiesta==="IN_CORSO"){
                    document.getElementById("btnCompletato").classList.remove('d-none');
                    document.getElementById("btnCompletato").setAttribute('data-id',richiesta.id);
                }

            }).catch(e => {
                reset(false);
                console.error("Errore: "+e);
                //modalBootstrap.hide();
            })
        })
    })

    // Funzione accetta task
    acceptButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            const idRichiesta = button.getAttribute("data-id");

            let card = document.getElementById("richiesta-"+idRichiesta);

            response.classList.add('d-none');

            let obj = new URLSearchParams({"id":idRichiesta,"type": "accept"});

            fetch('dettagliRichiesta',{
                method: 'POST',
                body: obj,
            }).then(response => {
                if(!response.ok)  throw new Error(`Errore del server: `+response.status);
                return response.json();
            }).then(datiJson=>{

                response.innerText = datiJson.messaggio;
                response.classList.remove('d-none');

                if(datiJson.successo){
                    runningTasks.appendChild(card);

                    if(runningTasks.querySelector("#noRunning")!=null)
                        runningTasks.removeChild(runningTasks.querySelector("#noRunning"));
                    if(pendingTasks.children.length===0)
                        pendingTasks.insertAdjacentHTML('beforeend', insertNoChild("pending"));

                    document.getElementById("btnAccetta").classList.add('d-none');
                }
            }).catch(e => {

                response.innerText = datiJson.messaggio;
                response.classList.remove('d-none');

                reset(false);
                console.error(e);
                //modalBootstrap.hide();
            })
        })
    })

    // Funzione completa task
    completeButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            const idRichiesta = button.getAttribute("data-id");

            let card = document.getElementById("richiesta-"+idRichiesta);

            response.classList.add('d-none');

            let obj = new URLSearchParams({"id":idRichiesta,"type": "complete"});

            fetch('dettagliRichiesta',{
                method: 'POST',
                body: obj,
            }).then(response => {
                if(!response.ok)  throw new Error(`Errore del server: `+response.status);
                return response.json();
            }).then(datiJson=>{

                response.innerText = datiJson.messaggio;
                response.classList.remove('d-none');

                if(datiJson.successo){
                    completedTasks.appendChild(card);

                    if(completedTasks.querySelector("#noRunning")!=null)
                        completedTasks.removeChild(completedTasks.querySelector("#noRunning"));
                    if(runningTasks.children.length===0)
                        runningTasks.insertAdjacentHTML('beforeend', insertNoChild("running"));

                    document.getElementById("btnCompletato").classList.add('d-none');
                }
            }).catch(e => {

                response.innerText = datiJson.messaggio;
                response.classList.remove('d-none');

                reset(false);
                console.error(e);
                //modalBootstrap.hide();
            })
        })
    })

    function insertNoChild(child){
        if(child==="pending") {
            return '<p class="text-muted" id="noPending">Nessuna nuova richiesta!</p>';
        }else if(child==="running") {
            return '<p class="text-muted" id="noRunning">Nessuna richiesta in corso!</p>';
        }
    }

    function reset(error) {
        if (!error) {
            document.getElementById('committente').innerHTML = '<span class="spinner-border spinner-border-sm" role="status"></span> Caricamento...';
            document.getElementById('citta').innerText = "Caricamento...";
            document.getElementById('dataIntervento').innerText = "Caricamento...";
            document.getElementById('oraInizio').innerText = "--:--";
            document.getElementById('oraFine').innerText = "--:--";
            document.getElementById('descrizione').innerText = "Recupero dettagli in corso...";
            response.classList.add('d-none');
            console.log(response);
        }else{
            document.getElementById('committente').innerHTML = 'Errore nel caricamento dei dati';
            document.getElementById('citta').innerText = "Errore nel caricamento dei dati";
            document.getElementById('dataIntervento').innerText = "--/--/----";
            document.getElementById('oraInizio').innerText = "--:--";
            document.getElementById('oraFine').innerText = "--:--";
            document.getElementById('descrizione').innerText = "Impossibile caricare i dati.";
        }
    }
})