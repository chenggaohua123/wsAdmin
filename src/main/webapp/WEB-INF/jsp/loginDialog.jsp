<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
%>

<div class="pageContent">
	
	<form method="post" action="<%=path %>/doDialogLogin" class="pageForm" onsubmit="return validateCallback(this, dialogAjaxDone)">

		<div class="pageFormContent" layoutH="57">
			<p>
				<label>用户名：</label>
				<input type="text" name="userName" size="20" class="login_input" />
			</p>
			<p>
				<label>密码：</label>
				<input type="password" name="passWord" size="20" class="login_input" />
			</p>
			<p>
				<label>动态码：</label>
				<input type="password" name="vilidateCode" size="20" class="login_input" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">Login</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">Close</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
