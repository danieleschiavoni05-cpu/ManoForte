document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.getElementById('propicInput');
    const currentPropic = document.getElementById('currentPropic');
    const homePropic = document.getElementById('homePropic');
    const deleteButton = document.getElementById("deleteButton");
    const originalSrc = currentPropic.src;

    const formUpload = document.getElementById("formModificaPropicProfessionista");
    const formDelete = document.getElementById("formDeletePropic");

    fileInput.addEventListener('change', function() {
        console.log(fileInput);
        if (this.files && this.files.length > 0) {
            console.log("ciao");
            const file = this.files[0];
            if (file && file.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    currentPropic.style.opacity = '0';
                    setTimeout(() => {
                        currentPropic.src = e.target.result;
                        currentPropic.style.opacity = '1';
                    }, 150);
                }
                reader.readAsDataURL(file);
            } else {
                resetPreview();
            }
        } else {
            resetPreview();
        }
    });

    function resetPreview() {
        currentPropic.style.opacity = '0';
        setTimeout(() => {
            currentPropic.src = originalSrc;
            currentPropic.style.opacity = '1';
        }, 150);
    }

    formUpload.addEventListener('submit', function (e){
        e.preventDefault();

        const containerErroriPropic = document.getElementById("containerErroriPropic");
        const listaErroriPropic = document.getElementById("listaErroriPropic");
        const listaSuccessiPropic = document.getElementById("listaSuccessiPropic");
        const containerSuccessoPropic = document.getElementById("containerSuccessoPropic");

        containerErroriPropic.classList.add("d-none");
        listaErroriPropic.innerHTML = "";
        containerSuccessoPropic.classList.add("d-none");
        listaSuccessiPropic.innerHTML = "";

        let data = new FormData(formUpload);

        fetch('updateImage', {
            method: 'POST',
            body: data,
        }).then(response => response.json()).then(dto => {
            console.log(dto);
            const li = document.createElement("li");
            li.textContent = dto.messaggio;
            console.log(li);
            if(dto.successo === true){
                listaSuccessiPropic.appendChild(li);
                containerSuccessoPropic.classList.remove("d-none");
                fileInput.value="";

                homePropic.style.opacity = '0';
                setTimeout(()=>{
                    homePropic.src = currentPropic.src;
                    homePropic.style.opacity = '1';
                });
                deleteButton.classList.remove("d-none");
            }else{
                listaErroriPropic.appendChild(li);
                containerErroriPropic.classList.remove("d-none");
                resetPreview();
            }

        });
    });

    formDelete.addEventListener('submit', function (e){
        e.preventDefault();

        const containerErroriPropic = document.getElementById("containerErroriPropic");
        const listaErroriPropic = document.getElementById("listaErroriPropic");
        const listaSuccessiPropic = document.getElementById("listaSuccessiPropic");
        const containerSuccessoPropic = document.getElementById("containerSuccessoPropic");

        containerErroriPropic.classList.add("d-none");
        listaErroriPropic.innerHTML = "";
        containerSuccessoPropic.classList.add("d-none");
        listaSuccessiPropic.innerHTML = "";

        fetch('deleteImage', {
            method: 'POST'
        }).then(response => response.json()).then(dto => {

            const li = document.createElement("li");
            li.textContent = dto.messaggio;
            console.log(li);
            if(dto.successo === true){
                listaSuccessiPropic.appendChild(li);
                containerSuccessoPropic.classList.remove("d-none");
                fileInput.value="";

                homePropic.style.opacity = '0';
                currentPropic.src = '0';
                setTimeout(()=>{
                    homePropic.src = defaultImg;
                    homePropic.style.opacity = '1';
                    currentPropic.src = defaultImg;
                    currentPropic.style.opacity = '1';
                });
                deleteButton.classList.add("d-none");
            }else{
                listaErroriPropic.appendChild(li);
                containerErroriPropic.classList.remove("d-none");
                resetPreview();
            }

        });
    })
});
