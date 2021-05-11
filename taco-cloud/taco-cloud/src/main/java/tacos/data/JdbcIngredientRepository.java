package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

// @Repository : 스프링 컴포넌트 검색에서 이 클래스를 자동으로 찾아 스프링 app 컨텍스트의 빈으로 생성해 준다
@Repository
public class JdbcIngredientRepository implements IngredientRepository{

    private JdbcTemplate jdbc;

    @Autowired
    // JdbcIngredientRepository 빈이 생성되면 @Autowired 를 통해 스프링이 해당 빈을 JdbcTemplate 에 연결 한다
    // JdbcIngredientRepository(JdbcTemplate jdbc) : JdbcTemplate 참조를 인스턴스 변수에 저장
    // => DB의 데이터를 쿼리, 추가하기 위해 다른 메서드에 사용 된다
    public JdbcIngredientRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }


    @Override
    //  findAll() : 객체가 저장된 컬렉션을 반환하는 메서드
    public Iterable<Ingredient> findAll() {
        // query() : 두개의 인자를 받는다
        // 첫번째 인자 : SQL
        // 두번째 인자 : 스프링의 RowMapper 인터페이스를 구현한 mapRowToIngredient 메서드
        // query() 에서는 해당 쿼리에서 요구하는 매개변수들의 내역을 마지막 인자로 받을 수 있다
        return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient);
    }

    @Override
    // findById() : 하나의 Ingredient 객체 반환 => queryForObject() 사용
    // 첫번째 인자 : SQL
    // 두번째 인자 : 스프링의 RowMapper 인터페이스를 구현한 mapRowToIngredient 메서드
    // 세번째 인자 : 검색할 행의 id(식자재 id) 전달 => 첫번째 인자로 전달된 SQL에 있는 ? 대신 교체되어 쿼리에 사용된다
    public Ingredient findById(String id) {
        return jdbc.queryForObject("select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient,id);
    }
    // mapRowToIngredient() : 쿼리로 생성된 결과 세트(Resultset 객체)의 행(row) 개수만큼 호출되며 
    // 결과 세트의 모든 행을 각각 객체(Ingredient)로 생성하고 List에 저장한 후 반환
    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
            throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }

    // 데이터 추가
    @Override
    public Ingredient save(Ingredient ingredient) {
        // update() : DB에 데이터를 추가,변경하는 어떤 쿼리에도 사용될 수 있다
        jdbc.update("inwert into Ingredient (id, name, type) values (?,?,?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

 



}
