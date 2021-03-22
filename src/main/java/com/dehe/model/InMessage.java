package com.dehe.model;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.io.Serializable;
import java.util.Date;

public class InMessage implements Serializable {
	private Integer userid;
	private String infrom;


	@JsonProperty
	private String Addressee;
	
	private String content;

	private String fileurl;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
	private Date time;

	public String getInfrom() {
		return infrom;
	}

	public void setInfrom(String infrom) {
		this.infrom = infrom;
	}

	public String getAddressee() {
		return Addressee;
	}

	public void setAddressee(String addressee) {
		Addressee = addressee;
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
