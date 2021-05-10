package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

// 주문 구현
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
   // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private final DiscountPolicy discountPolicy; // => 인터페이스에만 의존한다 => DIP 만족

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    // Order타입의 createOrder 메소드 생성하여 매개변수를 받는다
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // Member타입의 변수 member에 MemoryMemberRepository의findById 메소드에 매개변수 memberId 값을 넣는다
        Member member = memberRepository.findById(memberId);
        // int타입의 변수 discountPrice 에 FixDiscountPolicy의 discount메소드에 배개변수 member,itemPrice 값을 넣는다
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // Order객체를 생성해 변수값을 리턴한다
        // 생성과 동시에 리턴
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
    
    // => 주문생성(createOrder)요청이 오면 -> 회원 정보 조회(findById) -> 할인 정책 적용(discount) 
    // -> 주문 객체 생성하여 반환(return new Order)
    // MemoryMemberRepository 와 FixDiscountPolicy 을 구현체로 생성한다

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
