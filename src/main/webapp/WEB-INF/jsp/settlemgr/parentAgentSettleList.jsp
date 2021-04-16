<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建代理商分润表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/parentAgentSettleList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" id="settleForm" onsubmit="return  navTabSearch(this);"  action="<%=path %>/settlemgr/parentAgentSettleList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			 <li class="dateRange">
				<label>清算日期：</label>
				<input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date required" value="${param.transDateStart}"/>
				<input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date required" value="${param.transDateEnd}"/>
			</li>
		</ul>
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
			<li><a class="add" href="<%=path %>/settlemgr/exportAgentSettleInfoList" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>分润导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>分润月份</th>
				<th>代理商编号</th>
				<th>交易笔数</th>
				<th>交易金额</th>
				<th>交易手续费</th>
				<th>高签部分手续费</th>
				<th>分润收益</th>
				<th>二级代理高签手续费</th>
				<th>二级代理分润</th>
				<th>结算金额</th>
				<th>账户名称</th>
				<th>开户行</th>
				<th>银行卡号</th>
				<th>结算日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="settle">
				<tr>
					<td><fmt:formatDate value="${settle.settleDate }" pattern="yyyy-MM"/> </td>
					<td>${settle.agentNo }</td>
					<td>${settle.transCount }</td>
					<td><fmt:formatNumber value="${settle.transAmount }"  maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.transForAmaount }"  maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.diversityAgentForAmount }"  maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.diversitySplitAgentForAmount }" maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.diversityParentAgentForAmount}"  maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.diversitySplitParentAgentForAmount }"  maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.settleAmount }"  maxFractionDigits="2"/></td>
					<td>${settle.accountName }</td>
					<td>${settle.accountAddress }</td>
					<td>${settle.accountNo }</td>
					<td><fmt:formatDate value="${settle.createDate }" pattern="yyyy-MM-dd"/> </td>
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