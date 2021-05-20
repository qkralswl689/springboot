package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Taco;

// Taco 객체를 저장하는데 필요한 인터페이스
public interface TacoRepository extends CrudRepository<Taco, Long> {
  /*  Taco save(Taco design);*/
}
