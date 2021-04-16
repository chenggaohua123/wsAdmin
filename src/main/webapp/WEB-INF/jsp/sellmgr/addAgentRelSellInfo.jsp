<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="funcs" uri="funcs"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加/修改</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=path %>/sellmgr/addAgentRelSellInfo"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
				<div class="tabsContent pageFormContent" layoutH="50">
				<input name="id" value="${info.id }" type="hidden">
					<div>
						<p>
							<label>代理商名称：</label> 
							<input name="agentName" type="text" size="30" class="required" value="${info.agentName }">
						</p>
						<p>
							<label>销售员</label>
							<select class="combox" name="sellBy">
							<c:forEach items="${users }"  var="user">
							<option value="${user.realName }" ${info.sellBy==user.realName?'selected':'' }>${user.realName }</option>
							</c:forEach>
							</select>
						</p>
						<p>
							<label>销售员QQ：</label> 
							<input name="QQ" type="text" size="30" class="required" value="${info.QQ }"/>
						</p>
						<p>
							<label>销售员电话：</label> 
							<input name="phone" type="text" size="30" class="required" value="${info.phone }"/>
						</p>
						</div>
				</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>