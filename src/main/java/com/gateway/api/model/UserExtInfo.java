package com.gateway.api.model;

import java.util.ArrayList;
import java.util.List;

public class UserExtInfo extends UserInfo{
	
	private List<String> functionList = new ArrayList<String>();
	private List<TerInfo> terInfoList = new ArrayList<TerInfo>();
	private MerchantInfo merchantInfo;
	private LoginHisInfo loginHisInfo = new LoginHisInfo();
	private BusInfo busInfo = new BusInfo();
	
	public MerchantInfo getMerchantInfo() {
		return merchantInfo;
	}
	public void setMerchantInfo(MerchantInfo merchantInfo) {
		this.merchantInfo = merchantInfo;
	}
	public BusInfo getBusInfo() {
		return busInfo;
	}
	public void setBusInfo(BusInfo busInfo) {
		this.busInfo = busInfo;
	}
	public List<TerInfo> getTerInfoList() {
		return terInfoList;
	}
	public void setTerInfoList(List<TerInfo> terInfoList) {
		this.terInfoList = terInfoList;
	}
	public List<String> getFunctionList() {
		return functionList;
	}
	public void setFunctionList(List<String> functionList) {
		this.functionList = functionList;
	}
	public LoginHisInfo getLoginHisInfo() {
		return loginHisInfo;
	}
	public void setLoginHisInfo(LoginHisInfo loginHisInfo) {
		this.loginHisInfo = loginHisInfo;
	}
	
	
	
}
