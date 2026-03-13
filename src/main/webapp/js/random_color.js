function setPairedContrastingColorsOnLoad() {
    // --- PRIMA COPPIA ---
    // Peschiamo la prima tonalità casuale e calcoliamo il suo esatto opposto (+180 gradi)
    const baseHue1 = Math.floor(Math.random() * 360);
    const hue1 = baseHue1;
    const hue2 = (baseHue1 + 180) % 360;

    // --- SECONDA COPPIA ---
    // Aggiungiamo un "salto" casuale (tra 30 e 150 gradi) alla prima tonalità.
    // Questo ci assicura che la seconda coppia sia ben diversa e staccata dalla prima.
    const randomOffset = Math.floor(Math.random() * 120) + 30;
    const baseHue2 = (baseHue1 + randomOffset) % 360;

    // Ora calcoliamo l'opposto della nostra seconda tonalità
    const hue3 = baseHue2;
    const hue4 = (baseHue2 + 180) % 360;

    const randomOffset2 = Math.floor(Math.random() * 60) + 10;
    const baseHue3 = (baseHue2 + randomOffset2) % 360;

    const hue5 = baseHue3;
    const hue6 = (baseHue3 + 180) % 360;

    // Applichiamo le due coppie di colori al CSS
    document.documentElement.style.setProperty('--color1', `hsl(${hue1}, 80%, 50%)`);
    document.documentElement.style.setProperty('--color2', `hsl(${hue2}, 80%, 50%)`);
    document.documentElement.style.setProperty('--color3', `hsl(${hue3}, 80%, 50%)`);
    document.documentElement.style.setProperty('--color4', `hsl(${hue4}, 80%, 50%)`);
    document.documentElement.style.setProperty('--color5', `hsl(${hue5}, 80%, 50%)`);
    document.documentElement.style.setProperty('--color6', `hsl(${hue6}, 80%, 50%)`);
}

// Esegui la funzione al caricamento della pagina
window.addEventListener('DOMContentLoaded', setPairedContrastingColorsOnLoad);