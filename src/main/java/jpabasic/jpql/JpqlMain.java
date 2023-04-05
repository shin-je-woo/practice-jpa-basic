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

            User user = new User();
            user.setUsername("userA");
            user.setAge(10);
            em.persist(user);

            em.flush();
            em.clear();

            List<UserDTO> resultList = em.createQuery("select distinct new jpabasic.jpql.UserDTO(u.username, u.age) from User u", UserDTO.class)
                    .getResultList();

            for (UserDTO userDTO : resultList) {
                System.out.println("userDTO.username = " + userDTO.getUsername());
                System.out.println("userDTO.age = " + userDTO.getAge());
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
