package com.gateway.bankOrder.model;

public class BankOrderModel {

	//交易号
		private String transactionId;
		//商户号
		private String merchantId;
		//订单号
		private String orderId;
		//订单原始金额（原币种）
		private String amountOrder;
		//原因
		private String reason;
		//退款金额（CNY）
		//拒付金额（CNY）
		private  String amount;
		//退款状态 等待处理 Pending 退款成功 Success 已拒绝 Rejected 
		//拒付状态 冻结:Freeze 已拒付:Refused 已解冻:Thawed  取消:Cancel
		private String status;
		//退款日期
		//拒付添加日期
		private String dateCreated;
		//拒付日期
		private String dateRefused;
		
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		public String getMerchantId() {
			return merchantId;
		}
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getAmountOrder() {
			return amountOrder;
		}
		public void setAmountOrder(String amountOrder) {
			this.amountOrder = amountOrder;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDateCreated() {
			return dateCreated;
		}
		public void setDateCreated(String dateCreated) {
			this.dateCreated = dateCreated;
		}
		public String getDateRefused() {
			return dateRefused;
		}
		public void setDateRefused(String dateRefused) {
			this.dateRefused = dateRefused;
		}
		
		
		
		
		
}
