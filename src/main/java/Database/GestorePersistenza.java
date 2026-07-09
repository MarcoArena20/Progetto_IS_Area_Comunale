package Database;

//Façade
public class GestorePersistenza {

    //Attributi
    private static GestorePersistenza instance; //Singleton

    //Costruttori
    private GestorePersistenza() {}

    public static synchronized GestorePersistenza getInstance() {
        if (instance == null) {
            instance = new GestorePersistenza();
        }
        return instance;
    }

    // Metodi
    //TODO
}