package com.camping.jpa.admin.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.jpa.admin.model.vo.Admin;


public interface AdminRepository extends JpaRepository<Admin, String>{

	Admin findByAdminid(String adminid);

}
//int selectCount();
//List<User> selectAll();
//User selectUser(String userno);
//User findByUsername(String username);
//User findByKakaotoken(String kakaotoken);

//@Transactional
//@Modifying
//@Query("INSERT INTO USER"
//		+ " (userno, username, userphone, userimage, usercreated, userrole, kakaotoken)"
//		+ " VALUES (:#{#member.userno}, :#{#member.username}, :#{#member.userphone}, :#{#member.userimage}, :#{#member.usercreated}, :#{#member.userrole}, :#{#member.kakaotoken})")
//int insertUser(@Param("member") User member);

//int updateUser(User member);
