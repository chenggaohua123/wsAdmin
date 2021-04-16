package com.gateway.api.model;

public class ReSetPass extends BaseInfo{
	private String newPassWord="";
	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}
	
}
