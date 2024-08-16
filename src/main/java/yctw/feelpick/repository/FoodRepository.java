package yctw.feelpick.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yctw.feelpick.domain.Food;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class FoodRepository {

    private final EntityManager em;

    public Long save(Food food){
        em.persist(food);
        return food.getId();
    }

    public Food findOne(Long id){
        return em.find(Food.class, id);
    }

    public List<Food> findByName(String name) {
        List<Food> foods = em.createQuery("select f from Food f where f.name = :name", Food.class).setParameter("name", name).getResultList();
        return foods;
    }

    public List<Food> findAll(){
        return em.createQuery("select f from Food f", Food.class).getResultList();
    }


}
