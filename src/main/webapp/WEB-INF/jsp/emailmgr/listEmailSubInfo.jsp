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
<title>邮箱附带信息列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/emailmgr/listEmailSubInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/emailmgr/listEmailSubInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>tel：</label>
				<input type="text" name="tel" value="${param.tel}"/>
			</li>
			<li>
				<label>fax：</label>
				<input type="text" name="fax" value="${param.fax}"/>
			</li>
			<li>
				<label>Email：</label>
				<input type="text" name="email" value="${param.email}"/>
			</li>
			<li>
				<label>有效性：</label>
				<select name="enabled">
					<option value="" ${param.enabled==''?'selected':'' }>全部</option>
					<option value="1" ${param.enabled=='1'?'selected':'' }>有效</option>
					<option value="0" ${param.enabled=='0'?'selected':'' }>无效</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>replyEmail</label>
				<input type="text" name="replyEmail" value="${param.replyEmail}">
			</li>
			<li>
				<label>helpWebsite</label>
				<input type="text" name="helpWebsite" value="${param.helpWebsite}">
			</li>
			<li>
				<label>website</label>
				<input type="text" name="website" value="${param.website}">
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
			<li><a class="add" href="<%=path %>/emailmgr/goAddEmailSubInfo" target="dialog" width="550" height="380" mask="true" rel="add" ><span>添加邮箱附带信息</span></a></li>
			<li><a class="edit" href="<%=path %>/emailmgr/goUpdateEmailSubInfo?id={sid_user}" target="dialog" width="550" height="350" mask="true" warn="请选择一个用户" rel="update"><span>修改邮箱附带信息</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="139" style="text-align:center;" >
		<thead>
			<tr>
				<th>tel</th>
				<th>fax</th>
				<th>Email</th>
				<th>replyEmail</th>
				<th>helpWebsite</th>
				<th>website</th>
				<th>有效性</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>修改人</th>
				<th>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="sid_user" rel="${info.id }">
					<td>${info.tel }</td>
					<td>${info.fax }</td>
					<td>${info.email }</td>
					<td>${info.replyEmail}</td>
					<td>${info.helpWebsite }</td>
					<td>${info.website }</td>
					<td>${info.enabled==1?'有效':'无效'}</td>
					<td>${info.createBy }</td>
					<td>${info.createDate }</td>
					<td>${info.lastModifyBy }</td>
					<td>${info.lastModifyDate }</td>
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