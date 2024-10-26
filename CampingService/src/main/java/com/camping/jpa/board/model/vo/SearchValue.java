package com.camping.jpa.board.model.vo;

import lombok.Getter;

@Getter
public class SearchValue {
	String title;
	String writter;
	int category;
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setWritter(String writter) {
		this.writter = writter;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
}
