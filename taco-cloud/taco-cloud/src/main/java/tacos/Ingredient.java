package tacos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
// @RequiredArgsConstructor : 생성자를 만들어준다
@RequiredArgsConstructor

public class Ingredient {

    // 식자재를 나타내는데 필요한 속성
    private final String id;
    private final String name;
    private final Type type;

    // 열거형 타입
    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
