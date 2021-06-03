package hello.itemservice.domain.item;

import lombok.Data;


@Data //=> 객체(데이터)에서 사용하면 위험해서 권장 X
// 상품 객체
public class Item {
    
    private Long id;
    private String itemName;
    private Integer price; // price가 안들어가야할 가능성이 있기 때문에 Integer로 사용 => int로 사용하면 값이 무조건 들어가야한다
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
