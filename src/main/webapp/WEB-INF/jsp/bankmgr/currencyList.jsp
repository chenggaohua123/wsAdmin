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
<form id="pagerForm" method="post" action="<%=path %>/bankMgr/getCurrencyList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/bankMgr/getCurrencyList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>银行名称：</label>
				<input type="text" name="bankName" value="${param.bankName }"/>
			</li>
			<li>
				<label>通道名称：</label>
				<input type="text" name="currencyName" value="${param.currencyName}"/>
			</li>
			<li>
			    <label>状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTCONFIG" relValue="columnKey" selectedValue="${param.enabled }" relText="columnvalue" name="enabled">
					<option value="">所有</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>银行商户号：</label>
				<input type="text" name="merchantNo" value="${param.merchantNo }"/>
			</li>
			<li>
				<label>账单名称：</label>
				<input type="text" name="acquirer" value="${param.acquirer }"/>
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
			<li><a class="add" href="<%=path %>/bankMgr/goAddCurrencyInfo" target="dialog" rel="addCurrency" width="650" height="440" mask="true"><span>添加通道信息</span></a></li>
			<li><a class="add" href="<%=path %>/bankMgr/goCurrencyConfigInfo?id={currencyId}" target="dialog" width="450" height="300" mask="true" warn="请选择一个通道"><span>添加配置信息</span></a></li>
			<li><a class="add" href="<%=path %>/bankMgr/queryCurrencyConfig?currencyId={currencyId}"  target="dialog" width="650" height="300" rel="queryConfig"  mask="true" warn="请选择一个通道"><span>查看配置信息</span></a></li>
			<li><a class="edit" href="<%=path %>/bankMgr/goUpdateCurrencyInfo?currencyId={currencyId}" rel="addCurrency" target="dialog" width="650" height="450" mask="true" warn="请选择一个通道"><span>修改通道信息</span></a></li>
			<li><a class="add" href="<%=path %>/bankMgr/queryCurrencyListLog?id={currencyId}" target="dialog" width="750" height="300" mask="true" warn="请选择一个通道"><span>查看通道修改记录</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="145" style="text-align: center;">
		<thead>
			<tr>
				<th>通道ID</th>
				<th>银行名称</th>
				<th>通道名称</th>
				<th>银行商户号</th>
				<th>银行终端号</th>
				<th>英文账单名称</th>
				<th>日限额</th>
				<th>是否关闭</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>通道所属</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="currency">
				<tr target="currencyId" rel="${currency.id }">
					<td>${currency.id }</td>
					<td>${currency.bankName }</td>
					<td>${currency.currencyName }</td>
					<td>${currency.merchantNo }</td>
					<td>${currency.terNo }</td>
					<td>${currency.acquirer }</td>
					<td>
						<c:if test="${currency.topAmount <=0.0}">无</c:if>
						<c:if test="${currency.topAmount >0.0}">${currency.topAmount}</c:if>
					</td>
					<td>${funcs:getStringColumnKey('MERCHANTCONFIG',currency.enabled,'未知状态')}</td>
					<td>${funcs:formatDate(currency.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${currency.createBy }</td>
					<td>${currency.currencyBelongs }</td>
					<td>${currency.remark }</td>
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