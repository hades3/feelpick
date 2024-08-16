package yctw.feelpick.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Food {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String recipeUrl;

    private String videoUrl;

    @OneToMany(mappedBy = "food")
    private List<Post> posts = new ArrayList<>();

    // 생성 메서드
    public static Food createFood(String name){
        Food food = new Food();
        food.setName(name);
        food.setRecipeUrl("https://www.10000recipe.com/recipe/list.html?q=" + name);
        food.setVideoUrl("https://www.youtube.com/results?search_query=" + name + " 먹방");
        return food;
    }
}