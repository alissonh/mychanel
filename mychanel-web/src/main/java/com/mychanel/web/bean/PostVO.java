package com.mychanel.web.bean;

import com.mychanel.util.Util;

public class PostVO {

	private String fileName;

	private String thumbNail ;

	public String getFileName() {
		return  Util.src +  fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getThumbNail() {
		return Util.poster +  thumbNail;
	}

	public void setThumbNail(String thumbNail) {
		this.thumbNail = thumbNail;
	} 
	
}
