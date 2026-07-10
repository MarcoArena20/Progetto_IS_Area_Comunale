package Entity;

import Controller.ControllerUtenti;
import Database.GestorePersistenza;

public class GestoreAggiornamentoStato {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    //Costruttore
    public GestoreAggiornamentoStato() {
        this.gestorePersistenza = new GestorePersistenza();
    }

    //Metodi
    public boolean salvaOperatore(Operatore operatore, Segnalazione segnalazione) {
        System.out.println("[GestoreAggiornamentoStato] Invocato metodo di salva operatore");

        GestioneOperatoreEntry gestioneOperatoreEntry = new GestioneOperatoreEntry(operatore, segnalazione, true, null, null);

        boolean esito = this.gestorePersistenza.salva(gestioneOperatoreEntry);

        System.out.println("[GestoreAggiornamentoStato] Salvato operatore:\n"+ operatore.toString());


        return true;
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


        return esito1 && esito2;
    }

    public boolean concludiGestione(boolean esito, String titolo, String descrizione) {

        return true;
    }

}
