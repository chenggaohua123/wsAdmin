package com.gateway.fraud.model;

import java.util.ArrayList;
import java.util.List;


public class RiskControlInfo extends SecurityInfo{
	
	private static final long serialVersionUID = 1L;
	private String [] tradeNos;
	private String [] ips;
	private String [] cardNos;
	private String [] email;
	private String [] uid;
	private String [] police1;
	private String [] police2;
	private String [] police3;
	private String [] police4;
	private List<String> ipHighRisk;
	private List<String> cardNoHighRisk;
	private List<String> emailHighRisk;
	private List<String> uidHighRisk = new ArrayList<String>();
	private List<String> police1HighRisk = new ArrayList<String>();
	private List<String> police2HighRisk = new ArrayList<String>();
	private List<String> police3HighRisk = new ArrayList<String>();
	private List<String> police4HighRisk = new ArrayList<String>();
	
	public List<String> getUidHighRisk() {
		return uidHighRisk;
	}
	public void setUidHighRisk(List<String> uidHighRisk) {
		this.uidHighRisk = uidHighRisk;
	}
	public List<String> getPolice1HighRisk() {
		return police1HighRisk;
	}
	public void setPolice1HighRisk(List<String> police1HighRisk) {
		this.police1HighRisk = police1HighRisk;
	}
	public List<String> getPolice2HighRisk() {
		return police2HighRisk;
	}
	public void setPolice2HighRisk(List<String> police2HighRisk) {
		this.police2HighRisk = police2HighRisk;
	}
	public List<String> getPolice3HighRisk() {
		return police3HighRisk;
	}
	public void setPolice3HighRisk(List<String> police3HighRisk) {
		this.police3HighRisk = police3HighRisk;
	}
	public List<String> getPolice4HighRisk() {
		return police4HighRisk;
	}
	public void setPolice4HighRisk(List<String> police4HighRisk) {
		this.police4HighRisk = police4HighRisk;
	}
	
	public String[] getUid() {
		return uid;
	}
	public void setUid(String[] uid) {
		this.uid = uid;
	}
	public String[] getPolice1() {
		return police1;
	}
	public void setPolice1(String[] police1) {
		this.police1 = police1;
	}
	public String[] getPolice2() {
		return police2;
	}
	public void setPolice2(String[] police2) {
		this.police2 = police2;
	}
	public String[] getPolice3() {
		return police3;
	}
	public void setPolice3(String[] police3) {
		this.police3 = police3;
	}
	public String[] getPolice4() {
		return police4;
	}
	public void setPolice4(String[] police4) {
		this.police4 = police4;
	}
	
	public List<String> getIpHighRisk() {
		return ipHighRisk;
	}
	public void setIpHighRisk(List<String> ipHighRisk) {
		this.ipHighRisk = ipHighRisk;
	}
	public List<String> getCardNoHighRisk() {
		return cardNoHighRisk;
	}
	public void setCardNoHighRisk(List<String> cardNoHighRisk) {
		this.cardNoHighRisk = cardNoHighRisk;
	}
	public List<String> getEmailHighRisk() {
		return emailHighRisk;
	}
	public void setEmailHighRisk(List<String> emailHighRisk) {
		this.emailHighRisk = emailHighRisk;
	}
	public String[] getTradeNos() {
		return tradeNos;
	}
	public void setTradeNos(String[] tradeNos) {
		this.tradeNos = tradeNos;
	}
	public String[] getIps() {
		return ips;
	}
	public void setIps(String[] ips) {
		this.ips = ips;
	}
	public String[] getCardNos() {
		return cardNos;
	}
	public void setCardNos(String[] cardNos) {
		this.cardNos = cardNos;
	}
	public String[] getEmail() {
		return email;
	}
	public void setEmail(String[] email) {
		this.email = email;
	}
	
	
}
