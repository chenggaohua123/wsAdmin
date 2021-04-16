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
<title>订单详细信息</title>
</head>
<body>
<div class="pageContent"  layoutH="0">
<div class="tabs" currentIndex="0" eventType="click" style="width:100%">
		<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
					   <li><span>基本信息</span></li>
					   <c:if test="${transInfo.picInfo != null }">
					   		 <li><span>交易签名</span></li>
					   </c:if>
					</ul>
				</div>
		</div>
		<div class="tabsContent pageFormContent" >
			<div>
				 <dl>
		               <dt>商户号</dt>
					   <dd>${transInfo.merNo }</dd>
				</dl>
		         <dl>
		             <dt>交易流水号</dt>
			         <dd>${transInfo.tradeNo }</dd>
			     </dl>
				<dl> 
				     <dt>订单号</dt>
				     <dd>${transInfo.orderNo }</dd>
		         </dl>
		       
				<dl>	   
					   <dt>终端号</dt>
					   <dd>${transInfo.terNo}</dd>
		        </dl>
		        <dl>	   
					   <dt>标签金额</dt>
					   <dd>${transInfo.merBusCurrency} ${transInfo.merTransAmount} </dd>
		         </dl>
		       
				<dl>	   
					   <dt>结算金额</dt>
					   <dd>${transInfo.merSettleCurrency} ${transInfo.merSettleAmount} </dd>
		         </dl>
		         
		        <%-- <dl>
		               <dt>银行交易金额</dt>
					   <dd>${transInfo.bankCurrency} ${transInfo.bankTransAmount} </dd>
				</dl> --%>
				<dl>	   
					   <dt>交易时间</dt>
					   <dd>${funcs:formatDate(transInfo.transDate,'yyyy-MM-dd HH:mm:ss')}</dd>
		         </dl>
		         <dl>	   
					   <dt>手续费</dt>
					   <dd>${transInfo.merSettleCurrency} ${transInfo.merForAmount} </dd>
		         </dl>
		         <dl>	   
					   <dt>单笔手续费</dt>
					   <dd>${transInfo.merSettleCurrency} ${transInfo.singleFee} </dd>
		         </dl>
		         <dl>	   
					   <dt>保证金</dt>
					   <dd>${transInfo.merSettleCurrency} ${transInfo.bondAmount} </dd>
		         </dl>
		         <dl>	   
					   <dt>卡种</dt>
					   <dd>${transInfo.cardType} </dd>
		         </dl>
		           <dl>	   
					   <dt>Binlist国家</dt>
					   <dd>${cbInfo.country_code}</dd>
		         </dl>
		          <dl>	   
					   <dt>Pro国家</dt>
					   <dd>${cbInfo.country}</dd>
		         </dl>
		       <dl>	   
					   <dt>发卡行名称</dt>
					   <dd>${empty cbInfo.bank?cbInfo.proBank:cbInfo.bank}</dd>
		         </dl>
		         <dl>	   
					   <dt>卡类型</dt>
					   <dd>${cbInfo.card_type}</dd>
		         </dl>
		         <dl>	   
					   <dt>交易状态</dt>
					 <c:if test="${transInfo.respCode=='00'}">
						<font color="green">${funcs:getStringColumnKey('RESP_INFO',transInfo.respCode,transInfo.respCode)}</font>
					</c:if><c:if test="${transInfo.respCode=='0000'}">
						<font color="red">${funcs:getStringColumnKey('RESP_INFO',transInfo.respCode,transInfo.respCode)}</font>
					</c:if><c:if test="${transInfo.respCode=='01'}">
						<font color="red">${funcs:getStringColumnKey('RESP_INFO',transInfo.respCode,transInfo.respCode)}</font>
					</c:if>
		         </dl>
		         <dl>
		              <dt>交易返回信息</dt>
					   <dd>${transInfo.respMsg }</dd>
				</dl>
				<dl>
		              <dt>支付通道</dt>
					   <dd>${transInfo.currencyName }</dd>
				</dl>
				<dl>
		              <dt>勾兑状态</dt>
					   <dd>
					   <c:if test="${transInfo.ischecked=='0' }">
						<font color="red">未勾兑</font>
						</c:if>
					<c:if test="${transInfo.ischecked=='1' }">
						<font color="green">已勾兑</font>
					</c:if>
					</dd>
				</dl>
				
				<dl>	   
					   <dt>入账状态</dt>
					 <c:if test="${transInfo.access=='1'}">
						<font color="green">已入账</font>
					</c:if><c:if test="${transInfo.access=='0'}">
						<font color="red">未入账</font>
					</c:if>
		         </dl>
		         <dl>	   
					   <dt>拒付状态</dt>
					<dd> <c:if test="${transInfo.transDishonor=='0'}">
						<font color="red">未拒付</font>
					</c:if><c:if test="${transInfo.transDishonor=='1'}">
						<font color="green">已拒付</font>
					</c:if></dd>
		         </dl>
		        
		       <dl>
		              <dt> 拒付金额</dt>
					   <dd>${transInfo.merBusCurrency} ${transInfo.dishonorAmount}</dd>
				</dl>
				<dl>	   
					   <dt>退款状态</dt>
					<dd> <c:if test="${transInfo.refundStatus=='0'}">
						<font color="red">未退款</font>
					</c:if><c:if test="${transInfo.refundStatus=='1'}">
						<font color="green">已退款</font>
					</c:if></dd>
		         </dl>
				 <dl>
		               <dt> 退款金额</dt>
					   <dd>${transInfo.merBusCurrency} ${transInfo.refundAmount}</dd>
				</dl>
				
				 <dl>	   
					   <dt>冻结状态</dt>
					<dd> <c:if test="${transInfo.transFrozen=='0'}">
						<font color="red">未冻结</font>
					</c:if>
					<c:if test="${transInfo.transDishonor=='0'}">
					<c:if test="${transInfo.transFrozen=='1'}">
						<font color="green">已冻结</font>
					</c:if></c:if></dd>
		         </dl>
		         <dl>
		               <dt> 冻结金额</dt>
					   <dd><c:if test="${transInfo.transDishonor=='0'}">${transInfo.merBusCurrency} ${transInfo.frozenAmount}</c:if></dd>
				</dl>
		        <dl>
		              <dt>订单来源</dt>
					   <dd>${transInfo.webInfo}</dd>
				</dl>
				<dl>	   
					   <dt>结算批次号</dt>
					   <dd>${transInfo.batchNo }</dd>
		         </dl>
		        <dl>
		               <dt>通道英文账单名称</dt>
					   <dd>${transInfo.acquirer}</dd>
				</dl>
				<dl>	   
					   <dt>前六后四卡号</dt>
					   <dd>${transInfo.sixAndFourCardNo}</dd>
		         </dl>
		       
				<dl>	   
					   <dt>邮箱</dt>
					   <dd>${transInfo.email }</dd>
		         </dl>
		         <dl>	   
					   <dt>IP</dt>
					   <dd>${transInfo.ipAddress}</dd>
		         </dl>
		         <dl>	   
					   <dt>网站</dt>
					   <dd>${transInfo.payWebSite}</dd>
		         </dl>
		        
		         <dl>	   
					   <dt>姓名</dt>
					   <dd>${transInfo.grPerName}</dd>
		         </dl>
		         <dl>	   
					   <dt>电话</dt>
					   <dd>${transInfo.grphoneNumber}</dd>
		         </dl>
		         <dl>	   
					   <dt>支付国家</dt>
					   <dd>${transInfo.ipCountry} </dd>
		         </dl>
		         <dl>	   
					   <dt>收货国家</dt>
					   <dd>${transInfo.grCountry}</dd>
		         </dl>
		         
		         <dl>	   
					   <dt>收货省/ 州</dt>
					   <dd>${transInfo.grState}</dd>
		         </dl>
		          <dl>	   
					   <dt>邮编</dt>
					   <dd>${transInfo.grZipCode}</dd>
		         </dl>
		          <dl>	   
					   <dt>货运方式</dt>
					   <dd>${transInfo.iogistics}</dd>
		         </dl>
		         <dl>	   
					   <dt>货运单号</dt>
					   <dd>${transInfo.trackNo}</dd>
		         </dl>
		          <dl>	   
					   <dt>账单国家</dt>
					   <dd>${transInfo.cardCountry} </dd>
		         </dl>
		         <dl>	   
					   <dt>账单省/州</dt>
					   <dd>${transInfo.cardState}</dd>
		         </dl>
		         <dl>	   
					   <dt>账单地址</dt>
					   <dd>${transInfo.cardAddress}</dd>
		         </dl>
		          <dl>	   
					   <dt>欺诈分数</dt>
					   <dd>${transInfo.riskScore}</dd>
		         </dl>
		          <dl>	   
					   <dt>银行真实交易金额</dt>
					   <dd>
					   <c:if test="${transInfo.bankRealAmount==0.0 || empty transInfo.bankRealAmount}">
						   无法获取
					   </c:if>
					    <c:if test="${transInfo.bankRealAmount!=0.0 || ! empty transInfo.bankRealAmount }">
						${transInfo.bankRealCurrency} ${transInfo.bankRealAmount}
					   </c:if>
					   
					   </dd>
		         </dl>
		          <dl>	   
					   <dt>银行真实交易汇率</dt>
					   <dd>
					   <c:if test="${transInfo.bankRealAmount==0.0 || empty transInfo.bankRealAmount  }">
						   无法获取
					   </c:if>
					    <c:if test="${transInfo.bankRealAmount!=0.0 || ! empty transInfo.bankRealAmount  }">
					 ${transInfo.bankRealRate}
					   </c:if>
					  
					   </dd>
		         </dl>
		           <dl>	   
					   <dt>授权码</dt>
					   <dd>${transInfo.autoCode}</dd>
		         </dl>
		         
		        <table width="100%" class="list" >	   
					   <tr><td colspan="3">货物信息</td></tr>
					   <tr><td>货物名称</td><td>货物数量</td><td>货物价格</td></tr>
					   <c:forEach items="${transInfo.goodsInfos }" var="info">
					   <tr>
					   <td>${info.goodsName }</td>
					   <td>${info.quantity }</td>
					   <td>${info.goodsPrice }</td>
					   </tr>
					   </c:forEach>
		         </table>
		       </div>
         </div>
  </div>
</div>
</body>
</html>