package com.camping.jpa.board.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.jpa.board.model.vo.BoardReply;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Integer>{
	List<BoardReply> findByBidOrderByRcreatedateDesc(int bid);
}
