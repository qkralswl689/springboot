package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 상품 저장소
@Repository
public class ItemRepository {

    // 실무에서는 HashMap사용 안됨 => 동시에 접근하면 값이 꼬인다
    private static final Map<Long, Item> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용

    // 아이템 저장 기능
    public Item save(Item item) {
        
        // 시퀀스 값 증가
        item.setId(++sequence);
        
        // HashMap에 상품 저장
        store.put(item.getId(), item);
        
        // 저장된 상품 리턴
        return item;
    }

    // 상품 하나 조회
    public Item findById(Long id) {
        return store.get(id);
    }

    // 상품 전체 조회
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 상품 수정(updateParam 대신 실무에서는 dto사용)
    public void update(Long itemId, Item updateParam) {

        // 1. 수정할 아이템 찾기
        Item findItem = findById(itemId);

        // 2. 파라미터 정보(name,price,quantity)가 넘어온다
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    // 스토어를 다 지운다 => 테스트를 위해
    public void clearStore() {
        store.clear();
    }
}
