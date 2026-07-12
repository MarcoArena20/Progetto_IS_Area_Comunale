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

    public <T> T trovaPerId(Class<T> classe, Long id) {

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {
            /*
             * find cerca nel database una riga della tabella associata
             * alla classe indicata, usando l'id come chiave primaria.
             */
            return em.find(classe, id);

        } finally {
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

                    boolean like = nomeCampo.startsWith("LIKE:");

                    String campoReale = like
                            ? nomeCampo.substring(5)
                            : nomeCampo;

                    String nomeParametro = campoReale.replace(".", "");

                    if (contatore > 0) {
                        jpql.append(" AND ");
                    }

                    jpql.append("e.")
                            .append(campoReale)
                            .append(like ? " LIKE :" : " = :")
                                    .append(nomeParametro);

                    contatore++;
                }
            }

            TypedQuery<T> query = em.createQuery(
                    jpql.toString(),
                    classe
            );

            for (String nomeCampo : campi.keySet()) {

                boolean like = nomeCampo.startsWith("LIKE:");

                String campoReale = like
                        ? nomeCampo.substring(5)
                        : nomeCampo;

                String nomeParametro = campoReale.replace(".", "");

                Object valore = campi.get(nomeCampo);

                if (like && valore instanceof String) {
                    valore = "%" + valore + "%";
                }

                query.setParameter(nomeParametro, valore);
            }

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public <T> T cercaPrimoPerCampi(Class<T> classe,
                                    Map<String, Object> campi) {

        List<T> risultati = cercaPerCampi(classe, campi);

        if (risultati.isEmpty()) {
            return null;
        }

        return risultati.get(0);
    }


    public <T> T aggiorna(T oggetto) {

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {
            em.getTransaction().begin();

            T oggettoAggiornato = em.merge(oggetto);

            em.getTransaction().commit();

            return oggettoAggiornato;

        } catch (RuntimeException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public <T> boolean elimina(Class<T> classe, Long id) {

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {
            em.getTransaction().begin();

            /*
             * Cerchiamo nel database l'oggetto da eliminare,
             * usando la sua classe e il suo id.
             */

            T oggetto = em.find(classe, id);

            //se l'oggetto esiste, lo eliminiamo
            if (oggetto != null) {
                em.remove(oggetto);
                em.getTransaction().commit();
                return true;
            }

            em.getTransaction().commit();
            return false;

        } catch (RuntimeException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }

}