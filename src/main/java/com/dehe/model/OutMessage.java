package com.dehe.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class OutMessage {

	private Integer userid;
	private String from;
	
	private String content;

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
	private Date time = new Date();

	public OutMessage(){}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public OutMessage(String content){
		this.content = content;
		
	}
	private String fileurl;

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public OutMessage(Integer userid, String from, String content, Date time, String fileurl) {
		this.userid = userid;
		this.from = from;
		this.content = content;
		this.time = time;
		this.fileurl = fileurl;
	}

	public OutMessage(String from, String content) {
		this.from = from;
		this.content = content;
	}

	public String getFrom() {
		return from;
	}
	
	

	public void setFrom(String from) {
		this.from = from;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
	
}
