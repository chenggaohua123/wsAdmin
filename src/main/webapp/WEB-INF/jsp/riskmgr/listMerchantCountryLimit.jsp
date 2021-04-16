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
<title>商户国家限定查询</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/riskmgr/listMerchantCountryLimit">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/riskmgr/listMerchantCountryLimit" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>国家简写：</label>
				<input type="text" name="countryNameSimple" value="${param.countryNameSimple}"/>
			</li>
			<li>
				<label>国家名称(中文)：</label>
				<input type="text" name="countryNameCN" value="${param.countryNameCN}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>国家名称(英文)：</label>
				<input type="text" name="countryNameEN" value="${param.countryNameEN}"/>
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
		</ul>
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="142" style="text-align: center;">
			<thead>
				<tr>
					<th>商户号</th>
					<th>国家简写</th>
					<th>国家名称(中文)</th>
					<th>国家名称(英文)</th>
					<th>设置时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="country">
					<tr    >
						<td>${country.merNo }</td>
						<td>${country.countryNameSimple }</td>
						<td>${country.countryNameCN }</td>
						<td>${country.countryNameEN }</td>
						<td>${funcs:formatDate(country.createDate,'yyyy-MM-dd HH:mm')} </td>
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