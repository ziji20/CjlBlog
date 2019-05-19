package com.cjl.entity;

/**
 * 博主实体
 * @author Administrator
 *
 */
public class Blogger {

	private Integer id; // 编号
	private String userName; // 用户名
	private String password; // 密码
	private String profile; // 个人简介
	private String nickName; // 昵称
	private String sign; // 个性签名
	private String imageName; // 头像
	private Integer qq; //QQ
	private String telephone;// 电话
	private String email;//邮箱
	
	//忘记密码时
	private String newPassowrd ;//新密码
	private String vcod;//验证码
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Integer getQq() {
		return qq;
	}
	public void setQq(Integer qq) {
		this.qq = qq;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNewPassowrd() {
		return newPassowrd;
	}
	public void setNewPassowrd(String newPassowrd) {
		this.newPassowrd = newPassowrd;
	}
	public String getVcod() {
		return vcod;
	}
	public void setVcod(String vcod) {
		this.vcod = vcod;
	}
	
	
}
