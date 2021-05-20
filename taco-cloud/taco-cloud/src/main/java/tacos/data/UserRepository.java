package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    // findByUsername() : 사용자 이름 -> id로 User를 찾기 위해 사용자 명세 서비스에서 사용될 것이다
    User findByUsername(String username);

}
