package com.exe.smartcontact.helper;

public class Message {

	private String type;
	private String content;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Message(String type, String content) {
		super();
		this.type = type;
		this.content = content;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
