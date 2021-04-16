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
<style type="text/css">
</style>
<title>ThreatMetrix返回信息</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/riskmgr/queryThreatMetrixInfoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/riskmgr/queryThreatMetrixInfoList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
			</li>
			<li>
				<label>域名解析IP：</label>
				<input type="text" name="dns_ip" value="${param.dns_ip }"/>
			</li>
			<li>
				<label>输入IP：</label>
				<input type="text" name="input_ip_address" value="${param.input_ip_address }"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>真实IP：</label>
				<input type="text" name="true_ip" value="${param.true_ip }"/>
			</li>
			<li >
			  <label>交易日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date" value="${form['transDateStart']}" />
			</li>
			<li>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss"  class="date" value="${form['transDateEnd']}"/>		
			
			</li>
			
		</ul>
		<ul  class="searchContent">
		<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
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
		<li><a class="add" href="<%=path %>/riskmgr/exportThreatMetrixInfoList" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出明细</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
		<table class="list" width="100%" targetType="navTab" layoutH="160" style="text-align: center;">
			<thead>
				<tr>
					<th>订单号</th>
					<th>商户号</th>
					<th>卡BIN</th>
					<th>卡BIN国家</th>
					<th>发卡机构</th>
					<th>卡BIN类型</th>
					<th>邮箱地址</th>
					<th>设备ID</th>
					<th>SMART ID</th>
					<th>输入IP</th>
					<th>真实IP</th>
					<th>总结风险评分</th>
					<th>时间</th>
					<th>详细信息</th>
				</tr>
			</thead>
			<tbody style="word-wrap:break-word;word-break:break-all;overflow-x: hidden;">
				<c:forEach items="${page.data}" var="info">
					<tr  target="rateId" rel="${info.id }"   >
						<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${info.tradeNo }">${info.tradeNo }</a></td>
						<td>${info.merNo }</td>
						<td>${info.cc_bin_number }</td>
						<td>${info.cc_bin_number_geo }</td>
						<td>${info.cc_bin_number_org }</td>
						<td>${info.cc_bin_number_type }</td>
						<td>${info.account_email }</td>
						<td>${info.device_id }</td>
						<td>${info.fuzzy_device_id }</td>
						<td>${info.input_ip_address }</td>
						<td>${info.true_ip }</td>
						<td>
							<a target="dialog" title="风险项明细" width="450" height="320" mask="true" href="<%=path %>/riskmgr/queryRiskInfoList?id=${info.id }">${info.policy_score }</a>
						</td>
						<td>${funcs:formatDate(info.transDate,'yyyy-MM-dd HH:mm:ss')}</td>
						<td><a target="dialog" title="详细信息" width="900" height="520" mask="true" href="<%=path %>/riskmgr/queryThreatMetrixDetail?id=${info.id }">详细信息</a></td>
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