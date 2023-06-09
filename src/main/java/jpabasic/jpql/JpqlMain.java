package jpabasic.jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Belong belong1 = new Belong();
            belong1.setName("팀1");
            em.persist(belong1);

            Belong belong2 = new Belong();
            belong2.setName("팀2");
            em.persist(belong2);

            User user1 = new User();
            user1.setUsername("회원1");
            user1.changeBelong(belong1);
            em.persist(user1);

            User user2 = new User();
            user2.setUsername("회원2");
            user2.changeBelong(belong1);
            em.persist(user2);

            User user3 = new User();
            user3.setUsername("회원3");
            user3.changeBelong(belong2);
            em.persist(user3);

            int resultCount = em.createQuery("update User u set u.age = 20")
                    .executeUpdate();

            em.clear();

            User findUser = em.find(User.class, user1.getId());

            System.out.println("findUser = " + findUser);

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