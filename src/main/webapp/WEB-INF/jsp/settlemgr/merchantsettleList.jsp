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
<title>商户结算列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/merchantsettleList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/merchantsettleList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
             <li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo }"/>
			</li>
			<li>
			    <label>批次号：</label>
				<input type="text" name="batchNo" value="${param.batchNo }"/>
			</li>
			<li  class="dateRange">
			  <label>结算时间</label>
			  <input type="text" name="settleDateStart"  id = "settleDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.settleDateStart}"/>
			   <span class="limit">-</span>
			   <input type="text" name="settleDateEnd"  id = "settleDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.settleDateEnd}"/>		
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
			<li><a class="add" href="<%=path %>/settlemgr/exportMerchantSettle" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>商户导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>商户名称</th>
				<th>终端号</th>
				<th>交易金额</th>
				<th>交易笔数</th>
				<th>交易手续费</th>
				<th>结算日期</th>
				<th>批次号</th>
				<th>结算金额</th>
				<th>银行卡人</th>
				<th>银行卡号</th>
				<th>银行名称</th>
				<th>明细导出</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="settle">
				<tr target="settleId" rel="${settle.id }">
					<td>${settle.merNo }</td>
					<td>${settle.merchantName }</td>
					<td>${settle.terNo }</td>
					<td>${settle.transAmount }</td>
					<td>${settle.transCount }</td>
					<td>${settle.forAmount }</td>
					<td>${funcs:formatDate(settle.settleDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${settle.batchNo}</td>
					<td>${settle.settleAmount }</td>
					<td>${settle.accountName}</td>
					<td>${settle.accountNo}</td>
					<td>${settle.accountAddress}</td>
					<td><a href="<%=path %>/settlemgr/exportTrans?merNo=${settle.merNo}&terNo=${settle.terNo}&batchNo=${settle.batchNo}"  rel="addCurrency">明细导出</a></td>
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