package tacos.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tacos.Order;

import java.util.Date;
import java.util.List;

// Order 객체를 저장하는데 필요한 인터페이스
public interface OrderRepository extends CrudRepository<Order,Long> {
   /* Order save(Order order); => JPA 사용으로 삭제*/
    
    // JPA 사용법
/*    // 특정 ZIP(우편번호) 코드로 배달된 모든 주문 데이터를 DB에서 가져오기 위해 선언
    List<Order> findByDeliveryZip(String deliveryZip);
    
    // 지정된 일자 범위 내에서 특정 ZIP 코드로 배달된 모든 주문을 쿼리하기 위해 선언
    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

    // 지정된 열의 값을 기준으로 결과를 정렬하기 위한 선언
    List<Order> findByDeliveryCityOrderByDeliveryTo(String city);

    // @Query : 원하는 것을 지정한 후 해당 메서드가 호출 될 때 수행되는 쿼리를 지정
    // 시애틀에 배달된 모든 주문 요청
    @Query("Order o where o.deliveryCity='Seattle'")
    List<Order> readOrdersDeliveredInSeattle();*/
}
