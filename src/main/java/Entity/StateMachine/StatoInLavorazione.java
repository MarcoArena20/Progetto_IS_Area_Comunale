package Entity.StateMachine;

import Entity.Enum.StatoType;
import Entity.Segnalazione;

/**
 * Stato concreto del subject: rappresenta l'ultimo stato aggiornabile
 */

public class StatoInLavorazione extends StatoSegnalazione {

    //Costruttore
    public StatoInLavorazione() {}

    //Metodi

    /**
     * Metodo invocato per aggiornare lo stato della segnalazione.
     * Da notare che un aggiornamento positivo, porta alla risoluzione della segnalazione;
     *               un aggiornamento negativo, porta al passaggio allo stato iniziale;
     *
     * @param segnalazione riferimento alla segnalazione usato per settare il nuovo stato
     * @param esito booleano usato per distinguere l'effetto dell'azione
     * @return true se l'azione è andata a buon fine, false altrimenti
     */

    @Override
    public boolean aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoInLavorazione] Invocato aggiornaStato con esito: " + esito);

        if (esito == true) {
            //Avanzo di stato..
            System.out.println("[StatoInLavorazione] Avanzamento di stato..");
            segnalazione.setStato(new StatoRisolta());
        } else {
            //Ritorno a StatoInviata
            System.out.println("[StatoInLavorazione] Reset stato..");
            segnalazione.setStato(new StatoInviata());
        }

        return true;
    }

    @Override
    public String getStatoToString() {
        return StatoType.IN_LAVORAZIONE.name();
    }


}
