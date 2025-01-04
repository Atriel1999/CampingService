package com.camping.jpa.list.model.vo;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CAMPING_MEMBER")
@ToString(exclude= {"campinglist"})
@Transactional
public class CampingMember {
	@Id
	@Column(name = "JOINID")
	public int joinid;
//	@Column(name = "CID")
//	public String cid;
//	@Column(name = "USERNO")
//	public String userno;
	@Column(name = "JOINDATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date joindate;
	@Column(name = "CAAUTH")
	public int caauth; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fkcid")
	public CampingList campinglist;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fkuserno")
	public User userlist;
}
