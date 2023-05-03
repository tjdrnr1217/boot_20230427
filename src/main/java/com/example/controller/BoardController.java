package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Board;
import com.example.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/board")
@RequiredArgsConstructor //클래스에서만 가능함, 인터페이스에서는 안됨.
public class BoardController {
@Autowired BoardService boardService;

    @GetMapping(value={"/selectone.do"})
    public String selectoneGET(@RequestParam(name="no", defaultValue="0", required=false) long no, Model model){
        if(no==0){
            return "redirect:select.do";
        }
        Board obj = boardService.SelectBoardOne(no);
        model.addAttribute("obj", obj);
        System.out.println(no);
        return "/board/selectone";
    }

    @GetMapping(value="/select.do")
    public String selectGET(Model model){
        List<Board> list = boardService.selectBoardList();
        model.addAttribute("list", list);
        return "/board/select";
    }

    @GetMapping(value="/insert.do")
    public String insertGET(){
        return "/board/insert";
    }
    
    @GetMapping(value="/update.do")
    public String updateGET(@RequestParam(name="no",defaultValue ="0", required=false)long no,Model model){
        System.out.println(no);
        if(no==0){
            return "redirect:select.do";
        }
         Board obj = boardService.SelectBoardOne(no);
         model.addAttribute("obj", obj);
        return "/board/update";
    }
    @PostMapping(value="/update.do")
    public String updatePOST(@ModelAttribute Board board){
        int ret = boardService.UpdateBoard(board);
        
        System.out.println(ret);
        if(ret==1){
            return "redirect:selectone.do?no="+board.getNo();
        }
        return "redirect:update.do?no="+board.getNo(); //실패시 수정화면
    }

    @PostMapping(value="/insert.do")
    public String insertPost (@ModelAttribute Board board){
        // @RequestParam(name= "title") String title,
        // @RequestParam(name= "content") String content,
        // @RequestParam(name= "writer") String writer    ){
        //     System.out.println(title);
        //     System.out.println(content);
        //     System.out.println(writer);
        int ret = boardService.insertBoardOne(board);
        if(ret==1){
            return "redirect:select.do";
        }
            return "redirect:insert.do";
        }


        @PostMapping(value="/delete.do")
        public String deletePost(@RequestParam(name="no",defaultValue ="0", required=false) long no
        ){
            System.out.println(no);
            if(no==0){
                return "redirect:select.do";
            }
             int ret = boardService.DeleteBoard(no);
           if(ret==0){
               return "/board/selectone.do?no="+no;
            }
            return "redirect:select.do";
        }
}
