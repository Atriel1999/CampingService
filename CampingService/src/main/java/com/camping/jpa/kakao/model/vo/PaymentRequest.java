package com.camping.jpa.kakao.model.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.CampingSite;
import com.camping.jpa.list.model.vo.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PAYMENT_REQUEST")
@Transactional
public class PaymentRequest {
	@Id
	@Column(name="TID")		
	public String tid;
//	@Column(name="USERNO")			
//	public String userno;
//	@Column(name="CID")			
//	public int cid;
//	@Column(name="SITEID")			
//	public int siteid;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@Column(name="CREATED_AT")			
	public Date created_at;
	
	@OneToOne
	@JoinColumn(name = "fkcid")
	private CampingList campinglist;
	
	@OneToOne
	@JoinColumn(name = "fkuserno")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "fksiteid")
	private CampingSite siteid;
}
