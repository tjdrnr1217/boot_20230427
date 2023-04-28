package com.example.controller;

import java.util.List;


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
@RequiredArgsConstructor // 클레스에서만 가능함, 인터페이스에서는 안됨.
public class BoardController {

    // @Autowired
    // BoardService bService;
    final BoardService bService;
    //게시판 글쓰기
    @PostMapping(value = "/insert.do")
    public String insertPost(@ModelAttribute Board board){
        // 7줄을 한줄로 줄인것이다
        System.out.println(board.toString()); 

        int ret = bService.insertBoardOne(board);
        if(ret == 1){//성공시
            //적절한 페이지로 이동(게시판목록으로)
            return "redirect:select.do";
        }
       // 실패시
       return "redirect:insert.do";
    }

    // 게시글조회
    @GetMapping(value = {"/selectone.do"})
    public String selectOneGET(@RequestParam(name = "no", defaultValue = "0", required = false) long no, Model model){
        Board board = bService.selectBoardOne(no);
        if(no == 0){
            return "redirect:select.do";
        }
        model.addAttribute("brd", board);
        System.out.println(no);
        return "/board/selectone"; //board폴더에 selectone.html 생성하기
    }
    
    //게시글 전체조회
    // 127.0.0.1:9090/ROOT/board/select.do
    @GetMapping(value="/select.do")
    public String selectGET(Model model){
        List<Board> list = bService.selectBoardList();

        model.addAttribute("list",list);

        // templates에서 board폴더를 생성하고 select.html 생성
        return "/board/select";
    }

    // 게시글 추가
    // 127.0.0.1:9090/ROOT/board/insert.do
    @GetMapping(value="/insert.do")
    public String insertGET(){
        return "/board/insert";
    }

    // 게시글 수정
    // 127.0.0.1:9090/ROOT/board/update.do
    @GetMapping(value="/update.do")
    public String updateGET(@RequestParam(name = "no", defaultValue = "0", required = false) long no, Model model){
        if( no == 0){
            return "redirect:select.do";
        }
        Board obj = bService.selectBoardOne(no);
        model.addAttribute("obj", obj);
        return "/board/update";
    }

    // 게시글 수정
    @PostMapping(value="/update.do")
    public String updatePost(@ModelAttribute Board board){
        int ret = bService.updateBoardOne(board);

        if(ret == 1){
            return"redirect:selectone.do?no="+board.getNo();//성공시 상세 화면
        }
        return "/board/update.do?no="+board.getNo();
    }

    // 게시글삭제
    @PostMapping(value = "/delete.do")
    public String deletePost(@RequestParam(name = "no", defaultValue = "0", required = false) long no) {
        System.out.println(no);
        if (no == 0) {
            return "redirect:select.do";
        }
        int ret = bService.deleteBoardOne(no);
        System.out.println(ret);
        if (ret == 1) {
            return "redirect:select.do";
        }
        return "redirect:boardone.do?no=" + no;
    }

}
