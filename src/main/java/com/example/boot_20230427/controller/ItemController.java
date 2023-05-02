package com.example.boot_20230427.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.boot_20230427.dto.Item;
import com.example.boot_20230427.dto.ItemImage;
import com.example.boot_20230427.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/item")
@RequiredArgsConstructor
public class ItemController {
    
    @Autowired
    ItemService iService;

    @Autowired ResourceLoader resourceLoader; // resources폴더의 파일을 읽기 위한 객체 생성
    
    @Value("${default.image}") String defaultImage;

    // <img src="@{/item/image(no=1)}">
    // 반환값이 String = html파일을 표시
    // 반환값이 ResponseEntity<byte[]> => 이미지, 동영상등을 표시 
    // 127.0.0.1:9090/ROOT/item/image?no=1
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") long no) throws IOException {

        ItemImage obj =iService.selectItemImageOne(no);
        HttpHeaders headers = new HttpHeaders(); // import org.springframework.http.HttpHeaders;

        if(obj != null){ // 이미지 존재 확인
            if(obj.getFilesize()>0) {
                headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));
                ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(obj.getFiledata(), headers, HttpStatus.OK);
                return response;
            }
        }

        // 이미지가 없을 경우
        InputStream is = resourceLoader.getResource(defaultImage).getInputStream(); // exception 발생
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(is.readAllBytes(), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/insertimage.do")
    public String insertimagePOST(@ModelAttribute ItemImage obj, @RequestParam(name = "file1") MultipartFile file1) throws IOException {

        obj.setFilename(file1.getOriginalFilename());
        obj.setFilesize(file1.getSize());
        obj.setFiletype(file1.getContentType());
        obj.setFiledata(file1.getBytes()); // exception발생
        System.out.println(obj.toString());

        int ret = iService.insertItemImageOne(obj);
        if(ret ==1 ) {
            return "redirect:selectlist.do";
        }
        return "redirect:insertimage.do?no="+obj.getItemno();
    }

    // item/insertimage.do?no=7 => name값은 no이고 value값은 7숫자가 전달됨.
    // <input type="text" name="no" value="7" />
    @GetMapping(value = "/insertimage.do")
    public String insertimageGET(@RequestParam(name = "itemno", defaultValue = "0", required = false) long no, Model model) {
        if(no==0) {
            return "redirect:selectlist.do"; // 상대경로로 이동, 가장 마지막 주소만 변경해서 이동
        }

        // 현재 물품에 해당하는 이미지 번호
        List<Long> imgNo = iService.selectItemImageNo(no);
        System.out.println("insertimage.do=>"+imgNo.toString());
        
        model.addAttribute("imgno", imgNo);
        model.addAttribute("itemno",no);
        return "/item/insertimage"; // resources/templates/item폴더/insertimage.html
    }

    @GetMapping(value = "/selectlist.do")
    public String selectGET(Model model){

        // 1. 서비스를 호출하여 물품목록받기
        List<Item> list = iService.selectItemList();
        
        // 2. model을 이용하여 view로 받은 목록 전달하기
        model.addAttribute("list", list);

        // 3. view를 화면에 표시하기
        return "/item/selectlist"; // resources/templates/item/selectlist.html
    }
}
