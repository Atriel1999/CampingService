package com.camping.jpa.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.camping.jpa.kakao.KaKaoService;
import com.camping.jpa.list.controller.CampingListController;
import com.camping.jpa.member.model.service.MemberService;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Log4j2
@Controller
@SessionAttributes("loginMember")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private KaKaoService kakaoService;
	
	@GetMapping("/login")
	public String login(Model model) { //, String memberId, String memberPwd
//		log.debug("@@@@@ Login : " + memberId + ", " + memberPwd);
//		
//		Member loginMember = service.login(memberId, memberPwd);
		
//		if(loginMember != null) { // 로그인이 성공한 경우
//			model.addAttribute("loginMember", loginMember); // 세션에 저장하는 로직 @SessionAttributes 사용
//			return "redirect:/"; // index로 보내는 리다이렉트문
//		} else { // 로그인이 실패한 경우
//			model.addAttribute("msg", "아이디와 패스워드를 확인해주세요.");
//			model.addAttribute("location", "/");
//			return "login";
//		}
		
		return "login/login";
	}
	
	@GetMapping("/outh2/enroll/kakao")
	public String enrollKakao(Model model, @RequestParam("code") String code) {
		log.info("가입 페이지 요청");
		if(code != null) {
			try {
				String enrollUrl = "http://localhost/outh2/enroll/kakao";
				System.out.println("code : " + code);
				String token = kakaoService.getToken(code, enrollUrl);
				System.out.println("token : " + token);
				Map<String, Object> map = kakaoService.getUserInfo(token);
				System.out.println("listdbg1: " + map);
				model.addAttribute("kakaoMap",map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "login/signup";
	}
	
	@RequestMapping("/member/idCheck")
	public ResponseEntity<Map<String, Object>> idCheck(@RequestParam("id") String id){
		log.debug("아이디 중복 확인 : " + id);
		
		boolean validate = service.validate(id);
		Map<String, Object> map = new HashMap<>();
		map.put("validate", validate);
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	

}
