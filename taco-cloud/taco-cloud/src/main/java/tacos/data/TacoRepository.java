package tacos.data;

import tacos.Taco;

// Taco 객체를 저장하는데 필요한 인터페이스
public interface TacoRepository {
    Taco save(Taco design);
}
