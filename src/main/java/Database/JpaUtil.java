package Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static JpaUtil instance;
    private final EntityManagerFactory emf;

    private JpaUtil(){

        emf = Persistence.createEntityManagerFactory("AreaComunale");
    }

    public static JpaUtil getInstance(){

        if (instance == null)
            instance = new JpaUtil();

        return instance;
    }

    public EntityManager getEntityManager(){

        return emf.createEntityManager();

    }

    public void chiudi(){

        emf.close();

    }

}
