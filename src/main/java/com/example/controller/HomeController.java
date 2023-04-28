package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 임포트 shift + alt + o
    // 127.0.0.1:9090/ROOT/home.do
    // 127.0.0.1:9090/ROOT/
    @GetMapping(value = { "/home.do","/"}) // 배열이라 여러 개 설정가능
    public String homeGET(Model model) {

        // request.setAttribute("key","value")
        model.addAttribute("title", "전송된타이틀");
        model.addAttribute("abc", "마음대로");
        // request.getRequestDispatcher("/WEB-INF/member/seller_home.jsp").forward(request,
        // response); 같은 기능이다
        return "home";
    }

    // 127.0.0.1:9090/ROOT/main.do
    @GetMapping(value = "/main.do")
    public String mainGET() {
        return "main";
    }
}
