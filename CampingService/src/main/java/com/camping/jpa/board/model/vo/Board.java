package com.camping.jpa.board.model.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BOARD")
@Transactional
public class Board {
	@Id
	@Column(name = "BID")
	public int bid;      
	@Column(name = "BTITLE")
	public String btitle;      
	@Column(name = "BCONTENT")
	public String bcontent;
		
	@Column(name = "BREADCOUNT")
	public int breadcount;
		
	@Column(name = "BCREATEDATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	public Date bcreatedate;
	@Column(name = "BMODIFYDATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	public Date bmodifydate;
	
	@Column(name = "BCATEGORY")
	public int bcategory;
	
	@OneToOne
	@JoinColumn(name = "USERNO")
	private User userlist;
}
