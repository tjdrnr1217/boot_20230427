package com.example.controller.jpa;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Menu1;
import com.example.entity.Menu1ImageProjection;
import com.example.entity.Restaurant1;
import com.example.repository.Address1Repository;
import com.example.repository.Menu1Repository;
import com.example.repository.Restaurant1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping(value = "/menu1")
@RequiredArgsConstructor
public class Menu1Controller {

    final Address1Repository address1Repository;
    final Restaurant1Repository restaurant1Repository;
    final Menu1Repository menu1Repository;

    @Value("${default.image}") String defaultImage;
    final ResourceLoader resourceLoader; // resource폴더의 파일을 읽기위한 객체 생성

    @GetMapping(value = "/update.food")
    public String updateGET(
        Model model,
        @RequestParam(name="no") long no,
        @RequestParam(name="rphone") String rphone
    ){
        try {
            Menu1 obj = menu1Repository.findById(BigInteger.valueOf(no)).orElse(null);
            model.addAttribute("obj", obj);
            model.addAttribute("rphone", rphone);
            return "/menu1/update";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/update.food")
    public String updatePOST(
        @ModelAttribute Menu1 obj,
        @RequestParam(name = "tmpFile") MultipartFile tmpFile)
    {
        try {
            log.info(obj.toString());
            // 기존데이터를 읽어서 필요한 부분 변경후 다시 저장하기
            obj.setImagedata(tmpFile.getInputStream().readAllBytes());
            obj.setImagename(tmpFile.getOriginalFilename());
            obj.setImagesize(BigInteger.valueOf(tmpFile.getSize()));
            obj.setImagetype(tmpFile.getContentType());
            menu1Repository.save(obj);
            
            return "redirect:/menu1/insert.food?rphone="+obj.getRestaurant1().getPhone();
        } catch (Exception e) {
            e.printStackTrace();
            return"redirect:/home.do";
        }
    }

    @PostMapping(value = "/delete.food")
    public String deletePOST(
        @RequestParam(name="no") long no,
        @RequestParam(name="rphone") String rphone
    ){
       try {
        log.info("{}",no);
        menu1Repository.deleteById(BigInteger.valueOf(no));
        return "redirect:/menu1/insert.food?rphone="+rphone;
       } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/home.do";
       }
        
    }

    // 이미지는 DB에서 꺼내서 url형태로 변경시켜야 함.
    // 127.0.0.1:9090/menu1/image?no=1
    // <img src="/ROOT/menu1/image?no=???/">
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") long no) throws IOException {
        Menu1ImageProjection obj =menu1Repository.findByNo(BigInteger.valueOf(no));

        HttpHeaders headers = new HttpHeaders();
        if(obj != null){
            if(obj.getImagesize().longValue()>0){
                headers.setContentType(MediaType.parseMediaType((obj.getImagetype())));
                return new ResponseEntity<>(obj.getImagedata(),headers,HttpStatus.OK);
            }
        }

        // 이미지가 없을 경우
        InputStream is = resourceLoader.getResource(defaultImage).getInputStream();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/insert.food")
    public String insertGET(Model model,@RequestParam( name = "rphone") String rphone){
        try {
            List<Menu1> menulist =menu1Repository.findByRestaurant1_phone(rphone);
            model.addAttribute("rphone", rphone);
            model.addAttribute("list", menulist);
            return "/menu1/insert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/insert.food")
    public String insertPOST(Menu1 menu1, @RequestParam(name = "tmpFile") MultipartFile tmpFile,
    @RequestParam(name = "rphone") String rphone){
        try {
            Restaurant1 res = new Restaurant1();
            menu1.setImagedata(tmpFile.getInputStream().readAllBytes());
            menu1.setImagename(tmpFile.getOriginalFilename());
            menu1.setImagesize(BigInteger.valueOf(tmpFile.getSize()));
            menu1.setImagetype(tmpFile.getContentType());
            res.setPhone(rphone);
            menu1.setRestaurant1(res);
            menu1Repository.save(menu1);
            return "redirect:/menu1/insert.food?rphone="+rphone;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
}
