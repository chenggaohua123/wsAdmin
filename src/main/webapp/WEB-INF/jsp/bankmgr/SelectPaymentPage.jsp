<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
%>
<form onsubmit="return navTabSearch(this);" action="<%=path %>/bankMgr/selectPaymentPage" method="post">
<div class="pageHeader">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td width="11%" align="right">页面名称</td>
				<td width="22%"><input type="text" name="pageName" value="${param.pageName}"/></td>
				<td width="11%" align="right">状态</td>
				<td width="22%">
					<select name="status">
						<option value="0">-- 全部 --</option>
						<option value="1">正常</option>
						<option value="2">关闭</option>
					</select>
				</td>
				<td width="33%" align="right"></td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar"></ul>
	</div>
	<div id="w_list_print">
		<table class="list" width="100%" targetType="navTab" layoutH="115">
			<thead>
				<tr>
					<th width="30%">页面名称</th>
					<th width="40%">页面路径</th>
					<th width="20%">状态</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="item">
				<tr>
					<td>${item.pageName}</td>
					<td>${item.pageUrl}</td>
					<td>
						<c:choose>
							<c:when test="${item.status eq 1}">正常</c:when>
							<c:when test="${item.status eq 2}">关闭</c:when>
						</c:choose>
					</td>
					<td>
						<a href="javascript:$.bringBack({pageId:${item.id},pageName:'${item.pageName}'})" title="选择" class="btnSelect">选择</a>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</form>