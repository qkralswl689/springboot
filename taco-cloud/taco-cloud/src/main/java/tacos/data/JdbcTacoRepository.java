package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;


import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository{

    private JdbcTemplate jdbc;

    public JdbcTacoRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }


    @Override
    // save() : Taco 테이블에 각 식자재를 저장하는 saveTacoInfo() 메서드 호출
    public Taco save(Taco taco) {
        // 반환된 타코 id를 사용해 타코와 식자재의 연관 정보를 저장하는 saveIngredientToTaco() 호출
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }
    
    private long saveTacoInfo(Taco taco) {
        
        taco.setCreatedAt(new Date());
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                "insert into Taco(name,createdAt) values(?,?)",
                Types.VARCHAR, Types.TIMESTAMP).newPreparedStatementCreator(
                Arrays.asList(taco.getName(),
                        new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        // 타코 ID를 얻기위해
        // update() : PreparedStatementCreator 객체와 KeyHolder 객체를 인자로 받는다 
        // KeyHolder : 생성된 타코 ID 제공 => 사용하기 위해서는 PreparedStatementCreator 도 생성해야 한다
        jdbc.update(psc, keyHolder);

        // update()의 실행이 끝나면 keyHolder.getKey().longValue()의 연속호출로 타코 ID를 반환할 수 있다
        return keyHolder.getKey().longValue() ;
    }

    // save() 메서드로 제어가 복귀 된 후 saveIngredientToTaco()를 호출하여
    // 객체의 List에 저장된 각 Imgredient 객체를 반복 처리한다.
    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        // update()를 사용해 타코 ID와 Ingredient 객체 참조를 Taco_Ingredients 테이블에 저장한다
        jdbc.update(
                "insert into Taco_Ingredients (taco, ingredient) " +
                        "values (?,?)",
                tacoId, ingredient.getId());
    }
}
