package com.camping.jpa.list.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camping.jpa.list.model.repository.CampingListRepository;
import com.camping.jpa.list.model.repository.CampingSiteRepository;
import com.camping.jpa.list.model.repository.UserRepository;
import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampingListService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private CampingListRepository repo2;
	private final CampingSiteRepository repo3;
	
	public List<CampingList> findByOrderByCstartDesc() {
		return repo2.findByOrderByCstartDesc();
	}
	
	public CampingList findByCid(int cid) {
		return repo2.findByCid(cid);
	}
	
	public CampingSite findBySiteid(int siteid) {
		return repo3.findBySiteid(siteid);
	}
	
//	public List<User> findByMemberlist_Campinglist_Cid(int campinglistCid) {
//		return repo.findByMemberlist_Campinglist_Cid(campinglistCid);
//	}
}
