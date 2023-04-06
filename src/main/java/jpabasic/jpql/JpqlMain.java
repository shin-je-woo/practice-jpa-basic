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

            user.changeBelong(belong);

            em.persist(user);

            em.flush();
            em.clear();

            String query1 = "select u from User u left join u.belong b";
            List<User> resultList1 = em.createQuery(query1, User.class)
                    .getResultList();
            System.out.println("resultList1.size() = " + resultList1.size());

            String query2 = "select u from User u left join u.belong b on b.name ='A'";
            List<User> resultList2 = em.createQuery(query2, User.class)
                    .getResultList();
            System.out.println("resultList2.size() = " + resultList2.size());

            String query3 = "select u from User u left join Belong b on u.username = b.name";
            List<User> resultList3 = em.createQuery(query3, User.class)
                    .getResultList();
            System.out.println("resultList2.size() = " + resultList3.size());

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
