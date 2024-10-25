package com.camping.jpa.member.repository;

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
	User findByKakaotoken(String kakaotoken);
	
	@Transactional
	@Modifying
	@Query("INSERT INTO USER"
			+ " (userno, username, userphone, userimage, usercreated, userrole, kakaotoken)"
			+ " VALUES (:#{#member.userno}, :#{#member.username}, :#{#member.userphone}, :#{#member.userimage}, :#{#member.usercreated}, :#{#member.userrole}, :#{#member.kakaotoken})")
	int insertUser(@Param("member") User member);
	
//	int updateUser(User member);
	
}
