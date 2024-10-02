package com.camping.jpa.list.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.DTO.CampingSiteDTO;

public interface CampingSiteRepository extends JpaRepository<CampingSite, Integer> {

	@Query("select s from CAMPING_SITE s WHERE s.siteid = :siteid")
	CampingSite findBySiteid(@Param("siteid") int siteid);
	
}
