package Entity.Observer;

import Entity.Gestori.GestoreAggiornamentoStato;
import Entity.Segnalazione;
import Entity.StateMachine.StatoSegnalazione;

public class ConcreteObserver implements Observer {

    //Attributi
    private static ConcreteObserver instance;

    //Costruttore
    private ConcreteObserver() {

    }

    public static ConcreteObserver getInstance() {
        if (instance == null)
            instance = new ConcreteObserver();

        return instance;
    }

    @Override
    public boolean update(Segnalazione segnalazione, StatoSegnalazione newStato) {
        System.out.println("[Observer] Stato aggiornato:\n"+segnalazione.toString());
        System.out.println("[Observer] Contatto db..");

        GestoreAggiornamentoStato gestoreAggiornamento = new GestoreAggiornamentoStato();
        boolean esito = gestoreAggiornamento.aggiornaStato(segnalazione, newStato);


        return esito;
    }


}
