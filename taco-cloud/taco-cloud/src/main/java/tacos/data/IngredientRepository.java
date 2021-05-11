package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {
    // DB의 모든 식자재 데이터를 쿼리하여 Ingredient 객체 컬렉션(list)에 넣어야한다
    Iterable<Ingredient> findAll();
    
    // id를 사용해 하나의 Ingredient를 쿼리해야 한다
    Ingredient findById(String id);
    
    // Ingredient 객체를 DB에 저장해야 한다
    Ingredient save(Ingredient ingredient);

}
