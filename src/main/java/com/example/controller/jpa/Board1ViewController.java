package com.example.controller.jpa;

import java.util.ArrayList;
import java.util.List;

import org.h2.engine.Mode.ModeEnum;
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

    // num이 0이면 또는 없으면 전체
    // num이 1이면 and
    // num이 2이면 or
    // num이 3이면 글번호 in
    // num이 4이면 제목 in

    @GetMapping(value = "/selectlist.pknu")
    public String selectlistGET(
            Model model,
            @RequestParam(name = "num", defaultValue = "0") int num,
            @RequestParam(name = "no", defaultValue = "0") String no,
            @RequestParam(name = "text", defaultValue = "") String text) {
        try {
            List<Board1View> list = new ArrayList<>();
            list = b1vRepository.findAll();

            if (num == 1) {
                list = b1vRepository.findByNoAndTitle(Long.parseLong(no), text);
            } else if (num == 2) {
                list = b1vRepository.findByNoOrTitle(Long.parseLong(no), text);
            } else if (num == 3) {
                List<Long> list1 = new ArrayList<>();
                String[] ret = no.split(",");
                for (int i = 0; i < ret.length; i++) {
                    list1.add(Long.parseLong(ret[i]));
                }
                list = b1vRepository.findByNoIn(list1);

            } else if (num == 4) {
                List<String> list2 = new ArrayList<>();
                String[] ret = text.split(",");
                for (int i = 0; i < ret.length; i++) {
                    list2.add(ret[i]);
                }
                list = b1vRepository.findByTitleIn(list2);

            }

            model.addAttribute("list", list);
            return "/board1view/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

}
