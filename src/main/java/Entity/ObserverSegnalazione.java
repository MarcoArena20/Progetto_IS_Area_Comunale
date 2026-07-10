package Entity;

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

    protected boolean notifyObserver(Long idSegnalazione, StatoSegnalazione newState) {
        if (this.observer != null) {

            this.observer.update(idSegnalazione, newState);
            return true;

        } else {

            System.out.println("[Observer] Assente l'osservatore da notificare!");
            return false;

        }
    }
}
