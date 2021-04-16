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
<form id="pagerForm" method="post" action="<%=path %>/sysmgr/getuserlist">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sysmgr/getuserlist" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>用户名：</label>
				<input type="text" name="userName" value="${param.userName }"/>
			</li>
			<li>
				<label>真实姓名：</label>
				<input type="text" name="realName" value="${param.realName }"/>
			</li>
			<li>
			<label>是否有效：</label>
			<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=USERSTATUS" selectedValue="${param.enabled }" relValue="columnKey" relText="columnvalue" name="enabled">
			      <option value="">所有</option>
		    </select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>Email：</label>
				<input type="text" name="email" value="${param.email }"/>
			</li>
			<li>
				<label>Phone：</label>
				<input type="text" name="phoneNo" value="${param.phoneNo }"/>
			</li>
			<li>
			<label>登陆方式：</label>
			<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=VERTION" relValue="columnKey"  selectedValue="${param.verificationType }"  relText="columnvalue" name="verificationType">
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
			<li><a class="add" href="<%=path %>/sysmgr/goAddUserInfo" target="dialog" width="550" height="380" mask="true" ><span>添加用户</span></a></li>
			<li><a class="edit" href="<%=path %>/sysmgr/goreleInfo?id={sid_user}" target="dialog" width="650" height="550" mask="true" warn="请选择一个用户"><span>角色关联</span></a></li>
			<li><a class="edit" href="<%=path %>/sysmgr/goUpdateUserInfo?id={sid_user}" target="dialog" width="550" height="350" mask="true" warn="请选择一个用户"><span>修改用户</span></a></li>
			<%-- <li><a class="edit" href="<%=path %>/sysmgr/getAddUserRelAgentGo?id={sid_user}" target="dialog" width="580" height="350" mask="true" rel="UserRelAgent" warn="请选择一个用户"><span>用户关联代理商</span></a></li> --%>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="140" style="text-align:center;" >
		<thead>
			<tr>
				<th>用户名</th>
				<th>真实姓名</th>
				<th>邮箱</th>
				<th>电话</th>
				<th>登陆方式</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>地址</th>
				<th>是否有效</th>
				<th>所属系统</th>
				<!-- <th>关联代理商号</th> -->
				<th>关联商户号</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="user">
				<tr target="sid_user" rel="${user.id }">
					<td><a  href="<%=path %>/sysmgr/queryIDCordInfo?id=${user.id }" target="dialog" width="900" height="500" mask="true" title="详细信息">${user.userName }</a></td>
					<td>${user.realName }</td>
					<td>${user.email }</td>
					<td>${user.phoneNo }</td>
					<td>${funcs:getStringColumnKey('VERTION',user.verificationType,'未知状态')}</td>
					<td>${funcs:formatDate(user.createTime,'yyyy-MM-dd HH:mm')} </td>
					<td>${user.createBy }</td>
					<td>${user.address }</td>
					<td>
					   ${funcs:getStringColumnKey('USERSTATUS',user.enabled,'未知状态')}
					</td>
					<td>
					  ${funcs:getStringColumnKey('SYSTEMID',user.systemId,'未知状态')}
					</td>
					<%-- <td>${user.agentNo1 }</td> --%>
					<td>${user.merNo }</td>
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