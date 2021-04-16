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
<title>重置密码</title>
</head>
<body>
	<div class="pageContent" >
        <div id="w_list_print">
			<table class="list" width="100%" targetType="navTab" layoutH="0" style="text-align: center;">
				<thead>
					<tr>
						<th>商户号</th>
						<th>用户名</th>
						<th>是否有效</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="user">
						<tr target="sid_role" rel="${user.id }" align="center">
							<td>${user.merNo }</td>
							<td>${user.userName }</td>
							<td> ${funcs:getStringColumnKey('USERSTATUS',user.enabled,user.enabled)}</td>
							<td><a title="重置密码" mask="true"  href="<%=path %>/merchantmgr/reSetPass?id=${user.id}" target="dialog" rel="updateConfig" width="600" height="200"  title="重置密码"  class="btnEdit">重置密码</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
     <div class="formBar">
         <ul>
             <li>
                 <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
             </li>
         </ul>
     </div>
</div>
</body>
</html>