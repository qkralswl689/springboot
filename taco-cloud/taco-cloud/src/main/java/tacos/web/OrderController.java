package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.data.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
// @RequestMapping : /orders 로 시작되는 경로의 요청을 이 컨트롤러의 요청 처리 메서드가 처리한다는 것을 알려주는 애노테이션
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }
    // order 객체 세션 보전하기위해 아래처럼 변경
/*    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }*/
    
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    // Order 유효성 검사
    @PostMapping
    // processOrder() 메서드는  SessionStatus를 인자로 전달받아 
    // setComplete() 메서드를 호출하여 세션을 재설정 한다
      public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {

            // orderForm 을 리턴한다
            return "orderForm";
        }

        //log.info("Order submitted: " + order);

        // processOrder() 메서드에서 주입된 OrderRepository 의 save() 메서드를 통해 폼에서 제출된 Order 객체를 저장한다
        // => Order 객체도 세션에 보존되어야 한다
        orderRepo.save(order);

        // 세션 재설정
        sessionStatus.setComplete();
        
        return "redirect:/";
    }
}
