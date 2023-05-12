package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CustomUser;
import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {
    final String format ="CustomerController => {}";
    final MemberMapper memberMapper;
    final UserDetailsService userDetailsService;
   
    @GetMapping(value = "/joinok.do")
    public String joiokGET(){
        return "/customer/joinok";
    }

    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/customer/join";
    }
    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member member){
        log.info(format, member.toString());
        
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        member.setPassword(bcpe.encode(member.getPassword()));
        int ret = memberMapper.insertMemberOne(member);
        if(ret==1){
            return "redirect:joinok.do";
        }
        return "redirect:join.do";
    }
    
    @GetMapping(value = "/home.do")
    public String homeGET(
        Model model,
        @AuthenticationPrincipal CustomUser user,
        @RequestParam(name="menu", required = false, defaultValue = "0") int menu){
        if(menu==1){
            Member member = memberMapper.selectMemberone1(user.getUsername());
            log.info(format, member.toString());

            model.addAttribute("member", member);

            //체크박스에 표시할 항목들
            String[] checkLabel ={"가가가", "가나다", "나나나", "다다다", "가나다"};
            model.addAttribute("checklabel", checkLabel);
        }
        return "/customer/home";
    }

    
    @PostMapping(value = "/home.do")
    public String homePOST(@RequestParam(name = "menu") int menu,
    HttpServletRequest request,
    @AuthenticationPrincipal User user,
    @ModelAttribute Member member,
    //@AuthenticationPrincipla User user => HttpSession httpsession => httpsession.getAttribute("user")
    HttpServletResponse response){
        if(menu==1){
           String id= user.getUsername();
        //    String name=request.getParameter("name");
        //    long age=Long.parseLong(request.getParameter("age"));
        //    Member mem = new Member();
        //    mem.setId(id);
        //    mem.setName(name);
        //    mem.setAge(age);
        //입력받은 이름과 나이를 html에서 바로 들고옴
        member.setId(id);
           memberMapper.updateMember(member);
            return "redirect:/customer/home.do?menu=1";
        }
        else if(menu==2){
            String pw =userDetailsService.loadUserByUsername(user.getUsername()).getPassword();
            Member mem = new Member();
             String id =user.getUsername();
            //  String pw =userDetails.getPassword();
            //  String pw = user.getPassword();
             String password =request.getParameter("password");
             String newpassword =request.getParameter("newpassword");
             
             BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
             mem.setPassword(pw);
             mem.setNewpassword(bcpe.encode(newpassword));
             mem.setId(id);
            //아이디 정보를 이용해서 db에서 1명조회
            //조회된 정보의 암호와 사용자가 입력한 암호를 matches로 비교
            //비밀번호 확인 => matches바꾸기전 비번 , 해시된비번
            log.info("pw1=>{}", password);
            log.info("pw1=>{}", pw);
            log.info("pw1=>{}", id);
            if(bcpe.matches(password,pw)){
                log.info("asdasd=>{}", "일치함?");
           int ret =     memberMapper.updatepwMember(member);
           System.out.println(ret);
            }
            return "redirect:/customer/home.do?menu=2";
        }

        else if (menu==3){
            String pw =userDetailsService.loadUserByUsername(user.getUsername()).getPassword();
             String id =user.getUsername();
             String password =request.getParameter("password");
             BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            //아이디 정보를 이용해서 db에서 1명 조회
            //조회된 정보와 현재 암호가 일치하는지 matches로 비교
            if(bcpe.matches(password,pw)){
                log.info("asdasd=>{}", "일치함?");
           int ret =  memberMapper.deleteMember(id);
           System.out.println(ret);
            }
            //비교가 true이면 db에서 삭제 로그아웃
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("CustomerCotroller => {}", auth.toString());
            if(auth !=null){

                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            return "redirect:/home.do";
        }
        return "redirect:/customer/home.do";
    }
    
    
}
