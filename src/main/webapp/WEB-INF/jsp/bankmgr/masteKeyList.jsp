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
<title>主密钥查询</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/bankMgr/queryMasteKey">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/bankMgr/queryMasteKey" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>终端SN号：</label>
				<input type="text" name="tersn" value="${param.tersn }"/>
			</li>
			<li>
				<label>型号：</label>
				<input type="text" name="type" value="${param.type}"/>
			</li>
			<li>
			    <label>是否关闭：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTCONFIG" relValue="columnKey" selectedValue="${param.enabled }" relText="columnvalue" name="enabled">
					<option value="">所有</option>
				</select>
			</li>
			<li>
			    <label>状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=KEYMASTESTATUS" relValue="columnKey" selectedValue="${param.status }" relText="columnvalue" name="status">
					<option value="">所有</option>
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
			<li><a class="add" href="<%=path %>/bankMgr/exportMasteKey" target="dwzExport" targetType="navTab" width="550" height="300" mask="true"><span>主密钥导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>终端SN号</th>
				<th>主密钥</th>
				<th>校验值</th>
				<th>传输密钥索引</th>
				<th>品牌</th>
				<th>型号</th>
				<th>上传人</th>
				<th>上传时间</th>
				<th>密钥导出人</th>
				<th>导出时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="key">
				<tr target="keyId" rel="${key.id }">
					<td>${key.tersn }</td>
					<td>${key.key_content }</td>
					<td>${key.check_value }</td>
					<td>${key.key_index }</td>
					<td>${key.brand }</td>
					<td>${key.type }</td>
					<td>${key.sncreate }</td>
					<td>${funcs:formatDate(key.sndate,'yyyy-MM-dd HH:mm')}</td>
					<td>${key.key_person}</td>
					<td>${funcs:formatDate(key.key_expdate,'yyyy-MM-dd HH:mm')}</td>
					<td>${funcs:getStringColumnKey('KEYMASTESTATUS',key.status,'未知状态')}</td>
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