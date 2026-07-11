package Entity.Observer;

import Entity.Segnalazione;
import Entity.StateMachine.StatoSegnalazione;

public abstract class ObserverSegnalazione {

    //Attributi
    private Observer observer;


    //Metodi concreti per pattern Observer
    public void attach(Observer obsv) {
        this.observer = obsv;
    }

    public void detach () {
        this.observer = null;
    }

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
