package jpabasic.jpql;

import javax.persistence.*;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            User user = new User();
            user.setUsername("userA");
            user.setAge(10);
            em.persist(user);

            User singleResult = em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", "userA")
                    .getSingleResult();
            System.out.println("singleResult = " + singleResult);


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
