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
<title>产品与品牌列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/brandProductMgr/getBrandProductList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/brandProductMgr/getBrandProductList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>名称：</label>
				<input type="text" name="bpname" value="${param.bpname }"/>
			</li>
			<li>
				<label>类型：</label>
				<select class="combox" name="type">
					<option value="" ${param.type==''?'selected':'' }>所有</option>
					<option value="1" ${param.type=='1'?'selected':'' }>产品</option>
					<option value="2" ${param.type=='2'?'selected':'' }>品牌</option>
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
			<li><a class="add" href="<%=path %>/brandProductMgr/goBrandProductInfo" target="dialog" width="550" height="400" mask="true"><span>添加产品与品牌</span></a></li>
			<li><a class="edit" href="<%=path %>/brandProductMgr/goUpdatebrandProductInfo?id={id}" rel="brandproduct" target="dialog" width="650" height="450" mask="true" warn="请选择一条记录"><span>修改产品与品牌信息</span></a></li>
			<li><a class="delete" href="<%=path %>/brandProductMgr/deletebrandProductInfo?id={id}" target="ajaxTodo" width="950" height="400" mask="true"  mask="true"  rel="agentMerc11hant" ><span>删除</span></a></li>
		<li><a class="add" href="<%=path %>/brandProductMgr/exportbrandProductInfo" target="dwzExport" targetType="navTab"rel="addCurr11ency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>类型</th>
				<th>名称</th>
				<th>创建人</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="brandproduct">
				<tr target="id" rel="${brandproduct.id }">
					<td>${funcs:getStringColumnKey('BRAND_PRODUCT',brandproduct.type,brandproduct.type)}</td>
					<td>${brandproduct.bpname }</td>
					<td>${brandproduct.createBy}</td>
					<td>${brandproduct.createdate}</td>
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