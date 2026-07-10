package Entity;

public interface Observer {

    //Overload del metodo update per consentire di salvare cose diverse sulla base dell'aggiornamento che viene fatto
    boolean update(Long idSegnalazione, StatoSegnalazione newStato);

}
