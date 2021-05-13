package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

// 할인 구현체
@Component
@Qualifier("FixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000; // 천원 할인

    @Override
    public int discount(Member member, int price) {
        // Grade가 VIP라면 할인 적용, 아니면 적용 x
        if(member.getGrade() == Grade.VIP){
            return discountFixAmount;
        }else{
            return 0;
        }


    }
}
