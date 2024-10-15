package com.camping.jpa.admin.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.camping.jpa.admin.model.repository.AdminRepository;
import com.camping.jpa.admin.model.vo.Admin;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
public class AdminService {
	@Autowired
	private AdminRepository repo;
	
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
}
