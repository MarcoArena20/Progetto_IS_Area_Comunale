package Entity.StateMachine;

import Entity.Enum.StatoType;
import Entity.Segnalazione;

/**
 * Stato concreto del subject: rappresenta lo stato finale
 */

public class StatoRisolta extends StatoSegnalazione {

    //Costruttore
    public StatoRisolta() {}

    //Metodi

    /**
     * Metodo che non ha effetto.
     *
     * @param segnalazione riferimento alla segnalazione usato per settare il nuovo stato
     * @param esito booleano usato per distinguere l'effetto dell'azione
     * @return false in ogni caso vista l'invalidità dell'operazione aggiorna nello stato finale
     */

    @Override
    public boolean aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoRisolta] Impossibile modificare lo stato\n" + this.toString());
        return false;
    }

    @Override
    public String getStatoToString() {
        return StatoType.RISOLTA.name();
    }

}
