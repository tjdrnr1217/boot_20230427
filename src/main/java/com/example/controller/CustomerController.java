package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CustomerUser;
import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {
    final String format = "CustomerController => {}";
    final MemberMapper memberMapper;

    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/customer/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member member) {
        log.info(format, member.toString()); // 화면에 정확하게 표시되고 사용자가 입력한 항목을 member객체에 저장했음. 확인

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(); // salt값을 자동으로 부여함.
        member.setPassword(bcpe.encode(member.getPassword())); // 기존암호를 암호화 시켜서 다시 저장함.
        int ret = memberMapper.insertMemberOne(member);
        if (ret == 1) {
            return "redirect:joinok.do"; // 주소창에 127.0.0.1:9090/ROOT/customer/joinok.do입력후 엔터키를 자동화
        }
        return "redirect:join.do";// 실패시 회원가입화면으로
    }

    @GetMapping(value = "/home.do")
    public String homeGET(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "menu", required = false, defaultValue = "0") int menu) {
        if (menu == 1) {
            // 세션에서 아이디정보를 꺼내서 mapper에서 조회
            Member member = memberMapper.selectMemberone1(user.getUsername());
            log.info(format, member.toString());
            model.addAttribute("member", member);

            //체크박스에 표시할 항목들
            String[] checkLabel = {"가가가","나나나","다다다","가나다"};
            model.addAttribute("checklabel", checkLabel);
        }
        return "/customer/home";
    }

    @GetMapping(value = "/joinok.do")
    public String joinokGET() {
        return "/customer/joinok";
    }

    // @AuthenticationPrincipal User user => HttpSession httpSession =>
    // httpSession.getAttribute("user")
    @PostMapping(value = "/home.do")
    public String homePOST(@RequestParam(name = "menu", required = false) int menu,
            @AuthenticationPrincipal CustomerUser user,
            @ModelAttribute Member member, // 받아오는 값
            HttpServletRequest request,
            HttpServletResponse response) {
        log.info("CustomerController menu => {}", user.toString());
        log.info("CustomerController menu => {}", menu);
        if (menu == 1) {
            log.info(format, user.getUsername());
            // 아이디정보 가져오기 => user.getUsername()
            member.setId(user.getUsername());
            int ret = memberMapper.updateMember(member);
            log.info(format, ret);
            if (ret == 1) {
                return "redirect:home.do";
            }

        } else if (menu == 2) {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            // 아이디 정보를 이용해서 dB에서 1명 조회
            // 조회된 정보의 아이디와 사용자가 입력한 아이디를 matches로 비교
            // 비밀번호 확인 => matches(바꾸기전비번, 해시된비번)
            if (bcpe.matches("hash 전 비번", "hash 후 비번")) {

            }
        } else if (menu == 3) {
            // 아이디 정보를 이용해서 db에서 1명 조회
            // 조회된 정보와 현재 암호가 일치하는지 matches로 비교
            // 비교가 true이면 db에서 삭제, 로그아웃
            // 컨트롤러에서 logout처리하기
            Member m = memberMapper.selectMemberone1(user.getUsername());
            log.info(format, m.toString());
            member.setId(m.getId());
            member.setPassword(m.getPassword());
            int ret = memberMapper.updateMemberdb(member);
            log.info(format, ret);
            if (ret == 1) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                }
                return "redirect:/home.do";
            }
        }

        return "redirect:/customer/home.do";
    }
}
