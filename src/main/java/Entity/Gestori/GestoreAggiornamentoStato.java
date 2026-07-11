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
        //Non è necessario verificare il ruolo poiché l'observer verrà aggiunto al visualizza dettaglio da parte dell'operatore

        Operatore operatore = this.gestorePersistenza.trovaPerId(Operatore.class, idOperatore);

        AggiornamentoStatoEntry aggiornamentoStatoEntry = new AggiornamentoStatoEntry(segnalazione, statoSegnalazione, operatore);

        boolean esito1 = this.gestorePersistenza.salva(aggiornamentoStatoEntry);
        Segnalazione segnalazione_aggiornata = this.gestorePersistenza.aggiorna(segnalazione);
        System.out.println("[GestoreAggiornamentoStato] Segnalazione aggiornata:\n"+segnalazione_aggiornata.toString());


        boolean esito2 = false;
        if (statoSegnalazione.getStatoToString().equals(StatoType.PRESA_IN_CARICO.name()))
            esito2 = salvaOperatore(operatore, segnalazione);
        else if (statoSegnalazione.getStatoToString().equals(StatoType.RISOLTA.name())
                    || statoSegnalazione.getStatoToString().equals(StatoType.INVIATA.name()))
            esito2 = concludiGestione(operatore, segnalazione);


        return esito1 && esito2;
    }

    private boolean concludiGestione(Operatore operatore, Segnalazione segnalazione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di concludi gestione");

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

        System.out.println("[GestoreAggiornamentoStato] Salvato operatore:\n"+ operatore.toString());


        return true;
    }

    public boolean aggiungiNota(Operatore operatore, Segnalazione segnalazione, String titolo, String descrizione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di aggiungi nota");

        Map<String, Object> filter = Map.of(
                "operatore", operatore,
                "segnalazione", segnalazione
        );

        List<GestioneOperatoreEntry> listaGestioni = this.gestorePersistenza.cercaPerCampi(GestioneOperatoreEntry.class, filter);

        if (listaGestioni == null) {
            System.err.println("[GestoreAggiornamentoStato] Errore nell'aggiunta della nota!");
            return false;
        }

        GestioneOperatoreEntry gestioneOperatoreEntry = listaGestioni.get(listaGestioni.size()-1);
        gestioneOperatoreEntry.setTitolo(titolo);
        gestioneOperatoreEntry.setDescrizione(descrizione);

        this.gestorePersistenza.aggiorna(gestioneOperatoreEntry);

        System.out.println("[GestoreAggiornamentoStato] Salvato operatore:\n"+ operatore.toString());
        return true;
    }

}