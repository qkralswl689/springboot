package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 뷰 컨트롤러의 역할을수행하는 구성 클래스
// WebMvcConfigurer : 스프링 MVC를 구성하는 메서드를 정의하는 인터페이스
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    // addViewControllers()는 하나 이상의 뷰 컨트롤러를 등록하기 위해 사용할 수 있는
    // ViewControllerRegistry 를 인자로 받는다
    public void addViewControllers(ViewControllerRegistry registry) {
        // 뷰 컨트롤러가 GET 요청을 처리하는 경로인 "/" 를 인자로 전달해
        // addViewController()를 호출한다 => ViewControllerRegistry 객체를 반환한다
        // setViewName("home") : "/" 경로의 요청이 전달되어야 하는 뷰로 home 을 지정
        registry.addViewController("/").setViewName("home");
    }
}
