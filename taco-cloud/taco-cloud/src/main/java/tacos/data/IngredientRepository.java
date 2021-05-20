package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

// CrudRepository 인터페이스 : CRUD 연산을 많은 위한 메서드가 선언되어있다
// CrudRepository<Ingredient,String> : 첫 번째 매개변수 Ingredient => 리퍼지터리에 저장되는 개체 타입
// 두 번째 배개변수 String => Id 속성의 타입
public interface IngredientRepository extends CrudRepository<Ingredient,String> {


    /*// DB의 모든 식자재 데이터를 쿼리하여 Ingredient 객체 컬렉션(list)에 넣어야한다
    Iterable<Ingredient> findAll();
    
    // id를 사용해 하나의 Ingredient를 쿼리해야 한다
    Ingredient findById(String id);
    
    // Ingredient 객체를 DB에 저장해야 한다
    Ingredient save(Ingredient ingredient);*/

}
