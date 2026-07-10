package Entity;

public class ConcreteObserver implements Observer {

    //Attributi
    private final String nameObserver;

    //Costruttore
    public ConcreteObserver(String name) {
        this.nameObserver = name;
    }

    @Override
    public boolean update(Segnalazione segnalazione, StatoSegnalazione newStato) {
        System.out.println("["+this.nameObserver+"] Stato aggiornato:\n"+segnalazione.toString());
        System.out.println("["+this.nameObserver+"] Contatto db..");

        GestoreAggiornamento gestoreAggiornamento = new GestoreAggiornamento();
        boolean esito = gestoreAggiornamento.aggiornaStato(segnalazione, newStato);


        return esito;
    }


}
