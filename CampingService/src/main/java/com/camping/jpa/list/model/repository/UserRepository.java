package com.camping.jpa.list.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.User;

public interface UserRepository extends JpaRepository<User, String> {
//	@Query("select c.cname, c.ccomment, c.cstart, c.ctype, u.userno from CAMPING_MEMBER m"
//			+ " LEFT JOIN CAMPING c ON c.cid = :cid"
//			+ " LEFT JOIN USER u ON u.userno = :userno")
//	List<CampingList> findTop8ByOrderByCstartDesc();
	
//	List<User> findByMemberlist_Campinglist_Cid(int campinglistCid);
	
}

//@Query("select c.cname, c.ccomment, c.cstart, c.ctype, m.userno from CAMPING c"
//		+ " join c.campingmember m WHERE m.cid = :cid AND m.caauth = '1'"
//		+ " ORDER BY c.cstart DESC LIMIT 8")
