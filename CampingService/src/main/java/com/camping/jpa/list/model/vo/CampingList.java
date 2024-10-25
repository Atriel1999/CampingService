package com.camping.jpa.list.model.vo;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CAMPING")
@Transactional
public class CampingList {
	@Id
	@Column(name = "CID")
	public int cid;		// 캠핑고유번호
	@Column(name = "SITEID")
	public int siteid;		// 캠핑장이름
	@Column(name = "CNAME")
	public String cname;        // 캠핑이름
	@Column(name = "CPHONE")
	public String cphone;        // 전화번호
	@Column(name = "CPM")
	public String cpm;      // 이동수단
	@Column(name = "CEXPENSE")
	public String cexpense;      // 캠핑 총 비용
	@Column(name = "CDETAIL")
	public String cdetail;   // 캠핑 세부내용
	@Column(name = "CCOMMENT")
	public String ccomment;     // 캠핑 코멘트
	@Column(name = "CDRINK")
	public boolean cdrink;    // 음주가능여부
	@Column(name = "CPET")
	public boolean cpet;      // 애완동물가능여부
	@Column(name = "CCHILD")
	public boolean cchild;      // 아이가능여부
	@Column(name = "CSMOKING")
	public boolean csmoking;     // 흡연여부
	@Column(name = "CSTART")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date cstart;     // 시작일
	@Column(name = "CEND")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date cend;         // 종료일
	@Column(name = "CTYPE")
	public int ctype;
	@Column(name = "CSTATUS")
	public int cstatus;
	
	public String username;
	
	@OneToMany(mappedBy = "campinglist", fetch =FetchType.EAGER)
	public List<CampingMember> campingmember = new ArrayList<>();
	
	
}
