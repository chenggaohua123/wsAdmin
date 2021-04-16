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
<title>商户交易同比</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/merchantTransCountRate">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/merchantTransCountRate" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>周期类型：</label>
				<select class="combox" name="cycleType">
					<option value="day" ${param.cycleType=='day'?'selected':'' }>自然天</option>
					<option value="week" ${param.cycleType=='week'?'selected':'' }>自然周</option>
					<option value="month" ${param.cycleType=='month'?'selected':'' }>自然月</option>
					<option value="year" ${param.cycleType=='year'?'selected':'' }>自然年</option>
				</select>
			</li>
			<li>
				<label>N ：</label>
				<input type="text" name="n" value="${param.n}"/>
			</li>
		</ul>
		<ul  class="searchContent">
			<li>
				<label>查询周期数 ：</label>
				<input type="text" name="cycleCount" value="${param.cycleCount}"/>
			</li>
			<li>
				<label>商户类别</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${param.type }" relText="columnvalue" name="type">
					<option value="">所有</option>
				</select>
			</li>
			
		</ul>
		<br><font color="red"> 时间周期开始结束时间为当日的00:00:00</font>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
    <div class="pageContent">
    <div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/countAnalysis/exportMerchantTransCountRate" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
			<thead>
				<tr>
					<th>周期区间</th>
					<th>商户号</th>
					<th>商户性质</th>
					<th>是否开通</th>
					<th>开通日期</th>
					<th>时间周期内交易天数</th>
					<th>成功率</th>
					<th>成功金额</th>
					<th>日平均交易金额</th>
					<th>成功笔数</th>
					<th>退款笔数</th>
					<th>拒付笔数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="info">
					<tr  >
						<td>${info.cycle }</td>
						<td>${info.merNo }</td>
						<td>${funcs:getStringColumnKey('MERCHANTTYPE',info.type,info.type)}</td>
						<td>${funcs:getStringColumnKey('MERCHANTSTATUS',info.enabled,info.enabled)}</td>
						<td>${funcs:formatDate(info.activationDate,'yyyy-MM-dd HH:mm')} </td>
						<td>${info.transTime }</td>
						<td><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.successRate*100}"/>%</td>
						<td>${info.merSettleCurrency} <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.successAmount}"/> </td>
						
						<td>
						<c:if test="${info.transTime==0 }">
						${info.merSettleCurrency} 0.00
						</c:if>
						<c:if test="${info.transTime!=0 }">
						${info.merSettleCurrency} <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.successAmount/info.transTime}"/>
						</c:if>
						<td>${info.successCount }</td>
						<td>${info.refundCount }</td>
						<td>${info.disCount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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