package com.example.controller;

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

import com.example.dto.Item;
import com.example.dto.ItemImage;
import com.example.mapper.ItemMapper;
import com.example.service.ItemService;

@Controller
@RequestMapping(value = "/item")
public class ItemController {
    @Autowired ItemService iService;
    @Autowired ItemMapper itemMapper;//서비스사용x 수업용
    @Autowired ResourceLoader resourceLoader;
    @Value("${default.image}") String defaultImage;
    //<img src="@{/item/image(no=1)}"
    //String =html을 표시
    //ResponseEntity 이미지 동영상들을 표시
    //127.0.0.1:9090/ROOT/item/image?no=1
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name="no", defaultValue = "0")long no) throws IOException{
        ItemImage obj = itemMapper.selectItemImageOne(no);
    HttpHeaders headers = new HttpHeaders();
        if(obj!=null){
            if(obj.getFilesize()>0){
                headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));
                return  new ResponseEntity<>(obj.getFiledata(),headers, HttpStatus.OK);
               
            }
        }
       // InputStream is = resourceLoader.getResource("classpath:/static/images/default.png").getInputStream();
       InputStream is = resourceLoader.getResource(defaultImage).getInputStream();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }

    //127.0.0.1:9090/ROOT/item/selectlist.do
    @GetMapping(value = "/selectlist.do")
    public String selectListGET(Model model){
        //1.서비스 호출하여 물품목록받기
        List<Item> list = iService.itemSelectList();
        //2.model을 이용하여 view로 받은 목록 전달하기
        model.addAttribute("list", list);
        //3.view를 화면에 표시하기
        return "/item/selectlist"; //resources/templates /item폴더생성 selectlist.html을 생성
    }

    @GetMapping(value = "/insertimage.do")
    public String insertitemimageGET(@RequestParam(name="no", defaultValue="0", required=false) long no,Model model){
        if(no==0){
            return "redirect:selectlist.do";
        }
        model.addAttribute("itemno", no);
        //현재물품에 해당하는 이미지번호
        List<Long> imgNo = itemMapper.selectItemImageNo(no);
        model.addAttribute("imgno", imgNo);
        model.addAttribute("itemno", no);
        return "/item/insertitemimage";
    }
    @PostMapping(value="/insertimage.do")
    public String insertitemimagePOST(@ModelAttribute ItemImage obj, @RequestParam(name = "file1") MultipartFile file1) throws IOException{
        obj.setFilename(file1.getOriginalFilename());
        obj.setFilesize(file1.getSize());
        obj.setFiletype(file1.getContentType());
        obj.setFiledata(file1.getBytes());
        System.out.println(obj.toString());
        int ret=iService.insertitemimage(obj);
        if(ret==1){
            return "redirect:insertimage.do?no="+obj.getItemno();

        }
        return "redirect:insertimage.do?no="+obj.getItemno();
    }    



}
