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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>商户出入帐流水明细</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/listMerchantAccountAccessDetail">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<!-- form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/listMerchantAccountAccessDetail" method="post">
	<div class="searchBar">
	<input type="hidden" name="accountId" value="${param.accountId}">
	<input type="hidden" name="accessId" value="${param.accessId}">
		<ul class="searchContent">
			<li>
				<label>开始时间：</label>
				 <input type="text" name="dateStart"  id = "dateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.dateStart}"/>
			</li>
             <li>
				<label>结束时间：</label>
			   <input type="text" name="dateEnd"  id = "dateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.dateEnd}"/>		
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form-->
</div>

<div class="pageContent">
	<div class="panelBar">
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="65" style="text-align: center;">
		<thead>
			<tr>
				<th>入账流水号</th>
				<th>交易流水号</th>
				<th>结算批次号</th>
				<th>保证金结算批次号</th>
				<th>入账时间</th>
				<th>交易金额</th>
				<th>结算金额</th>
				<th>商户手续费</th>
				<th>保证金</th>
				<th>支付状态</th>
				<th>交易时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="detail">
				<tr target="detailId" rel="${detail.id }">
					<td>${detail.accessId }</td>
					<td>${detail.tradeNo }</td>
					<td>${detail.batchNo }</td>
					<td>${detail.bondBatchNo }</td>
					<td>${detail.createDate }</td>
					<td>${detail.busCurrency }&nbsp;${detail.busAmount }</td>
					<td>${detail.settleCurrency }&nbsp;${detail.settleAmount }</td>
					<td>${detail.settleCurrency }&nbsp;${detail.merForAmount }</td>
					<td>${detail.settleCurrency }&nbsp;${detail.bondAmount }</td>
					<td>${detail.respCode=='00'?'支付成功':'支付失败' }</td>
					<td>${detail.transDate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total }条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
</body>
</html>