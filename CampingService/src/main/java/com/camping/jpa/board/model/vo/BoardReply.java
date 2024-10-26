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
@Entity(name = "BOARD_REPLY")
@Transactional
public class BoardReply {
	@Id
	@Column(name = "RID")
	public int rid;      
	@Column(name = "BID")
	public int bid;      
	
	@Column(name = "RCONTENT")
	public String rcontent;
		
	@Column(name = "RCREATEDATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	public Date rcreatedate;
	@Column(name = "RMODIFYDATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	public Date rmodifydate;
	
	@OneToOne
	@JoinColumn(name = "RUSERNO")
	private User userlist;
}
