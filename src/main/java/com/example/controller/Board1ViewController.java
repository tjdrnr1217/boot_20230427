package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Board1View;
import com.example.repository.Board1ViewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/board1view")
@RequiredArgsConstructor
public class Board1ViewController {

    final Board1ViewRepository b1vRepository;
    final String format = "Board1viewController => {}";

    @GetMapping(value = "/selectlist.pknu")
    public String selectlistGET(Model model,
            @RequestParam(name = "num", defaultValue = "0", required = false) int num,
            @RequestParam(name = "brdno", defaultValue = "0", required = false) String brdno,
            @RequestParam(name = "brdtitle", defaultValue = "", required = false) String brdtitle) {
        try {
            List<Board1View> list = new ArrayList<>();
            // num 0 또는 없으면 전체
            // 1 and
            // 2 or
            // 3 글번호 in
            // 4 제목 in

            if (num == 0) {
                log.info(format, num);
                list = b1vRepository.findAll();
            } else if (num == 1) {
                // log.info(format, num);
                list = b1vRepository.findByNoAndTitleOrderByNoAsc(Long.parseLong(brdno), brdtitle);
            } else if (num == 2) {
                // log.info(format, num);
                list = b1vRepository.findByNoOrTitleOrderByNoAsc(Long.parseLong(brdno), brdtitle);
            } else if (num == 3) {
                // log.info(format, num);
                // log.info(format, brdno);
                List<Long> brdNolist = new ArrayList<>();
                String[] ret = brdno.split(",");                
                for (int i = 0; i < ret.length; i++) {
                    log.info(format, ret[i]);
                    brdNolist.add(Long.parseLong(ret[i].trim()));
                }
                // log.info(format, brdNolist);
                list = b1vRepository.findByNoInOrderByNoAsc(brdNolist);
            } else if (num == 4) {
                log.info(format, num);
                List<String> brdTitlelist = new ArrayList<>();
                String[] ret = brdtitle.split(",");
                for (int i = 0; i < ret.length; i++) {
                    brdTitlelist.add(ret[i].trim());
                }
                list = b1vRepository.findByTitleInOrderByNoAsc(brdTitlelist);
            }

            model.addAttribute("list", list);
            return "/board1view/selectlist";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";

        }

    }

}