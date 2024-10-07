package com.camping.jpa.member.model.service;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camping.jpa.list.model.vo.User;
import com.camping.jpa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	@Autowired
	private MemberRepository repo;
	
	public boolean validate(String username) {
		return repo.findByUsername(username) != null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int save(User member) {
		int result = 0;
		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(System.currentTimeMillis()));
		Date sqlDate = new java.sql.Date(System.currentTimeMillis());		
		if(member.getUsername().length() > 0) { // insert
			member.setUsercreated(sqlDate);
			member.setUserrole("user");
			member.setUserno("temp");
			result = repo.insertUser(member);
		} else { // fail
			result = 0;
		}
		
		return result;
	}
	
	public User loginKaKao(String kakaoToken) {
		User member = repo.findByKakaotoken(kakaoToken);
		if(member != null) {
			return member;
		}else {
			return null;
		}
	}
}
