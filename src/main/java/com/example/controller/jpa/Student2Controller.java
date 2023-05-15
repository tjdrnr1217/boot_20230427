package com.example.controller.jpa;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Student2;
import com.example.repository.library.Student2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/student2")
@RequiredArgsConstructor
@Slf4j
public class Student2Controller {

    final Student2Repository s2Repository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model){
        try {
            List<Student2> list = s2Repository.findAllByOrderBySnameAsc();
            log.info("{}",list.toString());
            model.addAttribute("list", list);
            return "/student2/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/join.do")
    public String joinGET(){
        try {
            return "/student2/join";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/join.do")
    public String insertPOST(@ModelAttribute Student2 obj){
        try {
            obj.setSpassword(bcpe.encode(obj.getSpassword()));
            log.info("{}",obj.toString());
            s2Repository.save(obj);
            return "redirect:/student2/join.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
}
