package com.camping.jpa.list.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.camping.jpa.list.model.vo.CampingImage;


public interface CampingImageRepository extends JpaRepository<CampingImage, Integer> {

	
//	@Transactional
//	@Modifying
//	@Query("INSERT INTO CAMPING_IMAGE (cid, ciimage) VALUES(:#{#campingimage.cid}, :#{#campingimage.ciimage})")
//	CampingImage InsertCampingImage(@Param("campingimage") CampingImage campingimage);
//	
//	@Transactional
//	@Modifying
//	@Query("DELETE FROM CAMPING_IMAGE c WHERE c.ccid = :ccid")
//	CampingImage DeleteCampingImage(int ccid);
	
	List<CampingImage> findByCid(int cid);
	
}
