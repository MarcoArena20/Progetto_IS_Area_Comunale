package Entity;

public class StatoRisolta extends StatoSegnalazione {

    //Costruttore
    public StatoRisolta() {}

    //Metodi
    @Override
    public boolean aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoRisolta] Impossibile modificare lo stato\n" + this.toString());
        return false;
    }

    @Override
    public String getStatoToString() {
        return StatoType.RISOLTA.name();
    }

}
