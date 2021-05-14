package tacos.web;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.Taco;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import javax.validation.Valid;
import org.springframework.validation.Errors;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;


@Slf4j

// @Controller : 해당 클래스가 컨트롤러로 식별되게 하며 컴포넌트 검색을 해야한다는 것을 나타냄
// => 스프링이 해당클래스를 찾은후 스프링 APP컴텍스트의 빈(Bean)으로 해당 클래스의 인스턴스를 자동 생성한다
@Controller

// @RequestMapping : 해당 클래스가 처리하는 요청의 종류
// 요청 경로가 disign 인 HTTP GET 요청 처리
@RequestMapping("/design")

// Order의 다수의 HTTP 요청에 걸처 존재 해야한다 => 다수의 타코를 생성하고 하나의 주문으로 추가할 수 있게 하기 위해
// 클래스 수즌의 @SessionAttributes 을 주문과 같은 모델 객체에 지정 => 세션에서 계속 보존되면서 다수의 요청에 걸쳐 사용될 수 있다
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    
    // TacoRepository를 주입하여 타코 저장시 사용
    private TacoRepository tacoRepo;

    @Autowired
    // showDesign(), processDesign() 메서드에서 사용 할 수 있도록 두 인자 모두 인스턴스 변수에 저장한다
    public DesignTacoController(IngredientRepository ingredientRepo,TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }

    // @GetMapping : /disign의 HTTP GET 요청이 수신될 때 그 요청을 처리하기 위해 showDesignForm() 메서드가 호출 되는것을 나타낸다
    @GetMapping
    public String showDesignForm(Model model){
        // Ingredient 객체 저장하는 List 생성
        
        // jdbc 연결때문에 삭제
       /* List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );*/
        // 식자재 내역 생성 => 식자재의 내역(유형)을 List 에서 필터링(filterByType 메서드) 한 후
        // showDesignForm()의 인자로 전달되는 Model 객체의 속성으로 추가한다
        // Model : 컨트롤러와 데이터를 보여주는 뷰 사이에서 데이터를 운반하는 객체

        // 위의 하드코딩 했던 Ingredient 객체의 List 대신 DB로부터 읽은 데이터로 생성한 Ingredient 객체의 List를 제공 할 수 있다
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        
        Type[] types = Type.values();
        for(Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("taco",new Taco());
        // design 을 리턴 한다 => 모델 데이터를 브라우저에 나타나는 데 사용될 뷰의 논리적인 이름
        return "design";
    }


    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    // @ModelAttribute(name = "order") : Order rorcprk ahepfdp todtjdehlehfhr gownsek
    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    // /design 경로의 POST 요청을 처리 => taco 를 디자인 하는 사용자가 제출한 것을 여기에서 처리한다
    @PostMapping
    // processDesign() 메서드에서는 taco 객체를 사용해서 어떤 것이든 원하는 처리를 할 수 있다
    // @Valid : 제출된 taco 객체의 유효성 검사를 수행 하라고 스프링 MVC 에 알려준다
    
    // @ModelAttribute => 매개변수의 값(order)이 모델로부터 전달되어야 한다는 것과 스프링 MVC가 이 매개변수에 요청 매개변수를 바인딩 하지 않아야 한다는 것을 나타낸다
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order){
        // Errors 객체의 hsErrors() 메서드를 호출하여 검사 에러가 있는지 확인
        // 에러가 있으면 taco 의 처리를 중지하고 "disign" 뷰 이름을 반환하여 폼이 다시 보이게 한다
        if(errors.hasErrors()){
            return "design";
        }
        //log.info("Processing design: " + design);

        // 주입된 TacoRepository(tacoRepo) 를 사용해 타코 저장 후
        Taco saved = tacoRepo.save(design);
        // 세션에 보존된 Order에서 Taco 객체를 추가한다
        order.addDesign(saved);


        // redirect : processDesign()의 실행이 끝난 후 사용자의 브라우저가 /orders/current 상대 경로로 재접속되어야 한다는 것을 나타낸다
        return "redirect:/orders/current";
    }


}
