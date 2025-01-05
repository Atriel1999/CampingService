package com.camping.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.camping.jpa.board.controller.BoardController;
import com.camping.jpa.board.model.service.BoardService;
import com.camping.jpa.board.model.vo.Board;
import com.camping.jpa.list.model.service.CampingListService;
import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.review.model.service.CampingReviewService;
import com.camping.jpa.review.model.vo.CampingReview;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {
	
	@Autowired
	private BoardService boardservice;
	
	@Autowired
	private CampingListService campingservice;
	
	@Autowired
	private CampingReviewService reviewservice;
	
	@GetMapping(value = {"/", "/home"})
	public String home(Model model) {
		
		List<CampingList> camp = campingservice.findByTop5();
		List<CampingReview> review = reviewservice.findReviewByTop3();
		List<Board> board = boardservice.findBoardByTop6();
		List<Board> notice = boardservice.findNoticeByTop6();
		
		log.info("dbg1: " + review);
		
		model.addAttribute("camp", camp);
		model.addAttribute("review", review);
		model.addAttribute("board", board);
		model.addAttribute("notice", notice);
		
		return "home";
	}

	
}