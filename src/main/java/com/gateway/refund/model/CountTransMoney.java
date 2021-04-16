package com.gateway.refund.model;

import java.math.BigDecimal;

/** 统计交易操作金额 */
public class CountTransMoney {
	/** 交易金额 */
	private BigDecimal transMoney;
	/** 订单已退款金额 */
	private BigDecimal refundMoney;
	/** 订单解冻金额 */
	private BigDecimal thawMoney;
	/** 订单冻结金额 */
	private BigDecimal frozenMoney;
	/** 订单拒付金额 */
	private BigDecimal dishonorMoney;
	/** 解冻操作金额 */
	private BigDecimal thawOperationMoney;
	/** 剩余交易金额 */
	private BigDecimal surplusMoney;
	
	public BigDecimal getThawOperationMoney() {
		return thawOperationMoney;
	}
	public void setThawOperationMoney(BigDecimal thawOperationMoney) {
		this.thawOperationMoney = thawOperationMoney;
	}
	public BigDecimal getTransMoney() {
		return transMoney;
	}
	public void setTransMoney(BigDecimal transMoney) {
		this.transMoney = transMoney;
	}
	public BigDecimal getSurplusMoney() {
		return surplusMoney;
	}
	public void setSurplusMoney(BigDecimal surplusMoney) {
		this.surplusMoney = surplusMoney;
	}
	public BigDecimal getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(BigDecimal refundMoney) {
		this.refundMoney = refundMoney;
	}
	public BigDecimal getThawMoney() {
		return thawMoney;
	}
	public void setThawMoney(BigDecimal thawMoney) {
		this.thawMoney = thawMoney;
	}
	public BigDecimal getFrozenMoney() {
		return frozenMoney;
	}
	public void setFrozenMoney(BigDecimal frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	public BigDecimal getDishonorMoney() {
		return dishonorMoney;
	}
	public void setDishonorMoney(BigDecimal dishonorMoney) {
		this.dishonorMoney = dishonorMoney;
	}
}
