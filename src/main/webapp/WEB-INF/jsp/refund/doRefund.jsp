<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%> 
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
		<form method="post" id="formid" action="<%=path %>/refund/doRefund"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" value="${refund.id }"> 
			<input type="hidden" name="busAmount" value="${refund.busAmount }">
			<input type="hidden" name="busCurrency" value="${refund.busCurrency }"> 
			<input type="hidden"name="refundAmount" value="${refund.refundAmount }"> 
			<input type="hidden" name="refundCurrency" value="${refund.refundCurrency }">
			<input type="hidden" name="transLogId" value="${refund.transLogId }">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>流水号：</label> <input name="tradeNo" type="text" size="30"
						class="required" value="${refund.tradeNo }" readonly="readonly" />
				</p>
				<p>
					<label>商户号：</label> <input name="merNo" type="text" size="30"
						class="required" value="${refund.merNo }" readonly="readonly" />
				</p>
				<p>
					<label>终端号：</label> <input name="terNo" type="text" size="30"
						class="required" value="${refund.terNo }" readonly="readonly" />
				</p>
				<p>
					<label>交易金额：</label> ${refund.busCurrency }&nbsp;&nbsp;${refund.busAmount }
				</p>
				<p>
					<label>退款金额：</label> ${refund.refundCurrency }&nbsp;&nbsp;${refund.refundAmount }
				</p>
				<p>
					<label>退款原因：</label> <input name="refundReason" type="text"
						size="30" class="required" value="${refund.refundReason }"
						readonly="readonly" />
				</p>
				<p>
					<label>申请退款时间：</label> <input name="applyDate" type="text"
						size="30" class="required" value="${refund.applyDate }"
						readonly="readonly" />
				</p>
				<p>
					<label>状态：</label>
					<select name="status">
						<option value="2">退款成功</option>
						<option value="4">退款失败</option>
						<option value="1">退款取消</option>
					</select>
				</p>
				<p>
					<label>备注：</label>
					<textarea rows="3" cols="30" name="remark" readonly="readonly">${refund.remark}</textarea>
				</p>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">确认
								</button>
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