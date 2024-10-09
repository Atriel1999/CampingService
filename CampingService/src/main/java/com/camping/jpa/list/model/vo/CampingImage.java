package com.camping.jpa.list.model.vo;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@AllArgsConstructor
@Entity(name = "CAMPING_IMAGE")
@Transactional
public class CampingImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CIID")
	public int ciid;
	@Column(name = "CID")
	public int cid;
	@Column(name = "CIIMAGE")
	public String ciimage;
}
