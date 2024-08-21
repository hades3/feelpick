package yctw.feelpick.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yctw.feelpick.domain.Food;
import yctw.feelpick.domain.Post;
import yctw.feelpick.dto.ChoiceDto;
import yctw.feelpick.repository.FoodRepository;
import yctw.feelpick.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping("/food")
public class FoodController {

    private final VertexAiGeminiChatModel vertexAiGeminiChatModel;
    private final FoodRepository foodRepository;
    private final PostService postService;

    @GetMapping("/recommend")
    public String recommendList(@ModelAttribute(name = "response") String response, @ModelAttribute(name="choiceDto") ChoiceDto choiceDto, Model model) {
        String[] foods = response.split(", ");
        model.addAttribute("foods", foods);
        model.addAttribute("choiceDto", choiceDto);
        return "/food/recommend";
    }

    @PostMapping("/recommend")
    public String recommendMenu(@ModelAttribute(name = "choiceDto") ChoiceDto choiceDto, RedirectAttributes redirectAttributes) {
        String prompt = "기분이 " + choiceDto.getMood() + "일 때, " + choiceDto.getType() + " 중에서 사람들이 많이 먹은 배달 음식의 구체적인 이름 5개를 반드시 ','로 구분해서 알려줘. 예를 들어, 피자, 치킨, 햄버거, 족발, 보쌈 이런 식으로 대답해야 해.";
        String response = vertexAiGeminiChatModel.call(prompt);
        redirectAttributes.addFlashAttribute("response", response);
        redirectAttributes.addFlashAttribute("choiceDto", choiceDto);
        return "redirect:/food/recommend";
    }

    @GetMapping("/foodInfo/{foodName}")
    public String foodInfo(@PathVariable(name = "foodName") String foodName, Model model) {
        List<Food> foods = foodRepository.findByName(foodName);
        Food food;
        if (foods.isEmpty()){
            food = Food.createFood(foodName);
            foodRepository.save(food);
        }
        else{
            food = foods.get(0);
        }

        List<Post> posts = postService.findPostByFoodId(food.getId());
        model.addAttribute("food", food);
        model.addAttribute("posts", posts);

        return "food/foodinfo";
    }
}
