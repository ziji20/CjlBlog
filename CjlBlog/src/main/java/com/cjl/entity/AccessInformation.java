package com.cjl.entity;

/**
 * ������ʵ��
 * @author hasee
 *
 */
public class AccessInformation {

	private String id;//id
	private String time;//ʱ��
	private String address;//��ַ
	private Integer count;//����
	
	public AccessInformation() {} 
	public AccessInformation(String id, String time, String address, Integer count) {
		super();
		this.id = id;
		this.time = time;
		this.address = address;
		this.count = count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
