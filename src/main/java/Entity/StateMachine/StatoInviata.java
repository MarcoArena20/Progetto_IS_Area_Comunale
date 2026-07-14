package Entity.StateMachine;

import Entity.Enum.StatoType;
import Entity.Segnalazione;

/**
 * Stato concreto del subject: rappresenta lo stato iniziale
 */

public class StatoInviata extends StatoSegnalazione {

    //Costruttore
    public StatoInviata() {}

    //Metodi

    /**
     * Metodo invocato per aggiornare lo stato della segnalazione (se legittimo).
     * Da notare che l'azione di aggiornamento "negativo" non ha effetto in questo stato
     *
     * @param segnalazione riferimento alla segnalazione usato per settare il nuovo stato
     * @param esito booleano usato per distinguere la validità dell'azione
     * @return true se l'azione è legittima ed è andata a buon fine, false altrimenti
     */

    @Override
    public boolean aggiornaStato(Segnalazione segnalazione, boolean esito) {
        System.out.println("[StatoInviata] Invocato aggiornaStato con esito: " + esito);

        if (esito == true) {
            //Avanzo di stato..
            System.out.println("[StatoInviata] Avanzamento di stato..");

            //Aggiorno stato
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
        return StatoType.INVIATA.name();
    }


    public static void main(String[] args) {
        System.out.println("[StatoInviata] MainTest avviato..");

        StatoInviata statoTest = new StatoInviata();

        System.out.println("[StatoInviata] getStato: "+ statoTest.getStatoToString());
        System.out.println("[StatoInviata] toString: "+ statoTest.toString());


    }

}
