package com.gateway.fraud.model;

import java.io.Serializable;



public class SecurityInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String accountNo;
	private String passWord;
	

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
