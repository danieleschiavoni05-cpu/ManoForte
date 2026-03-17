<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/color-var.css">
<style>
    .footer {
        background-color: var(--obsidian-base);
        padding: 50px 20px;
        color: var(--light-silver);
        font-family: sans-serif;
        margin-top: 60px;
        border-top: 1px solid var(--steel-variant);
    }
    .footer-content {
        display: flex;
        justify-content: space-between;
        flex-wrap: wrap;
        max-width: 1200px;
        margin: auto;
        gap: 40px;
    }
    .footer-col {
        flex: 1;
        min-width: 200px;
    }
    .footer-col h3, .footer-col h4 {
        margin-bottom: 15px;
        color: var(--craft-gold);
    }
    .footer-col p {
        opacity: 0.9;
    }
    .footer-col ul {
        list-style: none;
        padding: 0;
        line-height: 1.8;
    }
    .footer-col ul li a {
        color: var(--light-silver);
        text-decoration: none;
        transition: color 0.3s ease;
    }
    .footer-col ul li a:hover {
        color: var(--craft-gold);
    }
    .footer-social a {
        color: var(--light-silver);
        margin-right: 15px;
        text-decoration: none;
        font-size: 22px;
        transition: color 0.3s ease;
    }
    .footer-social a:hover {
        color: var(--craft-gold);
    }
    .footer-bottom {
        text-align: center;
        margin-top: 40px;
        padding-top: 20px;
        border-top: 1px solid var(--steel-variant);
        opacity: 0.9;
    }
</style>

<footer class="footer">

    <div class="footer-content">

        <div class="footer-col" style="min-width: 250px;">
            <h3>ManoForte</h3>
            <p>La piattaforma che ti connette con professionisti affidabili per ogni esigenza domestica.</p>
        </div>

        <div class="footer-col">
            <h4>Link Utili</h4>
            <ul>
                <li><a href="Homepage">Home</a></li>
                <li><a href="ListaProfessionisti">Servizi</a></li>
                <li><a href="ListaProfessionisti">Professionisti</a></li>
                <li><a href="login">Accedi</a></li>
            </ul>
        </div>

        <div class="footer-col">
            <h4>Contatti</h4>
            <p>📍 Via Roma 12, Milano</p>
            <p>📞 +39 320 123 4567</p>
            <p>📧 support@manoforte.it</p>
        </div>

        <div class="footer-col">
            <h4>Seguici</h4>
            <p class="footer-social">
                <a href="#">📘</a>
                <a href="#">📸</a>
                <a href="#">🐦</a>
            </p>
        </div>

    </div>

    <div class="footer-bottom">
        © 2026 ManoForte — Tutti i diritti riservati
    </div>

</footer>
