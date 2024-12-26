package com.camping.jpa.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camping.jpa.board.controller.BoardController;
import com.camping.jpa.board.model.repository.BoardReplyRepository;
import com.camping.jpa.board.model.repository.BoardRepository;
import com.camping.jpa.board.model.vo.Board;
import com.camping.jpa.board.model.vo.BoardReply;
import com.camping.jpa.list.model.repository.CampingSiteRepository;
import com.camping.jpa.list.model.vo.CampingSite;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class BoardService {
	
	@Autowired
	private BoardRepository repo;
	
	@Autowired
	private BoardReplyRepository repoReply;
		
	public List<Board> findBoardByTop6(){
		return repo.findBoardByTop6();
	}
	
	public List<Board> findNoticeByTop6(){
		return repo.findNoticeByTop6();
	}
	
	public Board findByBid(int bid) {
		return repo.findByBid(bid);
	}
	
	public int updateBreadcount(int bid) {
		return repo.updateBreadcount(bid);
	}
	
	public List<BoardReply> findReplyByBid(int bid) {
		return repoReply.findByBidOrderByRcreatedateDesc(bid);
	}
	
	public BoardReply saveBoardReply(BoardReply reply) {
		return repoReply.save(reply);
	}
	
	public List<Board> findNotice(){
		return repo.findNotice();
	}
	
	public Board saveBoard(Board board) {
		return repo.save(board);
	}
	
	public Page<Board> findSearchValue(String title, String writter, int category, int bcategory, Pageable pageable, String sortBy){
		
		/*				sortBy
		 * 정렬법 default b1(최신순): bcreatedate기준 desc
		 * 				b2(오래된순): bcreatedate기준 asc
		 * 				b3(조회수 많은순): breadcount기준 desc
		*/	
		PageRequest request = null;
		int page = pageable.getPageNumber() - 1;
		
		if(sortBy == null || sortBy.isEmpty() || sortBy.equals("b1")) {
			sortBy = "bcreatedate";
			request = PageRequest.of(page, 10, Sort.by(sortBy).descending());
		} else if(sortBy.equals("b2")) {
			sortBy = "bcreatedate";
			request = PageRequest.of(page, 10, Sort.by(sortBy).ascending());
		} else if(sortBy.equals("b3")) {
			sortBy = "breadcount";
			request = PageRequest.of(page, 10, Sort.by(sortBy).descending());
		}
		
		log.info("dbg1: " + title + ", " +  writter + ", " + category + ", " + bcategory);
		
		if(category != 0) {
			return repo.findSearchValue(title, writter, category, bcategory, request);
		} else {
			return repo.findSearchValueNocategory(title, writter, bcategory, request);
		}
			
	}

}
