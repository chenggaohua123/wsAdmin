<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//Dlabel HTML 4.01 pansitional//EN" "http://www.w3.org/p/html4/loose.dlabel">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看扣款类型</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="20">
		<thead>
			<tr>
				<th>扣款类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="config">
				<tr align="center">
					<td>${config.deductionType }</td>
					<td>
					 <a title="删除" target="ajaxTodo" href="<%=path %>/settlemgr/deleteDeductionType?id=${config.id}" rel="queryConfig" callback="dialogAjaxDone" class="btnDel">删除</a>
					 <a title="编辑" mask="true"  href="<%=path %>/settlemgr/goUpdateDeductionType?id=${config.id}" target="dialog" rel="updateConfig" width="450" height="250"  title="修改通道配置"  class="btnEdit">编辑</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
  </div>
</div>
</body>
</html>