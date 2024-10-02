package com.camping.jpa.list.model.vo;

import java.sql.Date;
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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER")
@ToString(exclude= {"memberlist"})
@Transactional
public class User {
	@Id
	@Column(name = "USERNO")
	public String userno;
	@Column(name = "USERID")
	public String userid;	
	@Column(name = "USERPW")
	public String userpw;       
	@Column(name = "USERNAME")
	public String username;      
	@Column(name = "USERPHONE")
	public String userphone;      
	@Column(name = "USERIMAGE")
	public String userimage;
	@Column(name = "USERCREATED")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date usercreated;
	@Column(name = "USERROLE")
	public String userrole;
	@Column(name = "KAKAOTOKEN")
	public String kakaotoken;
	
	@OneToMany(mappedBy = "userlist", fetch =FetchType.EAGER)
	public List<CampingMember> memberlist = new ArrayList<>();
}
