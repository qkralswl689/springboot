package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.Optional;

// Converter : 데이터의 타입을 변환해주는 클래스
// @Component : 스프링에 의해 자동 생성 및 주입되는 빈으로 생성
@Component
// Converter<String, Ingredient> : String => 변환할 값의 타입, Ingredient => 변환된 값의 타입
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepo;

    // @Autowired : IngredientRepository 인터페이스를 구현한 빈(JdbcIngredientRepository) 인스턴스가 생성자의 인자로 주입된다
    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo){
        this.ingredientRepo = ingredientRepo;
    }
    @Override
    public Ingredient convert(String id) {
        // IngredientRepository 인터페이스를 구현한 JdbcIngredientRepository 클래스 인스턴스의 findById() 메서드를 호출한다
        // findById() : 변환할 String 값을 id로 갖는 식자재 데이터를 DB 에서 찾는다
        // => JdbcIngredientRepository 의 private 메서드인 mapRowToIngredient() 메서드를 호출하여 결과 세트의 행 데이터를 속성 값으로 갖는 Ingredient 객체를 생성하고 반환한다
       /* return ingredientRepo.findById(id);*/ // => JPA 쓰려고 삭제
        Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
        // JPA 에서 자동으로 구현된 findById() 메서드가 실행되고 DB에서 식자재를 찾지 못했을 때 null이 반환될 수 있어 안전한 처리를 하기위해 아래와 같이 변경
        return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
    }
}
