package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {


    private static Map<Long,Member> store = new HashMap<>();
    // sequence : 0,1,2... 키 값을 생성
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // id셋팅
        store.put(member.getId(), member); // store에 저장 ->map에
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Optional.ofNullable() => null값일 경우 대비
        return Optional.ofNullable(store.get(id)); 
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
           .filter(member -> member.getName().equals(name))
            .findAny(); // map을 돌면서 name이 같은 값이면 반환해준다
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store에 있는 member들 반환
    }

    // 테스트 클리어해주는 메소드
    public void clearStore(){
        store.clear();
    }
}
