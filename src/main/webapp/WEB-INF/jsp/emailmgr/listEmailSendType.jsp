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
<title>邮件发送类型列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/emailmgr/listEmailSendType">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/emailmgr/listEmailSendType" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>发送类型：</label>
				<input type="text" name="sendType" value="${param.sendType }"/>
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
			<li><a class="add" href="<%=path %>/emailmgr/goAddEmailSendType" target="dialog" width="550" height="380" mask="true" rel="add" ><span>添加邮件发送类型</span></a></li>
			<li><a class="edit" href="<%=path %>/emailmgr/goUpdateEmailSendType?id={sid_user}" target="dialog" width="550" height="350" mask="true" warn="请选择一个邮箱类型"" rel="update"><span>修改邮件发送类型</span></a></li>
			<li><a class="edit" href="<%=path %>/emailmgr/goBindingEmailInfoList?id={sid_user}" target="dialog" width="550" height="350" mask="true" warn="请选择一个邮箱类型" rel="update"><span>绑定邮件发送附带信息</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="115" style="text-align:center;" >
		<thead>
			<tr>
				<th>邮件发送类型</th>
				<th>发送处理类</th>
				<th>邮箱主题</th>
				<th>发送类型名</th>
				<th>持卡人/商户</th>
				<th>预览</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="sid_user" rel="${info.id }">
					<td>${info.sendType }</td>
					<td>${info.sendService }</td>
					<td>${info.emailSubject }</td>
					<td>${info.name }</td>
					<td>${info.type=='2'?'发送给持卡人':'发送给商户' }</td>
					<td><a class="edit" href="<%=path %>/emailmgr/showEmailView?id=${info.id}" target="_blank" width="1000" height="800" mask="true"  rel="update"><span>预览</span></a></td>
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