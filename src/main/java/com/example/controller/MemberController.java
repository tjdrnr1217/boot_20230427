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
@RequiredArgsConstructor
public class MemberController {

    final MemberMapper mMapper; // 매퍼객체 생성하기
    final HttpSession httpSession; // 세션객체 생성하기

    @GetMapping(value = "/join.do")
    public String joinGET(){
        log.info("member=>{}", "joinGET");
        return "/member/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member obj){
        log.info("join.do POST => {}" , obj.toString());
        // 여기서 매퍼호출후 회원가입하기
        int ret  = mMapper.insertMemberOne(obj);
        if(ret == 1){
            // 127.0.0.1:9090/ROOT/member/home.do
            return "redirect:/home.do"; // 성공시 홈으로
        }
        return "redirect:/member/join.do"; // 실패시 회원가입으로
    }

    @GetMapping(value = "/login.do")
    public String loginGET(){
        return "/member/login";
    }

    @PostMapping(value="/login.do")
    public String loginPOST(@ModelAttribute Member member) {
        log.info("login.do => {}", member.toString()); // view에서 잘전송되었는지
        Member ret = mMapper.selectMemberOne(member); //로그인한 사용자의 정보 반환
        if(ret != null) {
            log.info("login1.do => {}", ret.toString()); 
            // 세션에 2개의 정보 아이디와 이름 추가하기 ( 기본시간 30분 )
            // 다른페이지에서 세션의 아이디가 존재하는지 확인후 로그인 여부 판단
            httpSession.setAttribute("USERID", ret.getId());
            httpSession.setAttribute("USERNAME", ret.getName());
            httpSession.setAttribute("USERAGE", ret.getAge());
            httpSession.setAttribute("USERROLE", ret.getRole());

            return "redirect:/home.do";    // 로그인 성공 시
        }
        return "redirect:login.do"; // 로그인 실패 시
    }


    // GET,POST가 같이 동작함
    @RequestMapping(value = "/logout.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutPOST(){
        httpSession.invalidate(); //세션의 정보를 다 지움
        return "redirect:/home.do";
    }

    @GetMapping(value = "/update.do")
    public String updateGET(@ModelAttribute Member member, Model model){
        member.setId((String)httpSession.getAttribute("USERID"));
       Member ret = mMapper.updateMemberOne(member);
        model.addAttribute("obj", ret);
    return "/member/update"; 
    }

    @PostMapping(value = "/update.do")
    public String updatePOST(@ModelAttribute Member member){
        member.setId((String)httpSession.getAttribute("USERID"));
        int ret = mMapper.updateMember(member);
        System.out.println(ret);
        if(ret == 1){
            return "redirect:/home.do";
        }
        return "redirect:/update.do";
    }

    @RequestMapping(value = "/delete.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String deletePOST(@ModelAttribute Member member) {
        member.setId((String)httpSession.getAttribute("USERID"));
        int ret = mMapper.deleteMember(member);
        System.out.println(ret);
        if(ret ==1 ){
            httpSession.invalidate();
            return "redirect:/home.do";
        }
        return "redirect:/home.do";
    }
    
    
}
