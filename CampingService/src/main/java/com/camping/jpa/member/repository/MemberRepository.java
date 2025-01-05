package com.camping.jpa.member.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.camping.jpa.list.model.vo.User;

public interface MemberRepository extends JpaRepository<User, String> {
	
//	int selectCount();
//	List<User> selectAll();
//	User selectUser(String userno);
	User findByUserno(String userno);
	User findByUsername(String username);
	
	@Query("SELECT u FROM USER u"
			+ " WHERE u.kakaotoken = kakaotoken LIMIT 1")
	User findByKakaotoken(@Param("kakaotoken") String kakaotoken);
	
	@Query("SELECT u FROM USER u"
			+ " WHERE u.username like CONCAT('%',:username,'%')")
	List<User> searchUsername(@Param("username") String username);
	
	
	
	@Query("SELECT u FROM USER u"
			+ " WHERE u.username like CONCAT('%',:username,'%') AND"
			+ " u.userstatus = 1")
	List<User> searchRestrictUsername(@Param("username") String username);
	
//	@Transactional
//	@Modifying
//	@Query("INSERT INTO USER u"
//			+ " (u.userbandate)"
//			+ " VALUES (:date) WHERE u.userno = :userno")
//	int setBanDate(@Param("date") Date date, @Param("userno") String userno);
	
	
	@Transactional
	@Modifying
	@Query("INSERT INTO USER"
			+ " (userno, username, userphone, userimage, usercreated, userrole, kakaotoken)"
			+ " VALUES (:#{#member.userno}, :#{#member.username}, :#{#member.userphone}, :#{#member.userimage}, :#{#member.usercreated}, :#{#member.userrole}, :#{#member.kakaotoken})")
	int insertUser(@Param("member") User member);
	
//	int updateUser(User member);
	
	@Transactional
	@Modifying
	@Query("UPDATE USER u SET"
			+ " u.userstatus = :status"
			+ " WHERE u.userno = :userno")
	int updateUserStatus(@Param("userno") String userno, @Param("status") int status);
	
	@Transactional
	@Modifying
	@Query("UPDATE USER u SET"
			+ " u.userrole = :role"
			+ " WHERE u.userno = :userno")
	int updateStatusAdmin(@Param("userno") String userno, @Param("role") String role);
	
	/*
	@Modifying
	@Query("UPDATE BOARD b SET"
			+ " b.breadcount = b.breadcount + 1"
			+ " WHERE b.bid = :bid")
	int updateBreadcount(@Param("bid") int bid);
*/
}
