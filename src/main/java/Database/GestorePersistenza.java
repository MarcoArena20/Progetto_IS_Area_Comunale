package Database;

import Entity.Cittadino;
import Entity.Operatore;
import Entity.Ruolo;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;

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

   /* public boolean cercaUtenteNelDB(String email, String passwordHash, Ruolo ruolo) {

        Map<String, Object> criteriRicerca = Map.of( "email", email,
                "password",passwordHash,
                "ruolo", ruolo);

        if (ruolo == Ruolo.CITTADINO){
            List<Cittadino> query = GestorePersistenza.cercaPerCampi(Cittadino.class, criteriRicerca);
            return !query.isEmpty();
        }
        // aggiungo un else if per verficare che ruolo sia proprio operatore comunale??
        else{
            List<Operatore> query = GestorePersistenza.cercaPerCampi(Operatore.class, criteriRicerca);
            return  !query.isEmpty();
        }

    }
    private List<T> cercaPerCampi(Class<T> classe, Map<String, Object> criteri){
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try{
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT u FROM").append(classe.getSimpleName()).append(" u");

            if (!criteri.isEmpty()){
                jpql.append(" WHERE ");
                int count =0;

                for (String nomeCampo: criteri.keySet()){
                    if (count>0){
                        jpql.append(" AND ");
                    }
                    String nomeParametro = nomeCampo.replace(".","-");

                    jpql.append("u.").append(nomeCampo).append(" = :").append(nomeParametro);
                    count++;
                }
                //query generata SELECT e FROM Cittadino e WHERE e.email = :email AND e.password = :password
            }
        }
    }

    */


}