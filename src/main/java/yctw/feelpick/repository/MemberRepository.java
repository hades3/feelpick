package yctw.feelpick.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yctw.feelpick.domain.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        return members;
    }

    public void remove(Member member){
        em.remove(member);
    }

    public List<Member> findByUsername(String username){
        List<Member> members = em.createQuery("select m from Member m where m.username = :username", Member.class).setParameter("username", username).getResultList();
        return members;
    }

}
