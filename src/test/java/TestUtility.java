import Database.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TestUtility {

    static void puliziaDatabase(){

        // Andiamo a ripulire le tabelle di cittadino e segnalazione di test in modo da conoscere lo stato iniziale

        EntityManager em = JpaUtil.getInstance().getEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        em.createQuery("DELETE FROM AggiornamentoStatoEntry").executeUpdate();
        em.createQuery("DELETE FROM GestioneOperatoreEntry").executeUpdate();
        em.createQuery("DELETE FROM Segnalazione").executeUpdate();
        em.createQuery("DELETE FROM Cittadino").executeUpdate();
        em.createQuery("DELETE FROM Operatore").executeUpdate();
        tx.commit();

        em.close();
    }

}
