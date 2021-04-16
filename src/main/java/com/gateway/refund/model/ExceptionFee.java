package com.gateway.refund.model;

import java.math.BigDecimal;

public class ExceptionFee {
	//退款处理费
		private BigDecimal refundFee;
		//退款交易手续费是否收取 1收取
		private int transRefundFeeStatus;
		//拒付处理费
		private BigDecimal disFee;
		//拒付罚金
		private BigDecimal disFine;
		//交易失败手续费
		private BigDecimal transFailFee;
		private String tradeNo;
		
		
		public String getTradeNo() {
			return tradeNo;
		}
		public void setTradeNo(String tradeNo) {
			this.tradeNo = tradeNo;
		}
		public BigDecimal getRefundFee() {
			return refundFee;
		}
		public void setRefundFee(BigDecimal refundFee) {
			this.refundFee = refundFee;
		}
		public int getTransRefundFeeStatus() {
			return transRefundFeeStatus;
		}
		public void setTransRefundFeeStatus(int transRefundFeeStatus) {
			this.transRefundFeeStatus = transRefundFeeStatus;
		}
		public BigDecimal getDisFee() {
			return disFee;
		}
		public void setDisFee(BigDecimal disFee) {
			this.disFee = disFee;
		}
		public BigDecimal getDisFine() {
			return disFine;
		}
		public void setDisFine(BigDecimal disFine) {
			this.disFine = disFine;
		}
		public BigDecimal getTransFailFee() {
			return transFailFee;
		}
		public void setTransFailFee(BigDecimal transFailFee) {
			this.transFailFee = transFailFee;
		}
		
}
