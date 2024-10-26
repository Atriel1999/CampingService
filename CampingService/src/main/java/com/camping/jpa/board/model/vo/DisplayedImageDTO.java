package com.camping.jpa.board.model.vo;

public class DisplayedImageDTO {
	String savePath;
	String originalName;
	
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public DisplayedImageDTO(String savePath, String originalName) {
		super();
		this.savePath = savePath;
		this.originalName = originalName;
	}
	public DisplayedImageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DisplayedImageDTO [savePath=" + savePath + ", originalName=" + originalName + "]";
	}
	
	
	
}
