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
<title>用户列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/sysmgr/getRoleList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sysmgr/getRoleList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>角色名称：</label>
				<input type="text" name="roleName" value="${param.roleName }"/>
			</li>
			<li>
				<label>角色类型：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=SYSTEMID" selectedValue="${param.systemId }"  relValue="columnKey" relText="columnvalue" name="systemId">
			      <option value="">所有</option>
			    </select>
			</li>
			<li>
			   <label>有效性：</label>
               <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=ROLESTATUS" selectedValue="${param.enabled }" relValue="columnKey" relText="columnvalue" name="enabled">
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
			<li><a class="add" href="<%=path %>/sysmgr/goaddRoleList" target="dialog" width="500" height="250" mask="true"><span>添加角色</span></a></li>
			<li><a class="edit" href="<%=path %>/sysmgr/addMenuToRole?roleId={sid_role}" target="dialog" width="550" height="350" mask="true" warn="请选择一个角色"><span>权限关联</span></a></li>
			<li><a class="edit" href="<%=path %>/sysmgr/goupdataddRole?id={sid_role}" target="dialog" width="550" height="350" mask="true" warn="请选择一个用户"><span>修改角色</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>角色名称</th>
				<th>角色类型</th>
				<th>是否有效</th>
				<th>创建时间</th>
				<th>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="role">
				<tr target="sid_role" rel="${role.id }">
					<td>${role.roleName }</td>
					<td>${funcs:getStringColumnKey('SYSTEMID',role.systemId,'未知状态')}</td>
					<td>${funcs:getStringColumnKey('ROLESTATUS',role.enabled,'未知状态')}</td>
					<td>${funcs:formatDate(role.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${role.createBy }</td>
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