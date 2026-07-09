package Entity;

public class StatoInviata implements StatoSegnalazione {

    //Costruttore
    public StatoInviata() {}

    //Metodi
    @Override
    public boolean aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoInviata] Invocato aggiornaStato con esito: " + esito);

        if (esito == true) {
            //Avanzo di stato..
            System.out.println("[StatoInviata] Avanzamento di stato..");
            segnalazione.setStato(new StatoPresaInCarico());
            return true;

        } else {
            //Operazione non ha effetto
            System.out.println("[StatoInviata] Aggiornamento non ha effetto..");
            return false;//Operazione non consentita anche se non ha effetto
        }


    }

    @Override
    public String getStatoToString() {
        return "StatoInviata";
    }

    @Override
    public String toString() {
        return "Stato{\n" +
                "statoCorrente=" + this.getStatoToString() + '\n' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("[StatoInviata] MainTest avviato..");

        StatoInviata statoTest = new StatoInviata();

        System.out.println("[StatoInviata] getStato: "+ statoTest.getStatoToString());
        System.out.println("[StatoInviata] toString: "+ statoTest.toString());


    }

}
