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

/**
 * E' una façade utilizzata per gestire aggiornamento stato delle segnalazioni
 */


public class GestoreAggiornamentoStato {

    //Attributi

    /**
     * attributo che si riferisce al gestorePersistenza in modo da poter salvare gli aggiornamenti degli stati su database
     * ed eventualmente effettuare query
     */

    private GestorePersistenza gestorePersistenza;

    //Costruttore

    /**
     * Costruttore per creare ed inizializzare il gestore
     */

    public GestoreAggiornamentoStato() {
        this.gestorePersistenza = new GestorePersistenza();
    }

    //Metodi

    /**
     * CASO D'USO: iniziaGestioneSegnalazione
     * Invocato all'aggiornamento di una segnalazione in "Presa in carico", il metodo invoca il gestore persistenza in modo
     * da salvare su database una nuova gestione attiva
     *
     * @param operatore riferimento all'operatore che ha preso in carico quella segnalazione
     * @param segnalazione riferimento alla segnalazione che è stata presa in carico
     * @return true se la gestione è stata salvata correttamente, false altrimenti
     */

    private boolean salvaOperatore(Operatore operatore, Segnalazione segnalazione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di salva operatore");

        GestioneOperatoreEntry gestioneOperatoreEntry = new GestioneOperatoreEntry(operatore, segnalazione, true, null, null);

        boolean esito = this.gestorePersistenza.salva(gestioneOperatoreEntry);

        System.out.println("[GestoreAggiornamentoStato] Salvato operatore:\n"+ operatore.toString());


        return esito;
    }

    /**
     * CASO D'USO: iniziaGestioneSegnalazione, aggiornaStatoSegnalazione, concludiGestioneSegnalazione
     * Invocato all'aggiornamento di stato di una segnalazione da parte dell'observer "attached" alla segnalazione,
     * recupera l'id dell'operatore e invoca il gestorePersistenza per
     * 1. Salvare il cambiamento di stato della segnalazione
     * 2. Aggiornare lo stato della segnalazione
     * 3. Verificare nuovo stato
     *      3.1 se lo stato è "Presa in carico", bisogna salvare la nuova gestione @see salvaOperatore
     *      3.2 se lo stato è "Inviata" o "Risolta", bisogna concludere la gestione @see concludiGestione
     *
     * @param segnalazione riferimento alla segnalazione che è stata aggiornata
     * @param statoSegnalazione stato successivo all'aggiornamento
     * @return true se la gestione è stata aggiornata correttamente, false altrimenti
     */

    public boolean aggiornaStato(Segnalazione segnalazione, StatoSegnalazione statoSegnalazione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di aggiorna stato");

        System.out.println("[GestoreAggiornamentoStato] Segnalazione da aggiornare:\n"+segnalazione.toString());
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();

        //Non è necessario verificare il ruolo poiché l'observer verrà aggiunto all'istanziazione della segnalazione

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

    /**
     * CASO D'USO: concludiGestioneSegnalazione
     * Invocato all'aggiornamento di una segnalazione in "Inviata" o "Risolta", il metodo invoca il gestore persistenza in modo
     * da cercare l'ultima gestione operatore-->segnalazione e aggiornare il flag "attiva" a false
     *
     * @param operatore riferimento all'operatore che sta gestendo quella segnalazione
     * @param segnalazione riferimento alla segnalazione in gestione
     * @return true se la gestione esiste ed è stata conclusa correttamente, false altrimenti
     */

    private boolean concludiGestione(Operatore operatore, Segnalazione segnalazione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di concludi gestione");

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

    /**
     * CASO D'USO: aggiungiNotaInterna
     * Invocato dal ControllerSegnalazioni se si vuole aggiungere la nota, il metodo invoca il gestore persistenza in modo
     * da cercare l'ultima gestione operatore-->segnalazione e aggiornare il titolo e la descrizione della nota
     *
     * @param operatore riferimento all'operatore che sta gestendo quella segnalazione
     * @param segnalazione riferimento alla segnalazione in gestione
     * @param titolo titolo della nota
     * @param descrizione descrizione della nota
     * @return true se la gestione esiste ed è stata salvata correttamente la nota, false altrimenti
     */

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

    /**
     * CASO D'USO: aggiornaStatoSegnalazione, concludiGestioneSegnalazione
     * Invocato dal ControllerSegnalazioni per verificare i permessi dell'operatore, il metodo invoca il gestore persistenza
     * per verificare che l'ultima (e unica) gestione attiva della segnalazione sia effettuata dall'operatore specificato in ingresso
     *
     * @param idOperatore id dell'operatore
     * @param idSegnalazione id della segnalazione
     * @return true se l'operatore sta gestendo la segnalazione , false altrimenti
     */

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

    /**
     * CASO D'USO: aggiornaStatoSegnalazione, concludiGestioneSegnalazione
     * Invocato dal GestoreAggiornamentoStato per ricercare l'ultima gestione operatore-->segnalazione, invoca il gestorePersistenza
     * per ricavare la lista delle gestioni e restituisce l'ultimo elemento
     *
     * @param operatore riferimento all'operatore
     * @param segnalazione riferimento alla segnalazione
     * @return entry rappresentante l'ultima gestione, null se non esiste
     */

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

    /**
     * CASO D'USO: aggiornaStatoSegnalazione, concludiGestioneSegnalazione
     * Invocato dal GestoreAggiornamentoStato per ricercare l'ultima gestione attiva di una segnalazionesegnalazione,
     * invoca il gestorePersistenza ricavando una lista di entry: se la lista è vuota o ha più di un elemento, ritorna null
     *
     * @param segnalazione riferimento alla segnalazione
     * @return entry rappresentante l'unica gestione attiva, null se non esiste o se ci sono errori (due gestioni attive della stessa segnalazione)
     */

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

        return listaGestioni.get(0);//primo e unico elemento
    }
}