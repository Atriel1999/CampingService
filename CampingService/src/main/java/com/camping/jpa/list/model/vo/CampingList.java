package com.camping.jpa.list.model.vo;

import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CAMPINGLIST")
@Transactional
public class CampingList {
	@Id
	@Column(name = "CID")
	public String cID;		// 캠핑고유번호
	@Column(name = "SITEID")
	public String siteID;		// 캠핑장이름
	@Column(name = "CNAME")
	public int cNAME;        // 캠핑이름
	@Column(name = "CPHONE")
	public int cPHONE;        // 전화번호
	@Column(name = "CPM")
	public String cPM;      // 이동수단
	@Column(name = "CEXPENSE")
	public String cEXPENSE;      // 캠핑 총 비용
	@Column(name = "CDETAIL")
	public String cDETAIL;   // 캠핑 세부내용
	@Column(name = "CCOMMENT")
	public String cCOMMENT;     // 캠핑 코멘트
	@Column(name = "CDRINK")
	public String cDRINK;    // 음주가능여부
	@Column(name = "CPET")
	public String cPET;      // 애완동물가능여부
	@Column(name = "CCHILD")
	public String cCHILD;      // 아이가능여부
	@Column(name = "CSMOKING")
	public String cSMOKING;     // 흡연여부
	@Column(name = "CSTART")
	public String cSTART;     // 시작일
	@Column(name = "CEND")
	public String cEND;         // 종료일
}
