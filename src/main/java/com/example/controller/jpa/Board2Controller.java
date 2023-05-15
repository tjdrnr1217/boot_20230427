package com.example.controller.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Board;
import com.example.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/board2")
@RequiredArgsConstructor
public class Board2Controller {

    final String format = "Board2Controller => {}";
    final BoardRepository bRepository;
    final HttpSession httpSession;
    final int PAGETOTAL = 10;

    @PostMapping(value = "/updatebatchaction.pknu")
    public String updatePOST(
            @RequestParam(name = "no[]") long[] no,
            @RequestParam(name = "title[]") String[] title,
            @RequestParam(name = "content[]") String[] content,
            @RequestParam(name = "writer[]") String[] writer) {
        try {
            List<Board> list = new ArrayList<>();
            for (int i = 0; i < no.length; i++) {
                // 1. no를 이용하여 기존 정보 가져오기
                Board obj = bRepository.findById(BigDecimal.valueOf(no[i])).orElse(null);
                // 2. 기존 정보에 위에서 받은 제목, 내용, 작성자 변경하기
                obj.setTitle(title[i]);
                obj.setContent(content[i]);
                obj.setWriter(writer[i]);

                // 3. list에 담기
                list.add(obj);

            }
            // 4. 일괄 저장
            bRepository.saveAll(list);

            return "redirect:/board2/selectlist.pknu";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = "/updatebatch.pknu")
    public String updateBatchGET(Model model) {
        try {
            List<BigDecimal> chk = (List<BigDecimal>) httpSession.getAttribute("chk[]");
            log.info(format, chk);
            List<Board> list = bRepository.findAllById(chk);
            model.addAttribute("list", list);
            return "/board2/updatebatch";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }

    }

    @PostMapping(value = "/updatebatch.pknu")
    public String updateBatchPOST(@RequestParam(name = "chk[]") List<BigDecimal> chk) {
        try {
            // log.info(format,chk);

            httpSession.setAttribute("chk[]", chk);
            return "redirect:/board2/updatebatch.pknu";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/deletebatch.pknu")
    public String deleteBatchPOST(@RequestParam(name = "chk[]") List<BigDecimal> chk) {
        try {
            log.info(format, chk);

            bRepository.deleteAllById(chk);
            return "redirect:/board2/selectlist.pknu";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 1. 전달값 받기
    // page=1&type=title&text=t
    @GetMapping(value = "/selectlist.pknu")
    public String selectListGET(Model model,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "type", defaultValue = "0", required = false) String type,
            @RequestParam(name = "text", defaultValue = "0", required = false) String text) {
        try {
            // 2. 타입에 따라서 다른 메소드 호출
            List<Board> list = new ArrayList<>();
            log.info(format, page);
            log.info(format, type);
            log.info(format, text);
            PageRequest pageRequest = PageRequest.of(page - 1, PAGETOTAL);
            long listsize = 0;
            if (text.equals("0") || type.equals("0")) {
                list = bRepository.findAllByOrderByNoDesc(pageRequest);
                log.info(format, "첫번째 if");
                model.addAttribute("list", list);
                // 전체 개수 가져오기
                long total = bRepository.count();
                model.addAttribute("pages", (total - 1) / PAGETOTAL + 1);
                return "/board2/selectlist";
            } else {
                if (type.equals("title")) {
                    log.info(format, "두번째 if");
                    listsize = bRepository.countByTitleIgnoreCaseContainingOrderByNoDesc(text);
                    list = bRepository.findByTitleIgnoreCaseContainingOrderByNoDesc(text, pageRequest);

                } else if (type.equals("content")) {
                    log.info(format, "세번째 if");
                    listsize = bRepository.countByContentIgnoreCaseContainingOrderByNoDesc(text);
                    list = bRepository.findByContentIgnoreCaseContainingOrderByNoDesc(text, pageRequest);

                } else if (type.equals("writer")) {
                    log.info(format, "네번째 if");
                    listsize = bRepository.countByWriterIgnoreCaseContainingOrderByNoDesc(text);
                    list = bRepository.findByWriterIgnoreCaseContainingOrderByNoDesc(text, pageRequest);

                }
            }
            log.info(format, list);            
            
            model.addAttribute("pages", (listsize - 1) / PAGETOTAL + 1);
            model.addAttribute("list", list);
            return "/board2/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }

    }

    @GetMapping(value = "/insertbatch.pknu")
    public String insertbatchGET(Model model) {
        try {
            return "/board2/insertbatch";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }

    }

    @PostMapping(value = "/insertbatch.pknu")
    public String insertbatchPOST(
            @RequestParam(name = "title[]") String[] title,
            @RequestParam(name = "content[]") String[] content,
            @RequestParam(name = "writer[]") String[] writer) {
        try {
            List<Board> list = new ArrayList<>();
            for (int i = 0; i < title.length; i++) {
                Board obj = new Board();
                obj.setTitle(title[i]);
                obj.setContent(content[i]);
                obj.setWriter(writer[i]);
                obj.setHit(BigDecimal.valueOf(1));

                list.add(obj);
            }

            bRepository.saveAll(list);
            return "redirect:/board2/selectlist.pknu";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }

    }
}
