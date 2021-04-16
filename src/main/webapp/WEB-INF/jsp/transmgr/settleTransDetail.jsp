<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>结算订单详细信息</title>
</head>
<body>
<div class="pageContent"  layoutH="0">
 <div class="tabs" currentIndex="0" eventType="click" style="width:100%">
		<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
					   <li><span>基本信息</span></li>
					   <c:if test="${settleTransInfo.picInfo != null }">
					   		 <li><span>交易签名</span></li>
					   </c:if>
					</ul>
				</div>
		</div>
		<div class="tabsContent pageFormContent" >
			<div>
	         <dl>
	             <dt>交易流水号</dt>
		         <dd>${settleTransInfo.tradeNo }</dd>
		     </dl>
			<dl> 
			     <dt>参考号</dt>
			     <dd>${settleTransInfo.relNo }</dd>
	         </dl>
	        <dl>
	               <dt>商户号</dt>
				   <dd>${settleTransInfo.merNo }</dd>
			</dl>
			<dl>	   
				   <dt>终端号</dt>
				   <dd>${settleTransInfo.terNo}</dd>
	        </dl>
	        <dl>
	               <dt>卡类型</dt>
				   <dd>${settleTransInfo.cardType}</dd>
			</dl>
			<dl>	   
				   <dt>代理商号</dt>
				   <dd>${settleTransInfo.agentNo} </dd>
	         </dl>
	        <dl>
	               <dt>顶级代理</dt>
				   <dd>${settleTransInfo.parentAgentNo} </dd>
			</dl>
			<dl>	   
				   <dt>交易时间</dt>
				   <dd>${funcs:formatDate(settleTransInfo.transDate,'yyyy-MM-dd HH:mm:ss')}</dd>
	         </dl>
	        <dl>
	              <dt>结算时间</dt>
				   <dd>${settleTransInfo.settleDate }</dd>
			</dl>
			<dl>	   
				   <dt>批次号</dt>
				   <dd>${settleTransInfo.batchNo }</dd>
	         </dl>
	        <dl>
	               <dt>POS流水号</dt>
				   <dd>${settleTransInfo.posNo }</dd>
			</dl>
			<dl>	   
				   <dt>银行通道编号</dt>
				   <dd>${settleTransInfo.currencyId}</dd>
	         </dl>
	        <dl>
	               <dt>交易金额</dt>
				   <dd>${settleTransInfo.transAmount }</dd>
			 </dl>
			<dl>	   
				   <dt>商户费率</dt>
				   <dd>${settleTransInfo.merForAmount }</dd>
	         </dl>
	         <dl>	   
				   <dt>代理商费率</dt>
				   <dd>${settleTransInfo.agentForAmount }</dd>
	         </dl>
	         <dl>	   
				   <dt>父级代理商费率</dt>
				   <dd>${settleTransInfo.parentAgentForAmount }</dd>
	         </dl>
	         <dl>	   
				   <dt>交易类型</dt>
				   <dd>${funcs:getStringColumnKey('gw_transtype_info',settleTransInfo.transType,'未知状态')}</dd>
	         </dl>
	         <dl>	   
				   <dt>返回码</dt>
				   <dd>${funcs:getStringColumnKey('RESP_INFO',settleTransInfo.respCode,settleTransInfo.respCode)}</dd>
	         </dl>
	         <dl>	   
				   <dt>卡号</dt>
				   <dd>${settleTransInfo.cardNo }</dd>
	         </dl>
	         <dl>	   
				   <dt>银行参考号</dt>
				   <dd>${settleTransInfo.bankRefNo }</dd>
	         </dl>
	         
	         <dl>	   
				   <dt>银行POS流水号</dt>
				   <dd>${settleTransInfo.bankPosNo }</dd>
	         </dl>
	         <dl>	   
				   <dt>银行批次号</dt>
				   <dd>${settleTransInfo.bankBatchNo }</dd>
	         </dl>
	         <dl>	   
				   <dt>授权码</dt>
				   <dd>${settleTransInfo.autoCode }</dd>
	         </dl>
	         <dl>	   
				   <dt>银行交易日期</dt>
				   <dd>${settleTransInfo.bankTransDate }</dd>
	         </dl>
	         <dl>	   
				   <dt>银行交易时间</dt>
				   <dd>${settleTransInfo.bankTransTime }</dd>
	         </dl>
	         <dl>	   
				   <dt>交易通道</dt>
				   <dd>${settleTransInfo.currencyName }</dd>
	         </dl>
	           <dl>	   
				   <dt>收单机构</dt>
				   <dd>${settleTransInfo.acquiringBank }</dd>
	         </dl>
	         <dl>	   
				   <dt>发卡行</dt>
				   <dd>${settleTransInfo.bankName }</dd>
	         </dl>
	         <dl>	   
				   <dt>卡名称</dt>
				   <dd>${settleTransInfo.cardName }</dd>
	         </dl>
	         <dl>
	               <dt>结算批次号 </dt>
	               <dd> ${settleTransInfo.settleBatchNo }</dd>
	         </dl>
	    </div>
	      <c:if test="${settleTransInfo.picInfo != null }">
					   <div><img src="data:image/${settleTransInfo.picInfo.picExtName };base64,${settleTransInfo.picInfo.picBuffer }" width="100%" ></div>
		</c:if>
	    </div>
  </div>
</div>
</body>
</html>