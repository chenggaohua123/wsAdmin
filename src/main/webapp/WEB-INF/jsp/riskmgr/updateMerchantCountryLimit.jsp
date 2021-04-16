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
		<form method="post" action="<%=path %>/riskmgr/updateMerchantPayCountLimit"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" value="${info.id}">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>商户号：</label>
					<input type="text" value="${info.merNo }" readonly="readonly">
				</p>
				<p>
					<label>商户号：</label>
					<input type="text" value="${info.terNo }" readonly="readonly">
				</p>
				<p>
					<label>限制类型：</label>
					<select name="type" class="combox">
					<option value="cardNo" ${info.type=='cardNo'?'selected':'' }>卡号</option>
					<option value="email" ${info.type=='email'?'selected':'' }>邮箱</option>
					<option value="IP" ${info.type=='IP'?'selected':'' }>IP</option>
				 </select>
				</p>
				<p>
					<label>限制时间(H)：</label>
					<input type="text" value="${info.limitTime }" readonly="readonly">
				</p>
				<p>
					<label>限制支付成功次数(H)：</label>
					<input type="text" value="${info.limitCount }" readonly="readonly">
				</p>
				<p>
					<label>状态：</label>
					<select name="status" class="combox">
					<option value="1" >审核通过</option>
					<option value="2" >审核驳回</option>
				 </select>
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
								<button type="submit" >审核</button>
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