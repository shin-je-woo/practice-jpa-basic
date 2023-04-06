package jpabasic.jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Belong belong = new Belong();
            belong.setName("belongA");
            em.persist(belong);

            User user = new User();
            user.setUsername("userA");
            user.setAge(10);
            user.setType(MyUserType.ADMIN);

            user.changeBelong(belong);

            em.persist(user);

            em.flush();
            em.clear();

            String query = "select u.username, 'HELLO', true from User u " +
                            "where u.type = :userType";
            List<Object[]> resultList = em.createQuery(query)
                    .setParameter("userType", MyUserType.ADMIN)
                    .getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
