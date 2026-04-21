<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Manoforte - Chi Siamo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    
    <style type="text/css">
        /* ANIMAZIONE GRADIENTE TOTALE */
        @keyframes rainbowFlow {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        body {
            margin: 0;
            padding: 0;
            width: 100%;
            min-height: 100vh;
            /* Inseriti tutti i colori principali in tonalità dark/vibrant */
            background: linear-gradient(-45deg, 
                #4b0000, /* Rosso Scuro */
                #4b2c00, /* Arancio Scuro */
                #4b4b00, /* Oro/Verde Scuro */
                #004b2c, /* Smeraldo Scuro */
                #002c4b, /* Blu Notte */
                #2c004b, /* Viola Profondo */
                #4b004b  /* Magenta Scuro */
            );
            background-size: 600% 600%; /* Aumentato per rendere il passaggio tra tutti i colori fluido */
            animation: rainbowFlow 20s ease infinite;
            color: #f8f9fa;
            font-family: 'Segoe UI', sans-serif;
            background-attachment: fixed;
        }

        /* Overlay per mantenere il contrasto alto */
        .page-overlay {
            min-height: 100vh;
            width: 100%;
            background: rgba(0, 0, 0, 0.5); /* Scurisce leggermente il gradiente */
            backdrop-filter: blur(3px);
        }

        .hero-section {
            padding: 80px 0;
            text-transform: uppercase;
            border-bottom: 4px solid #dc3545;
            background: rgba(0, 0, 0, 0.7);
        }

        .hero-section h1 {
            font-weight: 900;
            text-shadow: 0px 0px 20px rgba(255, 255, 255, 0.2);
        }

        .team-card {
            background-color: rgba(10, 10, 10, 0.95); 
            border: 1px solid rgba(255, 255, 255, 0.15);
            height: 100%;
            transition: all 0.3s ease;
        }

        .team-card:hover {
            transform: translateY(-12px);
            border-color: #dc3545;
            box-shadow: 0 0 40px rgba(220, 53, 69, 0.6);
            background-color: rgba(20, 20, 20, 1);
        }

        .img-box { 
            width: 100%; 
            height: 350px;       
            background: #000; 
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
            border-bottom: 3px solid #dc3545; 
        }

        .profile-img { width: 100%; height: 100%; object-fit: cover; }

        .rank-badge { font-size: 0.8rem; padding: 5px 15px; font-weight: 900; display: inline-block; margin-bottom: 15px; letter-spacing: 1px; }
        .rank-leader { background-color: #dc3545; color: white; box-shadow: 0 0 10px rgba(220, 53, 69, 0.5); } 
        .rank-co-leader { border: 2px solid #dc3545; color: #dc3545; } 
        .rank-member { border: 1px solid #555; color: #ccc; } 

        .motto {
            font-family: 'Courier New', monospace; 
            color: #ff4d4d; 
            font-weight: bold;
            background: rgba(0, 0, 0, 0.8);
            padding: 12px;
            display: block;
            margin-top: 20px;
            border-left: 4px solid #dc3545;
            font-size: 0.9rem;
        }

        .btn-home {
            border: 2px solid #dc3545;
            color: white;
            padding: 15px 45px;
            text-transform: uppercase;
            font-weight: bold;
            text-decoration: none;
            display: inline-block;
            background: rgba(0,0,0,0.8);
            transition: 0.4s;
            margin-top: 20px;
        }

        .btn-home:hover { 
            background: #dc3545; 
            color: white; 
            box-shadow: 0 0 30px rgba(220, 53, 69, 0.8); 
            transform: scale(1.05);
        }
    </style>
</head>
<body>

<div>

    <header class="hero-section text-center">
        <div class="container">
            <h1 class="display-3 fw-bold text-uppercase">Team <span style="color: #dc3545;">Manoforte</span></h1>
            <p class="lead fw-bold" style="letter-spacing: 5px;">ARCHIVIO OPERATIVO</p>
        </div>
    </header>

    <section class="container py-5">
        <div class="row g-4 justify-content-center">
            
            <div class="col-lg-3 col-md-6">
                <div class="card team-card">
                    <div class="img-box">
                        <img src="<%=request.getContextPath()%>/img/tecnico.jpeg" class="profile-img" alt="Capo" onerror="this.src='https://placehold.co/400x600/111/fff?text=IL+CAPO'">
                    </div>
                    <div class="card-body text-center">
                        <span class="rank-badge rank-leader">CAPO DEL GRUPPO</span>
                        <h5 class="text-white fw-bold">Marco Pasquale "Jihadista" Martino</h5>
                        <p class="small text-white">Fondatore e mente suprema del Team Manoforte.</p>
                        <span class="motto">"Andiamo. È il momento."</span>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="card team-card">
                    <div class="img-box">
                        <img src="<%=request.getContextPath()%>/img/ghost.png" class="profile-img" alt="Co-Capo" onerror="this.src='https://placehold.co/400x600/111/fff?text=CO-CAPO'">
                    </div>
                    <div class="card-body text-center">
                        <span class="rank-badge rank-co-leader">CO-CAPO</span>
                        <h5 class="text-white fw-bold">Daniele "Ghost" Schiavoni</h5>
                        <p class="small text-white">Responsabile tattico e infiltratore d'elite.</p>
                        <span class="motto">"Stiamo perdendo."</span>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="card team-card">
                    <div class="img-box">
                        <img src="<%=request.getContextPath()%>/img/luffy.jpeg" class="profile-img" alt="Luffy" onerror="this.src='https://placehold.co/400x600/111/fff?text=SOTTOPOSTO'">
                    </div>
                    <div class="card-body text-center">
                        <span class="rank-badge rank-member">SOTTOPOSTO</span>
                        <h5 class="text-white fw-bold">Juri D. Boragine</h5>
                        <p class="small text-white">Unità d'assalto. Forza combattiva fuori scala.</p>
                        <span class="motto">"Diventerò il Re!"</span>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="card team-card">
                    <div class="img-box">
                        <img src="<%=request.getContextPath()%>/img/fumo.jpeg" class="profile-img" alt="Fumo" onerror="this.src='https://placehold.co/400x600/111/fff?text=FUMO'">
                    </div>
                    <div class="card-body text-center">
                        <span class="rank-badge rank-member">SOTTOPOSTO</span>
                        <h5 class="text-white fw-bold">Gabriele "Fumo" Sebastianelli</h5>
                        <p class="small text-white">Supporto occulto e gestione perimetrale.</p>
                        <span class="motto">"Fumiamo?"</span>
                    </div>
                </div>
            </div>

        </div>
    </section>

    <div class="container text-center my-5 pb-5">
        <a href="<%=request.getContextPath()%>/Homepage" class="btn-home">
            <i class="bi bi-house-door-fill me-2"></i> Torna alla Home
        </a>
    </div>

</div>

</body>
</html>