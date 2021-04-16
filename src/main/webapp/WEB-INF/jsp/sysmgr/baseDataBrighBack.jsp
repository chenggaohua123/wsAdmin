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
<title>Insert title here</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/sysmgr/getBaseDataList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sysmgr/getBaseDataList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>Key：</label>
				<input type="text" name="columnKey" value="${param.columnKey }"/>
			</li>
			<li>
				<label>Value：</label>
				<input type="text" name="columnvalue" value="${param.columnvalue }"/>
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
	<table class="list" width="98%" targetType="navTab" layoutH="90" style="text-align: center;">
		<thead>
			<tr>
				<th>字典名称</th>
				<th>KEY</th>
				<th>VALUE</th>
				<th>字典数据表KEY列名</th>
				<th>字典数据表VALUE列名</th>
				<th>备注信息</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="baseData">
				<tr target="sid_role" rel="${baseData.id }">
					<td>${baseData.tableName }</td>
					<td>${baseData.columnKey}</td>
					<td>${baseData.columnvalue }</td>
					<td>${baseData.columnKeyName }</td>
					<td>${baseData.columnVauleName }</td>
					<td>${baseData.remark }</td>
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({tableName:'${baseData.columnKey }', columnKey:'${baseData.columnKey }',columnvalue:'${baseData.columnvalue }',columnKeyName:'${baseData.columnKeyName }',columnVauleName:'${baseData.columnVauleName }'})" title="查找带回">选择</a>
					</td>
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