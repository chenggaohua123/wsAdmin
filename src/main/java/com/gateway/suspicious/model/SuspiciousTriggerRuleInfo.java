package com.gateway.suspicious.model;

import java.util.List;

public class SuspiciousTriggerRuleInfo {
	private int count;
	private List<SuspiciousRuleInfo> list;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<SuspiciousRuleInfo> getList() {
		return list;
	}
	public void setList(List<SuspiciousRuleInfo> list) {
		this.list = list;
	}
}
