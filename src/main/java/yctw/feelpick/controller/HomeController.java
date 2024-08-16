package yctw.feelpick.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yctw.feelpick.domain.Member;
import yctw.feelpick.dto.ChoiceDto;

@Controller
@RequiredArgsConstructor
@Transactional
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        model.addAttribute("choiceDto", new ChoiceDto());

        HttpSession session = request.getSession();

        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");

        if (loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginhome";
    }

}
