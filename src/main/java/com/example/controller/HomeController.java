package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {
    @GetMapping(value = "/login.do")
    public String loginGET(){
        return  "login";
    }
    //127.0.0.1:9090/ROOT/home.do
    //임포트 shift +alt+o
@GetMapping(value={"/home.do", "/"})
public String homeGET(Model model, @AuthenticationPrincipal User user) {
  if(user != null){
    System.out.println(user.toString());
  }
  model.addAttribute("user", user);
    //templates/home.html
    return "home copy";
}
@GetMapping(value="/403page.do")
public String PageGET(){
    return "/error/403page";
}






//127.0.0.1:9090/ROOT/main.do
@GetMapping(value="/main.do")
public String mainGET() {
    return "main";
}
}
