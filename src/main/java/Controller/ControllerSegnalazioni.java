package Controller;

import Entity.Segnalazione;
import Entity.Categoria;
import Entity.GestoreSegnalazioni;
import Entity.Ruolo;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            }
        }

        return righeTabella;
    }

    public static Map<String, String> getDettagliSegnalazione(Long idSegnalazione) {

        //Istanziamo il Façade dello strato Entity per recuperare i dati dal dominio
        GestoreSegnalazioni gestore = new GestoreSegnalazioni();

        //Ricerchiamo l'entity Segnalazione tramite il suo identificativo
        Segnalazione s = gestore.cercaSegnalazione(idSegnalazione);

        if (s == null) {
            return null;
        }

        Map<String, String> dettagli = new HashMap<>();

        dettagli.put("id", s.getIdSegnalazione() != null ? String.valueOf(s.getIdSegnalazione()) : "");
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

        boolean esito = ControllerSegnalazioni.iniziaGestioneSegnalazione();





    }

}
