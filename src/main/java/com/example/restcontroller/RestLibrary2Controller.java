package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Library2;
import com.example.repository.library.Library2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/library2")
@RequiredArgsConstructor
@Slf4j
public class RestLibrary2Controller {

    final Library2Repository l2Repository;

    @GetMapping(value = "/selectlist.json")
    public Map<String, Object> selectlistGET(){
        Map<String,Object> retmap = new HashMap<>();
        try {
            List<Library2> list = l2Repository.findAllByOrderByLnameAsc();
            log.info("{}",list.toString());
            retmap.put("status", 200);
            retmap.put("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            retmap.put("status", -1);
            retmap.put("status", e.getMessage());
        }
        return retmap;
    }

    // 127.0.0.1:9090/ROOT/api/library2/insert.json
    // @RequestBody Library2 obj => 기본으로 보낼때
    // @ModelAttribute Library2 obj => 위에 방식이 불가능할때 이미지...
    @PostMapping(value = "/insert.json")
    public Map<String, Object> insertPOST(@RequestBody Library2 obj){
        Map<String, Object> retmap = new HashMap<>();
        try {
            log.info("{}",obj.toString());
            l2Repository.save(obj); 
            retmap.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            retmap.put("status", -1);
            retmap.put("error",e.getMessage());
        }
        return retmap;
    }
    
}
