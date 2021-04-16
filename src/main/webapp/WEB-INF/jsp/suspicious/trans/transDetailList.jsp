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
<title>关联订单信息</title>
</head>
<body>
    <div class="pageContent">
    <div class="pageHeader" id="pageHeaderx" style="display: block;">
		<div class="searchBar" >
			<ul class="searchContent" >
				<li>
				<span>触犯规则交易记录数: ${order.count}</span> 
				</li>
			</ul>	
		</div>
	</div>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/suspicious/exportSuspiciousRuleTransInfo?ruleIds=${order.ruleIds }&merNo=${order.merNo }&terNo=${order.terNo }&susType=${order.susType}&createDate=${order.createDate }&tradeNo=${order.tradeNo}" width="550" height="300" mask="true"><span>可疑关联流水号导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="10" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>订单号</th>
				<th>商户号</th>
				<th>商户交易金额</th>
				<th>银行交易金额</th>
				<th>商户结算金额</th>
				<th>交易状态</th>
				<th>返回原因</th>
				<th>交易时间</th>
				<th>卡种</th>
				<th>卡号</th>
				<th>IP</th>
				<th>邮箱</th>
				<th>交易通道</th>
				<th>是否勾兑</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${order.list}" var="trans">
				<tr>
					<td>${trans.tradeNo }</td>
					<td>${trans.orderNo}</td>
					<td>${trans.merNo }</td>
					<td>${trans.merBusCurrency }  ${trans.merTransAmount }</td>
					<td>${trans.bankCurrency } ${trans.bankTransAmount }</td>
					<td>${trans.merSettleCurrency } ${trans.merSettleAmount }</td>
					<td >
					<c:if test="${trans.respCode=='00'}">
						<font color="green">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if><c:if test="${trans.respCode!='00'}">
						<font color="red">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if>
					</td>
					<td>${trans.respMsg }</td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.cardType}</td>
					<td>${trans.sixAndFourCardNo}</td>
					<td>${trans.ipAddress}</td>
					<td>${trans.email}</td>
					<td>${trans.currencyName }</td>
					<td>
					<c:if test="${trans.ischecked==0 }">
						<font color="black">未勾兑</font>
					</c:if>
					<c:if test="${trans.ischecked==1 }">
						<font color="green">已勾兑</font>
					</c:if>
					<c:if test="${trans.ischecked==2 }">
						<font color="red">勾兑异常</font>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>