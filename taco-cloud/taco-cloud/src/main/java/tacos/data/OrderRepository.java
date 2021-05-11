package tacos.data;

import tacos.Order;

// Order 객체를 저장하는데 필요한 인터페이스
public interface OrderRepository {
    Order save(Order order);
}
