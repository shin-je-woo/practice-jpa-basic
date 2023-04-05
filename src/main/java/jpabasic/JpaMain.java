package jpabasic;

import jpabasic.domain.Member;
import jpabasic.embedded.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Address address = new Address("city", "street", "zipcode");

            Member member1 = new Member();
            member1.setName("member1");
            member1.setHomeAddress(address);
            em.persist(member1);

            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member1.setHomeAddress(newAddress);

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
