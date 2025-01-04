package com.camping.jpa.review.model.vo;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.camping.jpa.list.model.vo.CampingList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CAMP_REVIEW")
@Transactional
public class CampingReview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CRID")
	public int crid;
//	@Column(name = "CID")
//	public int cid;
	
	@Column(name = "CRNAME")
	public String crname;
	@Column(name = "CRCONTENT")
	public String crcontent;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "CRCREATEDATE")
	public Date crcreatedate;
	@Column(name = "CRUSERNO")
	public String cruserno;
	@Column(name = "CRRATING")
	public Double crrating;
	
	@ManyToOne
	@JoinColumn(name = "FKCID")
	private CampingList campinglist;
	
}
