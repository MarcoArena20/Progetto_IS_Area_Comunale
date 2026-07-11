package Entity.Gestori;

//Librerie
import Database.GestorePersistenza;
import Entity.*;
import Entity.Enum.Categoria;
import Entity.StateMachine.StatoSegnalazione;
import Entity.Enum.StatoType;

import java.time.LocalDateTime;
import java.util.List;

//Façade
public class GestoreSegnalazioni {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    public GestoreSegnalazioni(){

        this.gestorePersistenza = new GestorePersistenza();

    }

    //Metodi
    public boolean inserisciSegnalazione(Long idCittadino, String titolo, String descrizione, Categoria categoria, String posizione, LocalDateTime data, String urlImmagine) {

        // Bisogna trovare il cittadino corrispondente
        Cittadino cittadino = new GestoreUtenti().cercaUtente(idCittadino);

        //if(cittadino == null)
            //return false;


        Segnalazione segnalazione = new Segnalazione(cittadino, titolo, descrizione, categoria, posizione);

        if(data != null)
            segnalazione.setData(data);

        if (urlImmagine != null)
           segnalazione.setUrlImmagine(urlImmagine);

        System.out.println("[GestoreSegnalazioni] Inserita nuova segnalazione:\n"+segnalazione.toString());

        return gestorePersistenza.salva(segnalazione);
    }

    public boolean modificaSegnalazione(Long idSegnalazione, String titolo, String descrizione, Categoria categoria, String posizione, LocalDateTime data, String urlImmagine){

        Segnalazione segnalazione= gestorePersistenza.trovaPerId(Segnalazione.class, idSegnalazione);

        if (segnalazione == null)
            return false;

        segnalazione.setTitolo(titolo);
        segnalazione.setDescrizione(descrizione);
        segnalazione.setCategoria(categoria);
        segnalazione.setPosizione(posizione);
        segnalazione.setData(data);
        segnalazione.setUrlImmagine(urlImmagine);

        return (gestorePersistenza.aggiorna(segnalazione) != null);

    }

    public List<Segnalazione> cercaSegnalazioni(Long idCittadino) {
        //TODO

        return null;
    }

    public List<Segnalazione> cercaSegnalazioni(StatoSegnalazione stato, Categoria categoria, String posizione) {

        java.util.Map<String, Object> filtri = new java.util.HashMap<>();

        if (stato != null) {filtri.put("stato", stato);}
        if (categoria != null) {filtri.put("categoria", categoria);}
        if (posizione != null && !posizione.trim().isEmpty()) {filtri.put("posizione", posizione);}

        return gestorePersistenza.cercaPerCampi(Segnalazione.class, filtri);
    }

    public Segnalazione cercaSegnalazione(Long idSegnalazione) {
        Segnalazione segnalazione = gestorePersistenza.trovaPerId(Segnalazione.class, idSegnalazione);

        return segnalazione;
    }

    public boolean aggiungiNota(Long idSegnalazione, Long idOperatore, String titolo, String descrizione) {
        //Bisogna controllare che: 0. la segnalazione esiste, 1. lo stato sia Inviata o Risolta


        Segnalazione segnalazione = cercaSegnalazione(idSegnalazione);
        if (segnalazione == null) {
            System.err.println("[GestoreSegnalazioni] Nessuna segnalazione trovata..");
            return false;
        }

        System.out.println("[GestoreSegnalazioni] Trovata segnalazione:\n"+segnalazione.toString());


        StatoSegnalazione statoSegnalazione = segnalazione.getStato();

        if (!statoSegnalazione.getStatoToString().equals(StatoType.INVIATA.name())
                && !statoSegnalazione.getStatoToString().equals(StatoType.RISOLTA.name())) {

            //Impossibile aggiungere nota
            System.err.println("[GestoreSegnalazioni] Nota non aggiungibile..\n"+segnalazione.toString());
            return false;
        } else {
            GestoreAggiornamentoStato gestoreAggiornamentoStato = new GestoreAggiornamentoStato();

            Operatore operatore = this.gestorePersistenza.trovaPerId(Operatore.class, idOperatore);

            gestoreAggiornamentoStato.aggiungiNota(operatore, segnalazione, titolo, descrizione);
        }

        System.out.println("[GestoreSegnalazioni] Stato aggiornato correttamente");

        return true;
    }

    public boolean iniziaGestioneSegnalazione(Long idSegnalazione, Long idOperatore) {
        //Bisogna controllare che: 0. la segnalazione esiste, 1. accesso in mutua esclusione, 2. la segnalazione ha stato inviata
        Segnalazione segnalazione = cercaSegnalazione(idSegnalazione);
        if (segnalazione == null) {
            System.err.println("[GestoreSegnalazioni] Nessuna segnalazione trovata..");
            return false;
        }

        System.out.println("[GestoreSegnalazioni] Trovata segnalazione:\n"+segnalazione.toString());


        StatoSegnalazione statoSegnalazione = segnalazione.getStato();

        if (!statoSegnalazione.getStatoToString().equals(StatoType.INVIATA.name())) {
            //Impossibile iniziare la gestione
            System.err.println("[GestoreSegnalazioni] Impossibile prendere in carico la segnalazione, stato:"+statoSegnalazione.getStatoToString());
            return false;
        } else {

            segnalazione.aggiornaStato(true);

        }

        System.out.println("[GestoreSegnalazioni] Gestione iniziata correttamente");

        return true;
    }

    //metodo invocato sia per aggiornare lo stato che per concludere la gestione
    public boolean aggiornaStatoSegnalazione(Long idSegnalazione, Long idOperatore, boolean esito) {
        //Bisogna controllare che: 0. la segnalazione esiste, 1. la segnalazione non ha ne stato inviata ne stato risolta

        Segnalazione segnalazione = cercaSegnalazione(idSegnalazione);
        if (segnalazione == null) {
            System.err.println("[GestoreSegnalazioni] Nessuna segnalazione trovata..");
            return false;
        }

        System.out.println("[GestoreSegnalazioni] Trovata segnalazione:\n"+segnalazione.toString());


        StatoSegnalazione statoSegnalazione = segnalazione.getStato();

        //Se la segnalazione è INVIATA, è RISOLTA
        if (statoSegnalazione.getStatoToString().equals(StatoType.INVIATA.name())
                || statoSegnalazione.getStatoToString().equals(StatoType.RISOLTA.name())) {

            //Impossibile aggiornare stato
            System.err.println("[GestoreSegnalazioni] Stato della segnalazione non aggiornabile..");
            return false;
        } else {

            segnalazione.aggiornaStato(esito);
        }

        System.out.println("[GestoreSegnalazioni] Stato aggiornato correttamente");

        return true;
    }

}