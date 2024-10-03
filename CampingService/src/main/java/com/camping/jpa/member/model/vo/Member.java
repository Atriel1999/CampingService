//package com.camping.jpa.member.model.vo;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.format.annotation.DateTimeFormat;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity(name = "USER")
//@Transactional
//public class Member {
//	@Id
//	@Column(name = "USERNO")
//	public String userno;      
//	@Column(name = "USERNAME")
//	public String username;      
//	@Column(name = "USERPHONE")
//	public String userphone;      
//	@Column(name = "USERIMAGE")
//	public String userimage;
//	@Column(name = "USERCREATED")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	public Date usercreated;
//	@Column(name = "USERROLE")
//	public String userrole;
//	@Column(name = "KAKAOTOKEN")
//	public String kakaotoken;
//	@Column(name = "userid")
//	public String userid;
//}
