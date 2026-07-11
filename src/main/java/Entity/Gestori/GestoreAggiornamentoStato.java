package Entity.Gestori;

import Controller.ControllerUtenti;
import Database.GestorePersistenza;
import Entity.*;
import Entity.EntryDB.AggiornamentoStatoEntry;
import Entity.EntryDB.GestioneOperatoreEntry;
import Entity.StateMachine.StatoSegnalazione;
import Entity.Enum.StatoType;

import java.util.List;
import java.util.Map;


public class GestoreAggiornamentoStato {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    //Costruttore
    public GestoreAggiornamentoStato() {
        this.gestorePersistenza = new GestorePersistenza();
    }

    //Metodi
    private boolean salvaOperatore(Operatore operatore, Segnalazione segnalazione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di salva operatore");

        GestioneOperatoreEntry gestioneOperatoreEntry = new GestioneOperatoreEntry(operatore, segnalazione, true, null, null);

        boolean esito = this.gestorePersistenza.salva(gestioneOperatoreEntry);

        System.out.println("[GestoreAggiornamentoStato] Salvato operatore:\n"+ operatore.toString());


        return esito;
    }

    public boolean aggiornaStato(Segnalazione segnalazione, StatoSegnalazione statoSegnalazione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di aggiorna stato");

        System.out.println("[GestoreAggiornamentoStato] Segnalazione da aggiornare:\n"+segnalazione.toString());
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();
        //TODO Non è necessario verificare il ruolo poiché l'observer verrà aggiunto al visualizza dettaglio da parte dell'operatore

        Operatore operatore = this.gestorePersistenza.trovaPerId(Operatore.class, idOperatore);

        AggiornamentoStatoEntry aggiornamentoStatoEntry = new AggiornamentoStatoEntry(segnalazione, statoSegnalazione, operatore);

        boolean esito1 = this.gestorePersistenza.salva(aggiornamentoStatoEntry);
        Segnalazione segnalazione_aggiornata = this.gestorePersistenza.aggiorna(segnalazione);
        System.out.println("[GestoreAggiornamentoStato] Segnalazione aggiornata:\n"+segnalazione_aggiornata.toString());


        boolean esito2 = false;//TODO verificare bene logica
        if (statoSegnalazione.getStatoToString().equals(StatoType.PRESA_IN_CARICO.name()))
            esito2 = salvaOperatore(operatore, segnalazione);
        else if (statoSegnalazione.getStatoToString().equals(StatoType.RISOLTA.name())
                    || statoSegnalazione.getStatoToString().equals(StatoType.INVIATA.name()))
            esito2 = concludiGestione(operatore, segnalazione);


        return esito1 && esito2;
    }

    private boolean concludiGestione(Operatore operatore, Segnalazione segnalazione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di concludi gestione");

        /*
        Map<String, Object> filter = Map.of(
                "attiva", true,
                "operatore", operatore,
                "segnalazione", segnalazione
        );

        List<GestioneOperatoreEntry> listaGestioni = this.gestorePersistenza.cercaPerCampi(GestioneOperatoreEntry.class, filter);

        if (listaGestioni == null) {
            System.err.println("[GestoreAggiornamentoStato] Errore nella chiusura della gestione!");
            return false;
        }

        for (GestioneOperatoreEntry gestioneOperatoreEntry: listaGestioni) {
            gestioneOperatoreEntry.setAttiva(false);

            this.gestorePersistenza.aggiorna(gestioneOperatoreEntry);
        }

        */

        GestioneOperatoreEntry gestioneOperatoreEntry = cercaUltimaGestioneOperatoreSegnalazione(operatore, segnalazione);

        if (gestioneOperatoreEntry == null) {
            return false;
        }

        gestioneOperatoreEntry.setAttiva(false);

        this.gestorePersistenza.aggiorna(gestioneOperatoreEntry);

        System.out.println("[GestoreAggiornamentoStato] Conclusa gestione operatore:\n"+ operatore.toString()+
                "\n|" +
                "\nv" +
                "\n"+segnalazione.toString());

        return true;
    }

    public boolean aggiungiNota(Operatore operatore, Segnalazione segnalazione, String titolo, String descrizione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di aggiungi nota");

        GestioneOperatoreEntry gestioneOperatoreEntry = cercaUltimaGestioneOperatoreSegnalazione(operatore, segnalazione);

        if (gestioneOperatoreEntry == null) {
            return false;
        }

        gestioneOperatoreEntry.setTitolo(titolo);
        gestioneOperatoreEntry.setDescrizione(descrizione);

        this.gestorePersistenza.aggiorna(gestioneOperatoreEntry);

        System.out.println("[GestoreAggiornamentoStato] Salvato operatore:\n"+ operatore.toString());
        return true;
    }

    public boolean verificaOperatoreInGestioneCorrente(Long idOperatore, Long idSegnalazione) {

        //Ottengo riferimenti
        Operatore operatore = this.gestorePersistenza.trovaPerId(Operatore.class, idOperatore);
        Segnalazione segnalazione = this.gestorePersistenza.trovaPerId(Segnalazione.class, idSegnalazione);

        GestioneOperatoreEntry gestioneOperatoreEntry = cercaUltimaGestioneAttivaSegnalazione(segnalazione);

        if (gestioneOperatoreEntry == null) {
            return false;
        }

        return operatore.getIdOperatore().equals(gestioneOperatoreEntry.getOperatore().getIdOperatore());
    }

    private GestioneOperatoreEntry cercaUltimaGestioneOperatoreSegnalazione(Operatore operatore, Segnalazione segnalazione) {
        Map<String, Object> filter = Map.of(
                "operatore", operatore,
                "segnalazione", segnalazione
        );

        List<GestioneOperatoreEntry> listaGestioni = this.gestorePersistenza.cercaPerCampi(GestioneOperatoreEntry.class, filter);

        if (listaGestioni.isEmpty()) {
            System.err.println("[GestoreAggiornamentoStato] Impossibile trovare la gestione desiderata");
            return null;
        }

        return listaGestioni.get(listaGestioni.size()-1);
    }

    private GestioneOperatoreEntry cercaUltimaGestioneAttivaSegnalazione(Segnalazione segnalazione) {
        Map<String, Object> filter = Map.of(
                "attiva" , true,
                "segnalazione", segnalazione
        );

        List<GestioneOperatoreEntry> listaGestioni = this.gestorePersistenza.cercaPerCampi(GestioneOperatoreEntry.class, filter);

        if (listaGestioni.isEmpty()) {
            System.err.println("[GestoreAggiornamentoStato] Impossibile trovare la gestione desiderata");
            return null;
        } else if (listaGestioni.size()>1) {
            System.err.println("[GestoreAggiornamentoStato] ERRORE, DUE OPERATORI STANNO GESTENDO LA STESSA SEGNALAZIONE");
            return null;
        }

        //listaGestioni contiene una entry

        return listaGestioni.get(listaGestioni.size()-1);//primo e ultimo elemento
    }

}