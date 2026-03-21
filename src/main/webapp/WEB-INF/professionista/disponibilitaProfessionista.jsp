<%@ page import="org.elis.manoforte.model.Disponibilita" %>
<%@ page import="org.elis.manoforte.model.Richiesta" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.elis.manoforte.model.TipoDisponibilita" %>
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
        <h2 style="color: var(--white-text);">Le mie disponibilità</h2>
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
            <table class="agenda-table">
                <thead>
                    <tr>
                        <th style="color: var(--muted-silver); border-bottom: none; width: 60px;"></th>
                        <% for (int i = 0; i < 7; i++) {%>
                            <%LocalDate giornoSettimana = startOfWeek.plusDays(i);%>
                            <th><%= giornoSettimana.format(dayFormatter).toUpperCase() %></th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <%LocalTime oraInizio = LocalTime.of(8,0);%>
                    <%LocalTime oraFine = LocalTime.of(20,0);%>
                    <%for (LocalTime cellaOra =oraInizio; !cellaOra.isAfter(oraFine.minusMinutes(30)); cellaOra = cellaOra.plusMinutes(30)){%>
                        <tr>
                            <td class="agenda-time-col"><%=cellaOra.format(DateTimeFormatter.ofPattern("HH:mm"))%></td>
                            <%for (int i = 0; i < 7; i++) {%>
                                <%LocalDate giornoSettimana = startOfWeek.plusDays(i);%>
                                <%Disponibilita disp = null;%>
                                <%Richiesta rich = null;%>
                                <%String tipo = "Vuoto";%>

                                <%if(richiesteRicevute.get(giornoSettimana)!=null){%>
                                    <%rich = checkOrarioRichiesta(richiesteRicevute.get(giornoSettimana), cellaOra);%>
                                    <%if(rich!=null) tipo="Richiesta";%>
                                <%}%>

                                <%if(!tipo.equals("Richiesta")){%>
                                    <%if(disponibilitaEccezioni.get(giornoSettimana)!=null){%>
                                        <%disp = checkOrario(disponibilitaEccezioni.get(giornoSettimana), cellaOra);%>
                                        <%if(disp!=null) tipo = "Eccezione";%>
                                    <%}%>

                                    <%if(disp==null&&disponibilitaSingole.get(giornoSettimana)!=null){%>
                                        <%disp = checkOrario(disponibilitaSingole.get(giornoSettimana), cellaOra);%>
                                        <%if(disp!=null) tipo = "Disponibile";%>
                                    <%}%>

                                    <%if(disp==null&&disponibilitaRicorrenti.get(DayOfWeek.from(giornoSettimana))!=null){%>
                                        <%disp = checkOrario(disponibilitaRicorrenti.get(DayOfWeek.from(giornoSettimana)), cellaOra);%>
                                        <%if(disp!=null) tipo = "Disponibile";%>
                                    <%}%>
                                <%}%>

                                <%if(tipo.equals("Richiesta")){%>
                                    <%=stampaRichiesta()%>
                                <%}else if(tipo.equals("Eccezione")){%>
                                    <%=stampaEccezione(disp, cellaOra, giornoSettimana)%>
                                <%}else if(tipo.equals("Disponibile")){%>
                                    <%=stampaDisponibile(disp, cellaOra, giornoSettimana)%>
                                <%}else{%>
                                    <%=stampaVuoto(cellaOra, giornoSettimana)%>
                                <%}%>

                            <%}%>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%-- Modale per aggiungere una disponibilità--%>
<div class="modal fade" id="modalAggiungi" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content custom-modal-content">
            <div class="modal-header modal-header-custom">
                <h5 class="modal-title modal-title-custom">Aggiungi Disponibilità</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal">
                </button>
            </div>
            <div class="modal-body">
                <form action="gestisciDisponibilita" method="post" id="formAggiungi">
                    <input type="hidden" name="tipo" id="inputTipoAggiungi" value="SINGOLO">
                    <input type="hidden" name="giorno" id="inputGiorno">
                    <input type="hidden" name="action" value="add">
                    <p>Aggiungi disponibilità per: <br>
                        <input type="date" name="data" id="dataDisponibilita" class="form-control mt-3">
                    </p>
                    <div class="mb-3">
                        <label class="form-label label-orario" for="ora_inizio">Ora inizio</label>
                        <input type="time" step="1800" min="08:00" max="20:00" name="ora_inizio" id="ora_inizio" class="form-control selettore-orario">
                    </div>
                    <div class="mb-3">
                        <label class="form-label label-orario" for="ora_fine">Ora Fine</label>
                        <input type="time" name="ora_fine" id="ora_fine" class="form-control selettore-orario">
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
                    Rimuovi Disponibilità
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p>Confermi di voler rimuovere questa disponibilità?</p>
                <form action="gestisciDisponibilita" method="post">
                    <input type="hidden" name="ora" id="inputOraRimuovi">
                    <input type="hidden" name="data" id="inputDataRimuovi">
                    <input type="hidden" name="id_disponibilita" id="inputIdRimuovi">
                    <input type="hidden" name="action" value="remove">
                    <div class="d-flex justify-content-between aling-content-center mt-4">
                        <div class="d-flex align-items-center">
                            <input class="form-check-input" type="checkbox" id="ricorrenza" name="ricorrenza" style="margin-right: 10px;">
                            <label class="form-check-label text-white" for="ricorrenza">
                                Eliminare ricorrenza?</label>
                        </div>
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

    private Disponibilita checkOrario(List<Disponibilita> disponibilita, LocalTime slotTime) {
        for (Disponibilita d : disponibilita) {
            if (!slotTime.isBefore(d.getOra_inizio()) && slotTime.isBefore(d.getOra_fine())) {
                return d;
            }
        }
        return null;
    }

    private Richiesta checkOrarioRichiesta(List<Richiesta> richieste, LocalTime slotTime) {
        for (Richiesta r : richieste) {
            if (!slotTime.isBefore(r.getOra_inizio()) && slotTime.isBefore(r.getOra_fine())) {
                return r;
            }
        }
        return null;
    }

    private String stampaRichiesta(){
        return  "<td class='slot-requested' title='Richiesta'></td>";
    }

    private String stampaVuoto(LocalTime ora, LocalDate giornoSettimana){
        String action = "\"gestisciClick('"+giornoSettimana+"', '"+ora+"', false, '')\"";
        return "<td class='agenda-slot' onclick="+action+" title=''></td>";
    }

    private String stampaDisponibile(Disponibilita disponibilita, LocalTime ora, LocalDate giornoSettimana){
        String action = "\"gestisciClick('"+giornoSettimana+"', '"+ora+"', true, '"+disponibilita.getId()+"')\"";
        return  "<td class='slot-available' onclick="+action+" title='Disponibile'></td>";
    }

    private String stampaEccezione(Disponibilita disponibilita, LocalTime ora, LocalDate giornoSettimana){
        String action = "\"gestisciClick('"+giornoSettimana+"', '"+ora+"', true, '"+disponibilita.getId()+"')\"";
        return  "<td class='slot-exception' onclick="+action+" title='Eccezione'></td>";
    }


%>
