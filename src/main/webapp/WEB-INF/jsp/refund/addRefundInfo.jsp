<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="funcs" uri="funcs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退款审核</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=path %>/refund/addRefundInfo"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>流水号：</label>
					<input type="text" name="tradeNo" class="required">
				</p>
				<p>
					<label>退款金额：</label>
					<input type="text" name="refundAmount" class="required">
				</p>
				<p>
					<label>退款原因：</label>
					<textarea rows="3" cols="30" name="refundReason"></textarea>
				</p>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" >审核提交</button>
							</div>
						</div>
					</li>
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