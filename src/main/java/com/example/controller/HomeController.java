package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    // 127.0.0.1:9090/ROOT/login.do
    @GetMapping(value = "/login.do")
    public String loginGET() {
        return "login"; //login.html
    }

    // 127.0.0.1:9090/ROOT/home.do
    @GetMapping(value = {"/home.do","/"})
    public String homeGET(Model model, @AuthenticationPrincipal User user) {
        if(user != null){ //로그인 되었음
            System.out.println(user.toString());
        }
        model.addAttribute("user", user);
        return "home";
    }

    // 127.0.0.1:9090/ROOT/403page.do
    @GetMapping(value = "/403page.do")
    public String PageGET(){
        return "403page";
    }

}
