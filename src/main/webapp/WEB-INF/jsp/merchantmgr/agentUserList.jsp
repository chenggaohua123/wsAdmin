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
<title>商户列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/getAgentUserList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dialogSearch(this);" action="<%=path %>/merchantmgr/getAgentUserList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>用户账号：</label>
				<input type="hidden" name="agentNo" value="${agentNo }"/>
				<input type="hidden" name="agentId" value="${agentId }"/>
				<input type="text" name="loginAccount" value="${param.loginAccount }"/>
			</li>
			<li>
				<label>用户名：</label>
				<input type="text" name="userName" value="${param.userName }"/>
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
			<li><a class="edit" href="<%=path %>/merchantmgr/delAgentUser?id={agentUser_id}" target="dialog" width="600" height="300" mask="true" warn="请选择一个用户"><span>删除账号信息</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/getAgentUser?id={agentUser_id}&agentNo=${agentNo}" target="dialog" width="500" height="400" rel="updateAgentUserInfo" mask="true"><span>修改账号信息</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/getAgentUser?agentNo=${agentNo}" target="dialog" width="500" height="400" rel="updateConfig" mask="true"><span>添加账号信息</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/resAgentPassword?id={agentUser_id}" target="dialog" width="500" height="200" rel="addAgentUserInfo" mask="true" warn="请选择一个用户"><span>修改密码</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="dialogTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>代理商编号</th>
				<th>登陆账号</th>
				<th>用户名</th>
				<th>状态</th>
				<th>邮箱</th>
				<th>电话</th>
				<th>备注</th>
				<th>创建日期</th>
				<th>创建人</th>
				<th>最后修改日期</th>
				<th>最后修改人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="agentUser">
				<tr target="agentUser_id" rel="${agentUser.id }" align="center">
					<td>${agentUser.agentNo }</td>
					<td>${agentUser.loginAccount }</td>
					<td>${agentUser.userName }</td>
					<td>${funcs:getStringColumnKey('MERCHANTSTATUS',agentUser.status,agentUser.status)}</td>
					<!-- 
					<td>${funcs:getStringColumnKey('MERCHANT_ACCOUNT_STATUS',agentUser.status,agentUser.status)}</td>
					<td>${funcs:getStringColumnKey('MERCHANT_DIR_STATUS',agentUser.status,agentUser.status)}</td>
					 -->
					<td>${agentUser.email }</td>
					<td>${agentUser.phone }</td>
					<td>${agentUser.remark }</td>
					<td>${funcs:formatDate(agentUser.createdDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${agentUser.createdPerson }</td>
					<td>${funcs:formatDate(agentUser.lastUpdateDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${agentUser.lastUpdatePerson }</td>
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
		</div><%-- targetType="navTab" --%>
		<div class="pagination" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
</body>
</html>