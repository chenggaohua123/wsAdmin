package com.gateway.loginmgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.gateway.sysmgr.model.GwPicInfo;

public class UserInfo {
	private Integer id;
	private String userName;
	private String passWord;
	private String merNo;
	private String vilidateCode;
	private Timestamp createTime;
	private String createBy;
	private String email;
	private String phoneNo;
	private String realName;
	private String seed;
	private String systemId;
	private String verificationType;
	private String verificationCode;
	private Long iSucc;
	private Long iDrift;
	private int enabled;
	private String address;
	private String userRefMerNo;
	
	

	public String getUserRefMerNo() {
		return userRefMerNo;
	}
	public void setUserRefMerNo(String userRefMerNo) {
		this.userRefMerNo = userRefMerNo;
	}
	private String agentNo1;
	
	private String IDCardNo;
	
	private String comName;
	
	private String comAdress;
	
	private String busTimeStart;
	
	private String busTimeEnd;
	
	private BigDecimal maxTransAmount; 
    
	private List<GwPicInfo> list;
	
	private String loginType;
	private String agentNo;
	
	private String errorType;
    private String forPassword;
	private String rePassWord;
	
	
	public String getForPassword() {
		return forPassword;
	}
	public void setForPassword(String forPassword) {
		this.forPassword = forPassword;
	}
	public String getRePassWord() {
		return rePassWord;
	}
	public void setRePassWord(String rePassWord) {
		this.rePassWord = rePassWord;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getAgentNo1() {
		return agentNo1;
	}
	public void setAgentNo1(String agentNo1) {
		this.agentNo1 = agentNo1;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public List<GwPicInfo> getList() {
		return list;
	}
	public void setList(List<GwPicInfo> list) {
		this.list = list;
	}
	public String getIDCardNo() {
		return IDCardNo;
	}
	public void setIDCardNo(String iDCardNo) {
		IDCardNo = iDCardNo;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getComAdress() {
		return comAdress;
	}
	public void setComAdress(String comAdress) {
		this.comAdress = comAdress;
	}
	public String getBusTimeStart() {
		return busTimeStart;
	}
	public void setBusTimeStart(String busTimeStart) {
		this.busTimeStart = busTimeStart;
	}
	public String getBusTimeEnd() {
		return busTimeEnd;
	}
	public void setBusTimeEnd(String busTimeEnd) {
		this.busTimeEnd = busTimeEnd;
	}
	public BigDecimal getMaxTransAmount() {
		return maxTransAmount;
	}
	public void setMaxTransAmount(BigDecimal maxTransAmount) {
		this.maxTransAmount = maxTransAmount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSeed() {
		return seed;
	}
	public void setSeed(String seed) {
		this.seed = seed;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getVerificationType() {
		return verificationType;
	}
	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public Long getiSucc() {
		return iSucc;
	}
	public void setiSucc(Long iSucc) {
		this.iSucc = iSucc;
	}
	
	public Long getiDrift() {
		return iDrift;
	}
	public void setiDrift(Long iDrift) {
		this.iDrift = iDrift;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getVilidateCode() {
		return vilidateCode;
	}
	public void setVilidateCode(String vilidateCode) {
		this.vilidateCode = vilidateCode;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
}
