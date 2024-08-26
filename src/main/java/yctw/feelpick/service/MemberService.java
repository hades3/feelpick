package yctw.feelpick.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yctw.feelpick.domain.Member;
import yctw.feelpick.dto.MemberDto;
import yctw.feelpick.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    public Member findMember(Long id){
        return memberRepository.findOne(id);
    }

    public List<Member> findMembers(){
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public void remove(Long id){
        Member member = memberRepository.findOne(id);
        memberRepository.remove(member);
    }

    public boolean validateDuplicateUsername(String username){
        List<Member> findMembers = memberRepository.findByUsername(username);
        return findMembers.isEmpty();
    }

    public Member login(MemberDto memberDto){

        List<Member> findMembers = memberRepository.findByUsername(memberDto.getUsername());
        if (findMembers.isEmpty()){
            return null;
        }

        Member findMember = findMembers.get(0);

        if (bCryptPasswordEncoder.matches(memberDto.getPassword(), findMember.getPassword())) {
            return findMember;
        }

        return null;
    }
}
