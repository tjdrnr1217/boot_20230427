package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Cafe;
import com.example.repository.Cafe.CafeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/cafe")
@RequiredArgsConstructor
@Slf4j
public class RestCafeController {
    
    final CafeRepository cafeRepository;

    //127.0.0.1:9090/ROOT/api/cafe/insert.json
    @PostMapping(value = "/insert.json")
    public Map<String, Object> insertPOST(@RequestBody Cafe obj){
        Map<String, Object> retmap = new HashMap<>();
        try {
            log.info("{}",obj.toString());
            cafeRepository.save(obj);
            retmap.put("status", 200);
            
        } catch (Exception e) {
            e.printStackTrace();
            retmap.put("status", -1);
            retmap.put("error",e.getMessage());
        }
        return retmap;
    }
    
}
