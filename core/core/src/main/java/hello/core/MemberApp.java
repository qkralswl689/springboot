package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 안좋은 방법이다 : 눈으로 봐야해서 => 번지수가 다른걸 테스트할 수 없다
public class MemberApp {

    public static void main(String[] args){
       // AppConfig appConfig = new AppConfig();
        //MemberService memberService = appConfig.memberService();
        // MemberService memberService = new MemberServiceImpl();
        // Member 객체 생성하여 id,name,grade 매개변수로 넣는다
        
        // spring 코드 생성
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // memberService 객체 찾기 , 타입
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);

        // 회원가입
        // 객체 자체를 넣어줌
        //memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());

    }
}
