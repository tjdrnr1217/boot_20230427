package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Board1;
import com.example.repository.Board1Repository;
import com.example.repository.Reply1Repository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/board1")
@RequiredArgsConstructor
// @Slf4j
public class Board1Controller {
    final Board1Repository board1Repository;
    final Reply1Repository reply1Repository;
    final String format ="Board1Controller=>{}";

    @GetMapping(value = "/selectone.do")
    public String selectoneGET(Model model, @RequestParam(name="no") Long no){
        
         model.addAttribute("obj",board1Repository.findById(no).orElse(null) );
        return "/board1/selectone";
    }

    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model){
           List<Board1> list= board1Repository.findAllByOrderByNoDesc();
           model.addAttribute("list", list);
        return "/board1/selectlist";
    }
    

    @GetMapping(value = "/insert.do")
    public String insertGET(){
        return "/board1/insert";
    }

    @PostMapping(value = "/insert.do")
    public String insertPOST(@ModelAttribute Board1 board1){
        // log.info(format, board1.toString());
        board1Repository.save(board1);
        return "redirect:/board1/selectlist.do";
    }

}
