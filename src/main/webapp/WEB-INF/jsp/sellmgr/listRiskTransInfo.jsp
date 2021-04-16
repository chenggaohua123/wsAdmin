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
<title>风险订单查询</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/sellmgr/listRiskTransInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sellmgr/listRiskTransInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
			</li>
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>网站：</label>
				<input type="text" name="website" value="${param.website }"/>
			</li>
			 <li  class="dateRange">
			  <label>风控阻挡时间</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateStart']}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateEnd']}"/>		
			</li>
			<li>
				<label>规则ID：</label>
				<input type="text" name="ruleId" value="${param.ruleId }"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo }"/>
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
		<li><a class="add" href="<%=path %>/sellmgr/exportRiskTransInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出明细</span></a></li>
		</ul>
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
			<thead>
				<tr>
					<th>商户号</th>
					<th>终端号</th>
					<th>流水号</th>
					<th>订单号</th>
					<th>网站</th>
					<th>风控阻挡时间</th>
					<th>规则ID</th>
					<th>阻挡原因</th>
					<th>处理方式</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="riskTrans">
					<tr  target="rateId" rel="${riskTrans.tradeNo }"   >
						<td>${riskTrans.merNo }</td>
						<td>${riskTrans.terNo }</td>
						<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/sellmgr/queryTransByTradeNo?tradeNo=${riskTrans.tradeNo }"> ${riskTrans.tradeNo }</a></td>
						<td>${riskTrans.orderNo }</td>
						<td>${riskTrans.website }</td>
						<td>${funcs:formatDate(riskTrans.doDate,'yyyy-MM-dd HH:mm')} </td>
						<td>${riskTrans.ruleId }</td>
						<td>${riskTrans.doReason }</td>
						<td>${riskTrans.doStatus=='reject'?'<font color=red>拒绝交易</font>':'风险待处理'}</td>
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