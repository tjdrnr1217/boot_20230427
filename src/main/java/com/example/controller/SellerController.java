package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Item;
import com.example.dto.Member;
import com.example.mapper.ItemMapper;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/seller")
@Slf4j
@RequiredArgsConstructor
public class SellerController {
    final ItemMapper iMapper;
    final HttpSession httpSession;
    final MemberMapper membermapper;

    // 127.0.0.1:9090/ROOT/seller/home.do
    @GetMapping(value = "/home.do")
    public String homeGET(Model model, @RequestParam(name = "menu", defaultValue = "0", required = false) int menu) {
        if (menu == 2) {
            Item ii = new Item();
            ii.setSeller((String) httpSession.getAttribute("USERID"));
            List<Item> list = iMapper.selectItemseller(ii);
            log.info("seller.do=>{}", list.toString());
            model.addAttribute("list", list);
        }
        return "/seller/home";
    }

    // /ROOT/seller/home.do?menu=1
    @PostMapping(value = "/home.do")
    public String homePOST(Model model, @RequestParam(name = "menu", defaultValue = "0", required = false) int menu,
            @RequestParam(name = "name", required = false) String[] name,
            @RequestParam(name = "content", required = false) String[] content,
            @RequestParam(name = "price", required = false) long[] price,
            @RequestParam(name = "quantity", required = false) long[] quantity,
            @RequestParam(name = "chk[]", required = false) long[] no,
            @RequestParam(name = "btn", required = false) String btn) {
        if (menu == 0) {
            return "redirect:home.do?menu=1";
        }
        if (menu == 1) {
            List<Item> list = new ArrayList<>();
            for (int i = 0; i < name.length; i++) {
                Item ii = new Item();
                ii.setName(name[i]);
                ii.setContent(content[i]);
                ii.setPrice(price[i]);
                ii.setQuantity(quantity[i]);
                ii.setSeller((String) httpSession.getAttribute("USERID"));
                list.add(ii);
            }
            log.info("seller.do=>{}", list.toString());
            int ret = iMapper.insertItemBatch(list);
            log.info("seller.do=>{}", ret);
        } else if (menu == 2) {
            if (btn.equals("일괄삭제")) {
                int ret = iMapper.deleteItemBatch(no);
                System.out.println(ret);
            } else if (btn.equals("일괄수정")) {
                // get으로 이동후에 화면표시
                httpSession.setAttribute("chk[]", no);
                return "redirect:updatebatch.do";
            }
        }
        return "redirect:home.do?menu=" + menu;
    }

    @GetMapping(value = "/updatebatch.do")
    public String updateBatchGET(Model model) {
        long[] no = (long[]) httpSession.getAttribute("chk[]");
        log.info("updatebatch.do[GET]=> {}", no);
        List<Item> list = iMapper.selectItemNoList(no);
        model.addAttribute("list", list);
        return "/item/updatebatch";
    }

    @PostMapping(value = "/updatebatch.do")
    public String updateBatchPost(
            @RequestParam(name = "no[]") long[] no,
            @RequestParam(name = "name[]") String[] name,
            @RequestParam(name = "content[]") String[] content,
            @RequestParam(name = "price[]") long[] price,
            @RequestParam(name = "quantity[]") long[] quantity) {
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < no.length; i++) {
            Item item = new Item();
            item.setNo(no[i]);
            item.setName(name[i]);
            item.setContent(content[i]);
            item.setPrice(price[i]);
            item.setQuantity(quantity[i]);
            list.add(item);
        }
        log.info("updatebatch.do[POST]=>{}", list.toString());
        int ret= iMapper.updateItemBatch(list);
        System.out.println(ret);
        return "redirect:/seller/home.do?menu=2";
    }
    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/seller/join";
    }
    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member member){
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        member.setPassword(bcpe.encode(member.getPassword()));
        int ret = membermapper.insertMemberOne(member);
        if(ret==1){
            return "redirect:joinok.do";
        }
        return "redirect:join.do";
    }

    @GetMapping(value = "/login.do")
    public String loginGET(){
        return  "/customer/login";
    }
    @GetMapping(value = "/joinok.do")
    public String joiokGET(){
        return "/customer/joinok";
    }
}
