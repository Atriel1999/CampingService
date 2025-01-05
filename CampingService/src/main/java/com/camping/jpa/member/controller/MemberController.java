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
import com.camping.jpa.list.model.vo.User;
import com.camping.jpa.member.model.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
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
	public String login(HttpServletRequest request, Model model) { //, String memberId, String memberPwd
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
		String uri = request.getHeader("Referer");
	    if (uri != null && !uri.contains("/login")) {
	    	log.info("dbg1111 uri:" + uri);
//	        request.getSession().setAttribute("prevPage", uri);
	    	request.setAttribute("prevPage", uri);
	    }
		
		return "login/login";
	}

	
	@GetMapping("/outh2/login/kakao")
	public String kakaoLogin(HttpServletRequest request, Model model, @RequestParam("code") String code) {
		log.info("로그인 요청");
		if(code != null) {
			try {
				String loginUrl = "http://www.atrielcamping.com/outh2/login/kakao";
				String token = kakaoService.getToken(code, loginUrl);
				Map<String, Object> map = kakaoService.getUserInfo(token);
				String kakaoToken = (String) map.get("id");
				User loginMember = service.loginKaKao(kakaoToken);
				
				String prevPage = (String) request.getAttribute("prevPage");  //request.getSession().getAttribute("prevPage");
		        if (prevPage != null) {
//		            request.getSession().removeAttribute("prevPage");
		        	request.removeAttribute("prevPage");
		        }
				
				if(loginMember != null) { // 로그인 성공
					model.addAttribute("loginMember", loginMember); // 세션으로 저장되는 코드, 이유: @SessionAttributes
					System.out.println("listdbg2(loginMember): " + loginMember);
					
					log.info("dbg2222: " + prevPage);
					
					model.addAttribute("msg", "로그인에 성공하였습니다.");
					model.addAttribute("uri", prevPage);
					return "common/msgLogin";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("msg", "로그인에 실패하였습니다.");
		model.addAttribute("location","/login");
		return "common/msg";
	}
	
	@GetMapping("/logout")
	public String logout(SessionStatus status) { // status: 세션의 상태를 확인하는 인자
		log.debug("status : " + status.isComplete()); // isComplete : 세션이 완료 되었는지
		status.setComplete(); // 세션을 종료시키는 메소드
		log.debug("status : " + status.isComplete()); 
		return "redirect:/home";
	}
	
		
	
	@GetMapping("/outh2/enroll/kakao")
	public String enrollKakao(Model model, @RequestParam("code") String code) {
		log.info("가입 페이지 요청");
		if(code != null) {
			try {
				String enrollUrl = "http://www.atrielcamping.com/outh2/enroll/kakao";
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
	public ResponseEntity<Map<String, Object>> idCheck(@RequestParam("id") String id) {
		log.debug("아이디 중복 확인 : " + id);

		boolean validate = service.validate(id);
		Map<String, Object> map = new HashMap<>();
		map.put("validate", validate);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@PostMapping("/member/enroll")
	public String enroll(Model model, @ModelAttribute User member) { 
		log.debug("회원가입 요청 member : " + member.toString());

		int result = 0;
		try {
			result = service.save(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result > 0) {
			model.addAttribute("msg", "회원가입에 성공하였습니다.");
			model.addAttribute("location", "/login");
		} else {
			model.addAttribute("msg", "회원가입에 실패하였습니다. 입력정보를 확인하세요.");
			model.addAttribute("location", "/login");
		}
		
		return "common/msg";
	}
	
	@GetMapping("/login/user-profile")
	public String loadingProfile(Model model, @SessionAttribute(name="loginMember", required = false) User loginMember) {
		log.info("listdbg111: " + loginMember);
		
		
		model.addAttribute("user", loginMember);
				
		return "/login/user-profile";
	}
	

	
}
//		log.info("dbg1 camping data: " + campinglist);
//
//List<CampingImage> CampingImageList = new ArrayList<CampingImage>();

// 파일 저장 로직
//if(file != null) {
//	for(MultipartFile upfile : file) {
//		log.info("uploaded file each: " + upfile + ",size: " + upfile.getSize());
//		if(upfile.getSize() == 0) {
//			continue;
//		}
//		
//		String uploadedFileName = service.upload(upfile); // 실제 파일 저장되는 로직
//		log.info("dbg6 cid: " + campinglist.getCid() +", filename: " + uploadedFileName);
//		if(uploadedFileName != null) {
//			CampingImage Imagefile = new CampingImage();
//			Imagefile.setCid(campinglist.getCid());
//			Imagefile.setCiimage(uploadedFileName);
//			CampingImageList.add(Imagefile);
//		}
//	}
//}
//
//log.info("dbg7 camping data: " + CampingImageList);
//service.InsertCampingImage(CampingImageList);
//
//
//return "/camping/camping-list";