package Entity.StateMachine;

import Entity.Segnalazione;

/**
 * Classe astratta utilizzata per applicare il pattern State: in questo modo, il Context (classe principale) è disaccoppiato
 * dalla logica di aggiornamento dello stato
 */

public abstract class StatoSegnalazione {

    //Metodi

    /**
     * Metodo invocato per aggiornare lo stato della segnalazione.
     * Da notare che sono gli stati concreti a verificare la correttezza dell'operazione eseguita
     *
     * @param segnalazione riferimento alla segnalazione usato per settare il nuovo stato
     * @param esito booleano usato per distinguere avanzamento positivo o negativo (ritornando allo stato iniziale)
     * @return true se l'azione è legittima ed è andata a buon fine, false altrimenti
     */

    public abstract boolean aggiornaStato(Segnalazione segnalazione, boolean esito);

    public abstract String getStatoToString();

    @Override
    public String toString() {
        return "Stato: " + this.getStatoToString();
    }
}
