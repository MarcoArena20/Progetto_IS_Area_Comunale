package Controller;

import Entity.Gestori.GestoreSegnalazioni;
import Entity.Segnalazione;
import Entity.Enum.Categoria;
import Entity.Enum.Ruolo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

        String ruoloUtente = ControllerUtenti.getRuoloUtenteCorrente();//Controllo superfluo
        if (ruoloUtente.equals(Ruolo.CITTADINO.name())) {
            System.err.println("[ControllerSegnalazioni] Non si hanno i permessi per effettuare questa azione!");
            return false;
        }

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();


        boolean esito = gest.iniziaGestioneSegnalazione(idSegnalazioneCorrente, idOperatore);

        return esito;
    }

    public static boolean aggiornaStatoSegnalazione() {
        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        //TODO controllo nel boundary per verificare che l'operatore stia gestendo quella segnalazione
        //TODO controllo nel boundary per verificare che non può aggiornare una segnalazione con stato inLavorazione (si deve concludere)

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();

        boolean esitoAggiornamento = gest.aggiornaStatoSegnalazione(idSegnalazioneCorrente, idOperatore, true);

        return esitoAggiornamento;
    }

    public static boolean concludiGestioneSegnalazione(String titolo, String descrizione, boolean esitoGestione) {
        GestoreSegnalazioni gest = new GestoreSegnalazioni();

        //TODO controllo nel boundary per verificare che l'operatore stia gestendo quella segnalazione
        //TODO controllo nel boundary per verificare che non si può concludere con esito positivo una segnalazione con stato presaInCarico (si deve prima aggiornare)

        Long idSegnalazioneCorrente = ControllerSegnalazioni.getIdSegnalazioneCorrente();
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();

        boolean esitoAggiornamento = gest.aggiornaStatoSegnalazione(idSegnalazioneCorrente, idOperatore, esitoGestione);
        boolean esitoAggiuntaNota = false;

        if (esitoAggiornamento && titolo!=null && descrizione != null) {//Aggiornamento effettuato correttamente e posso aggiungere nota
            //TODO controllo nel boundary per fare in modo tale da avere o titolo e descrizione null o che rispettano i vincoli

            esitoAggiuntaNota = gest.aggiungiNota(idSegnalazioneCorrente, idOperatore, titolo, descrizione);
        }


        return esitoAggiuntaNota;
    }

    public static boolean verificaModificabilità(){

        // La prima cosa da fare è ottenere l'id della segnalazione corrente
        // e chiamare il gestore segnalazioni

        //Long idSegnalazione = getIdSegnalazioneCorrente();
        Long idSegnalazione = 1L;

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

    public static Map<String, String> ottieniParametriModificabili(){

        // La prima cosa da fare è ottenere l'id della segnalazione corrente
        // e chiamare il gestore segnalazioni

        //Long idSegnalazione = getIdSegnalazioneCorrente();
        Long idSegnalazione = 1L;

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

    public static boolean modificaSegnalazione(String titolo, String descrizione, String categoria, String posizione, String data, String urlImmagine){

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

        //Long idCittadino = ControllerUtenti.getIdUtenteCorrente();
        //Long idSegnalazione = getIdSegnalazioneCorrente();
        Long idSegnalazione = 1L;
        Long idCittadino = 1L;

        boolean esito = gest.modificaSegnalazione(idSegnalazione,titolo, descrizione, categoriaEnum, posizione, localData, urlImmagine);
        return esito;

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
        concludiGestioneSegnalazione(null, null, false);
        //Riga inserita per far tornare il db allo stato iniziale senza modifiche ulteriori


    }

}
