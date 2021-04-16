<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<title>修改</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=path %>/sellmgr/updateSellRefSells"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
				<div class="tabsContent pageFormContent" layoutH="50">
					<div>
						<p>
							<label>主管：</label>
							<select class="combox" name="sellMgr">
								<option value="${sellMgr }">${sellMgrRealName}</option>
							</select>
						</p>
						<p>
							<label>员工：</label> 
							<c:forEach items="${users }"  var="user">
								<input type="checkbox" name="sells" value="${user.userName }" ${fn:contains(sells,user.userName)?'checked="true"':'' }>
								${user.realName }
							</c:forEach>
						</p>
						<br><br><br>
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