package com.cjl.entity;

public class LMessage {

	private Integer id;//����id
	private String name;//����
	private String contactInformation;//��ϵ��ʽ
	private String content;//����
	
	
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
