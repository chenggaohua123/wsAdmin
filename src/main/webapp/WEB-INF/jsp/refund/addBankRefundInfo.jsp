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
<title>银行退款</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=path %>/refund/addBankRefundInfo"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
				<div class="tabsContent pageFormContent" layoutH="56">
					<div>
				
						<p>
							<label>流水号：</label> 
							<input name="tradeNo" type="text" size="30" />
						</p>
						<p>
							<label>银行交易币种：</label> 
							<input  type="text" size="30"  name="refundCurrency"/>
						</p>
						<p>
							<label>银行交易金额：</label> 
							<input  type="text" size="30"  name="bankAmount"/>
						</p>
						<p>
							<label>退款金额：</label> 
							<input  type="text" size="30" name="refundAmount" />
						</p>
						<p>
							<label>备注：</label> 
							<input  type="text" size="30"  name="remark"/>
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