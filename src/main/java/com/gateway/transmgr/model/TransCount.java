package com.gateway.transmgr.model;

import java.util.List;

/** 统计交易信息 */
public class TransCount {
	/** 交易总笔数 */
	private int countTrans;
	/** 成功笔数 */
	private int successTrans;
	/** 标签总金额 */
	private List<TransCountModel> labelMoney;
	/** 银行交易总额 */
	private List<TransCountModel> countMoney;
	/**商户结算金额*/
	private List<TransCountModel> settleMoney;
	
	
	public int getCountTrans() {
		return countTrans;
	}
	public void setCountTrans(int countTrans) {
		this.countTrans = countTrans;
	}
	public int getSuccessTrans() {
		return successTrans;
	}
	public void setSuccessTrans(int successTrans) {
		this.successTrans = successTrans;
	}
	public List<TransCountModel> getCountMoney() {
		return countMoney;
	}
	public void setCountMoney(List<TransCountModel> countMoney) {
		this.countMoney = countMoney;
	}
	public List<TransCountModel> getLabelMoney() {
		return labelMoney;
	}
	public void setLabelMoney(List<TransCountModel> labelMoney) {
		this.labelMoney = labelMoney;
	}
	public List<TransCountModel> getSettleMoney() {
		return settleMoney;
	}
	public void setSettleMoney(List<TransCountModel> settleMoney) {
		this.settleMoney = settleMoney;
	}

	
}
