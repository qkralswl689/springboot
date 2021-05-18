package tacos;

import lombok.Data;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Entity
public class Taco {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO) : 기본키 생성을 DB에 위임한다=> DB가 자동으로 생성해주는 ID값 사용
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdAt;

    // @NotNull, @Size() : 유효성 검사 규칙 선언

    // name 속성에는 값이 null 이면 X, 최소 5개문자여야 한다
    @NotNull
    @Size(min=5, message="Name must be at lease 5 characters long")
    private String name;

    // jdbc연결 위해 변경
 /* @Size(min=1, message="You must choose at lease 1 ingredient")
    private List<String> ingredients;*/

    // @ManyToMany(targetEntity=Ingredient.class) : 하나의 Taco 객체는 많은 Ingredient객체를 가질수 있다 => 다:다 연결 -> 실무사용 X
    @ManyToMany(targetEntity=Ingredient.class)
    @Size(min=1, message="You must choose at lease 1 ingredient")
    private List<Ingredient> ingredients;

    // @PrePersist : DB에 해당 테이블의 insert 연산을 실행 할 때 같이 실행하라는 의미를 가진다 
    // => Taco 객체가 저장되기 전에 createdAt 속성을 현재 일자와 시간으로 설정하는 데 사용
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
