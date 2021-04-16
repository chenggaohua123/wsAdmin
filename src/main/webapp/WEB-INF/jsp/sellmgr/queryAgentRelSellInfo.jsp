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
<title>查询销售员绑定代理商</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/sellmgr/queryAgentRelSellInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="sellRefSellsFromId" onsubmit="return navTabSearch(this);" action="<%=path %>/sellmgr/queryAgentRelSellInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>代理名称：</label>
				<input type="text" name="agentName" value="${param.agentName }"/>
			</li>
			<li>
				<label>销售员：</label>
				<input type="text" name="sellBy" value="${param.sellBy }"/>
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
			<li><a class="add" href="<%=path %>/sellmgr/goAddAgentRelSellInfo" target="dialog" width="800" hight="600" title="添加" mask="true"><span>添加</span></a></li>
			<li><a class="edit" href="<%=path %>/sellmgr/goAddAgentRelSellInfo?id={id}" target="dialog" width="800" hight="600" title="添加" mask="true" warn="请选择要修改的内容"><span>修改</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>代理商名称</th>
				<th>销售员</th>
				<th>销售员QQ</th>
				<th>销售员电话</th>
				<th>注册地址</th>
				<th>注册商户</th>
				<th>创建人</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr  target="id" rel="${info.id }">
					<td>${info.agentName }</td>
					<td>${info.sellBy }</td>
					<td>${info.QQ }</td>
					<td>${info.phone }</td>
					<td>https://shanghu.fhtpay.com/merchantReg/${info.shaKey }</td>
					<td>${info.merNos }</td>
					<td>${info.createBy }</td>
					<td>${info.createDate }</td>
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