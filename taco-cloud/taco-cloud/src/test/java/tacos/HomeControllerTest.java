package tacos;
import static org.hamcrest.Matchers.containsString;
import static
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


// HomeController의 웹 페이지 테스트
@WebMvcTest
public class HomeControllerTest {
    // MockMvc 주입
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
                        // GET /을 수행
        mockMvc.perform(get("/"))
                // HTTP 200이 되어야 한다
                .andExpect(status().isOk())
                // home 뷰가 있어야 한다
                .andExpect(view().name("home"))
                // 콘텐츠에 'Welcome to 가 포함 되어야 한다
                .andExpect(content().string(
                        containsString("Welcome to...")));
    }
}
