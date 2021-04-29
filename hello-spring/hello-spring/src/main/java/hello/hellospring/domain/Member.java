package hello.hellospring.domain;

import javax.persistence.*;

@Entity
public class Member {

    // ID(번호) 생성 어노테이션
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 시스템이 저장한 아이디 -> 데이터 구분을 위해

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
