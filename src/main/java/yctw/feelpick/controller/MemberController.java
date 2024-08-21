package yctw.feelpick.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yctw.feelpick.domain.Food;
import yctw.feelpick.domain.Member;
import yctw.feelpick.domain.Post;
import yctw.feelpick.domain.UploadFile;
import yctw.feelpick.dto.MemberDto;
import yctw.feelpick.dto.ModifyDto;
import yctw.feelpick.dto.PasswordDto;
import yctw.feelpick.dto.PostDto;
import yctw.feelpick.service.MemberService;
import yctw.feelpick.service.PostService;
import yctw.feelpick.service.UploadFileService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;
    private final UploadFileService uploadFileService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute(name = "loginResult") String loginResult, Model model){
        model.addAttribute("memberDto", new MemberDto());
        model.addAttribute("loginResult", loginResult);
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute(name = "memberDto") MemberDto memberDto, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Member loginMember = memberService.login(memberDto);

        if (loginMember == null){
            redirectAttributes.addFlashAttribute("loginResult", "fail");
            return "redirect:/member/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_MEMBER", loginMember);

        if (loginMember.getUsername().equals("admin") && loginMember.getPassword().equals("dlrjswjfeoahfmrpTwl")){
            return "redirect:/admin";
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute(name = "memberDto") MemberDto memberDto){
        Member member = Member.createMember(memberDto);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");
        loginMember = memberService.findMember(loginMember.getId());
        model.addAttribute("passwordDto", new PasswordDto());
        model.addAttribute("member", loginMember);
        return "member/mypage";
    }

    @GetMapping("/mypage/posts")
    public String viewMyPosts(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");
        List<Post> posts = postService.findPostByMemberId(loginMember.getId());
        model.addAttribute("posts", posts);
        return "post/myposts";
    }

    @PostMapping("/mypage/change")
    public String changePassword(@ModelAttribute(name = "passwordDto") PasswordDto passwordDto, BindingResult bindingResult, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");

        loginMember = memberService.findMember(loginMember.getId());

        if (!(loginMember.getPassword().equals(passwordDto.getCurrentPassword()))){
            bindingResult.addError(new ObjectError("currentPassword", "현재 비밀번호가 올바르지 않아요."));
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("member", loginMember);
            return "member/mypage";
        }
        loginMember.setPassword(passwordDto.getNewPassword());
        return "redirect:/member/mypage";
    }

    @PostMapping("/mypage/delete")
    public String deleteAccount(HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");
        memberService.remove(loginMember.getId());
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/mypage/posts/delete/{postId}")
    public String deleteMyPost(@PathVariable(name = "postId") Long postId){
        postService.remove(postId);
        return "redirect:/member/mypage/posts";
    }

    @GetMapping("/mypage/posts/modify/{postId}")
    public String modifyForm(@PathVariable(name = "postId") Long postId, Model model){
        Post post = postService.findPost(postId);
        ModifyDto modifyDto = new ModifyDto();
        modifyDto.setComment(post.getComment());
        modifyDto.setOldImageFiles(post.getImageFiles());

        model.addAttribute("modifyDto", modifyDto);
        model.addAttribute("/member/mypage/posts/modify/" + post.getId());
        return "post/modifypost";
    }

    @PostMapping("/mypage/posts/modify/{postId}")
    public String modifyMyPost(@PathVariable(name = "postId") Long postId, @ModelAttribute(name = "modifyDto") ModifyDto modifyDto) throws IOException {
        List<UploadFile> imageFiles = uploadFileService.storeFiles(modifyDto.getNewImageFiles());
        String comment = modifyDto.getComment();

        Post post = postService.findPost(postId);
        post.modifyPost(imageFiles, comment);

        return "redirect:/member/mypage/posts";
    }

    @GetMapping("/validate")
    @ResponseBody
    public Map<String, Boolean> validate(@RequestParam(name = "username") String username){
        boolean isAvailable = memberService.validateDuplicateUsername(username);
        return Collections.singletonMap("isAvailable", isAvailable);
    }

}
