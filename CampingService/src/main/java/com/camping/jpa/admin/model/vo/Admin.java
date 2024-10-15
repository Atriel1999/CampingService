package com.camping.jpa.admin.model.vo;

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
@Entity(name = "ADMIN_LOGIN")
@Transactional
public class Admin {
	@Id
	@Column(name = "ADMINID")
	public String adminid;      
	@Column(name = "ADMINPW")
	public String adminpw;      
}

