package com.camping.jpa.kakao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import java.text.SimpleDateFormat;

import com.camping.jpa.list.model.service.CampingListService;
import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.vo.User;
import com.camping.jpa.member.controller.MemberController;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class KaKaoController {
	@Autowired
    private KakaoPayService kakaopay;
	
	@Autowired
	private CampingListService camping;
	    
    @PostMapping("/kakaoPay")
    public String kakaoPay(@RequestParam Map<String, String> param, HttpSession session, @SessionAttribute(name="loginMember", required = false) User loginMember) {
        log.info("kakaoPay post............................................");
        session.setAttribute("param", param);

        return "redirect:" + kakaopay.kakaoPayReady(param);
 
    }
    
    @GetMapping("/kakao/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, HttpSession session) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        
        @SuppressWarnings("unchecked")
		Map<String, String> param = (Map<String, String>) session.getAttribute("param");
        
        KakaoPayApprovalVO data = kakaopay.kakaoPayInfo(pg_token, param);
        AmountVO amount = data.getAmount();
        int tempPrice = amount.getTotal() - amount.getVat();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String timedata = sdf.format(data.getApproved_at().getTime());
        
        CampingList tempList1 = camping.findByCid(Integer.parseInt(param.get("cid")));
        CampingSite tempList2 = camping.findBySiteid(Integer.parseInt(param.get("siteid")));
        
        User organizer = tempList1.getCampingmember().get(0).getUserlist();
        
        
        log.info("dbg1324 user: " + organizer);

        model.addAttribute("info", data);
        model.addAttribute("price", amount);
        model.addAttribute("time", timedata);
        model.addAttribute("camp", tempList1);
        model.addAttribute("site", tempList2);
        model.addAttribute("user", organizer);
        model.addAttribute("tempPrice", tempPrice);
        
        return "/kakao/kakaoPaySuccess";
    }
}
