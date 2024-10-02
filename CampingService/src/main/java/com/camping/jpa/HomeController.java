package com.camping.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model, @RequestParam(required = false) String command) {
		if(command != null && command.contains("init")) {
			init();
		}
		return "home";
	}
	
	@GetMapping("/home")
	public String testhome() {
		return "home";
	}
	
	public void init() {}
	
}