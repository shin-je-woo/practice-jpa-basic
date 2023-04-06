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
//            user.setUsername("userA");
            user.setUsername("관리자");
            user.setAge(10);
            user.setType(MyUserType.ADMIN);

            user.changeBelong(belong);

            em.persist(user);

            em.flush();
            em.clear();

            //case-when
            String query =
                    "select " +
                            "case when u.age <= 10 then '학생요금'" +
                            "     when u.age >= 60 then '경로요금'" +
                            "else '일반요금'" +
                            "end " +
                    "from User u";
            List<String> resultList = em.createQuery(query, String.class).getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
            }

            //coalesce
            String query1 = "select coalesce(u.username, '이름 없는 회원') as username from User u";
            List<String> resultList1 = em.createQuery(query1, String.class).getResultList();
            for (String s : resultList1) {
                System.out.println("s = " + s);
            }

            //nullif
            String query2 = "select nullif(u.username, '관리자') as username from User u";
            List<String> resultList2 = em.createQuery(query2, String.class).getResultList();
            for (String s : resultList2) {
                System.out.println("s = " + s);
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
