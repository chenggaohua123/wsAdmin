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
		<form method="post" action="<%=path %>/transInfoLog/insertTransInfoLog"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="tradeNo" value="${info.tradeNo}">
			<input type="hidden" id="transType" name="transType" value="${info.transType}">
			<input type="hidden" id="transCurrency" name="transCurrency" value="${info.merBusCurrency }">
			<input type="hidden" name="id" value="${info.id}">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>流水号：</label>${info.tradeNo}
				</p>
				<p>
					<label>商户号：</label>${info.merNo}
				</p>
				<p>
					<label>终端号：</label>${info.terNo}
				</p>
				<p>
					<label>原始币种：</label>${info.merBusCurrency}
				</p>
				<p>
					<label>原始交易金额：</label>${info.merTransAmount}
				</p>
				<p>
					<label>操作金额：</label><input type="text" name="transMoney" class="required">&nbsp;&nbsp;${info.merBusCurrency }
				</p>
				<p>
					<label>操作原因：</label><input type="text" name="transReason" class="required">
				</p>
				<p>
					<label>备注：</label>
					<textarea rows="3" cols="30" name="remark"></textarea>
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