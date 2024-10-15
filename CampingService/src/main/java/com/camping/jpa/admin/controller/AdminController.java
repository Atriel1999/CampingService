package com.camping.jpa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camping.jpa.admin.model.vo.Admin;
import com.camping.jpa.admin.model.service.AdminService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class AdminController {
	@Autowired
	private AdminService service;
	
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
			return "admin/adminpage";
		} else {
			model.addAttribute("msg", "아이디와 패스워드를 확인해주세요");
			model.addAttribute("location", "/admin/adminpage");
			return "common/msg";
		}
	}

}
