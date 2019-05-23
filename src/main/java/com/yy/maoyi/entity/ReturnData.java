package com.yy.maoyi.entity;

import java.util.List;
import java.util.Map;

public class ReturnData {

	private String ok;
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMygType() {
		return mygType;
	}
	public void setMygType(String mygType) {
		this.mygType = mygType;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public List getMessageList() {
		return messageList;
	}
	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	private String errorCode;
	private String mygType;
	private PData data;
	public PData getData() {
		return data;
	}
	public void setData(PData data) {
		this.data = data;
	}
	private String errors;
	private List messageList;
	private String messageType;
	
}
