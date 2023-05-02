package com.example.boot_20230427.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    // 자동 임포트 shift + alt + o
    // 주소 url 작성(127.0.0.1:9090/CONTEXTPATH/home.do)
    @GetMapping(value = {"/home.do","/"})
    public String homeGET(Model model) {
        model.addAttribute("title", "전송된타이틀");
        model.addAttribute("abc", "마음대로");
        // templates / home.html 실행
        // VIEW request.getRequestDispatcher("WEB-INF/home.jsp").forward(request, response);
        return "home";
    }

    // 주소 url 작성(127.0.0.1:9090/ROOT/main.do)
    @GetMapping(value = "/main.do")
    public String mainGET(){

        return "main";
    }


}
