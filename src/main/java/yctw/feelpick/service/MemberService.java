package yctw.feelpick.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yctw.feelpick.domain.Member;
import yctw.feelpick.dto.MemberDto;
import yctw.feelpick.dto.PasswordDto;
import yctw.feelpick.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers(){
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public void remove(Long id){
        Member member = memberRepository.findOne(id);
        memberRepository.remove(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByUsername(member.getUsername());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    public void changePassword(Long id, PasswordDto passwordDto){
        Member loginMember = memberRepository.findOne(id);

        if (!(loginMember.getPassword().equals(passwordDto.getCurrentPassword()))){
            throw new IllegalStateException("현재 비밀번호가 올바르지 않습니다.");
        }
        if (!(passwordDto.getNewPassord().equals(passwordDto.getConfirmPassord()))) {
            throw new IllegalStateException("새로운 비밀번호가 일치하지 않습니다.");
        }
        loginMember.setPassword(passwordDto.getNewPassord());
    }

    public Member logIn(MemberDto memberDto){
        List<Member> findMembers = memberRepository.findByUsername(memberDto.getUsername());
        if (findMembers.isEmpty()){
            return null;
        }

        Member findMember = findMembers.get(0);

        if (findMember.getPassword().equals(memberDto.getPassword())){
            return findMember;
        }

        return null;
    }
}
