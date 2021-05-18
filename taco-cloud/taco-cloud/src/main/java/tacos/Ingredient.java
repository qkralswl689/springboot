package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data // => @NoArgsConstructor가 지정되면 인자있는 생성자가 제거된다 -> So @RequiredArgsConstructor 를 선언

// @RequiredArgsConstructor : 생성자를 만들어준다 => 인자 있는 생성자 생성
@RequiredArgsConstructor

// @NoArgsConstructor : 인자 없는 생성자를 생성하기 위해 => JPA에서는 개체가 인자없는 생성자를 가져야 한다
// BUT 인자없는 생성자 사용을 원치 않기 때문에 access 속성을 AccessLevel.PRIVATE 으로 설정하여 클래스 외부에서 사용하지 못하게 함
// AND Ingredient 에는 초기화가 필요한 final 속성들이 있으므로 force 속성을 true로 설정했다 => Lombok이 자동 생성한 생성자에서 그 속성들을 null로 설정한다
@NoArgsConstructor(access = AccessLevel.PRIVATE, force=true)

// @Entity : JPA 개체(entity)로 선언 하려면 꼭 추가해야한다
@Entity
public class Ingredient {

    // 식자재를 나타내는데 필요한 속성
    // @Id@Id : 속성 DB의 개체를 고유하게 식별한다는 것을 나타낸다
    @Id
    private final String id;
    private final String name;
    private final Type type;

    // 열거형 타입
    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
