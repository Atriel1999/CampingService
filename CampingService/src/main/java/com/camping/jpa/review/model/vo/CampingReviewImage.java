package com.camping.jpa.review.model.vo;

import java.util.Date;

import com.camping.jpa.list.model.vo.CampingList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CAMP_REVIEW_IMAGE")
@Transactional
public class CampingReviewImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CRIID")
	public int criid;
	
	@Column(name = "FKCRID")
	public int crid;
	
	@Column(name = "CRIIMAGE")
	public String criimage;
}
