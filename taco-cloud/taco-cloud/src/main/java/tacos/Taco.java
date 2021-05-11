package tacos;

import lombok.Data;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class Taco {

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
    
    @Size(min=1, message="You must choose at lease 1 ingredient")
    private List<Ingredient> ingredients;
}
