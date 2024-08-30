package yctw.feelpick.controller;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yctw.feelpick.domain.Member;
import yctw.feelpick.dto.ChoiceDto;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class HomeController {
    private final EntityManager em;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        model.addAttribute("choiceDto", new ChoiceDto());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        List<Object[]> ranking = em.createQuery("select p.food.name, count(*) as post_count from Post p where p.createdDateTime >= :startOfDay and p.createdDateTime < :endOfDay GROUP BY p.food.name order by post_count desc limit 3", Object[].class).setParameter("startOfDay", startOfDay).setParameter("endOfDay", endOfDay).getResultList();

        int size = ranking.size();

        for (int i=0; i < 3 - size; i++){
            ranking.add(null);
        }

        model.addAttribute("ranking", ranking);

        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null){
            return "home";
        }
        model.addAttribute("member", loginMember);

        return "loginHome";
    }

}