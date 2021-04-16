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
<title>商户终端查找带回</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/getMccInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dialogSearch(this);" action="<%=path %>/merchantmgr/getMccInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>mcc描述：</label>
				<input type="text" name="mccDescribe" value="${param.mccDescribe}"/>
			</li>
			<li>
				<label>mcc值：</label>
				<input type="text" name="mccValue" value="${param.mccValue }"/>
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
	<table class="list" width="98%" targetType="navTab" layoutH="92" style="text-align: center;">
		<thead>
			<tr>
				<th>mcc描述</th>
				<th>mcc值</th>
				<th>mcc类型</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="terInfo">
				<tr>
					<td>${terInfo.mccDescribe }</td>
					<td>${terInfo.mccValue }</td>
					<td>${terInfo.type }</td>
					
					<td><a class="btnSelect" href="javascript:$.bringBack({mccValue:'${terInfo.mccValue }',mccValue:'${terInfo.mccValue }'})"  title="查找带回">选择</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="dialogPageBreak({numPerPage:this.value})">
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