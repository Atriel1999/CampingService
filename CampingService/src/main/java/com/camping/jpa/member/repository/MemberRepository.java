package com.camping.jpa.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.camping.jpa.list.model.vo.User;

public interface MemberRepository extends JpaRepository<User, String> {
	
//	int selectCount();
//	List<User> selectAll();
//	User selectUser(String userno);
	User findByUsername(String username);
	User findByKakaotoken(String token);
//	int insertUser(User member);
//	int updateUser(User member);
	
}
