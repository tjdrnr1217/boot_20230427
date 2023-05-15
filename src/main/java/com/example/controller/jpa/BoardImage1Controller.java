package com.example.controller.jpa;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.example.entity.Board1;
import com.example.entity.BoardImage1;
import com.example.repository.Board1Repository;
import com.example.repository.BoardImage1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/boardimage1")
@RequiredArgsConstructor
@Slf4j
public class BoardImage1Controller {
    final String format = "BImage=>{}";
    final Board1Repository board1Repository;
    final BoardImage1Repository boardImage1Repository;
    @Value("${default.image}")
    String defaultImage;
    final ResourceLoader resourceLoader; // resource폴더의 파일을 읽기위한 객체 생성

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") long no) throws IOException {

        BoardImage1 obj = boardImage1Repository.findById(no).orElse(null);
        HttpHeaders headers = new HttpHeaders();
        if (obj != null) {
            if (obj.getImageSize() > 0) {
                headers.setContentType(MediaType.parseMediaType(obj.getImageType()));
                return new ResponseEntity<>(obj.getImageData(), headers, HttpStatus.OK);

            }
        }
        // InputStream is =
        // resourceLoader.getResource("classpath:/static/images/default.png").getInputStream();
        InputStream is = resourceLoader.getResource(defaultImage).getInputStream();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/insertimage.do")
    public String insertImagePOST(@ModelAttribute BoardImage1 image1,
            @RequestParam(name = "tmpFile") MultipartFile file) {
        try {
            image1.setImageSize(file.getSize());
            image1.setImageType(file.getContentType());
            image1.setImageData(file.getInputStream().readAllBytes());
            image1.setImageName(file.getOriginalFilename());
            // log.info(format, image1.toString());/
            boardImage1Repository.save(image1);

            return "redirect:/boardimage1/selectlist.do?no=" + image1.getBoard1().getNo();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/selectlist.do")
    public String selectListGET(Model model,HttpServletRequest request ,@RequestParam(name = "no") long no) {
        try {
            // 게시글 정보
            Board1 board1 = board1Repository.findById(no).orElse(null);
            model.addAttribute("board1", board1);
            // 대표이미지
            BoardImage1 image1 = boardImage1Repository.findTopByBoard1_noOrderByNoAsc(no);
            board1.setImageUrl(request.getContextPath()+"/boardimage1/image?no=0");
            if (image1 != null) {
                log.info(format, image1.toString());
                board1.setImageUrl(request.getContextPath()+"/boardimage1/image?no=" + image1.getNo());
            }
            List<String> imageList = new ArrayList<>();
            List<BoardImage1> imgno = boardImage1Repository.findByBoard1_no(no);
            if(!imgno.isEmpty()){
                for(BoardImage1 tmp : imgno)
                imageList.add(request.getContextPath()+"/boardimage1/image?no="+tmp.getNo());
            }
            model.addAttribute("imageList", imageList);
            return "/boardimage1/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
}
