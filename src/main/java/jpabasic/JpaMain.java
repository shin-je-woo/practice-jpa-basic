package jpabasic;

import jpabasic.domain.AddressEntity;
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

            Member member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));


            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("보쌈");

            member.getAddressHistory().add(new AddressEntity("old1", "street1", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street2", "20000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=============START===========");
            Member findMember = em.find(Member.class, member.getId());

            //homeCity -> newCity
            Address oldAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", oldAddress.getStreet(), oldAddress.getZipcode()));

            //치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //remove()는 equals hashcode가 구현되어 있어야 동작함 , 구현 안되어 있으면 삭제는 안되고 추가만 되는 문제 발생!!
            findMember.getAddressHistory().remove(new AddressEntity("old1", oldAddress.getStreet(), oldAddress.getZipcode()));
            findMember.getAddressHistory().add(new AddressEntity("newCity1", oldAddress.getStreet(), oldAddress.getZipcode()));

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
