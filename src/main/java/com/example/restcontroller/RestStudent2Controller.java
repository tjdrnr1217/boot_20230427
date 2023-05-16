package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    final JwtUtil2 jwtUtil2; // 컴포넌트 객체 생성
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


    // 회워탈퇴, 비밀번호변경, 회원정보수정 ... 로그인이 되어애 되는 모든것.
    // 회원정보수정 => 토큰을 주세요. 검증해서 성공하면 정보수정을 진행.
    @PostMapping(value="/update.json")
    public Map<String, Object> updatePOST(@RequestHeader(name = "token") String token) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            // 1. 토큰을 받아서 출력
            log.info("{}", token);

            // 2. 실패시 전달값
            retMap.put( "status", 0 );
            
            // 3. 토큰을 검증 후 성공
            if ( jwtUtil2.checkJwt(token) == true) {
                //3. 정보를 수정함.
                retMap.put( "status", 200 );
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            retMap.put( "status", -1 );
            retMap.put( "error", e.getMessage() );
        }
        return retMap;
    }

    //127.0.0.1:9090/ROOT/api/student2/login.json
    @PostMapping(value="/login.json")
    public Map<String, Object> loginPOST(@RequestBody Student2 student2) {
    Map<String, Object> retMap = new HashMap<>();
        try {
            // 1. 이메일, 암호 전송 확인
            log.info("{}", student2.toString());

            // 2. 이메일을 이용해서 정보를 가져옴.
            Student2 retStudent2 = s2Repository.findById(student2.getSemail()).orElse(null);

            // 3. 실패시 전송할 데이터
            retMap.put( "status", 0 );

            // 4. 암호가 일치하는지 확인 => 전송된 hash되지 않은 암호와 DB에 해시된 암호 일치 확인
            if(  bcpe.matches( student2.getSpassword(), retStudent2.getSpassword()) ) {
                retMap.put( "status", 200 );
                retMap.put( "token", jwtUtil2.createJwt(retStudent2.getSemail(), retStudent2.getSname() ) );
                retMap.put("students",retStudent2.getSemail());

            }
        } catch (Exception e) {
            e.printStackTrace(); 
            retMap.put( "status", -1 );
            retMap.put( "error", e.getMessage() );
        }
        return retMap;
    }

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
