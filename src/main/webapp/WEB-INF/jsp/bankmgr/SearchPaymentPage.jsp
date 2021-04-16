<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" method="post" action="<%=path %>/bankMgr/searchPaymentPage">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/bankMgr/searchPaymentPage" method="post">
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
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/bankMgr/toAddPage" title="新增支付页面" target="dialog" mask="true" resizable="false" drawable="false" minable="false" width="550" height="440"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/bankMgr/toAddPage?id={page_id}" title="修改支付页面" rel="page_update_dialog" target="dialog" mask="true" resizable="false" drawable="false" minable="false" width="550" height="350"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="<%=path %>/bankMgr/deletePaymentPage?id={page_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
		<table class="list" width="100%" targetType="navTab" layoutH="115">
			<thead>
				<tr>
					<th width="10%">页面名称</th>
					<th width="20%">页面路径</th>
					<th width="10%">状态</th>
					<th width="20%">创建日期</th>
					<th width="10%">创建人</th>
					<th width="20%">更新日期</th>
					<th width="10%">更新人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data }" var="item">
				<tr target="page_id" rel="${item.id}">
					<td>
							<a href="<%=path %>/dwz/fhtlogin/img/${item.pageUrl}.png"  target="_blank">${item.pageName}</a>
					</td>
					<td>${item.pageUrl}</td>
					<td>
						<c:choose>
							<c:when test="${item.status eq 1}">正常</c:when>
							<c:when test="${item.status eq 2}">关闭</c:when>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${item.createdDate}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>${item.createdPerson}</td>
					<td><fmt:formatDate value="${item.lastUpdateDate}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>${item.lastUpdatePerson}</td>
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