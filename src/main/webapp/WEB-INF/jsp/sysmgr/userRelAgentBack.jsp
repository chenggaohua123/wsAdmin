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
<title>代理商查找带回列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/sysmgr/userRelAgetnBack">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dialogSearch(this);" action="<%=path %>/sysmgr/userRelAgetnBack" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>代理商编号：</label>
				<input type="text" name="agentNo" value="${param.agentNo }"/>
			</li>
			<li>
				<label>代理商姓名：</label>
				<input type="text" name="agentName" value="${param.agentName }"/>
			</li>
			<li>
			<label>状态：</label>
			<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=AGENT_STATUS" relValue="columnKey" selectedValue="${param.enabled }" relText="columnvalue" name="enabled">
				<option value="">所有</option>
			</select>
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
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="115">
		<thead>
			<tr>
				<th>商户号</th>
				<th>父级代理号</th>
				<th>商户名称</th>
				<th>手机号码</th>
				<th>账户</th>
				<th>状态</th>
				<th>注册时间</th>
				<th>开通时间</th>
				<th>失效时间</th>
				<th>创建人</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="merchant">
				<tr align="center">
					<td>${merchant.agentNo }</td>
					<td>${merchant.parentAgentNo=='0'?'顶级代理':merchant.parentAgentNo  }</td>
					<td>${merchant.agentName }</td>
					<td>${merchant.phoneNo }</td>
					<td>${merchant.accountNo}</td>
					<td>${funcs:getStringColumnKey('AGENT_STATUS',merchant.enabled,'未知状态')}</td>
					<td>${funcs:formatDate(merchant.regDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${funcs:formatDate(merchant.activationDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${funcs:formatDate(merchant.expDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${merchant.createBy }</td>
					<td><a class="btnSelect" href="javascript:$.bringBack({agentNo:'${merchant.agentNo }'})" title="查找带回">选择</a></td>
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