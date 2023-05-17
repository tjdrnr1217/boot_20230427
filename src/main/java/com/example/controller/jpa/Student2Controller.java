package com.example.controller.jpa;

import java.util.Collection;
import java.util.List;



import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Student2;
import com.example.repository.library.Student2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/student2")
@RequiredArgsConstructor
@Slf4j
public class Student2Controller {

    final Student2Repository s2Repository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 127.0.0.1:9090/ROOT/student2/mylogin.do
    @GetMapping(value = "/mylogin.do")
    public String myloginGET(){
        return"student2/mylogin";
    }

    @PostMapping(value = "/myloginaction.do")
    public String myloginAction(@ModelAttribute Student2 obj){
        try {
            log.info("{}", obj.toString());
            //DetailService를 사용하지 않고 세션에 저장하기
            //기본자료읽기
            Student2 obj1 =s2Repository.findById(obj.getSemail()).orElse(null);
            //전달한 아이디와 읽은 데이터 암호 비교
            if(bcpe.matches(obj.getSpassword(), obj1.getSpassword())){
                String[] strRole = {"ROLE_STUDENT2"};
                Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);

                //세션에 저장할 객체 생성하기(저장할 객체, null, 권한)
                User user = new User(obj.getSemail(), obj.getSpassword(), role);  //import org.springframework.security.core.userdetails.User
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, role);
                //세션에 저장
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
                //수동으로 세션에 저장(로그인)

            }
            return "redirect:/student2/home.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
            }
    }
    @GetMapping(value = "/home.do")
    public String homeGET(@AuthenticationPrincipal User user,Model model){
        try {
            model.addAttribute("user", user);
            return "/student2/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 127.0.0.1:9090/ROOT/student2/login.do
    // loginaction.do post는 만들지 않음. security에서 자동으로 처리함
    @GetMapping(value = "/login.do")
    public String loginGET(){
        try {
            return "/student2/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model){
        try {
            List<Student2> list = s2Repository.findAllByOrderBySnameAsc();
            log.info("{}",list.toString());
            model.addAttribute("list", list);
            return "/student2/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/join.do")
    public String joinGET(){
        try {
            return "/student2/join";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/join.do")
    public String insertPOST(@ModelAttribute Student2 obj){
        try {
            obj.setSpassword(bcpe.encode(obj.getSpassword()));
            log.info("{}",obj.toString());
            s2Repository.save(obj);
            return "redirect:/student2/join.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
}
