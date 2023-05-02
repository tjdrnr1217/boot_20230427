package com.example.boot_20230427.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.boot_20230427.dto.Item;
import com.example.boot_20230427.service.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/seller")
public class SellerController {
    
    @Autowired ItemService iService;
    final HttpSession httpSession;

    @GetMapping(value = "/home.do")
    public String homeGET(@RequestParam(name = "menu",defaultValue = "0",required = false) int menu, Model model) {
        if(menu == 0 ){
            return "redirect:home.do?menu=1";
        }
        if(menu==2) {
            System.out.println((String)httpSession.getAttribute("USERID"));
            List<Item> list = iService.selectItemListID((String)httpSession.getAttribute("USERID"));
            System.out.println(list.toString());
            model.addAttribute("list", list);
            log.info("seller.do = > {}", list.toString());
        }
        return "/seller/home";
    }

    // /ROOT/seller/home.do?menu=1

    @PostMapping(value="/home.do")
    public String homePOST(
        @RequestParam(name = "menu",defaultValue = "0",required = false) int menu,

        @RequestParam(name = "iname[]", required = false) String[] iname,
        @RequestParam(name = "icontent[]", required = false) String[] icontent,
        @RequestParam(name = "iprice[]", required = false) long[] iprice,
        @RequestParam(name = "iquantity[]", required = false) long[] iquantity,

        @RequestParam(name = "chk[]", required = false) long[] no,
        @RequestParam(name = "btn", required = false) String btn
        ) {
        if(menu == 0 ){
            return "redirect:home.do?menu=1";
        }

        if( menu == 1 ) { // 일괄등록
            List<Item> list = new ArrayList<>();
            for(int i = 0;i<iname.length;i++) {
                Item item = new Item();
                item.setIname(iname[i]);
                item.setIcontent(icontent[i]);
                item.setIprice(iprice[i]);
                item.setIquantity(iquantity[i]);
                item.setSeller((String)httpSession.getAttribute("USERID"));
                list.add(item);
            }
            log.info("seller.do = > {}",list.toString());
            int ret = iService.insertItemBatch(list);
            log.info("seller.do = > {}",ret);
        }
        else if ( menu == 2 ) { 
            log.info("seller.do => {}", btn) ;
            log.info("seller.do => {}", no) ;
            if(btn.equals("일괄삭제")) {
                int ret = iService.deleteItemBatch(no);
                if(ret > 0 ) {
                    return "redirect:home.do?menu="+menu;
                }
                
            }
            else if(btn.equals("일괄수정")) {
                // 체크한 항목 정보를 가지고 있음
                httpSession.setAttribute("chk[]", no);
                // redirect => get으로 이동후에 화면표시
                return "redirect:updatebatch.do";
            }
        }
        else if ( menu == 3 ){

        }
        
        return "redirect:home.do?menu="+menu;
    }
    
    @GetMapping(value = "/updatebatch.do")
    public String updatebatchGET(Model model) {
        long[] no = (long[])httpSession.getAttribute("chk[]");
        log.info("updatebatch.do[GET] => {} ", no);

        List<Item> list = iService.selectItemNoList(no);
        log.info("updatebatch.do[GET] => {} ", list.toString());
        model.addAttribute("list", list);

        return "/item/updatebatch";
    }
    @PostMapping(value = "/updatebatch.do")
    public String updatebatchPOST(
        @RequestParam(name = "no[]") long[] no,
        @RequestParam(name = "iname[]") String[] iname,
        @RequestParam(name = "icontent[]") String[] icontent,
        @RequestParam(name = "iprice[]") long[] iprice,
        @RequestParam(name = "iquantity[]") long[] iquantity
    ) {
        List<Item> list = new ArrayList<>();
        for(int i=0; i < no.length ; i++) {
            Item item = new Item();
            item.setNo(no[i]);
            item.setIname(iname[i]);
            item.setIcontent(icontent[i]);
            item.setIprice(iprice[i]);
            item.setIquantity(iquantity[i]);
            list.add(item);
        }
        log.info("updatebatch.do[POST]=>{}",list.toString());
        int ret = iService.updateItemBatch(list);
        if( ret > 0 ){
            return "redirect:/seller/home.do?menu=2";
        }
        return "redirect:/seller/home.do?menu=2";
    }
    
}
