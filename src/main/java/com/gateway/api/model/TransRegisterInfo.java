package com.gateway.api.model;


public class TransRegisterInfo extends BaseInfo{
	
    private BusInfo busInfo;
	private TerInfo terInfo;
	
	public TerInfo getTerInfo() {
		return terInfo;
	}
	public void setTerInfo(TerInfo terInfo) {
		this.terInfo = terInfo;
	}
	public BusInfo getBusInfo() {
		return busInfo;
	}
	public void setBusInfo(BusInfo busInfo) {
		this.busInfo = busInfo;
	}
}
