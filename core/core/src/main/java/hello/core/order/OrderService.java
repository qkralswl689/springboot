package hello.core.order;

// 주문 인터페이스
public interface OrderService {
    // 최종 order 결과 반환
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
