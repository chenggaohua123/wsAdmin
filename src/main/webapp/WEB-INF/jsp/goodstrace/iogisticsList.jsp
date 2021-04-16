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
<title>物流公司列表</title>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=path %>/delivery/getIogisticsList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/delivery/getIogisticsList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li><label>物流公司简称：</label>
				<input type="text" name="iogistics" value="${param.iogistics }"/>
			</li>
			<li>
				<label>物流公司全称：</label>
				<input type="text" name="name" value="${param.name }"/>
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
			<li><a class="add" href="<%=path %>/delivery/goAddIogistics" target="dialog" width="550" height="380" mask="true" rel="add"><span>添加物流公司</span></a></li>
			<li><a class="add" href="<%=path %>/delivery/goUpdateIogistics?id={id}" target="dialog" width="550" height="380" mask="true" rel="add"><span>修改物流公司</span></a></li>
			<li><a class="edit" href="<%=path %>/delivery/deleteIogistics?id={id}" target="ajaxTodo" width="550" height="380" title="是否删除！" mask="true" rel="add"><span>删除</span></a></li>
			<li><a class="add" href="<%=path %>/delivery/exportIogistics" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
					  
			<tr>
				<th>货运公司简称</th>
				<th>货运公司全称</th>
				<th>运单查询网址</th>
				<th>添加人</th>
				<th>添加时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data}" var="iogistics">
				<tr target="id" rel="${iogistics.id }">
					<td>${iogistics.iogistics}</td>
					<td>${iogistics.name}</td>
					<td>${iogistics.iogisticsUrl}</td>
					<td>${iogistics.createby}</td>
					<td>${funcs:formatDate(iogistics.createDate,'yyyy-MM-dd HH:mm:ss')}	</td>
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
</body>
</html>