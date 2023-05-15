package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Student2;
import com.example.entity.Student2Projection;
import com.example.repository.library.Student2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/student2")
@RequiredArgsConstructor
public class RestStudent2Controller {

    final Student2Repository s2Repository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 이메일 중복확인용
    // GET은 @RequestBody 쓰지 않는 것이 좋다 
    @GetMapping(value = "/emailcheck.json")
    public Map<String,Object> emailcheckGET(@RequestParam(name = "semail") String semail){
        Map<String,Object> retmap = new HashMap<>();
        try {
            retmap.put("status", 200);
            retmap.put("chk",s2Repository.countBySemail(semail));
        } catch (Exception e) {
            e.printStackTrace();
            retmap.put("status", -1);
            retmap.put("status", e.getMessage());
        }
        return retmap;
    }
    
    // 이메일이 전달되면 회원의 이름, 연락처가 반환됨.
    
    @GetMapping(value = "/selectone.json")
    public Map<String,Object> selectoneGET(@RequestParam(name = "semail") String semail){
        Map<String,Object> retmap = new HashMap<>();
        try {
            Student2Projection obj2 = s2Repository.findBySemail(semail);
            log.info("{}",obj2.toString());
            retmap.put("status", 200);
            retmap.put("obj", obj2);
        
        } catch (Exception e) {
            e.printStackTrace();
            retmap.put("status", -1);
            retmap.put("status", e.getMessage());
        }
        return retmap;
    }

    @GetMapping(value = "/selectlist.json")
    public Map<String,Object> selectlistGET(){
        Map<String,Object> retmap = new HashMap<>();
        try {
            List<Student2> list = s2Repository.findAllByOrderBySnameAsc();
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

    // 127.0.0.1:9090/ROOT/api/student2/join.json
    @PostMapping(value = "/join.json")
    public Map<String,Object> joinPOST(@RequestBody Student2 obj){
        Map<String,Object> retmap = new HashMap<>();
        try {
            obj.setSpassword(bcpe.encode(obj.getSpassword()));
            log.info("{}",obj.toString());
            s2Repository.save(obj);
            retmap.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            retmap.put("status", -1);
            retmap.put("error", e.getMessage());
        }
        return retmap;
    }
    
}
