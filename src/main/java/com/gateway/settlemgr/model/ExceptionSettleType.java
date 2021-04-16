package com.gateway.settlemgr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExceptionSettleType {
	private String id;
	private String merNo;
	private String terNo;
	private int currencyId;
	private String currencyName;
	private int bankId;
	private String bankName;
	private int gatherType;
	private int enabled;
	private String currency;
	private BigDecimal amount;
	private String createBy;
	private Timestamp createDate;
	private String lastModifyBy;
	private Timestamp lastModifyDate;
	private String disRate;
	private String refundHour;
	private int isAllOrOver;
	private int stepId;
	private double start1;
	private double end1;
	private double stepAmount1;
	private double start2;
	private double end2;
	private double stepAmount2;
	private double start3;
	private double end3;
	private double stepAmount3;
	private double start4;
	private double end4;
	private double stepAmount4;
	private double start5;
	private double end5;
	private double stepAmount5;
	private int refundReturn;
	private String cardType;
	
	
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public int getRefundReturn() {
		return refundReturn;
	}
	public void setRefundReturn(int refundReturn) {
		this.refundReturn = refundReturn;
	}
	public double getStart1() {
		return start1;
	}
	public void setStart1(double start1) {
		this.start1 = start1;
	}
	public double getEnd1() {
		return end1;
	}
	public void setEnd1(double end1) {
		this.end1 = end1;
	}
	public double getStepAmount1() {
		return stepAmount1;
	}
	public void setStepAmount1(double stepAmount1) {
		this.stepAmount1 = stepAmount1;
	}
	public double getStart2() {
		return start2;
	}
	public void setStart2(double start2) {
		this.start2 = start2;
	}
	public double getEnd2() {
		return end2;
	}
	public void setEnd2(double end2) {
		this.end2 = end2;
	}
	public double getStepAmount2() {
		return stepAmount2;
	}
	public void setStepAmount2(double stepAmount2) {
		this.stepAmount2 = stepAmount2;
	}
	public double getStart3() {
		return start3;
	}
	public void setStart3(double start3) {
		this.start3 = start3;
	}
	public double getEnd3() {
		return end3;
	}
	public void setEnd3(double end3) {
		this.end3 = end3;
	}
	public double getStepAmount3() {
		return stepAmount3;
	}
	public void setStepAmount3(double stepAmount3) {
		this.stepAmount3 = stepAmount3;
	}
	public double getStart4() {
		return start4;
	}
	public void setStart4(double start4) {
		this.start4 = start4;
	}
	public double getEnd4() {
		return end4;
	}
	public void setEnd4(double end4) {
		this.end4 = end4;
	}
	public double getStepAmount4() {
		return stepAmount4;
	}
	public void setStepAmount4(double stepAmount4) {
		this.stepAmount4 = stepAmount4;
	}
	public double getStart5() {
		return start5;
	}
	public void setStart5(double start5) {
		this.start5 = start5;
	}
	public double getEnd5() {
		return end5;
	}
	public void setEnd5(double end5) {
		this.end5 = end5;
	}
	public double getStepAmount5() {
		return stepAmount5;
	}
	public void setStepAmount5(double stepAmount5) {
		this.stepAmount5 = stepAmount5;
	}
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public int getIsAllOrOver() {
		return isAllOrOver;
	}
	public void setIsAllOrOver(int isAllOrOver) {
		this.isAllOrOver = isAllOrOver;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getDisRate() {
		return disRate;
	}
	public void setDisRate(String disRate) {
		this.disRate = disRate;
	}
	public String getRefundHour() {
		return refundHour;
	}
	public void setRefundHour(String refundHour) {
		this.refundHour = refundHour;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public int getGatherType() {
		return gatherType;
	}
	public void setGatherType(int gatherType) {
		this.gatherType = gatherType;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	
	
}
