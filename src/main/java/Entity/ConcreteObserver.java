package Entity;

public class ConcreteObserver implements Observer {

    //Attributi
    private final String nameObserver;

    //Costruttore
    public ConcreteObserver(String name) {
        this.nameObserver = name;
    }

    @Override
    public boolean update(Long idSegnalazione, StatoSegnalazione newStato) {
        System.out.println("["+this.nameObserver+"] Stato aggiornato:\nSegnalazione: "+idSegnalazione+"\tStato: "+newStato);
        System.out.println("["+this.nameObserver+"] Contatto db..");

        //TODO

        return true;
    }


}
