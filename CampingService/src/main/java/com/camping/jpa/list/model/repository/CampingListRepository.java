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
			+ " WHERE c.cname like CONCAT('%',:searchValue,'%')"
			+ " AND c.ctype = :ctype"
			+ " ORDER BY c.cstart DESC")
	List<CampingList> findByOrderByCstartDesc(@Param("searchValue") String searchValue, @Param("ctype") int ctype);
	
	@Query("select c from CAMPING c"
			+ " JOIN fetch c.campingmember m"
			+ " JOIN fetch m.userlist u"
			+ " WHERE c.cname like CONCAT('%',:searchValue,'%')"
			+ " ORDER BY c.cstart DESC")
	List<CampingList> findByOrderByCstartDesc(@Param("searchValue") String searchValue);
	
	CampingList findByCid(int cid);
	
	CampingList findByCname(String cname);
	
	CampingSite findBySiteid(int siteid);
	
	@Modifying
	@Query("UPDATE CAMPING c SET c.cstatus = :status"
			+ " WHERE c.cid = :cid")
	int setStatusCamping(@Param("cid") int cid, @Param("status") int status);
	
}


//@Query("select e from EVENTCALENDAR e "
//		+ " WHERE e.subtitle like CONCAT('%',:searchValue1,'%')"
//		+ " AND e.sido like CONCAT('%',:searchValue2,'%')"
//		+ " AND e.gugun like CONCAT('%',:searchValue3,'%')"
//		+ " AND ((e.sdate <= CAST(:searchValue4 AS integer) AND e.edate >= CAST(:searchValue4 AS integer)) OR CAST(:searchValue4 AS integer)<100)")
//Page<EventCalendar> findBysearchValueContaining(@Param("searchValue1") String searchValue1,@Param("searchValue2") String searchValue2,
//		@Param("searchValue3") String searchValue3,@Param("searchValue4") String searchValue4, Pageable pageable);
//

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


