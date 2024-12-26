package com.camping.jpa.admin.model.service;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.camping.jpa.admin.model.repository.AdminRepository;
import com.camping.jpa.admin.model.vo.Admin;
import com.camping.jpa.list.model.vo.User;
import com.camping.jpa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
public class AdminService {
	@Autowired
	private AdminRepository repo;
	
	@Autowired
	private MemberRepository userrepo;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public Admin login(String id, String pwd) {
		Admin admin = repo.findByAdminid(id);
		log.info("dbg1333 login data: " + admin + ", and pwd: " + pwd);
				
		if(admin == null) {
			return null;
		}
		
		if(passwordEncoder.matches(pwd, admin.getAdminpw())) {
			return admin;
		} else {
			return null;
		}
	}
	
	public List<User> searchUsername(String username){
		return userrepo.searchUsername(username);
	}
	
	public List<User> searchRestrictUsername(String username){
		return userrepo.searchRestrictUsername(username);
	}
	
	public int retrictUserStatus(String userno) {
		int status = 1;
		return userrepo.updateUserStatus(userno, status);
	}
	
	public int releaseUserStatus(String userno) {
		int status = 0;
		return userrepo.updateUserStatus(userno, status);
	}
	
	public int setAdmin(String userno) {
		String role = "admin";
		return userrepo.updateStatusAdmin(userno, role);
	}
	
	public int revkoeAdmin(String userno) {
		String role = "user";
		return userrepo.updateStatusAdmin(userno, role);
	}
	
	public User setBanDate(String userno) {
		Date now = new Date();
		
		User TempUser = userrepo.findByUserno(userno);
		TempUser.setUserbandate(now);
		
		return userrepo.save(TempUser);
//		return userrepo.setBanDate(now , userno);
	}
}
