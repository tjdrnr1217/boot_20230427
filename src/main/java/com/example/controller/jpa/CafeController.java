package com.example.controller.jpa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cafe")
public class CafeController {

    @GetMapping(value = "/insert.json")
    public String insertGET(){
        try {
            return "/cafe/insert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
}
