package com.camping.jpa.board.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.camping.jpa.board.model.service.BoardService;
import com.camping.jpa.board.model.vo.Board;
import com.camping.jpa.board.model.vo.BoardReply;
import com.camping.jpa.board.model.vo.DisplayedImageDTO;
import com.camping.jpa.board.model.vo.PageInfo;
import com.camping.jpa.board.model.vo.SearchValue;
import com.camping.jpa.list.model.service.CampingListService;
import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.vo.User;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private CampingListService serviceUpload;
	
	@GetMapping("/camping-boardedit")
	public String updateboardView(Model model) {
		
		return "/board/camping-boardedit";
	}
	
	@PostMapping("/camping-boardedit")
	public String updateboardInfo(Model model, @ModelAttribute("board") Board board, 
			@SessionAttribute(name="loginMember", required=false) User loginMember) {
		log.info("dbg1: " + board);
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		dateFormat.format(date);
		
		board.setBcreatedate(date);
		board.setUserlist(loginMember);
		
		Board resultBoard = service.saveBoard(board);
		
		model.addAttribute("msg", "게시글이 등록 되었습니다.");
		model.addAttribute("location", "/board/camping-board?page=1");
		return "/common/msg";
	}
	
	
	@ResponseBody
	@PostMapping("/image")
	public ResponseEntity<DisplayedImageDTO> saveTempImg(@RequestParam("img") MultipartFile img){
		 if(img == null || img.isEmpty()) {
	            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	        }

	        DisplayedImageDTO imgDTO = new DisplayedImageDTO();
	        String originName = img.getOriginalFilename();
//	        JsonObject jsonObject = new JsonObject();
	        
	        String path = serviceUpload.upload(img);

	        imgDTO.setSavePath(path);
	        imgDTO.setOriginalName(originName);
	        
//	        jsonObject.addProperty("savePath", path);
//	        jsonObject.addProperty("originalName", originName);
//	        jsonObject.addProperty("responseCode", "success");
	        
	        if (imgDTO.getSavePath() == null) {
	            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//	        	jsonObject.addProperty("responseCode", "error");
	        }
	        
	        
	        
	        return new ResponseEntity<>(imgDTO, HttpStatus.OK);
//	        return jsonObject;
	}
	
	@PostMapping("/camping-replysubmit")
	public String insertReply(@ModelAttribute("reply") BoardReply reply, Model model, 
			@RequestParam("ruserno") String ruserno) {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		dateFormat.format(date);
		
		User user = new User();
		
		user.setUserno(ruserno);
		reply.setUserlist(user);
		reply.setRcreatedate(date);
		
		service.saveBoardReply(reply);
		
		model.addAttribute("msg", "댓글 등록 되었습니다.");
		model.addAttribute("location", "/board/camping-boardsingle?bid=" + reply.getBid());
		return "/common/msg";
	}
	
	@GetMapping("/camping-boardsingle")
	public String boardSingleView(Model model, @RequestParam("bid") int bid) {
		
		Board board = service.findByBid(bid);
		service.updateBreadcount(bid);
		
		List<BoardReply> reply = service.findReplyByBid(bid);
		
		model.addAttribute("board", board);
		model.addAttribute("reply", reply);
		
		return "/board/camping-boardsingle";
	}
	
	@GetMapping("/camping-board")
	public String boardLoading(Model model, @RequestParam(value = "title", required = false) String title, 
			@RequestParam(value = "writter", required = false) String writter, 
			@RequestParam(value = "category", required = false, defaultValue = "0") int category,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@PageableDefault(page = 0, size = 5) Pageable pageable) {
		
		if(title == null) {
			title = "";
		}
		
		if(writter == null) {
			writter = "";
		}
		
		Page<Board> boardList = service.findSearchValue(title, writter, category, 1, pageable, sortBy);
		List<Board> findNotice = service.findNotice();
		SearchValue searchValue = new SearchValue();
		
		searchValue.setTitle(title);
		searchValue.setWritter(writter);
		searchValue.setCategory(category);
		
		
		int boardListCount = (int)boardList.getTotalElements();
		PageInfo pageInfo = new PageInfo(pageable.getPageNumber(), 5, boardListCount, 10);
		
		
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("title", title);
		model.addAttribute("findNotice",findNotice);
		model.addAttribute("searchValue", searchValue);
		
		
		return "/board/camping-board";
	}
}
