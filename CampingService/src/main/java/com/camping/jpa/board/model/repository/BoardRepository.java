package com.camping.jpa.board.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camping.jpa.board.model.vo.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	
	Board findByBid(int bid);
	
	@Query("select b from BOARD b"
			+ " join fetch b.userlist u"
			+ " WHERE b.btitle like CONCAT('%',:title,'%')"
			+ " AND u.username like CONCAT('%',:writter,'%')"
			+ " AND b.bcategory = :category"
			+ " AND b.bcategory != :bcategory")
	Page<Board> findSearchValue(@Param("title") String title, @Param("writter") String writter, @Param("category") int category, 
			@Param("bcategory") int bcategory, Pageable pageable);
	
	@Query("select b from BOARD b"
			+ " join fetch b.userlist u"
			+ " WHERE b.btitle like CONCAT('%',:title,'%')"
			+ " AND u.username like CONCAT('%',:writter,'%')"
			+ " AND b.bcategory != :bcategory")
	Page<Board> findSearchValueNocategory(@Param("title") String title, @Param("writter") String writter, 
			@Param("bcategory") int bcategory, Pageable pageable);
	
	@Query("select b from BOARD b"
			+ " WHERE b.bcategory = 1")
	List<Board> findNotice();
	
	@Modifying
	@Query("UPDATE BOARD b SET"
			+ " b.breadcount = b.breadcount + 1"
			+ " WHERE b.bid = :bid")
	int updateBreadcount(@Param("bid") int bid);
	
	@Query(value = "select * from board b"
			+ " WHERE b.bcategory != 1"
			+ " ORDER BY bcreatedate DESC"
			+ " LIMIT 6", nativeQuery = true)
	List<Board> findBoardByTop6();
	
	@Query(value = "select * from board b"
			+ " WHERE b.bcategory = 1"
			+ " ORDER BY bcreatedate DESC"
			+ " LIMIT 6", nativeQuery = true)
	List<Board> findNoticeByTop6();
}
