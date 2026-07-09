package Database;

import jakarta.persistence.EntityManager;

//Façade
public class GestorePersistenza {

    public boolean salva(Object oggetto){

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try{

            em.getTransaction().begin();
            em.persist(oggetto);
            em.getTransaction().commit();

            return true;
        }catch(RuntimeException e){

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            //throw e;
            e.printStackTrace();
            return false;

        }finally{

            em.close();

        }

    }

}