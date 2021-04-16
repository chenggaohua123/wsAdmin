package com.gateway.fraud.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class PayCountInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String elementType;
	private String elementValue;
	private Timestamp lastPayTime;

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}

	public Timestamp getLastPayTime() {
		return lastPayTime;
	}

	public void setLastPayTime(Timestamp lastPayTime) {
		this.lastPayTime = lastPayTime;
	}

}
