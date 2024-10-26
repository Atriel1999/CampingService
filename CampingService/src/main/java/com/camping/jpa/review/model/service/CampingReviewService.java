package com.camping.jpa.review.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.AmazonS3;
import com.camping.jpa.review.model.repository.CampingReviewImageRepository;
import com.camping.jpa.review.model.repository.CampingReviewRepository;
import com.camping.jpa.review.model.vo.CampingReview;
import com.camping.jpa.review.model.vo.CampingReviewImage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class CampingReviewService {
	@Autowired
	CampingReviewRepository repo;
	
	@Autowired
	CampingReviewImageRepository repoImage;
	
	public List<CampingReview> findReviewByTop3(){
		return repo.findReviewByTop3();
	}
	
	public CampingReview saveReview(CampingReview review) {
		return repo.save(review);
	}
	
	public int InsertReviewImage(List<CampingReviewImage> reviewImage) {
		int result = 1;
		
		for(CampingReviewImage Review : reviewImage) {
			CampingReviewImage saveReview = repoImage.save(Review);
			
			if(saveReview.getCriimage().isEmpty()) {
				result = result * 0;
				log.info("listdbg5 fail! with :" + Review);
			} else {
				log.info("listdbg5 success! with :" + saveReview);
			}
		}
		
		return result;
	}
	
	public CampingReview findByCrid(int crid) {
		return repo.findByCrid(crid);
	}
	
	public List<CampingReview> findByCid(int cid){
		return repo.findByCid(cid);	
	}
	
	public List<CampingReviewImage> findImageByCrid(int crid){
		return repoImage.findImageByCrid(crid);
	}
}
