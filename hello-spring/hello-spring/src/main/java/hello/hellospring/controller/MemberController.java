package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;

    // Autowired : memberservice를 스프링이 컨테이너에 있는 memberservice를 연결해 준다
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
