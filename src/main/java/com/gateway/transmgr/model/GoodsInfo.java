package com.gateway.transmgr.model;

import java.math.BigDecimal;

/** 货物信息表 */
public class GoodsInfo {

	/** id */
	private int id;
	/** 交易流水号 */
	private String tradeNo;
	/** 货物名称 */
	private String goodsName;
	/** 货物数量 */
	private String quantity;
	/** goodPrice */
	private BigDecimal goodsPrice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
}
