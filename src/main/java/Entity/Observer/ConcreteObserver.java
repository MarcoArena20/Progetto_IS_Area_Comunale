package Entity.Observer;

import Entity.Gestori.GestoreAggiornamentoStato;
import Entity.Segnalazione;
import Entity.StateMachine.StatoSegnalazione;

/**
 * Classe che implementa l'interfaccia Observer; rappresenta l'osservatore concreto responsabile di gestire la logica
 * scatenata dall'evento. E' stato scelto di usare il pattern Singleton in modo da garantire l'accesso controllato ad un'unica instanza,
 * "attached" al subject all'ottenimento del riferimento all'oggetto da parte del GestorePersistenza.
 */

public class ConcreteObserver implements Observer {

    //Attributi
    /**
     * Pattern Singleton
     */
    private static ConcreteObserver instance;

    //Costruttore
    private ConcreteObserver() {

    }

    /**
     * Getter con questa struttura garantisce l'esistenza di un'unica instanza dell'observer
     */

    public static ConcreteObserver getInstance() {
        if (instance == null)
            instance = new ConcreteObserver();

        return instance;
    }

    /**
     * metodo che gestisce la logica scatenata dall'evento: ovvero, al cambio stato del subject (evento), l'observer invoca
     * il GestoreAggiornamentoStato per salvare su database i cambiamenti (in termini di stato della segnalazione,mapping temporale
     * degli aggiornamenti di stato e mapping temporale delle gestioni degli operatori)
     *
     * @param segnalazione riferimento alla segnalazione da aggiornare
     * @param newStato nuovo stato della segnalazione, in accordo al push model
     * @return true se l'aggiornamento è andato a buon fine, false altrimenti
     */

    @Override
    public boolean update(Segnalazione segnalazione, StatoSegnalazione newStato) {
        System.out.println("[Observer] Stato aggiornato:\n"+segnalazione.toString());
        System.out.println("[Observer] Contatto db..");

        GestoreAggiornamentoStato gestoreAggiornamento = new GestoreAggiornamentoStato();


        return gestoreAggiornamento.aggiornaStato(segnalazione, newStato);
    }


}
