package com.camping.jpa.list.model.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	@Autowired
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
	
	private static int count = 0; // 파일 식별을 위한 count 값
	public String saveFile(MultipartFile upfile) {
		
		// 폴더가 존재하지 않을때 해당 경로를 만드는 코드
//		if(folder.exists() == false) {
//			folder.mkdirs();
//		}
//		System.out.println(savePath);
		
		// 파일 이름을 날짜시간 + count를 더해서 만드는 코드, test.jpg -> 20240112_1044232_1.jpg
		String originalFilename = upfile.getOriginalFilename();
		String renamedFilename = "camp" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_hhmmssSSS"));
		renamedFilename += "_" + (count++);
		renamedFilename += originalFilename.substring(originalFilename.lastIndexOf("."));
		
		try {
			// 실제 파일을 디스크에 쓰는 로직
		} catch (Exception e) {
			return null;
		}
		return renamedFilename;
	}
	
	
	
//	public List<User> findByMemberlist_Campinglist_Cid(int campinglistCid) {
//		return repo.findByMemberlist_Campinglist_Cid(campinglistCid);
//	}
}
