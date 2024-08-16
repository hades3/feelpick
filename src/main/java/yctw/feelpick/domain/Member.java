package yctw.feelpick.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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

    private String username;

    private String password;

    @OneToMany(mappedBy = "member")
    List<Post> posts = new ArrayList<>();

    // 생성 메서드
    public static Member createMember(MemberDto memberDto){
        Member member = new Member();
        member.setUsername(memberDto.getUsername());
        member.setPassword(memberDto.getPassword());
        return member;
    }
}
