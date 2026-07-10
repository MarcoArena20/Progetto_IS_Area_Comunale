package Entity;

import Controller.ControllerSegnalazioni;
import Controller.ControllerUtenti;
import Database.GestorePersistenza;

public class GestoreAggiornamento {

    //Attributi
    private GestorePersistenza gestorePersistenza;

    //Costruttore
    public GestoreAggiornamento() {
        this.gestorePersistenza = new GestorePersistenza();
    }

    //Metodi
    public boolean aggiornaStato(Segnalazione segnalazione, StatoSegnalazione statoSegnalazione) {
        System.out.println("[GestoreAggiornamento] Invocato metodo di aggiorna stato");

        System.out.println("[GestoreAggiornamento] Segnalazione da aggiornare:\n"+segnalazione.toString());
        Long idOperatore = ControllerUtenti.getIdUtenteCorrente();
        //Non è necessario verificare il ruolo poiché l'observer verrà aggiunto al visualizza dettaglio da parte dell'operatore

        Operatore operatore = this.gestorePersistenza.trovaPerId(Operatore.class, idOperatore);

        AggiornamentoStatoEntry aggiornamentoStatoEntry = new AggiornamentoStatoEntry(segnalazione, statoSegnalazione, operatore, null);

        boolean esito1 = this.gestorePersistenza.salva(aggiornamentoStatoEntry);
        Segnalazione segnalazione_aggiornata = this.gestorePersistenza.aggiorna(segnalazione);
        System.out.println("[GestoreAggiornamento] Segnalazione aggiornata:\n"+segnalazione_aggiornata.toString());

        return esito1;
    }

}
