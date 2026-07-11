package Controller;

import Entity.EntryDB.AggiornamentoStatoEntry;
import Entity.Gestori.GestoreAggiornamentoStato;
import Entity.Gestori.GestoreSegnalazioni;
import Entity.Segnalazione;
import Entity.Enum.Ruolo;
import Entity.Gestori.GestoreSegnalazioni;
import Entity.Enum.Categoria;
import Entity.StateMachine.StatoInLavorazione;
import Entity.StateMachine.StatoInviata;
import Entity.StateMachine.StatoRisolta;
import Entity.StateMachine.StatoSegnalazione;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


//Façade
public class ControllerSegnalazioni {

    private static Map<Integer,Long> bindingId;
    private static Long idSegnalazioneCorrente;

    public static Long getIdSegnalazioneCorrente(){
        return idSegnalazioneCorrente;
    }

    public static void setIdSegnalazioneCorrente(Long idSegnalazioneCorrente){
        ControllerSegnalazioni.idSegnalazioneCorrente = idSegnalazioneCorrente;
    }



    // Metodo per creare una segnalazione
    public static final boolean creaSegnalazione(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        // Prima di effettuare la chiamata al Façade dello strato Entity, convertiamo il valore di categoria
        Categoria categoriaEnum = Categoria.valueOf(categoria);

        LocalDateTime localData;

        if(data != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            localData = LocalDateTime.parse(data, formatter);

        }else{

            localData = null;

        }

        // Otteniamo l'id del cittadino per poter verificare la sua esistenza
        Long idCittadino = ControllerUtenti.getIdUtenteCorrente();

        GestoreSegnalazioni gest = new GestoreSegnalazioni();
        boolean esito = gest.inserisciSegnalazione(idCittadino, titolo, descrizione, categoriaEnum, posizione, localData, urlImmagine);

        return esito;

    }

    public static boolean iniziaGestioneSegnalazione () {
        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        if (!verificaRuoloUtenteCorrente(Ruolo.OPERATORE)) {
            return false;
        }

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();

        return gest.iniziaGestioneSegnalazione(idSegnalazioneCorrente);
    }

    public static boolean aggiornaStatoSegnalazione() {
        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();

        if (!verificaPermessiOperatore(idOperatore, idSegnalazioneCorrente)) {
            return false;
        }

        return gest.aggiornaStatoSegnalazione(idSegnalazioneCorrente, idOperatore, true);
    }

    public static boolean concludiGestioneSegnalazione(String titolo, String descrizione, boolean esitoGestione) {
        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();

        if (!verificaPermessiOperatore(idOperatore, idSegnalazioneCorrente)) {
            return false;
        }

        //TODO controllo nel boundary per verificare che non si può concludere con esito positivo una segnalazione con stato presaInCarico (si deve prima aggiornare)

        boolean esitoAggiornamento = gest.aggiornaStatoSegnalazione(idSegnalazioneCorrente, idOperatore, esitoGestione);
        boolean esitoAggiuntaNota = false;

        if (esitoAggiornamento && titolo!=null && descrizione != null) {//Aggiornamento effettuato correttamente e posso aggiungere nota
            esitoAggiuntaNota = gest.aggiungiNota(idSegnalazioneCorrente, idOperatore, titolo, descrizione);
        }


        return esitoAggiuntaNota;
    }

    public static boolean verificaModificabilità(Integer idRow){

        // La prima cosa da fare è ottenere l'id della segnalazione corrente
        // e chiamare il gestore segnalazioni

        Long idSegnalazione = bindingId.get(idRow);

        // Andiamo a chiamare il gestore segnalazioni per ottenere la segnalazione
        Segnalazione segnalazione = new GestoreSegnalazioni().cercaSegnalazione(idSegnalazione);

        if (segnalazione == null)
            return false;

        // Dopo aver trovato la segnalazione abbiamo bisogno di verificare il suo stato
        if (segnalazione.getStato().getStatoToString().equals("RISOLTA"))
            return false;
        else
            return true;

    }

    public static boolean verificaRuoloUtenteCorrente(Ruolo ruolo) {

        String ruoloUtente = ControllerUtenti.getRuoloUtenteCorrente();
        if (!ruoloUtente.equals(ruolo.name())) {
            System.err.println("[ControllerSegnalazioni] Non si hanno i permessi per effettuare questa azione!");
            return false;
        } else {
            return true;
        }

    }

    public static boolean verificaPermessiOperatore(Long idOperatore, Long idSegnalazione){
        GestoreAggiornamentoStato gestoreAggiornamentoStato = new GestoreAggiornamentoStato();

        return gestoreAggiornamentoStato.verificaOperatoreInGestioneCorrente(idOperatore, idSegnalazione);
    }

    public static Map<String, String> ottieniParametriModificabili(Integer idRow){

        // La prima cosa da fare è ottenere l'id della segnalazione corrente
        // e chiamare il gestore segnalazioni

        Long idSegnalazione = bindingId.get(idRow);

        // Andiamo a chiamare il gestore segnalazioni per ottenere la segnalazione
        Segnalazione segnalazione = new GestoreSegnalazioni().cercaSegnalazione(idSegnalazione);

        if (segnalazione == null)
            return null;

        Map<String, String> parametri = new HashMap<>();

        parametri.put("titolo",segnalazione.getTitolo());
        parametri.put("descrizione", segnalazione.getDescrizione());
        parametri.put("categoria", segnalazione.getCategoria().name());
        parametri.put("posizione", segnalazione.getPosizione());

        if (segnalazione.getData() != null)
            parametri.put("data", segnalazione.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        else
            parametri.put("data", "");

        if (segnalazione.getUrlImmagine() != null)
            parametri.put("immagine", segnalazione.getUrlImmagine());
        else
            parametri.put("immagine", "");

        return parametri;

    }

    public static boolean modificaSegnalazione(Integer idRow, String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        // Prima di effettuare la chiamata al Façade dello strato Entity, convertiamo il valore di categoria
        Categoria categoriaEnum = Categoria.valueOf(categoria);

        LocalDateTime localData;

        if(data != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            localData = LocalDateTime.parse(data, formatter);

        }else{

            localData = null;

        }

        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        Long idCittadino = ControllerUtenti.getIdUtenteCorrente();
        Long idSegnalazione = bindingId.get(idRow);

        boolean esito = gest.modificaSegnalazione(idSegnalazione,titolo, descrizione, categoriaEnum, posizione, localData, urlImmagine);
        return esito;

    }

    public static List<String[]> caricaSegnalazioni(){
        /*
         * Questo metodo svolge il ruolo di "adapter" tra:
         *
         * - il livello applicativo, che lavora con oggetti del dominio
         *   come Imbarcazione;
         *
         * - la GUI, che invece non dovrebbe conoscere direttamente
         *   le Entity del sistema.
         *
         * In altre parole, il metodo adatta una lista di oggetti
         * Segnalazione.InfoAnteprima in una lista di array di String, cioè in un formato
         * semplice e già pronto per essere visualizzato in una JTable.
         */
        bindingId = new HashMap<>();
        GestoreSegnalazioni gestore = new GestoreSegnalazioni();

        //Recuperiamo l'id del cittadino
        Long id = ControllerUtenti.getIdUtenteCorrente();

        // Recuperiamo le segnalazioni associate al cittadino.
        List<Segnalazione.InfoAnteprima> anteprime =
                gestore.visualizzaSegnalazioniPerCittadino(id);

        /*
         * Questa sarà la lista da restituire alla GUI.
         * Ogni elemento della lista rappresenta una riga della JTable.
         */
        List<String[]> righe = new ArrayList<>();

        /*
         * Convertiamo ogni oggetto Segnalazione.InfoAnteprima in un array di String.
         * In questo modo la GUI riceverà solo dati testuali,
         * non oggetti Entity.
         */

        int indiceRiga = 0;
        for (Segnalazione.InfoAnteprima anteprima : anteprime) {

            String[] riga = new String[]{
                    anteprima.categoria().toString(),
                    (anteprima.data() != null)? anteprima.data().toString(): "",
                    anteprima.posizione().toString(),
                    anteprima.stato().toString(),
            };

            righe.add(riga);
            bindingId.put(indiceRiga, anteprima.idSegnalazione());
            indiceRiga++;
        }

        return righe;
    }

    public static List<String[]> caricaDettaglioSegnalazione(int indiceRiga){

        GestoreSegnalazioni gestore = new GestoreSegnalazioni();
        Long idSegnalazione = bindingId.get(indiceRiga);

        // Recuperiamo le segnalazioni associate al cittadino.
        GestoreSegnalazioni.dettaglioCompleto dettaglioCompleto =
                gestore.visualizzaDettaglioSegnalazione(idSegnalazione);

        List<String[]> righeTabellaInvertita = new ArrayList<>();

        String titolo = dettaglioCompleto.dettaglio().titolo().toString();
        String categoria = dettaglioCompleto.dettaglio().anteprima().categoria().toString();
        String stato = dettaglioCompleto.dettaglio().anteprima().stato().toString();
        String data = (dettaglioCompleto.dettaglio().anteprima().data() != null) ? dettaglioCompleto.dettaglio().anteprima().data().toString(): "";
        String posizione = dettaglioCompleto.dettaglio().anteprima().posizione().toString();

        righeTabellaInvertita.add(new String[]{"Titolo:", titolo});
        righeTabellaInvertita.add(new String[]{"Categoria:", categoria});
        righeTabellaInvertita.add(new String[]{"Stato:", stato});
        righeTabellaInvertita.add(new String[]{"Data:", data});
        righeTabellaInvertita.add(new String[]{"Posizione:", posizione});

        return righeTabellaInvertita;
    }

    public static List<String[]> caricaStoricoStatiSegnalazione(int indiceRiga){

        GestoreSegnalazioni gestore = new GestoreSegnalazioni();
        Long idSegnalazione = bindingId.get(indiceRiga);

        // Recuperiamo le segnalazioni associate al cittadino.
        GestoreSegnalazioni.dettaglioCompleto dettaglioCompleto =
                gestore.visualizzaDettaglioSegnalazione(idSegnalazione);

        List<String[]> righeCronologia = new ArrayList<>();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (AggiornamentoStatoEntry agg : dettaglioCompleto.aggiornamentiStato()) {

            String[] riga = new String[] {
                    agg.getData().format(formatter),          // Colonna 0: Data e Ora
                    agg.getStato().toString(),                // Colonna 1: Nome dello Stato (es. INVIATA)
            };

            righeCronologia.add(riga);
        }

        return righeCronologia;
    }

    public static String[] caricaDescrizioneEImmagineSegnalazione(int indiceRiga){

        GestoreSegnalazioni gestore = new GestoreSegnalazioni();
        Long idSegnalazione = bindingId.get(indiceRiga);

        // Recuperiamo le segnalazioni associate al cittadino.
        GestoreSegnalazioni.dettaglioCompleto dettaglioCompleto =
                gestore.visualizzaDettaglioSegnalazione(idSegnalazione);

        String[] datiRimanenti = new String[]{
                dettaglioCompleto.dettaglio().descrizone().toString(),
                dettaglioCompleto.dettaglio().urlImmagine()
        };

        return datiRimanenti;
    }
    public static List<String[]> visualizzaSegnalazioniPerOperatore(String statoStr, String categoriaStr, String areaStr) {

        //Traduzione dei parametri dal Boundary ai tipi Entity
        Categoria categoria = null;
        if (categoriaStr != null && !categoriaStr.equals("Tutte")) {
            categoria = Categoria.valueOf(categoriaStr.toUpperCase().replace(" ", "_"));
        }

        StatoSegnalazione stato = null;
        if (statoStr != null && !statoStr.equals("Tutti")) {
            switch (statoStr.toLowerCase()) {
                case "inviata":
                    stato = new StatoInviata();
                    break;
                case "in lavorazione":
                    stato = new StatoInLavorazione();
                    break;
                case "risolta":
                    stato = new StatoRisolta();
                    break;
            }
        }

        String posizione = (areaStr != null && !areaStr.equals("Tutte")) ? areaStr : null;

        //Invocazione del Façade dello strato Entity
        GestoreSegnalazioni gestore = new GestoreSegnalazioni();

        // Il gestore si occuperà di chiamare il database e restituire gli oggetti Segnalazione
        List<Segnalazione> listaEntity = gestore.cercaSegnalazioni(stato, categoria, posizione);

        // Mappatura inversa: da Entity a Stringhe primitive per la GUI
        List<String[]> righeTabella = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        if (listaEntity != null) {
            int idRow = 0;
            bindingId = new HashMap<>();

            for (Segnalazione s : listaEntity) {

                String[] riga = new String[] {
                        s.getIdSegnalazione() != null ? String.valueOf(s.getIdSegnalazione()) : "N/D",
                        s.getIdCittadino() != null ? String.valueOf(s.getIdCittadino()) : "Utente Sconosciuto",
                        (s.getData() != null) ? s.getData().format(formatter) : "",
                        s.getDescrizione(),
                        (s.getStato() != null) ? s.getStato().getStatoToString() : "Sconosciuto",
                        s.getCategoria() != null ? s.getCategoria().name() : "",
                        s.getPosizione()
                };

                righeTabella.add(riga);
                bindingId.put(idRow, s.getIdSegnalazione());
                idRow++;
            }
        }

        return righeTabella;
    }

    public static Map<String, String> getDettagliSegnalazione(Integer idRow) {

        //Istanziamo il Façade dello strato Entity per recuperare i dati dal dominio
        GestoreSegnalazioni gestore = new GestoreSegnalazioni();

        Long idSegnalazione = bindingId.get(idRow);
        setIdSegnalazioneCorrente(idSegnalazione);

        //Ricerchiamo l'entity Segnalazione tramite il suo identificativo
        Segnalazione s = gestore.cercaSegnalazione(idSegnalazione);

        if (s == null) {
            return null;
        }

        Map<String, String> dettagli = new HashMap<>();

        dettagli.put("titolo", s.getTitolo() != null ? s.getTitolo() : "");
        dettagli.put("descrizione", s.getDescrizione() != null ? s.getDescrizione() : "");
        dettagli.put("categoria", s.getCategoria() != null ? s.getCategoria().name() : "");
        dettagli.put("posizione", s.getPosizione() != null ? s.getPosizione() : "");
        dettagli.put("stato", s.getStato() != null ? s.getStato().getStatoToString() : "Sconosciuto");

        if (s.getData() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            dettagli.put("data", s.getData().format(formatter));
        } else {
            dettagli.put("data", "");
        }

        dettagli.put("idCittadino", s.getIdCittadino() != null ? String.valueOf(s.getIdCittadino()) : "");
        dettagli.put("urlImmagine", s.getUrlImmagine() != null ? s.getUrlImmagine() : "");

        return dettagli;
    }


    public static void main(String[] args) {
        System.out.println("[ControllerSegnalazioni] MainTest avviato..");

        setIdSegnalazioneCorrente(1L);
        ControllerUtenti.setIdUtenteCorrente(1L, Ruolo.OPERATORE.name());


        //1. flusso normale
        System.out.println("[ControllerSegnalazioni] Test flusso principale");

        iniziaGestioneSegnalazione();

        aggiornaStatoSegnalazione();

        concludiGestioneSegnalazione("Problema", "Riscontrato problema nella risoluzione", false);

        //2. dopo aver preso in carico una segnalazione, un altro operatore tenta l'accesso
        System.out.println("[ControllerSegnalazioni] Test operatore prende in carico una segnalazione non sua");

        iniziaGestioneSegnalazione();

        ControllerUtenti.setIdUtenteCorrente(2L, Ruolo.OPERATORE.name());
        iniziaGestioneSegnalazione();

        ControllerUtenti.setIdUtenteCorrente(1L, Ruolo.OPERATORE.name());
        concludiGestioneSegnalazione("Problema", "Riscontrato problema nella risoluzione", false);

        //3. tentativo di prendere in carico una segnalazione da parte di un cittadino
        System.out.println("[ControllerSegnalazioni] Test cittadino prende in carico una segnalazione");

        ControllerUtenti.setIdUtenteCorrente(1L, Ruolo.CITTADINO.name());
        iniziaGestioneSegnalazione();


        //4. tentativo di prendere in carico una segnalazione risolta
        System.out.println("[ControllerSegnalazioni] Test operatore prende in carico una segnalazione risolta");

        ControllerUtenti.setIdUtenteCorrente(1L, Ruolo.OPERATORE.name());
        setIdSegnalazioneCorrente(4L);
        iniziaGestioneSegnalazione();

        //5. tentativo di concludere con esito positivo una segnalazione presa in carico
        System.out.println("[ControllerSegnalazioni] Test operatore tenta di risolvere con esito positivo una segnalazione presa in carico");

        setIdSegnalazioneCorrente(1L);
        iniziaGestioneSegnalazione();
        concludiGestioneSegnalazione("Risolta", "Segnalazione risolta con successo", true);
        /*
            TODO| non è un problema ma è il flusso di esecuzione: se si fa concludi gestione con esito true da presaInCarico:
            TODO| la segnalazione passa in inLavorazione, rimane attiva e non viene aggiunta l'eventuale nota interna
            TODO| NB: inserire conclusione e aggiornamento come operazione atomica potrebbe causare problemi a questo flusso
         */
        //concludiGestioneSegnalazione(null, null, false);
        //Riga inserita per far tornare il db allo stato iniziale senza modifiche ulteriori


    }

}
