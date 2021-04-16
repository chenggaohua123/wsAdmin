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
<title>终端下发列表</title>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/terSnRelAgentInfoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantmgr/terSnRelAgentInfoList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>顶级代理号：</label>
				<input type="text" name="parentAgentNo" value="${param.parentAgentNo }"/>
			</li>
			<li>
				<label>二级代理号：</label>
				<input type="text" name="agentNo" value="${param.agentNo }"/>
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
			<li><a class="add" href="<%=path %>/merchantmgr/addTerSnRelAgentgo" target="dialog" mask="true" width="500" height="200" title="终端下发" rel="parentRelAgent"><span>终端下发</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/exportTerSNRel" target="dwzExport" targetType="navTab" mask="true"><span>导出</span></a></li>
			<li class="line">line</li>
			<li><a title="确实要回收这些记录吗?" target="selectedTodo" rel="ids" href="<%=path %>/merchantmgr/TerSnRelRecycle" class="delete"><span>机具回收</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>终端序列号</th>
				<th>父级代理商号</th>
				<th>代理商号</th>
				<th>下发时间</th>
				<th>下发人</th>
				<th>修改时间</th>
				<th>修改人</th>
				<th>机具下发</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="terInfo">
				<tr target="sid_role" rel="${terInfo.id }" align="center">
				   <td><input name="ids" value="${terInfo.id }" type="checkbox"></td>
					<td>${terInfo.SNNo }</td>
					<td>${terInfo.parentAgentNo }</td>
					<td>${terInfo.agentNo }</td>
					<td>${funcs:formatDate(terInfo.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${terInfo.creatBy }</td>
					<td>${funcs:formatDate(terInfo.updateDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${terInfo.updateBy } </td>
					<td>${funcs:getStringColumnKey('TOOLSSTATUS',terInfo.state,'未知状态')} </td>
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