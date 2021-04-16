package com.gateway.goodstrace.model;

import java.sql.Timestamp;

public class DeliveryInfo {

	// Fields
	private String tradeNo;
	private String createby;
	private String orderNo;
	private Integer id;
	private String merOrder;
	private String merNo;
	private String goodsName;
	private String cardHolderEmail;
	private String cardHolder;
	private String cardSix;
	private String cardMd5;
	private String realCurrency;
	private Double realAmount;
	private String sourceCurrency;
	private Double sourceAmount;
	private Timestamp tradetime;
	private String status;
	private String holderIp;
	private String holderCtcode;
	private String holderCtname;
	private String trackNo;
	private String iogistics;
	private String imageUrl;
	private Timestamp uploadTime;
	private Timestamp arrivalTime;
	private String shipAddress;
	private String shipCity;
	private String shipState;
	private String shipCountry;
	private String merIp;
	private String merCtcode;
	private String merCtname;
	private String receiverIp;
	private String receiverCtcode;
	private String receiverCtname;
	private Timestamp sureTime;
	private String modifyPerson;
	private Timestamp modifyTime;
	private Integer emailStatus;
	private String scardHolder;
	private String name;
	private String last_name;
	private String email;
	private String amount;
	private String tel;
	private String country;
	private String address1;
	private String state;
	private String city;
	private String cardCountry;
	private String countryCode;
	private String binCountry;
	private String protest;
	private String currency;
	private String currencyName;
	private String cell_phone;
	private String merwebsite;
	private String remark;
	// 是否划款
	private String batchNo;
	// 是否退款
	private Integer refundment;
	private String startDate;
	private String endDate;
	private String idList;
	private Integer successCount;
	private Integer congeal;
	
	// 上传发货单变量
	private String orderNoList;

	private String uploadStartDate;
	private String uploadsEndDate;
	private String transDateStart;
	private String transDateEnd;
	private String lastModifyBy;
	private String lastDateTime;
	private String operationStatus;
	

	// Property accessors

	public String getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(String orderNoList) {
		this.orderNoList = orderNoList;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerOrder() {
		return this.merOrder;
	}

	public void setMerOrder(String merOrder) {
		this.merOrder = merOrder;
	}

	public String getMerNo() {
		return this.merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCardHolderEmail() {
		return this.cardHolderEmail;
	}

	public void setCardHolderEmail(String cardHolderEmail) {
		this.cardHolderEmail = cardHolderEmail;
	}

	public String getCardHolder() {
		return this.cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getCardSix() {
		return this.cardSix;
	}

	public void setCardSix(String cardSix) {
		this.cardSix = cardSix;
	}

	public String getCardMd5() {
		return this.cardMd5;
	}

	public void setCardMd5(String cardMd5) {
		this.cardMd5 = cardMd5;
	}

	public String getRealCurrency() {
		return this.realCurrency;
	}

	public void setRealCurrency(String realCurrency) {
		this.realCurrency = realCurrency;
	}

	public Double getRealAmount() {
		return this.realAmount;
	}

	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}

	public String getSourceCurrency() {
		return this.sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public Double getSourceAmount() {
		return this.sourceAmount;
	}

	public void setSourceAmount(Double sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHolderIp() {
		return this.holderIp;
	}

	public void setHolderIp(String holderIp) {
		this.holderIp = holderIp;
	}

	public String getHolderCtcode() {
		return this.holderCtcode;
	}

	public void setHolderCtcode(String holderCtcode) {
		this.holderCtcode = holderCtcode;
	}

	public String getHolderCtname() {
		return this.holderCtname;
	}

	public void setHolderCtname(String holderCtname) {
		this.holderCtname = holderCtname;
	}

	public String getTrackNo() {
		return this.trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	public String getIogistics() {
		return this.iogistics;
	}

	public void setIogistics(String iogistics) {
		this.iogistics = iogistics;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Timestamp getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Timestamp getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getShipAddress() {
		return this.shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getShipCity() {
		return this.shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipState() {
		return this.shipState;
	}

	public void setShipState(String shipState) {
		this.shipState = shipState;
	}

	public String getShipCountry() {
		return this.shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

	public String getMerIp() {
		return this.merIp;
	}

	public void setMerIp(String merIp) {
		this.merIp = merIp;
	}

	public String getMerCtcode() {
		return this.merCtcode;
	}

	public void setMerCtcode(String merCtcode) {
		this.merCtcode = merCtcode;
	}

	public String getMerCtname() {
		return this.merCtname;
	}

	public void setMerCtname(String merCtname) {
		this.merCtname = merCtname;
	}

	public String getReceiverIp() {
		return this.receiverIp;
	}

	public void setReceiverIp(String receiverIp) {
		this.receiverIp = receiverIp;
	}

	public String getReceiverCtcode() {
		return this.receiverCtcode;
	}

	public void setReceiverCtcode(String receiverCtcode) {
		this.receiverCtcode = receiverCtcode;
	}

	public String getReceiverCtname() {
		return this.receiverCtname;
	}

	public void setReceiverCtname(String receiverCtname) {
		this.receiverCtname = receiverCtname;
	}

	public Timestamp getSureTime() {
		return this.sureTime;
	}

	public void setSureTime(Timestamp sureTime) {
		this.sureTime = sureTime;
	}

	public String getModifyPerson() {
		return this.modifyPerson;
	}

	public void setModifyPerson(String modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getEmailStatus() {
		return this.emailStatus;
	}

	public void setEmailStatus(Integer emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getScardHolder() {
		return this.scardHolder;
	}

	public void setScardHolder(String scardHolder) {
		this.scardHolder = scardHolder;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getRefundment() {
		return refundment;
	}

	public void setRefundment(Integer refundment) {
		this.refundment = refundment;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String lastName) {
		last_name = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCardCountry() {
		return cardCountry;
	}

	public void setCardCountry(String cardCountry) {
		this.cardCountry = cardCountry;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getBinCountry() {
		return binCountry;
	}

	public void setBinCountry(String binCountry) {
		this.binCountry = binCountry;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}
	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public String getProtest() {
		return protest;
	}

	public void setProtest(String protest) {
		this.protest = protest;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Integer getCongeal() {
		return congeal;
	}

	public void setCongeal(Integer congeal) {
		this.congeal = congeal;
	}

	

	public String getCell_phone() {
		return cell_phone;
	}

	public void setCell_phone(String cellPhone) {
		cell_phone = cellPhone;
	}

	public String getMerwebsite() {
		return merwebsite;
	}

	public void setMerwebsite(String merwebsite) {
		this.merwebsite = merwebsite;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}


	public Timestamp getTradetime() {
		return tradetime;
	}

	public void setTradetime(Timestamp tradetime) {
		this.tradetime = tradetime;
	}

	public String getUploadStartDate() {
		return uploadStartDate;
	}

	public void setUploadStartDate(String uploadStartDate) {
		this.uploadStartDate = uploadStartDate;
	}

	public String getUploadsEndDate() {
		return uploadsEndDate;
	}

	public void setUploadsEndDate(String uploadsEndDate) {
		this.uploadsEndDate = uploadsEndDate;
	}

	public String getTransDateStart() {
		return transDateStart;
	}

	public void setTransDateStart(String transDateStart) {
		this.transDateStart = transDateStart;
	}

	public String getTransDateEnd() {
		return transDateEnd;
	}

	public void setTransDateEnd(String transDateEnd) {
		this.transDateEnd = transDateEnd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLastModifyBy() {
		return lastModifyBy;
	}

	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}

	public String getLastDateTime() {
		return lastDateTime;
	}

	public void setLastDateTime(String lastDateTime) {
		this.lastDateTime = lastDateTime;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}


	

}