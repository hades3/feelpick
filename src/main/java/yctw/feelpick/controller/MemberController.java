package yctw.feelpick.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import yctw.feelpick.domain.Member;
import yctw.feelpick.domain.Post;
import yctw.feelpick.dto.MemberDto;
import yctw.feelpick.dto.ModifyDto;
import yctw.feelpick.dto.PasswordDto;
import yctw.feelpick.dto.PostDto;
import yctw.feelpick.service.MemberService;
import yctw.feelpick.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/member/login")
    public String loginForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute(name = "memberDto") MemberDto memberDto, HttpServletRequest request){
        Member loginMember = memberService.logIn(memberDto);

        if (loginMember == null){
            throw new IllegalStateException("아이디 또는 패스워드가 올바르지 않습니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_MEMBER", loginMember);

        return "redirect:/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/member/register")
    public String registerForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/register";
    }

    @PostMapping("/member/register")
    public String register(@ModelAttribute(name = "memberDto") MemberDto memberDto){
        Member member = Member.createMember(memberDto);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/member/mypage")
    public String myPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");
        model.addAttribute("passwordDto", new PasswordDto());
        model.addAttribute("member", loginMember);
        return "member/mypage";
    }

    @GetMapping("/member/mypage/posts")
    public String viewMyPosts(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");
        List<Post> posts = postService.findPostByMemberId(loginMember.getId());
        model.addAttribute("posts", posts);
        return "post/myposts";
    }

    @PostMapping("/member/mypage/change")
    public String changePassword(@ModelAttribute(name = "passwordDto") PasswordDto passwordDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");
        memberService.changePassword(loginMember.getId(), passwordDto);
        return "redirect:/member/mypage";
    }

    @PostMapping("/member/mypage/delete")
    public String deleteAccount(HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");
        memberService.remove(loginMember.getId());
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/member/mypage/posts/delete/{postId}")
    public String deleteMyPost(@PathVariable(name = "postId") Long postId){
        postService.remove(postId);
        return "redirect:/member/mypage/posts";
    }

    @GetMapping("/member/mypage/posts/modify/{postId}")
    public String modifyForm(@PathVariable(name = "postId") Long postId, Model model){
        Post post = postService.findPost(postId);
        ModifyDto modifyDto = new ModifyDto();
        modifyDto.setComment(post.getComment());
        modifyDto.setImageFiles(post.getImageFiles());

        model.addAttribute("modifyDto", modifyDto);
        model.addAttribute("/member/mypage/posts/modify/" + post.getId());
        return "post/modifypost";
    }

    @PostMapping("/member/mypage/posts/modify/{postId}")
    public String modifyMyPost(@PathVariable(name = "postId") Long postId, @ModelAttribute(name = "postDto") PostDto postDto){
        Post post = postService.findPost(postId);
        post.setComment(postDto.getComment());
        return "redirect:/member/mypage/posts";
    }

}
