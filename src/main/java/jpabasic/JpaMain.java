package jpabasic;

import jpabasic.domain.Member;
import jpabasic.domain.Team;

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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setName("member1");
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member1.getId());

            System.out.println("team = " + m.getTeam().getClass()); // m.getTeam()은 프록시 객체

            m.getTeam().getName(); // team을 사용할 때 프록시 객체를 초기화 요청 -> 이 때 DB에서 select(지연로딩LAZY의 동작 원리)

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
