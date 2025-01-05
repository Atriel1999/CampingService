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
import jakarta.persistence.ManyToOne;
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
@Entity(name = "CAMPING_SITE")
@Transactional
public class CampingSite {
	@Id
	@Column(name="SITEID")				
	public int siteid;
	@Column(name="SITECOMPANY")			
	public String sitecompany;
	@Column(name="SITELOCATE")			
	public String sitelocate;
	@Column(name="SITEINDUTY")			
	public String siteinduty;
	@Column(name="SITEFACILITY")		
	public String sitefacility;
	@Column(name="SITESTATUS")			
	public String sitestatus;
	@Column(name="SITEWATERAMOUNT")		
	public int sitewateramount;
	@Column(name="SITETOILETAMOUNT")	
	public int sitetoiletamount;
	@Column(name="SITESHOWERAMOUNT")	
	public int siteshoweramount;
	@Column(name="SITEFEATURE")			
	public String sitefeature;
	@Column(name="SITEADDR")			
	public String siteaddr;
	@Column(name="SITEX")				
	public double sitex;
	@Column(name="SITEY")				
	public double sitey;
	@Column(name="SITETEL")				
	public String sitetel;
	@Column(name="SITEURL")				
	public String siteurl;
	@Column(name="SITETERM")			
	public String siteterm;
	@Column(name="SITEOPER")			
	public String siteoper;
	@Column(name="SITEANIMAL")			
	public String siteanimal;
	@Column(name="SITEIMAGE")			
	public String siteimage;
	@Column(name="PRICE")			
	public int price;
}
