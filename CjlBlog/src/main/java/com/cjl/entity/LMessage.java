package com.cjl.entity;

public class LMessage {

	private Integer id;//留言id
	private String name;//姓名
	private String contactInformation;//联系方式
	private String content;//内容
	
	
	public LMessage() {}
	public LMessage(Integer id, String name, String contactInformation, String content) {
		super();
		this.id = id;
		this.name = name;
		this.contactInformation = contactInformation;
		this.content = content;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
