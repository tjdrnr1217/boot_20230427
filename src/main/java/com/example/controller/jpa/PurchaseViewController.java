package com.example.controller.jpa;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Board1;
import com.example.entity.PurchaseView;
import com.example.repository.PurchaseViewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/purchaseview")
@RequiredArgsConstructor
public class PurchaseViewController {

    final PurchaseViewRepository pvRepository;

    // 127.0.0.1:9090/ROOT/purchaseview/selectlist.pknu
    @GetMapping(value="/selectlist.pknu")
    public String selectlistGET(Model model){
        try{
            List<PurchaseView> list = pvRepository.findAll();
            model.addAttribute("list", list);
            return "/purchaseview/selectlist";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    

}