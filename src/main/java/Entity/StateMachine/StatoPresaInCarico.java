package Entity.StateMachine;

import Entity.Enum.StatoType;
import Entity.Segnalazione;

/**
 * Stato concreto del subject: rappresenta lo stato in cui la segnalazione è in gestione dall'operatore
 */

public class StatoPresaInCarico extends StatoSegnalazione{

    //Costruttore
    public StatoPresaInCarico() {}

    //Metodi

    /**
     * Metodo invocato per aggiornare lo stato della segnalazione.
     *
     * @param segnalazione riferimento alla segnalazione usato per settare il nuovo stato
     * @param esito booleano usato per distinguere l'effetto dell'azione
     * @return true se l'azione è andata a buon fine, false altrimenti
     */

    @Override
    public boolean aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoPresaInCarico] Invocato aggiornaStato con esito: " + esito);

        if (esito == true) {
            //Avanzo di stato..
            System.out.println("[StatoPresaInCarico] Avanzamento di stato..");
            segnalazione.setStato(new StatoInLavorazione());
        } else {
            //Ritorno a StatoInviata
            System.out.println("[StatoPresaInCarico] Reset stato..");
            segnalazione.setStato(new StatoInviata());
        }

        return true;

    }

    @Override
    public String getStatoToString() {
        return StatoType.PRESA_IN_CARICO.name();
    }

}
