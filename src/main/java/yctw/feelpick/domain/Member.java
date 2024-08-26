package yctw.feelpick.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import yctw.feelpick.config.WebSecurityConfig;
import yctw.feelpick.dto.MemberDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "member")
    List<Post> posts = new ArrayList<>();

    // 생성 메서드
    public static Member createMember(MemberDto memberDto){
        Member member = new Member();
        member.setUsername(memberDto.getUsername());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(memberDto.getPassword());
        member.setPassword(encodedPassword);
        return member;
    }
}
