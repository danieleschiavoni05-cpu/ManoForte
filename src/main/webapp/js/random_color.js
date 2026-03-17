const fun = () => {
    const baseHue1 = Math.floor(Math.random() * 360);
    const offset1 = Math.floor(Math.random() * 120) + 30;
    const baseHue2 = (baseHue1 + offset1) % 360;
    const offset2 = Math.floor(Math.random() * 60) + 10;
    const baseHue3 = (baseHue2 + offset2) % 360;

    const hues = [
        baseHue1, (baseHue1 + 180) % 360,
        baseHue2, (baseHue2 + 180) % 360,
        baseHue3, (baseHue3 + 180) % 360
    ];

    hues.forEach((hue, index) => {
        document.documentElement.style.setProperty(`--color${index + 1}`, `hsl(${hue}, 80%, 50%)`);
    });
};

const setEffectCookie = (val) => {
    document.cookie = `effect=${val}; path=/; max-age=${60*60*24*30}`;
    console.log(document.cookie);
};

const getEffectStatus = () => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; effect=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return "true";
};

const updateUI = (isEnabled) => {
    const noEffectColor = window.getComputedStyle(document.body).getPropertyValue('--steel-variant');

    if (isEnabled === "true") {
        document.body.style.background = "";
        document.body.classList.add("rotation");
        fun();
    } else {
        document.body.style.background = noEffectColor;
        document.body.classList.remove("rotation");
    }
};

document.addEventListener('DOMContentLoaded', () => {
    let currentStatus = getEffectStatus();

    updateUI(currentStatus);

    const disableBtn = document.getElementById("disableEffect");
    if (disableBtn) {
        disableBtn.addEventListener("click", () => {
            console.log(currentStatus);
            currentStatus = (currentStatus === "true") ? "false" : "true";
            setEffectCookie(currentStatus);
            updateUI(currentStatus);
        });
    }
});