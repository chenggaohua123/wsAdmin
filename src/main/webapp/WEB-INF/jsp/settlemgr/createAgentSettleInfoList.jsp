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
<script type="text/javascript">
function changeSubmitAction(){
	$("#settleForm").attr("action","<%=path %>/settlemgr/effectAgentSettleInfo");
	validateCallback($("#settleForm"),'','确定要生效？');
}

</script>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/viewAgentSettleInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" id="settleForm" onsubmit="return  viewMerchantSettleList(this)"  action="<%=path %>/settlemgr/viewAgentSettleInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			 <li class="dateRange">
				<label>清算截止月份：</label>
				<input type="text" name="transDate"  id = "settleDate" readonly="readonly"  class="required" value="${param.transDate}"/>
			</li>
		</ul>
			<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button onclick="changeSubmitAction()"  type="button">生效代理商清算记录</button></div></div></li>
			</ul>
		</div>
	</div>
	
	</form>
</div>
<div class="pageContent">
<div class="panelBar">
		<ul class="toolBar">
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>代理商编号</th>
				<th>一级代理商编号</th>
				<th>交易笔数</th>
				<th>交易金额</th>
				<th>交易手续费</th>
				<th>高签部分手续费</th>
				<th>分润收益</th>
				<th>二级代理高签手续费</th>
				<th>二级代理分润</th>
				<th>结算金额</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="settle">
				<tr>
					<td>${settle.agentNo }</td>
					<td>${settle.parentAgentNo }</td>
					<td>${settle.transCount }</td>
					<td><fmt:formatNumber value="${settle.transAmount }" maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.transForAmaount }" maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.diversityAgentForAmount }" maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.diversitySplitAgentForAmount }" maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.diversityParentAgentForAmount}" maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.diversitySplitParentAgentForAmount }" maxFractionDigits="2"/></td>
					<td><fmt:formatNumber value="${settle.settleAmount }" maxFractionDigits="2"/></td>
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