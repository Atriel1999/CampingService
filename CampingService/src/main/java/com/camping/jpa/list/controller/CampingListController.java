package com.camping.jpa.list.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camping.jpa.list.model.service.CampingListService;
import com.camping.jpa.list.model.vo.CampingList;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/camping")
public class CampingListController {
	@Autowired
	private CampingListService service;	
		
	@GetMapping("/camping-list")
	public String campingListLoading(Model model, @RequestParam(value = "cid") String cid) {
		
		CampingList list = service.findByCid(cid);
		if (list == null) {
			return "redirect:error";
		}
		
		model.addAttribute("list",list);

		return "/camping/camping-list";
	
	}

}
