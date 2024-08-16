package yctw.feelpick.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yctw.feelpick.service.MemberService;
import yctw.feelpick.service.PostService;

@Controller
@RequiredArgsConstructor
@Transactional
public class AdminController {
    private final MemberService memberService;

    private final PostService postService;

    @GetMapping("/admin")
    public String admin(Model model){

        return "admin/admin";
    }
}
