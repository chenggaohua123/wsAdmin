<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>交易变更--退款</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
	<script type="text/javascript">
	$(function(){
		var transType = "${info.transType}";
		var transMoney = "${info.transInfoLog.transMoney}";
		if('thaw' == transType){
			$("#transMoney").val(transMoney);
			$("#transMoney").attr("readonly", "readonly");
		}
	});
	
	</script>
	<body>
		<div id="content">
		<form method="post" id="formid" action="<%=path %>/transInfoLog/insertTransInfoLog" class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" id="tradeNo" name="tradeNo" value="${info.tradeNo}">
			<input type="hidden" id="transType" name="transType" value="${info.transType}">
			<input type="hidden" id="id" name="id" value="${info.transInfoLog.id}">
			<input type="hidden" id="status" name="status" value="0">
			<input type="hidden" id="transCurrency" name="transCurrency" value="${info.merBusCurrency }">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>商户号：</label>
					${info.merNo}
				</p>
				<p>
					<label>流水号：</label>
					${info.tradeNo}
				</p>
				<p>
					<label>交易时间：</label>
					${info.transDate }
				</p>
				<p>
					<label>交易金额：</label>
					${info.merTransAmount} &nbsp;${info.merBusCurrency}
				</p>
				<p>
					<label>操作类型：</label>
					<c:if test="${info.transType == 'refund'}">
						退款
					</c:if>
					<c:if test="${info.transType == 'dishonor'}">
						拒付
					</c:if>
					<c:if test="${info.transType == 'frozen'}">
						冻结
					</c:if>
					<c:if test="${info.transType == 'thaw'}">
						解冻
					</c:if>
					<c:if test="${info.transType == 'check'}">
						审核
					</c:if>
				</p>
				<p>
					<label>操作金额：</label>
					<font><input type="text" name="transMoney" id="transMoney" value="">${info.merBusCurrency }</font>
				</p>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									提交审核
								</button>
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