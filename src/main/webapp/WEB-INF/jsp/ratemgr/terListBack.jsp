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
<form id="pagerForm" method="post" action="<%=path %>/ratemgr/getTerListBack">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dialogSearch(this);" action="<%=path %>/ratemgr/getTerListBack" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo }"/>
			</li>
			<%-- <li>
			<label>是否禁用：</label>
			<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=TER_STATUS" relValue="columnKey" selectedValue="${param.enabled }" relText="columnvalue"  name="enabled">
				<option value="">所有</option>
			</select>
			</li> --%>
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
				<th>商户号</th>
				<th>终端号</th>
				<th>终端序列号</th>
				<th>密钥</th>
				<th>是否有效</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>备注</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:if test="${type=='exceptionType' }">
			<tr>
					<td>所有</td>
					<td>所有</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td> </td>
					<td> </td>
					<td><a class="btnSelect" href="javascript:$.bringBack({terNo:'0',merNo:'0',currency:'CNY'})"  title="查找带回">选择</a></td>
				</tr>
		</c:if>
			<c:forEach items="${page.data }" var="terInfo">
				<tr>
					<td>${terInfo.merNo }</td>
					<td>${terInfo.terNo }</td>
					<td>${terInfo.serNo }</td>
					<td>${terInfo.shaKey }</td>
					<td>${funcs:getStringColumnKey('TER_STATUS',terInfo.enabled,'未知状态')}</td>
					<td>${funcs:formatDate(terInfo.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${terInfo.createby } </td>
					<td>${terInfo.remark } </td>
					<td><a class="btnSelect" href="javascript:$.bringBack({terNo:'${terInfo.terNo }',merNo:'${terInfo.merNo }',currency:'${terInfo.settleCurrency }'})"  title="查找带回">选择</a></td>
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