package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
// @ComponentScan => component 들어가는 애노테이션 찾아 스프링 빈에 등록
@ComponentScan(
        // basePackages : 탐색할 패키지의 시작 위치를 지정, 이 패키지를 포함해서 하위 패키지를 모두 탐색
        // => 지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다
        basePackages  ="hello.core.member",
        // @ComponentScan.Filter , Configuration.class : Configuration 는 등록하지 않는 필터
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION ,classes = Configuration.class))
public class AutoAppConfig {
}
