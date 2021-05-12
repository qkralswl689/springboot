package tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.Order;
import tacos.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@@Repository
public class JdbcOrderRepository implements OrderRepository{

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    // 생성자 통해 JdbcTemplate 주입
    public JdbcOrderRepository(JdbcTemplate jdbc)
    {
        // JdbcTemplate 을 사용해 두개의 SimpleJdbcInsert 인스턴스 생성
        
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                // Taco_Order 테이블에 주문 데이터를 추가하기 위해 구성
                .withTableName("Taco_Order")
                // Order 객체의 id 속성 값은 DB가 생성해 주는것을 사용한다
                .usingGeneratedKeyColumns("id");

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                // Taco_Order_Tacos 테이블에 해당 주문 id 및 연관된 타코들의 id 를 추가하기 위해 구성
                // =>but 어떤 id 값들을 Taco_Order_Tacos 테이블의 데이터에 생성할 것인지는 지정하지 않는다
                .withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    // save() 메소드 : 실제로 저장하는 일 X => Order 및 연관된 Taco 객체들을 저장하는 처리를 총괄한다
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();

        for(Taco taco : tacos){
            saveTacoToOrder(taco, orderId);
        }
        return order;
    }

     private long saveOrderDetails(Order order) {
        @SuppressWarnings("uncheked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());

        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String,Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
