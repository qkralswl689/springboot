package hello.itemservice.web.iitem.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    // 아이템 목록 출력
    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }

    // 제품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    // 상품 등록폼 - 폼을 보여만 준다
    @GetMapping("/add")
    public String addFrom() {
        return "basic/addForm";
    }

/*    //상품등록 처리
    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        // item을 model에 넣는다
        model.addAttribute("item", item);

        return "basic/item";
    }*/

    // 상품 등록 처리 - ModelAttribute
    /*
     * @ModelAttribute("item") Item item
     * model.addAttribute("item", item); 자동 추가
     */

/*    @PostMapping("/add")
    //ModelAttribute 가 item (모델)객체 만들어주고 네임, 프라이스, 수량 set을 불러온다
    public String addItemV2(@ModelAttribute("item") Item item*//*, Model model*//*) {
        
        itemRepository.save(item);

        // ModelAttribute : 모델에 데이터(item)를 넣어준다 => 위의 Model도 생략가능, "item"과 이름이 같아야한다
        //model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }*/

  /*  // 상품 등록 처리 - ModelAttribute 이름 생략
    *//**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     *//*
    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {

        itemRepository.save(item);
        
        // ModelAttribute의 이름을 생략하면 클래스명의 첫글자를 소문자로 바꾸어 넣어준다
        //model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }*/

/*    // 상품 등록 처리 - ModelAttribute 전체 생략
    *//**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     *//*
    @PostMapping("/add")
    public String addItemV4(Item item) {

        itemRepository.save(item);

        // ModelAttribute의 이름을 생략하면 클래스명의 첫글자를 소문자로 바꾸어 넣어준다
        //model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }*/

/*    // 상품 등록 처리 - PRG : 새로고침시 중복 데이터 저장 해결
    *//**
     * PRG - Post/Redirect/Get
     *//*
    @PostMapping("/add")
    public String addItemV5(Item item) {

        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }*/

    // RedirectAttributes - URL 인코딩 해결
    /**
     * RedirectAttributes
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {

        // 저장 결과 가져옴
        Item savedItem = itemRepository.save(item);

        // redirect와 관련된 속성을 넣는다
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    // 상품 수정 폼
    @GetMapping("/{itemId}/edit")
    // 어떤 상품을 수정 할 지 id넘어와야 한다
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);
        
        return "basic/editForm";
    }

    // 상품 수정 저장
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {

        itemRepository.update(itemId, item);
        
        return "redirect:/basic/items/{itemId}";
    }


}
