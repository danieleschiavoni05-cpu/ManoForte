document.addEventListener('DOMContentLoaded', () => {
    const modalElement = document.getElementById('dettagliProfessionista');
    if (!modalElement) return;

    const modalBootstrap = bootstrap.Modal.getOrCreateInstance(modalElement);
    const loader = document.getElementById('modalLoader');
    const content = document.getElementById('modalContent');

    const setLoadingState = (isLoading) => {
        if (isLoading) {
            loader.classList.remove('d-none');
            content.classList.add('d-none');
        } else {
            loader.classList.add('d-none');
            content.classList.remove('d-none');
        }
    };


    const createBadgeElement = (text) => {
        const badge = document.createElement('div');
        badge.className = 'readonly-badge';
        badge.textContent = text;
        return badge;
    };


    const doFetch = async (id_professionista, media) => {
        setLoadingState(true);
        modalBootstrap.show();

        console.log(id_professionista);
        try {
            const response = await fetch(`dettagliProfessionista?id=${id_professionista}&media=${media}`);

            if (!response.ok) {
                throw new Error(`Errore del server: ${response.status}`);
            }

            const data = await response.json();


            document.getElementById('profNomeCompleto').innerHTML = data.nomeCompleto;
            document.getElementById('profTariffa').innerHTML = data.tariffa+'$';
            document.getElementById('profAvatarDetails').src = "getImmagine?path=" + data.immagine;
            document.getElementById('profMedia').innerHTML = data.mediaVoto+"/5";


            const containerProfessioni = document.getElementById('listaProfessioni');
            containerProfessioni.innerHTML = '';
            if (data.professioni && data.professioni.length > 0) {
                data.professioni.forEach(p =>
                    containerProfessioni.appendChild(createBadgeElement(p.nome))
                );
            } else {
                containerProfessioni.innerHTML = '<span class="text-muted" style="padding: 0 10px;">Nessuna specializzazione aggiuntiva</span>';
            }

            const containerVeicoli = document.getElementById('listaVeicoli');
            containerVeicoli.innerHTML = '';
            if (data.veicoli && data.veicoli.length > 0) {
                data.veicoli.forEach(v =>
                    containerVeicoli.appendChild(createBadgeElement(v.nome))
                );
            } else {
                containerVeicoli.innerHTML = '<span class="text-muted" style="padding: 0 10px;">Nessun veicolo registrato</span>';
            }

            setLoadingState(false);
        } catch (error) {
            console.error("Errore esecuzione fetch:", error);
            document.getElementById('modalLoader').innerHTML =
                '<div class="text-danger"><i class="fa-solid fa-circle-exclamation mb-2"></i><p>Errore nel caricamento dei dati.</p></div>';
        }
    };


    document.querySelectorAll('.btn-apri-modal').forEach(button => {
        button.addEventListener('click', () => {
            const id = button.getAttribute('data-id');
            const media = button.getAttribute('data-media');
            if (id) doFetch(id, media);
        });
    });
});