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
<title>通道列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/riskReport/queryGwRiskTransList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/riskReport/queryGwRiskTransList" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
		 	</li>
		 	<li>
			    <label>状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=AMOUNT_STATE" relValue="columnKey" selectedValue="${param.status }" relText="columnvalue" name="status" >
					<option value="">所有</option>
				</select>
			</li>
			
		</ul>
		<ul  class="searchContent">
		  <li>
			    <label>风险类型：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=AMOUNT_TYPE" relValue="columnKey" selectedValue="${param.riskType }" relText="columnvalue" name="riskType" >
					<option value="">所有</option>
				</select>
			</li>
			<li  class="dateRange">
			  <label>查询日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateStart}"/>
			     <span class="limit">-</span>
			   <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateEnd}"/>		
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
	    <li><a title="确实处理风险记录吗?" target="selectedTodo" rel="ids" href="<%=path %>/riskMgr/RiskCheckTrans" class="delete"><span>风险处理</span></a></li>
	  </ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="145" style="text-align: center;">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>流水号</th>
				<th>参考号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>交易类型</th>
				<th>交易金额</th>
				<th>风险类型</th>
				<th>状态</th>
				<th>返回码</th>
				<th>交易时间</th>
				<th>交易通道</th>
				<th>收单机构</th>
				<th>发卡行</th>
				<th>卡名称</th>
				<th>卡类型</th>
				<th>结算日期</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr>
				    <td><input name="ids" value="${trans.id }" type="checkbox"></td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${trans.tradeNo }"> ${trans.tradeNo }</a></td>
					<td><a target="dialog" title="历史状态变更" width="1050" height="470" mask="true" href="<%=path %>/transmgr/queryTransDetailByRelNo?relNo=${trans.relNo }">${trans.relNo }</a></td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${funcs:getStringColumnKey('gw_transtype_info',trans.transType,'未知状态')}</td>
					<td>${trans.transAmount }</td>
					<td>${funcs:getStringColumnKey('AMOUNT_TYPE',trans.riskType,'未知状态')}</td>
					<td>${funcs:getStringColumnKey('AMOUNT_STATE',trans.status,'未知状态')}</td>
					<td>${trans.respCode }</td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.currencyName }</td>
					<td>${trans.acquiringBank }</td>
					<td>${trans.bankName }</td>
					<td>${trans.cardName }</td>
					<td>${trans.cardType }</td>
					<td>${trans.settleDate }</td>
					<td>${funcs:formatDate(trans.createDate,'yyyy-MM-dd HH:mm:ss')} </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" style="height:30px">
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