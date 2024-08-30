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

import java.io.IOException;
import java.net.URLDecoder;
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
    public String recommendList(@ModelAttribute(name = "response") String response, @ModelAttribute(name="choiceDto") ChoiceDto choiceDto, Model model) throws IOException {
        String[] foods = response.split(", ");
        model.addAttribute("foods", foods);
        model.addAttribute("choiceDto", choiceDto);
        model.addAttribute("response", response);
        return "/food/recommend";
    }

    @PostMapping("/recommend")
    public String recommendMenu(@ModelAttribute(name = "choiceDto") ChoiceDto choiceDto, RedirectAttributes redirectAttributes) {
        String prompt = "당신은 심리 전문가이고, 음식 전문가입니다. 당신의 임무는 기분이 " + choiceDto.getMood() + "인 사람에게 " + choiceDto.getType() + " 중에서 5개를 반드시 추천해주는 것입니다. 추천하는 메뉴의 이름만을 반드시 ','로 구분해서 알려주어야 합니다. 답변은 반드시 메뉴 이름과 ','로만 구성되어야 합니다. 이 구성을 지키지 않을 시에는 엄청난 불이익을 받을 것입니다. 답변 예시는 피자, 케이크, 비빔밥, 우동, 국수 입니다. 답변 예시처럼 올바르게 알려줄 시에는 300달러의 팁을 받게 됩니다. 맞춤법을 정확하게 지키면 엄청난 팁을 받게 됩니다.";
        String response = vertexAiGeminiChatModel.call(prompt);
        System.out.println("response = " + response);
        redirectAttributes.addFlashAttribute("response", response);
        redirectAttributes.addFlashAttribute("choiceDto", choiceDto);
        return "redirect:/food/recommend";
    }

    @GetMapping("/foodInfo/{foodName}")
    public String foodInfo(@PathVariable(name = "foodName") String foodName, Model model) throws IOException {
        foodName = URLDecoder.decode(foodName, "UTF-8");
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

        return "/food/foodInfo";
    }
}