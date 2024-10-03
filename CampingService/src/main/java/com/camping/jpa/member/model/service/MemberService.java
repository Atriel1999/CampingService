package com.camping.jpa.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
