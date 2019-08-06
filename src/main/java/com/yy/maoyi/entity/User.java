package com.yy.maoyi.entity;/**
							* Function: <br/>
							* REASON: <br/>
							* VERSION: 4.0
							*
							* @Auther: zhangyang
							* @Date: 2019/5/28.
							*/

/**
 * File Name:User
 * 
 * @Description TODO Date:2019/5/28 18:41
 * @author zhangyang
 * @Version 4.0 Copyright (c) 2019, All Rights Reserved.
 */
public class User {

	private String uname;
	private String password;
	private String queryData;
	private String vuname;
	private String vpassword;

	public String getVuname() {
		return vuname;
	}

	public void setVuname(String vuname) {
		this.vuname = vuname;
	}

	public String getVpassword() {
		return vpassword;
	}

	public void setVpassword(String vpassword) {
		this.vpassword = vpassword;
	}

	public String getQueryData() {
		return queryData;
	}

	public void setQueryData(String queryData) {
		this.queryData = queryData;
	}

	public String getDownBG() {
		return downBG;
	}

	public void setDownBG(String downBG) {
		this.downBG = downBG;
	}

	public String getDownFX() {
		return downFX;
	}

	public void setDownFX(String downFX) {
		this.downFX = downFX;
	}

	private String downBG;
	private String downFX;

	public String getPassword() {
		return password;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
