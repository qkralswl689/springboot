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


@Repository
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

    // 실제 저장하는 일 => saveOrderDetails(),saveTacoToOrder() 메서드에서 처리한다
    // execute() , executeAndReturnKey() : 데이터를 추가하는 메서드
    // 두 메서드 모두 Map<String,Object> 를 이자로 받는다 
    // => Map의 키 : 데이터가 추가되는 테이블의 열(column) 이름과 대응
    // => Map의 값 : 해당 열에 추가되는 값
     private long saveOrderDetails(Order order) {
        @SuppressWarnings("uncheked")
        // objectMapper.convertValue(order, Map.class) : order 를 Map 으로 변환
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        // Map 이 생성되면 키가 placedAt 인 항목의 값을 Order 객체의 placedAt 속성 값으로 변경한다
         // => objectMapper 는 Date 타입의 값을 long 타입의 값으로 변환해 Taco_Order 테이블의 placedAt 열과 타입이 호환되지 않기 때문
        values.put("placedAt", order.getPlacedAt());
        
        // executeAndReturnKey() : 해당 주문 데이터가 Taco_Order 테이블에 저장된 후 DB에서 생성된 ID가 Number 객체로 반환된다
         // 따라서 연속으로 longValue()를 호출하여 saveOrderDetails() 메서드에서 반환하는 long 타입으로 변환할 수 있다
        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        // 객체를 Map 으로 변환하기 위해 ObjectMapper 를 사용하는 대신 Map을 생성하고 각 항목에 적합한 값을 설정한다
        Map<String,Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        // exexute() 메서드 호출하여 데이터를 저장한다
        orderTacoInserter.execute(values);
    }
}
