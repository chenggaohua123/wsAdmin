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
<title>投诉类型查找带回</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/complaint/complaintListBack">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<%-- <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/complaint/complaintListBack" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>key：</label>
				<input type="text" name="cKey" value="${param.cKey}"/>
			</li>
			<li>
				<label>value：</label>
				<input type="text" name="cValue" value="${param.cValue}"/>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form> --%>
</div>

<div class="pageContent">

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="35" style="text-align:center;" >
		<thead>
			<tr>
				<th>ID</th>
				<th>key</th>
				<th>value</th>
				<th>状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>修改人</th>
				<th>修改时间</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="sid_user" rel="${info.id }">
					<td>${info.id }</td>
					<td>${info.cKey }</td>
					<td>${info.cValue }</td>
					<td>${info.enabled==1?'有效':'无效'}</td>
					<td>${info.createdBy}</td>
					<td>${info.createdDate }</td>
					<td>${info.lastUpdateBy }</td>
					<td>${info.lastUpdateDate }</td>
					<td><a class="btnSelect" href="javascript:$.bringBack({cId:'${info.id }',cValue:'${info.cValue }'})"  title="查找带回">选择</a></td>
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