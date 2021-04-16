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
<title>中行汇率</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/exchangRate/goListBankSourceRateLog">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/exchangRate/listBankSourceRateInfo" method="post">
	<input type="hidden" name="sourceCurrencyCode" value="${param.sourceCurrencyCode }">
	
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>货币名称</th>
				<th>现汇买入价</th>
				<th>现钞买入价</th>
				<th>现汇卖出价</th>
				<th>现钞卖出价</th>
				<th>中行折算价</th>
				<th>中行发布时间</th>
				<th>系统获取时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="bankRate">
				<tr  target="rateId" rel="${bankRate.sourceCurrencyCode }"   >
					<td>${bankRate.sourceCurrencyCode}</td>
					<td>${bankRate.buyingRate}</td>
					<td>${bankRate.cashBuyingRate}</td>
					<td>${bankRate.sellingRate}</td>
					<td>${bankRate.cashSellingrate}</td>
					<td>${bankRate.middleRate}</td>
					<td>${bankRate.bcDate}</td>
					<td>${bankRate.createDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total }条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
</body>
</html>