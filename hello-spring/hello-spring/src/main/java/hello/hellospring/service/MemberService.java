package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원 가입
     * */
   public Long join(Member member){
       // 같은 이름 중복 X
       validateDuplicateMember(member); // 중복 회원 검증
       memberRepository.save(member); // save 불러온다
       return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(member1 -> {  // ifPresent : 값이 있다면
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    
    /**
     * 전체 회원 조회
     * */
    public List<Member> findMembers(){
       return memberRepository.findAll();
    }

    /**
     * 회원 조회
     * */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
