package Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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

    public <T> List<T> cercaPerCampo(Class<T> classe,
                                     String nomeCampo,
                                     Object valore) {

        return cercaPerCampi(
                classe,
                Map.of(nomeCampo, valore)
        );
    }

    public <T> List<T> cercaPerCampi(Class<T> classe, Map<String, Object> campi) {

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {
            StringBuilder jpql = new StringBuilder();

            jpql.append("SELECT e FROM ")
                    .append(classe.getSimpleName())
                    .append(" e");

            if (!campi.isEmpty()) {
                jpql.append(" WHERE ");

                int contatore = 0;

                for (String nomeCampo : campi.keySet()) {
                    if (contatore > 0) {
                        jpql.append(" AND ");
                    }

                    String nomeParametro = nomeCampo.replace(".", "_");

                    jpql.append("e.")
                            .append(nomeCampo)
                            .append(" = :")
                            .append(nomeParametro);

                    contatore++;
                }
            }

            TypedQuery<T> query = em.createQuery(
                    jpql.toString(),
                    classe
            );

            for (String nomeCampo : campi.keySet()) {
                String nomeParametro = nomeCampo.replace(".", "_");
                query.setParameter(nomeParametro, campi.get(nomeCampo));
            }

            return query.getResultList();

        } finally {
            em.close();
        }
    }

}