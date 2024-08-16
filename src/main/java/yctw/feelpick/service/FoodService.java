package yctw.feelpick.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yctw.feelpick.domain.Food;
import yctw.feelpick.repository.FoodRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {

    private final FoodRepository foodRepository;

//    public Long join(Food food){
//        if (!validateDuplicateFood(food)){
//
//        }
//
//        return food.getId();
//    }
//
//    private boolean validateDuplicateFood(Food food) {
//
//        List<Food> findFoods = foodRepository.findByName(food.getName());
//
//        if(!findFoods.isEmpty()){
//            return false;
//        }
//        return true;
//    }
}
