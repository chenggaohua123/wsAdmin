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
<title>商户信息查找带回</title>
</head>
<style> 
</style> 
<script type="text/javascript">

</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/queryMerchantMerAndTerNoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form onsubmit="return dialogSearch(this);" action="<%=path %>/merchantmgr/queryMerchantMerAndTerNoList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<input type="hidden" name="type" value="${param.type }" readonly="readonly"/>
			</li>
		</ul>
	</div>
	</form>
</div>

<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="90" >
		<thead>
			<tr>
				<th style="word-break:break-all">类型</th>
				<th style="word-break:break-all">类型名称</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="typeInfo">
				<tr>
					<td>${typeInfo.type }</td>
					<td>${typeInfo.typeName }</td>
					<td>
					<a class="btnSelect" href="javascript:$.bringBack({merType:'${typeInfo.type }', merTypeName:'${typeInfo.typeName }'})" title="查找带回">选择</a>
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
		<div class="pagination" targetType="dialog" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>

</div>
</body>
</html>