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
<title>修改</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=path %>/sellmgr/updateSellRefMerNo"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" value="${info.id }"> 
				<div class="tabsContent pageFormContent" layoutH="50">
					<div>
						<p>
							<label>用户名</label>
							<input type="text" name="userName"  value="${info.userName }" readonly="readonly"> 
						</p>
						<p>
						
							<label>商户号：</label> 
							<textarea rows="7" cols="100" class="required" name="merNo" id="merNo" >${info.merNo }</textarea>
						</p>
						<br><br><br>
						商户号用英文逗号隔开 例如::1888,2888
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