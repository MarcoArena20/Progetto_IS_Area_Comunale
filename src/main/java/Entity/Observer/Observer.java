package Entity.Observer;

import Entity.Segnalazione;
import Entity.StateMachine.StatoSegnalazione;

/**
 * Interfaccia Observer, dichiarata in modo da disaccoppiare il subject dal ConcreteObserver
 */

public interface Observer {

    /**
     * Metodo invocato dal subject al verificarsi dell'evento, ha la responsabilità di invocare il gestoreAggiornamentoStato
     * per aggiornare il database
     *
     * @param segnalazione riferimento alla segnalazione da aggiornare
     * @param newStato nuovo stato della segnalazione, in accordo al push model
     * @return true se l'aggiornamento è andato a buon fine, false altrimenti
     */

    boolean update(Segnalazione segnalazione, StatoSegnalazione newStato);

}
