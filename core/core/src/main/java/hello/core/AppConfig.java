package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// APP 전체 설정,구성 => 객체의생성,연결 담당
// @Configuration : APP 설정,구성 정보를 담당하는것을 표현
@Configuration
public class AppConfig {

    // @Bean => 붙혀주면 스프링 컨테이너에 저장된다
    @Bean
    public MemberService memberService(){
        //1번
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //2번? 3번?
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        //1번
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(), discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy(){
        
        // OCP 만족
       // return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
