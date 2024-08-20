package yctw.feelpick.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yctw.feelpick.domain.Member;
import yctw.feelpick.domain.Post;
import yctw.feelpick.service.MemberService;
import yctw.feelpick.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping("/admin")
public class AdminController {
    private final MemberService memberService;

    private final PostService postService;

    @GetMapping("")
    public String admin(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");

        if (loginMember == null){
            return "redirect:/";
        }

        if (!(loginMember.getUsername().equals("admin") && loginMember.getPassword().equals("dlrjswjfeoahfmrpTwl"))){
            return "redirect:/";
        }

        List<Member> members = memberService.findMembers();
        List<Post> posts = postService.findPosts();
        model.addAttribute("members", members);
        model.addAttribute("posts", posts);
        return "admin";
    }

    @PostMapping("/banish/{memberId}")
    public String banishMember(@PathVariable(name = "memberId") Long memberId) {
        memberService.remove(memberId);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable(name = "postId") Long postId){
        postService.remove(postId);
        return "redirect:/admin";
    }


}
