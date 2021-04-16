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
<title>银行结算记录</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/transmgr/queryBankSettleDetail">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transmgr/queryBankSettleDetail" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>银行商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
		 	<li>
		 		<label>银行终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
		 	</li>
		 <li  class="dateRange">
			  <label>结算日期</label>
			  <input type="text" name="settleDateStart"  id = "settleDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.settleDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="settleDateEnd"  id = "settleDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.settleDateEnd}"/>		
			</li>
		</ul>
		<ul class="searchContent">
			<li>
		 		<label>银行参考号：</label>
				<input type="text" name="refNo" value="${param.refNo}"/>
		 	</li>
		 	<li>
			    <label>对账状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CHECK_STAUS" relValue="columnKey" selectedValue="${param.checkSucceed }" relText="columnvalue" name="checkSucceed" >
					<option value="">所有</option>
					<option value="100">对账不成功交易</option>
				</select>
			</li>
			<li>
			    <label>对账批次号：</label>
				<input type="text" name="batchNo" value="${param.batchNo}"/>
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
			<li><a class="add" href="<%=path %>/transmgr/updateCheckStatus" target="selectedTodo" rel="ids" width="550" height="300" mask="true"><span>修改对账成功</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="147" style="text-align: center;">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>银行参考号</th>
				<th>原参考号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>交易金额</th>
				<th>商户手续费</th>
				<th>交易时间</th>
				<th>原交易时间</th>
				<th>结算日期</th>
				<th>对帐批次号</th>
				<th>对账状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr>
					<td><input name="ids" value="${trans.id }" type="checkbox"></td>
					<td>${trans.refNo }</td>
					<td>${trans.oriRefNo }</td>
					<td>${trans.merchantNo }</td>
					<td>${trans.terNo }</td>
					<td>${trans.transAmount }</td>
					<td>${trans.forAmount }</td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${funcs:formatDate(trans.oriTransDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${funcs:formatDate(trans.settleDate,'yyyy-MM-dd')}</td>
					<td>${trans.batchNo}</td>
					<td>${funcs:getStringColumnKey('CHECK_STAUS',trans.checkSucceed,trans.checkSucceed)}</td>
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