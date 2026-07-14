package Controller;

import Entity.Cittadino;
import Entity.EntryDB.AggiornamentoStatoEntry;
import Entity.Gestori.*;
import Entity.Segnalazione;
import Entity.Enum.*;
import Entity.StateMachine.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Fornisce un punto di accesso per le operazioni
 * relative alla gestione delle segnalazioni dell'applicazione.
 * <p>
 * La classe permette alle classi del livello Boundary di interagire
 * con il sottosistema di gestione delle segnalazioni senza conoscere i
 * dettagli implementativi.
 *
 *
 * @version 1.0
 */
public class ControllerSegnalazioni {

    /**
     *  Mappa che associa l'indice numerico della riga della JTable (Integer) all'ID reale della segnalazione (Long).
     */
    private static Map<Integer,Long> bindingId;
    private static Long idSegnalazioneCorrente;

    /**
     * Ritorna l'id della segnalazione corrente visualizzata
     * @return l'id della segnalazione corrente
     */
    private static Long getIdSegnalazioneCorrente(){
        return idSegnalazioneCorrente;
    }

    /**
     * Permette di modificare l'id della segnalazione corrente
     * @param idSegnalazioneCorrente id della segnalazione corrente
     */
    public static void setIdSegnalazioneCorrente(Long idSegnalazioneCorrente){
        ControllerSegnalazioni.idSegnalazioneCorrente = idSegnalazioneCorrente;
    }


    /**
     * CASO D'USO: CreazioneSegnalazione
     * Converte categoria e data nei formati Categoria e LocalDateTime prima di contattare
     * il GestoreSegnalazioni del package Entity per creare la segnalazione
     *
     * @param titolo titolo della segnalazione (campo obbligatorio)
     * @param descrizione descrizione della segnalazione (campo obbligatorio)
     * @param categoria categoria della segnalazione (campo obbligatorio)
     * @param posizione posizione della segnalazione (campo obbligatorio)
     * @param data data della segnalazione (campo opzionale)
     * @param urlImmagine url dell'allegato della segnalazione (campo opzionale)
     * @return true se la creazione è andata a buon fine, false altrimenti
     */
    public static boolean creaSegnalazione(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        // Prima di effettuare la chiamata al GestoreSegnalazioni dello strato Entity effettuiamo
        // il typecasting di categoria e data

        Categoria categoriaEnum = Categoria.valueOf(categoria);
        LocalDateTime localData;

        if(data != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            localData = LocalDateTime.parse(data, formatter);

        }else
            localData = null;

        // Otteniamo l'id del cittadino corrente per associarlo alla segnalazione creata
        Long idCittadino = ControllerUtenti.getIdUtenteCorrente();

        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        return gest.inserisciSegnalazione(idCittadino, titolo, descrizione, categoriaEnum, posizione, localData, urlImmagine);

    }

    /**
     * CASO D'USO: ModificaSegnalazione
     * Verifica la modificabilità di una segnalazione controllando se essa si trova
     * nello stato RISOLTA (non modificabile) o no
     * @param idRow indice della riga della segnalazione corrente --> permette al controller di associare
     *              l'indice della riga all'id della segnalazione
     *
     * @return true se la segnalazione è modificabile, false altrimenti
     */
    public static boolean verificaModificabilita(Integer idRow){

        // Effettuiamo il binding tra idRow e idSegnalazione
        Long idSegnalazione = bindingId.get(idRow);

        // Invochiamo il GestoreSegnalazioni per ottenere il riferimento alla segnalazione corrente
        Segnalazione segnalazione = new GestoreSegnalazioni().cercaSegnalazione(idSegnalazione);

        // Controlliamo lo stato della segnalazione corrente
        return !segnalazione.getStato().getStatoToString().equals("RISOLTA");

    }

    /**
     * CASO D'USO: ModificaSegnalazione
     * Restituisce al chiamante i parametri della segnalazione che possono essere modificati
     * dal cittadino
     * @param idRow indice della riga della segnalazione corrente --> permette al controller di associare
     *              l'indice della riga all'id della segnalazione
     * @return una map che effettua il binding tra attributo della segnalazione e valore
     */
    public static Map<String, String> ottieniParametriModificabili(Integer idRow){

        // Effettuiamo il binding tra idRow e idSegnalazione
        Long idSegnalazione = bindingId.get(idRow);

        // Invochiamo il GestoreSegnalazioni per ottenere il riferimento alla segnalazione corrente
        Segnalazione segnalazione = new GestoreSegnalazioni().cercaSegnalazione(idSegnalazione);

        // Creiamo la map dei parametri
        Map<String, String> parametri = new HashMap<>();

        // Otteniamo i parametri della segnalazione
        parametri.put("titolo",segnalazione.getTitolo());
        parametri.put("descrizione", segnalazione.getDescrizione());
        parametri.put("categoria", segnalazione.getCategoria().name());
        parametri.put("posizione", segnalazione.getPosizione());

        // Verifichiamo la presenza dei parametri opzionali
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

    /**
     * CASO D'USO: ModificaSegnalazione
     * Converte categoria e data nei formati Categoria e LocalDateTime prima di contattare
     * il GestoreSegnalazioni del package Entity per modificare la segnalazione
     * @param idRow indice della riga della segnalazione corrente --> permette al controller di associare
     *              l'indice della riga all'id della segnalazione
     * @param titolo titolo della segnalazione (campo obbligatorio)
     * @param descrizione descrizione della segnalazione (campo obbligatorio)
     * @param categoria categoria della segnalazione (campo obbligatorio)
     * @param posizione posizione della segnalazione (campo obbligatorio)
     * @param data data della segnalazione (campo opzionale)
     * @param urlImmagine url dell'allegato della segnalazione (campo opzionale)
     * @return true se la creazione è andata a buon fine, false altrimenti
     */
    public static boolean modificaSegnalazione(Integer idRow, String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

        // Prima di effettuare la chiamata al GestoreSegnalazioni dello strato Entity effettuiamo
        // il typecasting di categoria e data
        Categoria categoriaEnum = Categoria.valueOf(categoria);
        LocalDateTime localData;

        if(data != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            localData = LocalDateTime.parse(data, formatter);

        }else{

            localData = null;

        }

        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        // Effettuiamo il binding tra idRow e idSegnalazione
        Long idSegnalazione = bindingId.get(idRow);

        return gest.modificaSegnalazione(idSegnalazione,titolo, descrizione, categoriaEnum, posizione, localData, urlImmagine);

    }

    /**
     * CASO D'USO: iniziaGestioneSegnalazione
     * Invocato dal button btnPrendiInCarico @see FormVisualizzaDettaglioSegnalazioneRicevuta
     * Dopo aver verificato che l'utente sia un'operatore, invoca il gestoreSegnalazioni per iniziare la gestione della segnalazione,
     * specificando l'id della segnalazione corrente
     *
     * @return true se la segnalazione è stata presa in carico correttamente, false altrimenti
     */
    public static boolean iniziaGestioneSegnalazione () {
        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        if (!ControllerUtenti.verificaRuoloUtenteCorrente(Ruolo.OPERATORE)) {
            return false;
        }

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();

        return gest.iniziaGestioneSegnalazione(idSegnalazioneCorrente);
    }

    /**
     * CASO D'USO: aggiornaStatoSegnalazione
     * Invocato dal button btnAggiornaStato @see FormVisualizzaDettaglioSegnalazioneRicevuta
     * Dopo aver verificato che l'operatore stia gestendo la segnalazione corrente, invoca il gestoreSegnalazioni per aggiornare lo stato,
     * specificando l'esito dell'aggiornamento (positivo)
     *
     * @return true se la segnalazione è stata aggiornata correttamente, false altrimenti
     */
    public static boolean aggiornaStatoSegnalazione() {
        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();

        if (!verificaPermessiOperatore(idOperatore, idSegnalazioneCorrente)) {
            return false;
        }

        return gest.aggiornaStatoSegnalazione(idSegnalazioneCorrente, idOperatore, true);
    }

    /**
     * CASO D'USO: concludiGestioneSegnalazione, aggiungiNotaInterna
     * Invocato dal button confermaEConcludiButton @see FormConclusioneGestione
     * Dopo aver verificato che l'operatore stia gestendo la segnalazione corrente e che l'operazione di conclusione sia effettuabile,
     * invoca il gestoreSegnalazioni per aggiornare lo stato della segnalazione (con esito specificato in ingresso alla funzione)
     * e, se l'aggiornamento va a buon fine e si vuole aggiungere una nota, per aggiungere la nota
     *
     * @param titolo titolo della nota, null se non la si vuole aggiungere
     * @param descrizione descrizione della nota, null se non la si vuole aggiungere
     * @param esitoGestione specifica se la gestione è stata risolutiva oppure no
     * @return true se la segnalazione è stata conclusa correttamente (ed eventualmente la nota è stata aggiunta), false altrimenti
     */
    public static boolean concludiGestioneSegnalazione(String titolo, String descrizione, boolean esitoGestione) {
        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();

        if (!verificaPermessiOperatore(idOperatore, idSegnalazioneCorrente)) {
            return false;
        }

        //Se si vuole concludere la gestione con esito positivo e lo stato corrente è presaInCarico, errore
        if (esitoGestione && gest.cercaSegnalazione(idSegnalazioneCorrente).getStato().getStatoToString().equals(StatoType.PRESA_IN_CARICO.name())) {
            return false;
        }

        boolean esitoAggiornamento = gest.aggiornaStatoSegnalazione(idSegnalazioneCorrente, idOperatore, esitoGestione);
        boolean esitoAggiuntaNota = true;

        if (esitoAggiornamento && titolo!=null && descrizione != null) {//Aggiornamento effettuato correttamente e posso aggiungere nota
            esitoAggiuntaNota = gest.aggiungiNota(idSegnalazioneCorrente, idOperatore, titolo, descrizione);
        }


        return esitoAggiuntaNota;
    }

    /**
     * CASO D'USO: aggiornaStatoSegnalazione, concludiGestioneSegnalazione
     * Invocato dai metodi utilizzati dai suddetti casi d'uso, precedentemente commentati
     * Invoca il gestoreAggiornamento per verificare che l'operatore stia gestendo la segnalazione che intende aggiornare o concludere
     *
     * @param idOperatore id dell'operatore corrente
     * @param idSegnalazione id della segnalazione corrente
     * @return true se l'operatore sta gestendo quella segnalazione, false altrimenti
     */
    public static boolean verificaPermessiOperatore(Long idOperatore, Long idSegnalazione){
        GestoreAggiornamentoStato gestoreAggiornamentoStato = new GestoreAggiornamentoStato();

        return gestoreAggiornamentoStato.verificaOperatoreInGestioneCorrente(idOperatore, idSegnalazione);
    }

    /**
     * CASO D'USO: visualizzaSegnlazioniInviate
     * Questo metodo svolge il ruolo di "adapter" tra:
     * <p>
     * - il livello applicativo, che lavora con oggetti del dominio
     *   come Segnalazione;
     * <p>
     * - la GUI, che invece non dovrebbe conoscere direttamente
     *   le Entity del sistema.
     * <p>
     * In altre parole, il metodo adatta una lista di oggetti
     * Segnalazione.InfoAnteprima in una lista di array di String, cioè in un formato
     * semplice e già pronto per essere visualizzato in una JTable.
     * <p>
     * Invoca il GestoreSegnalazioni per recuperare una lista di anteprime
     * di ogni segnalazione associata al CITTADINO corrente
     *
     * @return una List di array di Stringhe rappresentanti le righe sintetiche delle segnalazioni
     */
    public static List<String[]> caricaSegnalazioni(){

        bindingId = new HashMap<>();
        GestoreSegnalazioni gestore = new GestoreSegnalazioni();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
                    (anteprima.data() != null)? anteprima.data().format(formatter): "",
                    anteprima.posizione(),
                    anteprima.stato().getStatoToString(),
            };

            righe.add(riga);
            bindingId.put(indiceRiga, anteprima.idSegnalazione());
            indiceRiga++;
        }

        return righe;
    }

    /**
     * CASO D'USO: visualizzaDettaglioSegnlazioneInviata
     * Questo metodo svolge il ruolo di "adapter" tra:
     * <p>
     * - il livello applicativo, che lavora con oggetti del dominio
     *   come Segnalazione;
     * <p>
     * - la GUI, che invece non dovrebbe conoscere direttamente
     *   le Entity del sistema.
     * <p>
     * In altre parole, il metodo adatta una lista di oggetti
     * GestoreSegnalazioni.dettaglioCompleto in una lista di array di String, cioè in un formato
     * semplice e già pronto per essere visualizzato in una JTable.
     * <p>
     * Invoca il GestoreSegnalazioni per caricare i dettagli informativi di una determinata segnalazione.
     *
     * @param indiceRiga l'indice della riga selezionata nella tabella della GUI
     * @return una List di array di stringhe idonea a popolare una tabella invertita verticalmente
     */
    public static List<String[]> caricaDettaglioSegnalazione(int indiceRiga){

        GestoreSegnalazioni gestore = new GestoreSegnalazioni();
        Long idSegnalazione = bindingId.get(indiceRiga);

        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Recuperiamo le segnalazioni associate al cittadino.
        GestoreSegnalazioni.dettaglioCompleto dettaglioCompleto =
                gestore.visualizzaDettaglioSegnalazione(idSegnalazione);

        List<String[]> righeTabellaInvertita = new ArrayList<>();

        String titolo = dettaglioCompleto.dettaglio().titolo();
        String categoria = dettaglioCompleto.dettaglio().anteprima().categoria().toString();
        String stato = dettaglioCompleto.dettaglio().anteprima().stato().getStatoToString();
        String data = (dettaglioCompleto.dettaglio().anteprima().data() != null) ? dettaglioCompleto.dettaglio().anteprima().data().format(formatter): "";
        String posizione = dettaglioCompleto.dettaglio().anteprima().posizione();

        righeTabellaInvertita.add(new String[]{"Titolo:", titolo});
        righeTabellaInvertita.add(new String[]{"Categoria:", categoria});
        righeTabellaInvertita.add(new String[]{"Stato:", stato});
        righeTabellaInvertita.add(new String[]{"Data:", data});
        righeTabellaInvertita.add(new String[]{"Posizione:", posizione});

        return righeTabellaInvertita;
    }

    /**
     * CASO D'USO: visualizzaDettaglioSegnlazioneInviata
     * Questo metodo svolge il ruolo di "adapter" tra:
     * <p>
     * - il livello applicativo, che lavora con oggetti del dominio
     *   come Segnalazione;
     * <p>
     * - la GUI, che invece non dovrebbe conoscere direttamente
     *   le Entity del sistema.
     * <p>
     * In altre parole, il metodo adatta una lista di oggetti
     * GestoreSegnalazioni.dettaglioCompleto in una lista di array di String, cioè in un formato
     * semplice e già pronto per essere visualizzato in una JTable.
     * <p>
     * Invoca il GestoreSegnalazioni per recuperare lo storico cronologico complessivo dei cambi di stato subiti dalla segnalazione selezionata.
     *
     * @param indiceRiga l'indice della riga selezionata nella tabella della GUI
     * @return una List di array di stringhe
     */
    public static List<String[]> caricaStoricoStatiSegnalazione(int indiceRiga) {

        GestoreSegnalazioni gestore = new GestoreSegnalazioni();
        Long idSegnalazione = bindingId.get(indiceRiga);

        // Recuperiamo le segnalazioni associate al cittadino.
        GestoreSegnalazioni.dettaglioCompleto dettaglioCompleto =
                gestore.visualizzaDettaglioSegnalazione(idSegnalazione);

        List<String[]> righeCronologia = new ArrayList<>();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (AggiornamentoStatoEntry agg : dettaglioCompleto.aggiornamentiStato().keySet()) {

            String[] riga = new String[]{
                    agg.getData().format(formatter),                 // Colonna 0: Data e Ora
                    agg.getStato().toString(),                       // Colonna 1: Nome dello Stato
                    dettaglioCompleto.aggiornamentiStato().get(agg)[0], // titolo della nota
                    dettaglioCompleto.aggiornamentiStato().get(agg)[1] // descrizione nota
            };

            righeCronologia.add(riga);
        }

        return righeCronologia;
    }

    /**
     * CASO D'USO: visualizzaDettaglioSegnlazioneInviata
     * Questo metodo svolge il ruolo di "adapter" tra:
     * <p>
     * - il livello applicativo, che lavora con oggetti del dominio
     *   come Segnalazione;
     * <p>
     * - la GUI, che invece non dovrebbe conoscere direttamente
     *   le Entity del sistema.
     * <p>
     * Invoca il GestoreSegnalazioni per recuperare le informazioni relative alla descrizione testuale estesa e al link dell'immagineallegata di una segnalazione.
     *
     * @param indiceRiga l'indice della riga selezionata nella tabella della GUI
     * @return un array di stringhe di dimensione fissa (= 2), dove l'indice 0 rappresenta la descrizione e l'indice 1 l'URL dell'immagine
     */
    public static String[] caricaDescrizioneEImmagineSegnalazione(int indiceRiga){

        GestoreSegnalazioni gestore = new GestoreSegnalazioni();
        Long idSegnalazione = bindingId.get(indiceRiga);

        // Recuperiamo le segnalazioni associate al cittadino.
        GestoreSegnalazioni.dettaglioCompleto dettaglioCompleto =
                gestore.visualizzaDettaglioSegnalazione(idSegnalazione);

        return new String[]{
                dettaglioCompleto.dettaglio().descrizone(),
                dettaglioCompleto.dettaglio().urlImmagine()
        };
    }

    /**
     *
     * Converte i filtri in formato stringa provenienti dall'interfaccia grafica nei corrispondenti
     * tipi del livello Entity, interroga il GestoreSegnalazioni e mappa la lista di oggetti risultante
     * in una struttura dati primitiva adatta al popolamento della JTable dell'operatore, aggiornando
     * contestualmente la mappa di binding degli identificativi.
     *
     * @param statoStr stringa per filtrare lo stato della segnalazione ("Tutti", "inviata", "in lavorazione", "risolta", "presa in carico")
     * @param categoriaStr stringa per filtrare la categoria della segnalazione ("Tutte" o nome specifico della categoria dell'enum)
     * @param areaStr stringa per filtrare la posizione geografica o area di competenza ("Tutte" o area specifica)
     * @return una lista di array di stringhe contenente i dati testuali delle segnalazioni pronti per essere visualizzati nella GUI
     */
    public static List<String[]> visualizzaSegnalazioniPerOperatore(String statoStr, String categoriaStr, String areaStr) {

        //Traduzione dei parametri dal Boundary ai tipi Entity
        Categoria categoria = null;
        if (categoriaStr != null && !categoriaStr.equals("Tutte")) {
            categoria = Categoria.valueOf(categoriaStr.toUpperCase().replace(" ", "_"));
        }

        StatoSegnalazione stato = null;
        if (statoStr != null && !statoStr.equals("Tutti")) {
            stato = switch (statoStr.toLowerCase()) {
                case "inviata" -> new StatoInviata();
                case "in lavorazione" -> new StatoInLavorazione();
                case "risolta" -> new StatoRisolta();
                case "presa in carico" -> new StatoPresaInCarico();
                default -> null;
            };
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

    /**
     *
     * Recupera i dettagli completi di una segnalazione partendo dall'indice della riga selezionata nella GUI,
     * imposta l'identificativo associato come segnalazione corrente del sistema e inserisce tutti i suoi
     * attributi all'interno di una mappa strutturata per non esporre l'oggetto Entity alla Boundary.
     *
     * @param idRow indice della riga della segnalazione corrente nella tabella che permette al controller di risalire all'identificativo reale
     * @return una map ordinata che effettua il binding tra il nome dell'attributo della segnalazione ed il relativo valore in formato stringa, oppure null se la segnalazione non viene trovata
     * @throws IllegalArgumentException se l'indice della riga passato come parametro non è presente nella mappa di binding interna
     */
    public static Map<String, String> getDettagliSegnalazione(Integer idRow) {

        //Istanziamo il Façade dello strato Entity per recuperare i dati dal dominio
        GestoreSegnalazioni gestore = new GestoreSegnalazioni();

        if(!bindingId.containsKey(idRow)) {
            throw new IllegalArgumentException("Identificativo non trovato");
        }

        Long idSegnalazione = bindingId.get(idRow);
        setIdSegnalazioneCorrente(idSegnalazione);

        //Ricerchiamo l'entity Segnalazione tramite il suo identificativo
        Segnalazione s = gestore.cercaSegnalazione(idSegnalazione);

        if (s == null) {
            return null;
        }

        Map<String, String> dettagli = new LinkedHashMap<>();

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

        String nomeCittadino = "Utente Sconosciuto";
        if (s.getIdCittadino() != null) {
            GestoreUtenti gestoreUtenti = new GestoreUtenti();
            Cittadino cittadino = gestoreUtenti.cercaCittadino(s.getIdCittadino());

            if (cittadino != null) {
                nomeCittadino = cittadino.getNome() + " " + cittadino.getCognome();
            } else {
                nomeCittadino = String.valueOf(s.getIdCittadino()); // Fallback all'ID se non trovato
            }
        }
        dettagli.put("nomeCittadino", nomeCittadino);

        dettagli.put("urlImmagine", s.getUrlImmagine() != null ? s.getUrlImmagine() : "");

        return dettagli;
    }

}
