package com.example.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Search;
import com.example.entity.Restaurant1;
import com.example.repository.Address1Repository;
import com.example.repository.Restaurant1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping(value = "/restaurant1")
@RequiredArgsConstructor
public class Restaurant1Controller {

    final Address1Repository address1Repository;
    final Restaurant1Repository restaurant1Repository;

    @PostMapping(value = "/delete.food")
    public String deletePOST(@RequestParam(name = "phone") String phone){
        try {
            log.info(phone);
            restaurant1Repository.deleteById(phone);
            return "redirect:/restaurant1/selectlist.food";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/insert.food")
    public String insertGET(){
        try {
            return "/restaurant1/insert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/insert.food")
    public String insertPOST(@ModelAttribute Restaurant1 restaurant1){
        try {
            restaurant1Repository.save(restaurant1);
            return "redirect:/restaurant1/selectlist.food";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }



    //127.0.0.1:9090/ROOT/restaurant1/selectlist.food?page=1&type=phone&text=
    @GetMapping(value="/selectlist.food")
    public String selectlistGET(Model model,
   @ModelAttribute Search obj){
        try {
          
            PageRequest pageRequest = PageRequest.of(obj.getPage()-1, 10);
            List<Restaurant1>list =restaurant1Repository.findByPhoneContainingOrderByNoDesc(obj.getText(), pageRequest);
            long total = restaurant1Repository.countByPhoneContaining(obj.getText());
            
            // List<Restaurant1> list = restaurant1Repository.selectByNameContainingPagenation(obj.getText(), (obj.getPage()*10)-9, obj.getPage()*10);
            if(obj.getType().equals("name")){
                list = restaurant1Repository.findByNameContainingOrderByNoDesc(obj.getText(), pageRequest);
                total = restaurant1Repository.countByNameContaining(obj.getText());
            }
            else if(obj.getType().equals("address")){
                list = restaurant1Repository.findByAddressContainingOrderByNoDesc(obj.getText(), pageRequest);
                total = restaurant1Repository.countByAddressContaining(obj.getText());
            }
            else if(obj.getType().equals("type")){
                list = restaurant1Repository.findByTypeContainingOrderByNoDesc(obj.getText(), pageRequest);
                total = restaurant1Repository.countByType(obj.getText());
            }
            model.addAttribute("search", obj);
            model.addAttribute("list", list);
            model.addAttribute("pages", (total-1)+1/10);

            // model.addAttribute("list", list);
            return "/restaurant1/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
}
