package com.camping.jpa.review.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camping.jpa.review.model.vo.CampingReview;

public interface CampingReviewRepository extends JpaRepository<CampingReview, Integer> {
	
	CampingReview findByCrid(int crid);
	
	@Query("select r from CAMP_REVIEW r" 
			+ " JOIN fetch r.campinglist c"
			+ " WHERE c.cid = :cid")
	List<CampingReview> findByCid(@Param("cid") int cid);
}
