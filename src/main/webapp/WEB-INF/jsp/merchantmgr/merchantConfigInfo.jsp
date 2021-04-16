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
<title>查看配置信息</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	   <table class="list" width="98%" targetType="dialog" layoutH="0">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>配置名称</th>
				<th>配置的KEY</th>
				<th>配置值</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="config">
				<tr align="center">
					<td>${config.merNo }</td>
					<td>${config.terNo }</td>
					<td>${config.configName }</td>
					<td>${config.configKey}</td>
					<td>${config.configValue}</td>
					<td>${config.createBy} </td>
					<td>${funcs:formatDate(config.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${config.remark }</td>
					<td>
					 <a title="删除" target="ajaxTodo" href="<%=path %>/merchantmgr/deleteMerchantConfig?id=${config.id}" rel="queryConfig" callback="dialogAjaxDone" class="btnDel">删除</a>
					 <a title="编辑" mask="true"  href="<%=path %>/merchantmgr/goUpdateMerchantConfig?id=${config.id}" target="dialog" rel="updateConfig" width="650" height="450"  title="修改商户终端配置"  class="btnEdit">编辑</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    </div>
</div>	
</body>
</html>