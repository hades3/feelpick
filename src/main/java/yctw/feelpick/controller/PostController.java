package yctw.feelpick.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yctw.feelpick.domain.Food;
import yctw.feelpick.domain.Member;
import yctw.feelpick.domain.Post;
import yctw.feelpick.dto.ModifyDto;
import yctw.feelpick.dto.PostDto;
import yctw.feelpick.domain.UploadFile;
import yctw.feelpick.service.UploadFileService;
import yctw.feelpick.repository.FoodRepository;
import yctw.feelpick.service.PostService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class PostController {

    private final PostService postService;
    private final FoodRepository foodRepository;
    private final UploadFileService uploadFileService;

    @GetMapping("/post/create/{foodId}")
    public String writeForm(@PathVariable(name = "foodId") Long foodId, Model model) throws IOException{
        Food food = foodRepository.findOne(foodId);
        model.addAttribute("foodId", foodId);
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("returnAddress", "/food/foodInfo/" + URLEncoder.encode(food.getName(), "UTF-8"));
        return "/post/createPost";
    }

    @PostMapping("/post/create/{foodId}")
    public String writePost(@PathVariable(name = "foodId") Long foodId, @ModelAttribute(name = "postDto") PostDto postDto, HttpServletRequest request) throws IOException {
        List<UploadFile> imageFiles = uploadFileService.storeFiles(postDto.getImageFiles());
        String content = postDto.getContent();

        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");

        Food food = foodRepository.findOne(foodId);

        Post post = Post.createPost(loginMember, food, imageFiles, content);

        postService.write(post);

        return "redirect:/food/foodInfo/" + URLEncoder.encode(food.getName(), "UTF-8");
    }

    @ResponseBody
    @GetMapping("/post/images/{filename}")
    public UrlResource downloadImage(@PathVariable(name = "filename") String filename) throws MalformedURLException {
        return new UrlResource("file:" + uploadFileService.getFullPath(filename));
    }

    @PostMapping("/post/delete/{postId}")
    public String deletePost(@PathVariable(name = "postId") Long postId) throws IOException{
        Post post = postService.findPost(postId);
        Food food = post.getFood();
        postService.remove(postId);
        return "redirect:/food/foodInfo/" + URLEncoder.encode(food.getName(), "UTF-8");
    }

    @GetMapping("/post/modify/{postId}")
    public String modifyForm(@PathVariable(name = "postId") Long postId, Model model) throws IOException {
        Post post = postService.findPost(postId);
        ModifyDto modifyDto = new ModifyDto();
        modifyDto.setContent(post.getContent());
        modifyDto.setOldImageFiles(post.getImageFiles());

        model.addAttribute("modifyDto", modifyDto);

        String postAddress = "/post/modify/" + postId;
        String returnAddress = "/food/foodInfo/" + URLEncoder.encode(post.getFood().getName(), "UTF-8");
        model.addAttribute("postAddress", postAddress);
        model.addAttribute("returnAddress", returnAddress);
        return "/post/modifyPost";
    }

    @PostMapping("/post/modify/{postId}")
    public String modifyMyPost(@PathVariable(name = "postId") Long postId, @ModelAttribute ModifyDto modifyDto) throws IOException{
        List<UploadFile> imageFiles = uploadFileService.storeFiles(modifyDto.getNewImageFiles());
        String content = modifyDto.getContent();

        Post post = postService.findPost(postId);
        post.modifyPost(imageFiles, content);

        Food food = post.getFood();
        return "redirect:/food/foodInfo/" + URLEncoder.encode(food.getName(), "UTF-8");
    }

}
