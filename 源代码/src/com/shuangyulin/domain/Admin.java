package com.shuangyulin.domain;

public class Admin {
	/*管理员用户名*/
	private String username;
	/*登陆密码*/
	private String password;
	/*用户身份*/
	private String identify;
	
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
