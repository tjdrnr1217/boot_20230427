package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// =>html을 표시할수없음.
//Map.Member Board List=> 를 반환하면 자동으로 json으로 바꿔줌
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Board;
import com.example.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController  
@RequestMapping(value = "/api/board")
@RequiredArgsConstructor
//GET 조회
//POST 추가, 로그인 보안요소
//DELETE 삭제
//PUT 전체수정
//PATCH 일부수정
@Slf4j
public class RestBoardController {
    final BoardMapper boardMapper;
    final String format ="RestBoard => {}";
    //게시글 조회수 증가
    //게시글 번호가 전달되면 update를 이용해서 게시글 증가 시키고 결과를 result:1, result:0
    @PutMapping(value = "/updatehit.json")
    public Map<String, Integer> updatehitPUT(@RequestBody Board board){
        long no= board.getNo();
         Board boa= boardMapper.selectBoardOne(no);
         log.info(format, boa.toString());
         Map<String, Integer> retMap = new HashMap<>();
        if(boa !=  null){
            long hit=boa.getHit();
            boa.setHit(hit+1);
       int ret= boardMapper.updateBoard(boa);
        retMap.put("result", ret);
    }
        return retMap;
    }
    //127.0.0.1:9090/ROOT/api/board/insert.json
    //게시판글쓰기 =>제목, 작성자, 내용 => {"title"="a", "content"="b", "writer":"c"}
    @RequestMapping(value = "/insert.json", method = {RequestMethod.POST})
    public Map<String, Integer> insertPOST(@RequestBody Board board){
        log.info(format, board.toString());
        //DB에 추가하고 결과를 1또는 0으로 반환
        int ret =boardMapper.insertBoardOne(board);
        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret);
        return retMap;
    }


    @GetMapping(value = "/select.json")
    public Map<String ,  String > selectGET(){
        Map<String, String> retMap = new HashMap<>();
        retMap.put("result", "ok");
        return retMap;
    }
 //127.0.0.1:9090
    @RequestMapping(value="/selectlist.json", method={RequestMethod.GET})
    public List<Board> requestMethodName() {
        //[{},{},{},{}........{}]
        return boardMapper.selectBoardList();
    }
    
}
