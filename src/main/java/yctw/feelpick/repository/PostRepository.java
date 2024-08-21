package yctw.feelpick.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yctw.feelpick.domain.Post;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public Post findOne(Long id){
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        List<Post> posts = em.createQuery("select p from Post p", Post.class).getResultList();
        return posts;
    }

    public void remove(Post post){
        em.remove(post);
    }

    public List<Post> findAllByMemberId(Long id) {
        List<Post> resultList = em.createQuery("select p from Post p where p.member.id = :id order by p.createdDateTime desc", Post.class).setParameter("id", id)
                .getResultList();
        return resultList;
    }

    public List<Post> findAllByFoodId(Long id) {
        List<Post> resultList = em.createQuery("select p from Post p where p.food.id = :id order by p.createdDateTime desc", Post.class).setParameter("id", id)
                .getResultList();
        return resultList;
    }



}
