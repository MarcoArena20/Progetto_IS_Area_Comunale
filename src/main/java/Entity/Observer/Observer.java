package Entity.Observer;

import Entity.Segnalazione;
import Entity.StateMachine.StatoSegnalazione;

public interface Observer {

    //Overload del metodo update per consentire di salvare cose diverse sulla base dell'aggiornamento che viene fatto
    boolean update(Segnalazione segnalazione, StatoSegnalazione newStato);

}
