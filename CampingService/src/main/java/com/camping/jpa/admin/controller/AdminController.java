package com.camping.jpa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camping.jpa.admin.model.vo.Admin;
import com.camping.jpa.kakao.KakaoPayService;
import com.camping.jpa.kakao.model.vo.PaymentApprove;
import com.camping.jpa.list.model.vo.User;

import jakarta.servlet.http.HttpServletRequest;

import com.camping.jpa.admin.model.service.AdminService;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Log4j2
@Controller
public class AdminController {
	@Autowired
	private AdminService service;
	
	@Autowired
	private KakaoPayService servicekakao;
	
	@GetMapping("/admin/adminlogin")
	public String adminloginView(){
		
		return "admin/adminlogin";
	}
	
	@PostMapping("/adminlogin")
	public String adminlogin(Model model, @RequestParam("adminid") String adminid,  @RequestParam("adminpw") String adminpw) {
		log.debug("@@@@@ Login : " + adminid + ", " + adminpw);
		
		Admin loginAdmin = service.login(adminid, adminpw);
		
		
		if(loginAdmin != null) {
			model.addAttribute("loginAdmin", loginAdmin);
			
			model.addAttribute("msg", "로그인 성공");
			model.addAttribute("location", "/admin/admin-paymentmanage");
			return "common/msg";
		} else {
			model.addAttribute("msg", "아이디와 패스워드를 확인해주세요");
			model.addAttribute("location", "/admin/adminlogin");
			return "common/msg";
		}
	}

	
	@GetMapping("/admin/admin-paymentmanage")
	public String adminMainPage(Model model) {
		
		List<PaymentApprove> Listkakao = servicekakao.findAllByOrderByAidDesc();
		log.info("dbg1324: " + Listkakao);
		
		model.addAttribute("data", Listkakao);
		
		return "admin/admin-paymentmanage";
	}
	
	
	@GetMapping("/admin/admin-usermanage")
	public String adminUserPage(Model model, @RequestParam(name = "searchName", required = false) String searchName) {
		
		if(searchName == null || searchName.isEmpty()) {
			searchName = "";
		}
		
		List<User> listUser = service.searchUsername(searchName);
		
		model.addAttribute("data", listUser);
		model.addAttribute("searchName", searchName);
		
		return "admin/admin-usermanage";
	}
	
	@GetMapping("/admin/admin-restrictusermanage")
	public String adminRestrictUserPage(Model model, @RequestParam(name = "searchName", required = false) String searchName) {
		
		if(searchName == null || searchName.isEmpty()) {
			searchName = "";
		}
		
		List<User> listUser = service.searchRestrictUsername(searchName);
		
		model.addAttribute("data", listUser);
		model.addAttribute("searchName", searchName);
		
		return "admin/admin-restrictusermanage";
	}
	
	
	@PostMapping("/admin/userbanned")
	public String adminUserBan(Model model, HttpServletRequest request, @RequestParam("userretrictno") String userretrictno) {
		service.retrictUserStatus(userretrictno);
		service.setBanDate(userretrictno);
		
		String prevPage = request.getHeader("Referer");
		
		model.addAttribute("msg", "유저번호 [" + userretrictno +"] 임시정지 완료");
		model.addAttribute("uri", prevPage);
		return "common/msgLogin";
	}
	
	@PostMapping("/admin/userUnban")
	public String adminUserUnban(Model model, HttpServletRequest request, @RequestParam("userbanno") String userbanno) {
		service.releaseUserStatus(userbanno);
		
		String prevPage = request.getHeader("Referer");
		
		model.addAttribute("msg", "유저번호 [" + userbanno +"] 정지 해제 완료");
		model.addAttribute("uri", prevPage);
		return "common/msgLogin";
	}
	
	@PostMapping("/admin/userRevokeAdmin")
	public String adminRevoke(Model model, HttpServletRequest request, @RequestParam("userrevokeno") String userrevokeno) {
		
		service.revkoeAdmin(userrevokeno);
		
		model.addAttribute("msg", "유저번호 [" + userrevokeno +"] 어드민 해제 완료");
		
		String prevPage = request.getHeader("Referer");
		
		model.addAttribute("uri", prevPage);
		return "common/msgLogin";
	}
	
	@PostMapping("/admin/userSetAdmin")
	public String adminSet(Model model, HttpServletRequest request, @RequestParam("usersetno") String usersetno) {
		
		service.setAdmin(usersetno);
		
		String prevPage = request.getHeader("Referer");
		log.info("log11111: " + prevPage);
		
		model.addAttribute("msg", "유저번호 [" + usersetno +"] 어드민 등록 완료");
		model.addAttribute("uri", prevPage);
		return "common/msgLogin";
	}
	
	
}
