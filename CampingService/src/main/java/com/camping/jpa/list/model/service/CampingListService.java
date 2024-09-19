package com.camping.jpa.list.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camping.jpa.list.model.repository.CampingListRepository;
import com.camping.jpa.list.model.vo.CampingList;

@Service
public class CampingListService {
	@Autowired
	private CampingListRepository repo;
	
	public CampingList findByCid(String cid) {
		return repo.findByCid(cid);
	}
}
