// package com.example.controller;

// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.example.entity.PurchaseView;
// import com.example.repository.PurchaseViewRepository;

// import lombok.RequiredArgsConstructor;

// // @Slf4j
// @Controller
// @RequestMapping(value = "/purchaseview")
// @RequiredArgsConstructor
// public class PurchaseViewController {

//     final PurchaseViewRepository pvRepository;

//     @GetMapping(value = "/selectlist.pknu")
//     public String selectlistGET(Model model){
//         try {
//             List<PurchaseView> list = pvRepository.findAll();
//             model.addAttribute("list", list);
//             return "/purchase/selectlist";
//         } catch (Exception e) {
//             e.printStackTrace();
//             return "redirect:/home.do";

//         }

//     }
    
    
// }
