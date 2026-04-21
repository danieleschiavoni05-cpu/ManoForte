function setDeleteId(id) {
	console.log("Tentativo di inserimento ID: ", id);

	document.getElementById("id_eliminare_recensione").value=id;

}

function prepareReviewModal(id) {
	document.getElementById("modalRichiestaId").value=id;
}
