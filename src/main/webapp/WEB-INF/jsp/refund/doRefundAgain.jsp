<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>再次退款</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=path %>/transInfoLog/doRefundAgain"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" value="${info.id }">
			<input type="hidden" name="tradeNewNo" value="${info.tradeNewNo }">
			<input type="hidden" name="transMoney" value="${info.refundAmount }">
				<div class="tabsContent pageFormContent" layoutH="56">
					<div>
				
						<p>
							<label>流水号：</label> 
							<input name="tradeNo" type="text" size="30" value="${info.tradeNo }" readonly="readonly"/>
						</p>
						<p>
							<label>标签金额：</label> 
							<input type="text" size="30" value="${info.currency } ${info.transAmount }" readonly="readonly"/>
						</p>
						<p>
							<label>标签退款金额：</label> 
							<input  type="text" size="30" value="${info.currency } ${info.refundAmount }" readonly="readonly"/>
						</p>
						<p>
							<label>银行交易金额：</label> 
							<input  type="text" size="30" value="${info.bankTransAmount }" name="bankTransAmount"/>
						</p>
						<p>
							<label>银行退款金额：</label> 
							<input  type="text" size="30" value="${info.bankRefundTransAmount }" name="bankRefundTransAmount"/>
						</p>
						<p>
							<label>银行商户号：</label> 
							<input  type="text" size="30"  name="mid"/>
						</p>
						<p>
							<label>key：</label> 
							<input  type="text" size="30"  name="key"/>
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