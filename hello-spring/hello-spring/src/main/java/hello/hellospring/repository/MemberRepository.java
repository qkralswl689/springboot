package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id); // id로 회원 찾는 기능
    Optional<Member> findByName(String name); // name으로 회원 찾는 기능
    List<Member> findAll(); // 모든 회원
}
