package com.gateway.bankmgr.model;

import java.sql.Timestamp;

public class MasteKey {
	private Integer id;
	
	private String tersn;
	
	private String key_content;
	
	private String check_value;
	
	private Timestamp sndate;
	
	private String sncreate;
	
	private String key_index;
	
	private String type;
	
	private String brand;
	
	private Timestamp key_expdate;
	
	private String key_person;

	private String status;
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTersn() {
		return tersn;
	}

	public void setTersn(String tersn) {
		this.tersn = tersn;
	}

	public String getKey_content() {
		return key_content;
	}

	public void setKey_content(String key_content) {
		this.key_content = key_content;
	}

	public String getCheck_value() {
		return check_value;
	}

	public void setCheck_value(String check_value) {
		this.check_value = check_value;
	}

	public Timestamp getSndate() {
		return sndate;
	}

	public void setSndate(Timestamp sndate) {
		this.sndate = sndate;
	}

	public String getSncreate() {
		return sncreate;
	}

	public void setSncreate(String sncreate) {
		this.sncreate = sncreate;
	}

	public String getKey_index() {
		return key_index;
	}

	public void setKey_index(String key_index) {
		this.key_index = key_index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Timestamp getKey_expdate() {
		return key_expdate;
	}

	public void setKey_expdate(Timestamp key_expdate) {
		this.key_expdate = key_expdate;
	}

	public String getKey_person() {
		return key_person;
	}

	public void setKey_person(String key_person) {
		this.key_person = key_person;
	}

}
