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
<title>商户重复支付限定</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/riskmgr/queryRiskCountryInfoLogList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/riskmgr/queryRiskCountryInfoLogList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>国家名称(英文)：</label>
				<input type="text" name="countryNameEN" value="${param.countryNameEN }"/>
			</li>
			<li>
				<label>国家名称(中文)：</label>
				<input type="text" name="countryNameCN" value="${param.countryNameCN }"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>国家名称(简称)：</label>
				<input type="text" name="countryNameSimple" value="${param.countryNameSimple }"/>
			</li>
			<li>
				<label>操作类型：</label>
				<select name="actionType">
					<option <c:if test="${param.actionType=='' }">selected</c:if> value="">所有</option>
					<option <c:if test="${param.actionType=='1' }">selected</c:if> value="1">用户添加</option>
					<option <c:if test="${param.actionType=='2' }">selected</c:if> value="2">用户删除</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
			  <label>操作日期</label>
			  <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startDate']}" />
			</li>
			<li>
		       <span class="limit">-</span>
			  <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['endDate']}"/>		
			
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
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
			<thead>
				<tr>
					<th>商户号</th>
					<th>操作类型</th>
					<th>国家简写</th>
					<th>国家名称(中文)</th>
					<th>国家名称(英文)</th>
					<th>修改人</th>
					<th>操作时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="info">
					<tr  target="counrtryId" rel="${info.id }"   >
						<td>${info.merNo }</td>
						<td>
							<c:if test="${info.actionType=='1' }">用户添加</c:if>
							<c:if test="${info.actionType=='2' }">用户删除</c:if>
						</td>
						<td>${info.countryNameSimple }</td>
						<td>${info.countryNameCN }</td>
						<td>${info.countryNameEN }</td>
						<td>${info.createby }</td>
						<td>${info.createDate }</td>
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