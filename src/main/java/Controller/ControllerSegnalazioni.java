package Controller;

import Entity.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Façade
public class ControllerSegnalazioni {

    public static Long getIdSegnalazioneCorrente(){

        Path path = Path.of("configuration/config.txt");

        try {
            if (!Files.exists(path)) {

                return null;

            }else{

                List<String> lines = Files.readAllLines(path);
                return Long.parseLong(lines.get(2).split(":")[1]);

            }


        }catch(IOException e){

            e.printStackTrace();
            return null;

        }
    }

    public static void setIdSegnalazioneCorrente(Long idSegnalazioneCorrente){

        // Il primo controllo da fare è verificare se il file esiste, altrimenti va creato da zero con la configurazione
        // di default, ovvero
        // idUtente:
        // ruolo:
        // idSegnalazione:

        Path path = Path.of("configuration/config.txt");


        try {

            Files.createDirectories(Path.of("configuration"));

            if (!Files.exists(path)) {

                Files.createFile(path);
                Files.writeString(path, "idUtente:\nruolo:\nidSegnalazione:" + idSegnalazioneCorrente + "\n", StandardOpenOption.APPEND);

            }else{

                List<String> lines = Files.readAllLines(path);
                lines.set(2, "idSegnalazione:" + idSegnalazioneCorrente);

                Files.write(path, lines);

            }


        }catch(IOException e){

            e.printStackTrace();

        }

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

        String ruoloUtente = ControllerUtenti.getRuoloUtenteCorrente();
        if (ruoloUtente.equals(Ruolo.CITTADINO.name())) {
            System.err.println("[ControllerSegnalazioni] Non si hanno i permessi per effettuare questa azione!");
            return false;
        }

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();


        boolean esito = gest.iniziaGestioneSegnalazione(idSegnalazioneCorrente, idOperatore);

        return esito;

    }

    //Mappa usata per associare ogni riga all'id della segnalazione
    private static Map<Integer, Long> mapId;
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
        mapId = new HashMap<>();
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
                    anteprima.data().toString(),
                    anteprima.posizione().toString(),
                    anteprima.stato().toString(),
            };

            righe.add(riga);
            mapId.put(indiceRiga, anteprima.idSegnalazione());
            indiceRiga++;
        }

        return righe;
    }

    public static List<String[]> caricaDettaglioSegnalazione(int indiceRiga){

        GestoreSegnalazioni gestore = new GestoreSegnalazioni();
        Long idSegnalazione = mapId.get(indiceRiga);

        // Recuperiamo le segnalazioni associate al cittadino.
        GestoreSegnalazioni.dettaglioCompleto dettaglioCompleto =
                gestore.visualizzaDettaglioSegnalazione(idSegnalazione);

        List<String[]> righeTabellaInvertita = new ArrayList<>();

        String titolo = dettaglioCompleto.dettaglio().titolo().toString();
        String categoria = dettaglioCompleto.dettaglio().anteprima().categoria().toString();
        String stato = dettaglioCompleto.dettaglio().anteprima().stato().toString();
        String data = dettaglioCompleto.dettaglio().anteprima().data().toString();
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
        Long idSegnalazione = mapId.get(indiceRiga);

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
        Long idSegnalazione = mapId.get(indiceRiga);

        // Recuperiamo le segnalazioni associate al cittadino.
        GestoreSegnalazioni.dettaglioCompleto dettaglioCompleto =
                gestore.visualizzaDettaglioSegnalazione(idSegnalazione);

        String[] datiRimanenti = new String[]{
                dettaglioCompleto.dettaglio().descrizone().toString(),
                dettaglioCompleto.dettaglio().urlImmagine()
        };

        return datiRimanenti;
    }

    public static void main(String[] args) {
        System.out.println("[ControllerSegnalazioni] MainTest avviato..");

        setIdSegnalazioneCorrente(1L);
        ControllerUtenti.setIdUtenteCorrente(1L, Ruolo.OPERATORE.name());

        boolean esito = ControllerSegnalazioni.iniziaGestioneSegnalazione();


    }

}
