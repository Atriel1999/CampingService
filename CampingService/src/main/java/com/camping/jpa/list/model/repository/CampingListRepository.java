package com.camping.jpa.list.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.vo.User;
import com.camping.jpa.list.model.DTO.CampingListDTO;

public interface CampingListRepository extends JpaRepository<CampingList, Integer> {

//	@Query("select c from CAMPING c"
//			+ " ORDER BY c.cstart DESC")
//	List<CampingList> findByOrderByCstartDesc();
	
	@Query("select c from CAMPING c"
			+ " JOIN fetch c.campingmember m"
			+ " JOIN fetch m.userlist u"
			+ " ORDER BY c.cstart DESC")
	List<CampingList> findByOrderByCstartDesc();
	
	CampingList findByCid(int cid);
	
	CampingSite findBySiteid(int siteid);
	
}

//@Query("select c.cname, c.ccomment, c.cstart, c.ctype, u.userid from CAMPING c"
//		+ " LEFT JOIN c.campingmember m"   // AND m.caauth = '1'
//		+ " RIGHT JOIN m.userlist u"
//		+ " ORDER BY c.cstart DESC")
//List<CampingList> findByOrderByCstartDesc();

//@Query(value = "select com.camping.jpa.list.model.DTO.CampingListDTO(c.cname, c.ccomment, c.cstart, c.ctype, u.userid) from CAMPING c"
//		+ " LEFT JOIN c.campingmember m"   // AND m.caauth = '1'
//		+ " RIGHT JOIN m.userlist u"
//		+ " ORDER BY c.cstart DESC", nativeQuery = true)
//List<CampingList> findByOrderByCstartDesc();

//@Query("select c.cname, c.ccomment, c.cstart, c.ctype, u.userno from CAMPING_MEMBER m"
//+ " LEFT JOIN CAMPING c ON c.cid = :cid"
//+ " LEFT JOIN USER u ON u.userno = :userno")


