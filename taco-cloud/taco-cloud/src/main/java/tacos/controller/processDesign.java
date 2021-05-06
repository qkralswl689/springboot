package tacos.controller;

import org.springframework.web.bind.annotation.PostMapping;
import tacos.domain.Taco;

public class processDesign {
    @PostMapping
    public String processDesign(Taco design){
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }
}
