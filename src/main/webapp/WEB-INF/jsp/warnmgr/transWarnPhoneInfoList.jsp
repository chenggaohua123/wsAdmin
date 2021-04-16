<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>   

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电话管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
</head>
<body>
 <form id="pagerForm" method="post" action="<%=path %>/warnmgr/listWarnPhoneInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
 </form>
<div class="pageHeader">
	<form rel="pagerForm" id="TransarnForm" onsubmit="return navTabSearch(this);" action="<%=path %>/warnmgr/listWarnPhoneInfo" method="post">
	  <div class="searchBar">
		<ul class="searchContent">
		 <li>
			<label>用户姓名</label>
			<input type="text" name="userName" value="${param.userName }" />
			<input type="hidden" name="systemId" value="1" />
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
		    <li><a class="add" href="<%=path %>/warnmgr/goAddWarnPhoneInfo" target="dialog" width="450" height="300" mask="true" rel="add" mask="true"  ><span>新增</span></a></li>
		    <li><a class="edit" href="<%=path %>/warnmgr/goUpdateWarnPhoneInfo?id={id}" target="dialog" width="450" height="300" rel="update" mask="true"><span>修改</span></a></li>
		    <li><a class="delete" href="<%=path %>/warnmgr/delWarnPhoneInfo?id={id}" target="ajaxTodo" title="请慎重删除,确定删除该参数吗?" mask="true"><span>删除</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	   <table class="list" width="100%" targetType="navTab" layoutH="115">
		<thead>
			<tr>
						<th>姓名</th>
						<th>邮箱</th>
						<th>电话</th>
						<th>关联预警</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="phone">
						<tr  target="id" rel="${phone.id }" align="center">
							<td>${phone.userName }</td>
							<td>${phone.email }</td>
							<td>${phone.phoneNo }</td>
							<td>
								<a target="dialog" title="交易预警信息" width="700" height="400" mask="true" href="<%=path%>/warnmgr/queryWarnTranInfo?warnIds=${phone.warnId }">查看交易预警信息</a>
							</td>
							<td>${phone.createDate }</td>
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