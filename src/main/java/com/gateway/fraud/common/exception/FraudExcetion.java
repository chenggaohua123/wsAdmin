package com.gateway.fraud.common.exception;


public class FraudExcetion extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMsg;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public FraudExcetion() {

	}

	public FraudExcetion(String errorCode,String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	
	public FraudExcetion(String message) {
		super(message);
	}

	public FraudExcetion(Throwable cause) {
		super(cause);
	}

	public FraudExcetion(String message, Throwable cause) {
		super(message, cause);
	}

}
