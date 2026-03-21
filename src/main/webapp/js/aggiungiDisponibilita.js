function gestisciClick(dateStr, hour, isAvailable, id) {
    if (isAvailable) {
        document.getElementById('inputOraRimuovi').value = hour;
        document.getElementById('inputDataRimuovi').value = dateStr;
        document.getElementById('inputIdRimuovi').value = id;
        new bootstrap.Modal(document.getElementById('modalRimuovi')).show();
    } else if (dateStr) {
        document.getElementById('inputGiorno').value = dateStr;
        document.getElementById('dataDisponibilita').value = dateStr;
        document.getElementById('ora_inizio').value = hour.toString().padStart(2, '0') + ":00";
        document.getElementById('checkRicorsivo').checked = false;
        document.getElementById('inputTipoAggiungi').value = "SINGOLO";
        new bootstrap.Modal(document.getElementById('modalAggiungi')).show();
    }
}

document.getElementById('checkRicorsivo').addEventListener('change', function() {
    document.getElementById('inputTipoAggiungi').value = this.checked ? "RICORSIVO" : "SINGOLO";
});

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('checkRicorsivo').addEventListener('change', function() {
        if(this.checked) {
            document.getElementById("inputGiorno").value = document.getElementById("dataDisponibilita").value;
        }else document.getElementById('inputGiorno').value = "";
    })
})