package com.example.boot_20230427.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.boot_20230427.dto.Member;
import com.example.boot_20230427.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    
    @Autowired MemberService mService;
    final HttpSession httpSession; //세션 객체 생성

    @GetMapping(value = "/join.do")
    public String joinGET() {
        log.info("member={}", "joinGET");
        return "/member/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST( @ModelAttribute Member obj) {
        log.info("join.do POST => {}", obj.toString());
        int ret = mService.memberJoin(obj);
        if(ret == 1) {
            // 127.0.0.1:9090/ROOT/member/home.do
            return "redirect:/home.do"; // 성공시 홈으로
        }
        return "redirect:join.do";
        
    }

    @GetMapping(value = "/login.do")
    public String loginGET() {
        return "/member/login";
    }

    @PostMapping(value = "/login.do")
    public String loginPOST( @ModelAttribute Member member) {
        log.info("login.do => {}", member.toString());
        Member ret = mService.selectMemberOne(member);
        
        if(ret!=null){
            log.info("login.do => {}", ret.toString());
            // 세션에 2개의 정보 아이디와 이름 추가하기 ( 기본시간 30분 )
            // 다른페이지에서 세션의 아이디가 존재하는지 확인후 로그인 여부 판단
            httpSession.setAttribute("USERID", ret.getId());
            httpSession.setAttribute("USERNAME", ret.getName());
            httpSession.setAttribute("ROLE", ret.getRole());
            return "redirect:/home.do"; // 로그인 성공
        }
        
        return "redirect:login.do";
    }

    // GET, POST 같은 동작을 함
    @RequestMapping(value = "/logout.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutPOST() {
        httpSession.invalidate(); // 세션의 정보를 다 지움.
        return "redirect:/home.do";
    }

    @GetMapping(value = "/update.do")
    public String updateGET(){
        return "/member/update";
    }
    @PostMapping(value = "/update.do")
    public String updatePOST(@ModelAttribute Member member){
        log.info("update.do => {}", member.toString());
        int ret = mService.updateMemberOne(member);
        if(ret == 1) {
            log.info("update.do => {} ", ret);
            return "redirect:/home.do";    
        }
        return "redirect:update.do";
    }

    @GetMapping(value = "/delete.do")
    public String deleteGET(){
        return "/member/delete";
    }

    @PostMapping(value = "/delete.do")
    public String deletePOST(@ModelAttribute Member member){

        int ret = mService.deleteMemberOne(member);
        if(ret == 1) {
            httpSession.invalidate();
            return "redirect:login.do";
        }
        return "redirect:delete.do";
    }
}
