package Entity.Observer;

import Entity.Segnalazione;
import Entity.StateMachine.StatoSegnalazione;

/**
 * Classe astratta ereditata dal subject; possiede i metodi utilizzati nell'applicazione del design pattern Observer (push model)
 */

public abstract class ObserverSegnalazione {

    //Attributi

    /**
     * Osservatore responsabile di gestire l'evento "cambio di stato"
     */

    private Observer observer;


    //Metodi concreti per pattern Observer
    public void attach(Observer obsv) {
        this.observer = obsv;
    }

    public void detach () {
        this.observer = null;
    }

    /**
     * Metodo invocato dal subject al cambio di stato
     *
     * @param segnalazione riferimento alla segnalazione da aggiornare
     * @param newState nuovo stato della segnalazione, in accordo al push model
     * @return true se l'aggiornamento è andato a buon fine, false se l'observer è assente (e quindi impossibile notificare dell'evento avvenuto)
     */

    protected boolean notifyObserver(Segnalazione segnalazione, StatoSegnalazione newState) {
        if (this.observer != null) {

            this.observer.update(segnalazione, newState);
            return true;

        } else {

            System.out.println("[Observer] Assente l'osservatore da notificare!");
            return false;

        }
    }
}
