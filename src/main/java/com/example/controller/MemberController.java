package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/member")
@Slf4j
@RequiredArgsConstructor //롬복 객체생성 final써야댐
public class MemberController {
final MemberMapper mMapper; //매퍼객체 생성하기
final HttpSession httpSession;//세션 객체 생성하기
    @GetMapping(value = "/join.do")
    public String joinGET(@ModelAttribute Member obj) {
        log.info("member={}", "joinGET");
        return "/member/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member obj) {
        log.info("join.do POST=>{}" ,obj.toString());
        int ret= mMapper.insertMemberOne(obj);
        if(ret==1){
            return "redirect:/home.do";
        }
        return "redirect:/join.do";
    }

    @GetMapping(value = "/update.do")
    public String updateGET(@ModelAttribute Member obj, Model model) {
        log.info("member={}", "updateGET");
        String id =(String)httpSession.getAttribute("USERID");
        obj.setId(id);
        Member m = mMapper.selectMemberone(obj);
        model.addAttribute("mem", m);
        return "/member/update";
    }

    @PostMapping(value = "/update.do")
    public String updatePOST(@ModelAttribute Member obj) {
        String id =(String)httpSession.getAttribute("USERID");
        obj.setId(id);
        System.out.println(obj.toString());
        log.info("update.do POST=>{}" ,obj.toString());
        int ret= mMapper.updateMember(obj);
        if(ret==1){
            return "redirect:/home.do";
        }
        return "redirect:update.do";
    }

   @RequestMapping(value = "delete.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String deletePOST() {
       String id =(String)httpSession.getAttribute("USERID");
       int ret= mMapper.deleteMember(id);
       if(ret==1){
           return "redirect:login.do";
       }
       return "redirect:home.do";
    }


    @GetMapping(value = "/login.do")
    public String loginGET() {

        return "/member/login";
    }

    @PostMapping(value = "/login.do")
    public String loginPOST(@ModelAttribute Member member) {
        log.info("login.do => {}",member.toString());
        Member ret = mMapper.loginMember(member);
        if(ret !=null){
            //세션에 3개의 정보아이디와 이름, 권한 추가하기 기본 시간 30분
            //다른페이지에서 세션의 아이디가 존재하는지 확인후 로그인 여부 판단
            httpSession.setAttribute("USERID", ret.getId());
            httpSession.setAttribute("USERNAME", ret.getName());
            httpSession.setAttribute("ROLE", ret.getRole());

            return "redirect:/home.do";

        }
        return "redirect:login.do";
    }
    @RequestMapping(value = "logout.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutPOST(){
        httpSession.invalidate(); //세션의 정보를 다지움
        return "redirect:/member/login.do";
    }

}
