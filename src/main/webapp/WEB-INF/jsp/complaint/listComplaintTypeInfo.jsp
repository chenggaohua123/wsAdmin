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
<title>投诉类型列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/complaint/listComplaintTypeInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/complaint/listComplaintTypeInfo" method="post">
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
			<li>
				<label>有效性：</label>
				<select name="enabled" class="combox">
					<option value="" ${param.enabled==''?'selected':'' }>全部</option>
					<option value="1" ${param.enabled=='1'?'selected':'' }>有效</option>
					<option value="0" ${param.enabled=='0'?'selected':'' }>无效</option>
				</select>
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
			<li><a class="add" href="<%=path %>/complaint/goAddComplaintTypeInfo" target="dialog" width="550" height="380" mask="true" rel="add" ><span>添加投诉类型信息</span></a></li>
			<li><a class="edit" href="<%=path %>/complaint/goUpdateComplaintTypeInfo?id={sid_user}" target="dialog" width="550" height="350" mask="true" warn="请选择一条投诉类型信息"><span>修改投诉类型信息</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="115" style="text-align:center;" >
		<thead>
			<tr>
			<th>ID</th>
				<th>key</th>
				<th>value</th>
				<th>状态</th>
				<th>原因类型</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>修改人</th>
				<th>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="sid_user" rel="${info.id }">
					<td>${info.id }</td>
					<td>${info.cKey }</td>
					<td>${info.cValue }</td>
					<td>${info.enabled==1?'有效':'无效'}</td>
					<td>
						<c:if test="${info.type =='0'}">调查单</c:if>
						<c:if test="${info.type =='1'}">拒付单</c:if>
						<c:if test="${info.type =='2'}">投诉单</c:if>
					</td>
					<td>${info.createdBy}</td>
					<td>${info.createdDate }</td>
					<td>${info.lastUpdateBy }</td>
					<td>${info.lastUpdateDate }</td>
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