const orariCalendario = [
    "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
    "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
    "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
    "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"
];

function gestisciClick(dateStr, hour, isAvailable, id) {
    if (isAvailable) {
        document.getElementById('inputOraRimuovi').value = hour;
        document.getElementById('inputDataRimuovi').value = dateStr;
        document.getElementById('inputIdRimuovi').value = id;
        let modaleElement = document.getElementById('modalRimuovi');
        let modale = bootstrap.Modal.getInstance(modaleElement);
        if (!modale) {
            modale = new bootstrap.Modal(modaleElement);
        }
        modale.show();
    } else if (dateStr) {
        document.getElementById('inputGiorno').value = dateStr;
        document.getElementById('dataDisponibilita').value = dateStr;

        let oraInizioPulita = hour.substring(0, 5).padStart(5, '0');
        document.getElementById('ora_inizio').value = oraInizioPulita;

        let orarioDiviso = oraInizioPulita.split(':');
        let hFine = parseInt(orarioDiviso[0]) + 1;
        let mFine = orarioDiviso[1];

        if (hFine >= 20) {
            hFine = 20;
            mFine = '00';
        }

        let oraFinePulita = hFine.toString().padStart(2, '0') + ":" + mFine;
        document.getElementById('ora_fine').value = oraFinePulita;

        document.getElementById("tipoDisponibilita").disabled = false;
        document.getElementById("tipoEccezione").disabled = false;

        document.getElementById('checkRicorsivo').checked = false;
        document.getElementById('inputTipoAggiungi').value = "SINGOLO";
        let modalElement = document.getElementById('modalAggiungi');
        let modale = bootstrap.Modal.getInstance(modalElement);
        if (!modale) {
            modale = new bootstrap.Modal(modalElement);
        }
        modale.show();
    }
}

document.getElementById('checkRicorsivo').addEventListener('change', function() {
    document.getElementById('inputTipoAggiungi').value = this.checked ? "RICORSIVO" : "SINGOLO";
});

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('checkRicorsivo').addEventListener('change', function() {
        if(this.checked) {
            document.getElementById("inputGiorno").value = document.getElementById("dataDisponibilita").value;
            document.getElementById("tipoDisponibilita").disabled = true;
            document.getElementById("tipoEccezione").disabled = true;
        }else{
            document.getElementById('inputGiorno').value = "";
            document.getElementById("tipoDisponibilita").disabled = false;
            document.getElementById("tipoEccezione").disabled = false;
        }
    })

    document.getElementById('dataDisponibilita').addEventListener('change', function() {
        if(document.getElementById('checkRicorsivo').checked) {
            document.getElementById("inputGiorno").value = this.value;
        }
    })

    document.getElementById('ora_inizio').addEventListener('change', function() {
        let scelta = this.value;
        let opzioniFine = document.getElementById('ora_fine').options;
        for(let i=0; i<opzioniFine.length; i++){
            if(opzioniFine[i].value<=scelta) opzioniFine[i].disabled = true;
            else if(opzioniFine[i-1].value===scelta) document.getElementById("ora_fine").value = opzioniFine[i].value;
        }

    })
})

