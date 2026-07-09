package Entity;

public interface StatoSegnalazione {

    //Metodi
    public abstract boolean aggiornaStato(boolean esito);
    public abstract String getStato();
}
