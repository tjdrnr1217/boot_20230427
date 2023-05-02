package com.example.boot_20230427.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.boot_20230427.dto.Board;
import com.example.boot_20230427.service.BoardService;

import lombok.RequiredArgsConstructor;



// shift+alt+o
// ctrl+k,f

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor // 클래스에서만 가능함, 인터페이스에서는 불가능
public class BoardController {

    // 서비스 객체 생성
    // final BoardService bService;
    @Autowired BoardService bService;

    // 127.0.0.1:9090/ROOT/board/select.do
    @GetMapping(value = "/select.do")
    public String selectGET(Model model) {
        List<Board> list = bService.selectBoardList();
        model.addAttribute("list", list);


        return "/board/select";
    }

    // 127.0.0.1:9090/ROOT/board/insert.do
    @GetMapping(value = "/insert.do")
    public String insertGET() {
        return "/board/insert";
    }

    // 127.0.0.1:9090/ROOT/board/update.do
    @GetMapping(value = "/update.do")
    public String updateGET(@RequestParam(name = "no", defaultValue = "0", required = false) long no, Model model) {
        System.out.println(no);

        if(no == 0){
            return "redirect:select.do";
        }

        Board board = bService.selectBoardOne(no);

        System.out.println(board);
        model.addAttribute(board); // 키 값 생략 변수명이 곧 키 값

        return "/board/update";
    }
    // 게시판 글쓰기    
    @PostMapping(value = "/insert.do")
    public String insertPost( @ModelAttribute Board board ) 
    { // dto로 바로 값을 받기 중요한 점! dto의 변수 값과 html의 name값을 일치시켜야만 modelattribute annotation 사용 가능 값을 하나씩 받으려면 @RequestParam("html name값") [데이터타입][담을변수명]
        System.out.println(board.toString());

        int ret = bService.insertBoardOne(board);
        System.out.println(ret);
        if(ret == 1 ) {
            return "redirect:select.do";
        }
        // 실패시
        return "redirect:insert.do";
    }
    @GetMapping(value={"/selectone.do"})
    public String selectoneGET(@RequestParam(name = "no", defaultValue = "0", required = false) long no, Model model) {

        System.out.println(no);

        if(no == 0){
            return "redirect:select.do";
        }

        Board board = bService.selectBoardOne(no);

        System.out.println(board);
        model.addAttribute(board); // 키 값 생략 변수명이 곧 키 값

        return "/board/selectone";
    }

    @PostMapping(value = "update.do")
    public String updatePOST(@ModelAttribute Board board) {
        System.out.println(board);

        int ret = bService.updateBoardOne(board);
        if( ret == 1){
            return "redirect:select.do";
        }
        return "redirect:update.do?no="+board.getNo();
        
    }
    @PostMapping(value = "delete.do")
    public String deletePOST(@RequestParam(name = "no", defaultValue = "0", required = false) long no) {
        int ret = bService.deleteBoardOne(no);
        if(ret == 1) {
            return "redirect:select.do";
        }
        return "redirect:selectone.do?no="+no;
    }
    
}
