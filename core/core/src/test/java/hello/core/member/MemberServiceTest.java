package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberServiceTest {

   // MemberService memberService = new MemberServiceImpl();
    MemberService memberService;
    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    // 어노테이션 무조건 
    @Test
    void join(){
        // given : 주어진 상황
        Member member = new Member(1L,"memberA", Grade.VIP);
        // when : 일을 만드는 작업
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then: 이상황에서 일 이있을때 작동
        assertThat(member).isEqualTo(findMember);

    }
}
