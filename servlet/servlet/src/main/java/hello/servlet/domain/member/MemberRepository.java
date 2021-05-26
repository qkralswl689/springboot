package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용
    
    private static final MemberRepository instance = new MemberRepository();
    
    public static MemberRepository getInstance() {
        return instance;
    }
    
    private MemberRepository() {
    }
    
    // 저장
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    
    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        // store에 있는 모든 값을 List에 넣어준다
        return new ArrayList<>(store.values());
    }
    
    // 테스트에서 사용 -> 스토어 전부 삭제
    public void clearStore() {
        store.clear();
    }
}
