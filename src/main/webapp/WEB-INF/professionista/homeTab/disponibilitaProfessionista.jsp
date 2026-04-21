<%@ page import="org.elis.manoforte.model.Disponibilita" %>
<%@ page import="org.elis.manoforte.model.Richiesta" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.Duration"%>
<%@ page import="java.util.Map" %>

<%
    String dateParam = request.getParameter("date");
    LocalDate referenceDate = (dateParam != null && !dateParam.isEmpty()) ? LocalDate.parse(dateParam) : LocalDate.now();
    LocalDate startOfWeek = referenceDate.with(DayOfWeek.MONDAY);

    String prevWeekDate = startOfWeek.minusWeeks(1).toString();
    String nextWeekDate = startOfWeek.plusWeeks(1).toString();

    DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE dd");
    DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

    Map<LocalDate, List<Disponibilita>> disponibilitaSingole = (Map<LocalDate, List<Disponibilita>>) request.getAttribute("disponibilitaSingole");
    Map<DayOfWeek, List<Disponibilita>> disponibilitaRicorrenti = (Map<DayOfWeek, List<Disponibilita>>) request.getAttribute("disponibilitaRicorrenti");
    Map<LocalDate, List<Disponibilita>> disponibilitaEccezioni = (Map<LocalDate, List<Disponibilita>>) request.getAttribute("disponibilitaEccezioni");
    Map<LocalDate, List<Richiesta>> richiesteRicevute = (Map<LocalDate, List<Richiesta>>) request.getAttribute("richiesteRicevute");

%>

<div class="row section-title mb-4 align-items-center">
    <div class="col-md-12 mb-2">
        <h2 style="color: var(--white-text);">Le mie disponibilit&agrave;</h2>
    </div>
    <div class="col-12 d-flex justify-content-between align-items-center gap-3 mt-4">
        <div class="btn-group d-flex justify-content-center align-items-center" role="group">
            <a href="?date=<%= prevWeekDate %>#availability" class="btn btn-outline-light btn-sm me-2 ">&lt;</a>
            <span class="text-white fw-bold">
                <%= startOfWeek.format(fullDateFormatter) %> - <%= startOfWeek.plusDays(6).format(fullDateFormatter) %>
            </span>
            <a href="?date=<%= nextWeekDate %>#availability" class="btn btn-outline-light btn-sm ms-2">&gt;</a>
        </div>
        <div class="btn-group">
            <button class="btn ms-3 btn-aggiungi-disponibilita" data-bs-toggle="modal" data-bs-target="#modalAggiungi">
                + Aggiungi
            </button>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-12">
        <div class="agenda-container">
            <div style="display: flex; margin-left: 60px;">
                <%for(int i=0;i<7;i++){%>
                    <%LocalDate giornoSettimana = startOfWeek.plusDays(i);%>
                    <div style="flex:1;" class="day-header">
                        <%=giornoSettimana.format(dayFormatter)%>
                    </div>
                <%}%>
            </div>
        </div>
        <div class="timeline-wrapper">
            <div class="time-labels-col">
                <%for(int ora=8; ora<=20; ora++){%>
                    <%int top = (ora-8)*60;%>
                    <div class="time-label" style="top:<%=top%>px;">
                        <%=String.format("%02d:00",ora)%>
                    </div>
                <%}%>
            </div>
            <%for(int i=0;i<7;i++){%>
                <%LocalDate giorno = startOfWeek.plusDays(i);%>
                <%String actionAdd = "gestisciClick('"+giorno+"', '08:00', false, '')";%>
                <div class="day-column" onclick="<%=actionAdd%>">
                    <%if(disponibilitaRicorrenti!=null && disponibilitaRicorrenti.get(giorno.getDayOfWeek())!=null){%>
                        <%for(Disponibilita d: disponibilitaRicorrenti.get(giorno.getDayOfWeek())){%>
                            <%String actionClick = "event.stopPropagation(); gestisciClick('"+giorno+"', '"+d.getOra_inizio()+"', true, '"+d.getId()+"' );";%>
                            <div class="disponibile-block disponibile-ricorrenza"
                                 style="<%=calcolaPosizione(d.getOra_inizio(), d.getOra_fine())%> z-index:10;"
                                 onclick="<%=actionClick%>"
                                 title="Ricorrenza: <%=d.getOra_inizio()%> - <%=d.getOra_fine()%>">Ricorrenza</div>
                        <%}%>
                    <%}%>

                    <%if(disponibilitaSingole!=null && disponibilitaSingole.get(giorno)!=null){%>
                        <%for(Disponibilita d: disponibilitaSingole.get(giorno)){%>
                            <%String actionClick = "event.stopPropagation(); gestisciClick('"+giorno+"', '"+d.getOra_inizio()+"', true, '"+d.getId()+"' );";%>
                            <div class="disponibile-block disponibile-singolo"
                                 style="<%=calcolaPosizione(d.getOra_inizio(), d.getOra_fine())%> z-index:20;"
                                 onclick="<%=actionClick%>"
                                 title="Singolo: <%=d.getOra_inizio()%> - <%=d.getOra_fine()%>">Singolo</div>
                        <%}%>
                    <%}%>

                    <%if(disponibilitaEccezioni!=null && disponibilitaEccezioni.get(giorno)!=null){%>
                        <%for(Disponibilita d: disponibilitaEccezioni.get(giorno)){%>
                            <%String actionClick = "event.stopPropagation(); gestisciClick('"+giorno+"', '"+d.getOra_inizio()+"', true, '"+d.getId()+"' );";%>
                            <div class="disponibile-block disponibile-exception"
                                 style="<%=calcolaPosizione(d.getOra_inizio(), d.getOra_fine())%> z-index:30;"
                                 onclick="<%=actionClick%>"
                                 title="Chiuso: <%=d.getOra_inizio()%> - <%=d.getOra_fine()%>">Occupato</div>
                        <%}%>
                    <%}%>

                    <%if(richiesteRicevute!=null && richiesteRicevute.get(giorno)!=null){%>
                        <%for(Richiesta r:richiesteRicevute.get(giorno)){%>
                            <div class="disponibile-block disponibile-richiesta"
                                 style="<%=calcolaPosizione(r.getOra_inizio(), r.getOra_fine())%> z-index: 40;"
                                 title="Richiesta: <%=r.getOra_inizio()%> - <%=r.getOra_fine()%>"
                                 onclick="event.stopPropagation();">Task confermata</div>
                        <%}%>
                    <%}%>
                </div>
            <%}%>
        </div>
    </div>
</div>

<%-- Modale per aggiungere una disponibilità--%>
<div class="modal fade" id="modalAggiungi" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content custom-modal-content">
            <div class="modal-header modal-header-custom">
                <h5 class="modal-title modal-title-custom">Aggiungi disponibilit&agrave;</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal">
                </button>
            </div>
            <div class="modal-body">
                <form action="gestisciDisponibilita" method="post" id="formAggiungi">
                    <input type="hidden" name="giorno" id="inputGiorno">
                    <input type="hidden" name="action" value="add">
                    <div class="mb-3 text-white">
                        <label class="form-label label-orario">Cosa stai inserendo?</label><br>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="tipo_inserimento" id="tipoDisponibilita" value="SINGOLO" checked>
                            <label class="form-check-label" for="tipoDisponibilita">Ore di lavoro (Extra)</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="tipo_inserimento" id="tipoEccezione" value="ECCEZIONE">
                            <label class="form-check-label text-warning" for="tipoEccezione">Pausa / Non disponibile</label>
                        </div>
                    </div>
                    <p>Aggiungi disponibilit&agrave; per: <br>
                        <input type="date" name="data" id="dataDisponibilita" class="form-control mt-3">
                    </p>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label label-orario" for="ora_inizio">Ora inizio</label>
                            <select name="ora_inizio" id="ora_inizio" class="form-select selettore-orario-custom">
                                <%for(int h = 8; h <= 19; h++){%>
                                    <%for(int m = 0; m <= 30; m += 30){%>
                                        <%String timeStr = String.format("%02d:%02d", h, m);%>
                                        <option value="<%=timeStr%>"><%=timeStr%></option>
                                    <%}%>
                                <%}%>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label label-orario" for="ora_fine">Ora fine</label>
                            <select name="ora_fine" id="ora_fine" class="form-select selettore-orario-custom">
                                <%for(int h = 8; h <= 20; h++){%>
                                    <%for(int m = 0; m <= 30; m += 30){%>
                                        <%if(h == 8 && m == 0) continue;%>
                                        <%if(h == 20 && m == 30) continue;%>
                                        <%String timeStr = String.format("%02d:%02d", h, m);%>
                                        <option value="<%=timeStr%>"><%=timeStr%></option>
                                    <%}%>
                                <%}%>
                            </select>
                        </div>
                    </div>
                    <div class="form-check mb-3">
                        <input class="form-check-input" type="checkbox" id="checkRicorsivo" name="checkRicorsivo">
                        <label class="form-check-label text-white" for="checkRicorsivo">
                            Ogni settimana</label>
                    </div>
                    <div class="text-end mt-4">
                        <button type="button" class="btn btn-secondary me-2" data-bs-dismiss="modal" style="background-color: var(--steel-variant); border: none;">Annulla</button>
                        <button type="submit" class="btn" style="background-color: var(--craft-gold); color: var(--obsidian-base); font-weight: bold;">Salva</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%-- Modale per rimuovere una disponibilità--%>
<div class="modal fade" id="modalRimuovi" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content custom-modal-content">
            <div class="modal-header modal-header-custom">
                <h5 class="modal-title text-danger">
                    Rimuovi Disponibilit&agrave;
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p>Confermi di voler rimuovere questa disponibilit&agrave;?</p>
                <form action="gestisciDisponibilita" method="post">
                    <input type="hidden" name="id_disponibilita" id="inputIdRimuovi">
                    <input type="hidden" name="data_rimozione" id="dataRimozione">
                    <input type="hidden" name="action" value="remove">
                    <div class="d-flex justify-content-between aling-content-center mt-4">
                        <div>
                            <button type="button" class="btn btn-secondary me-2" data-bs-dismiss="modal" style="background-color: var(--steel-variant); border: none;">Annulla</button>
                            <button type="submit" class="btn btn-danger">Elimina</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%!
    private String calcolaPosizione(LocalTime inizio, LocalTime fine){
        LocalTime timelineInizio = LocalTime.of(8,0);
        LocalTime timelineFine = LocalTime.of(20,0);

        if(inizio.isBefore(timelineInizio)) inizio=timelineInizio;
        if(fine.isAfter(timelineFine)) fine=timelineFine;

        long marginTop = Duration.between(timelineInizio, inizio).toMinutes();
        long altezza = Duration.between(inizio, fine).toMinutes();

        return "top: "+marginTop+"px; height: "+altezza+"px;";
    }

%>
