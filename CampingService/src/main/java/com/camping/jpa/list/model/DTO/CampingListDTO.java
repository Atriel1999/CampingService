package com.camping.jpa.list.model.DTO;



import com.camping.jpa.list.model.vo.CampingList;
import com.camping.jpa.list.model.vo.CampingMember;
import com.camping.jpa.list.model.vo.User;

public interface CampingListDTO {

	Integer getCid();		// 캠핑고유번호
	Integer getSiteid();		// 캠핑장이름
	String getCname();        // 캠핑이름
	String getCphone();        // 전화번호
	String getCpm();      // 이동수단
	String getCexpense();      // 캠핑 총 비용
	String getCdeatail();   // 캠핑 세부내용
	String getCcomment();     // 캠핑 코멘트
	String getCdrink();    // 음주가능여부
	String getCpet();      // 애완동물가능여부
	String getCchild();      // 아이가능여부
	String getCsmoking();     // 흡연여부
	String getCstart();     // 시작일
	String getCend();         // 종료일
	String getCtype();         // 종료일
	String getUserid();
	String getUserno();
}
