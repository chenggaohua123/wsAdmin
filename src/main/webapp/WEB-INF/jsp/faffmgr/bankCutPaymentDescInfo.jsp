<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退款/拒付扣款详情</title>
</head>
<body>
<div class="pageHeader">
</div>
    <div class="pageContent">
    <div class="panelBar">
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="142" style="text-align: center;">
			<thead>
				<tr>
					<th>商户号</th>
					<th>流水号</th>
					<th>银行交易金额</th>
					<th>退款/拒付金额</th>
					<th>交易时间</th>
					<th>退款/拒付时间</th>
					<th>银行退款/拒付扣款金额</th>
					<th>银行退款/拒付扣款时间</th>
					<th>录入人</th>
					<th>录入时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page}" var="info">
					<tr>
						<td>${info.merNo }</td>
						<td>${info.tradeNo }</td>
						<td>${info.bankCurrency } ${info.bankTransAmount }</td>
						
						<td>
						<c:if test="${settleType=='refund' }">
						${info.bankCurrency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.refundAmount }"></fmt:formatNumber>
						</c:if>
						<c:if test="${settleType=='dis' }">
						${info.bankCurrency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.disAmount }"></fmt:formatNumber>
						</c:if>
						</td>
						<td>${funcs:formatDate(info.transDate,'yyyy-MM-dd HH:mm:ss')}</td>
						<td>
						<c:if test="${settleType=='refund' }">
						${funcs:formatDate(info.refundDate,'yyyy-MM-dd HH:mm:ss')}
						</c:if>
						<c:if test="${settleType=='dis' }">
						${funcs:formatDate(info.disDate,'yyyy-MM-dd HH:mm:ss')}
						</c:if>
						</td>
						<td>
						${info.bankSettleCurrency } ${info.bankSettleAmount }
						</td>
						<td>
						${funcs:formatDate(info.settleDate,'yyyy-MM-dd HH:mm:ss')}
						</td>
						<td>
						${info.createBy }
						</td>
						<td>
						${funcs:formatDate(info.createDate,'yyyy-MM-dd HH:mm:ss')}
						</td>
						<td>
						<c:if test="${settleType=='refund' }">
						<a title="编辑" mask="true"  href="<%=path %>/faffmgr/goUpdateBankCutAmount?id=${info.id}" target="dialog" rel="updateConfig" width="450" height="250"  title="修改银行退款扣款金额"  class="btnEdit">编辑</a>
						</c:if>
						<c:if test="${settleType=='dis' }">
						<a title="编辑" mask="true"  href="<%=path %>/faffmgr/goUpdateBankCutAmount?id=${info.id}" target="dialog" rel="updateConfig" width="450" height="250"  title="修改银行拒付扣款金额"  class="btnEdit">编辑</a>
						</c:if>
							 
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>