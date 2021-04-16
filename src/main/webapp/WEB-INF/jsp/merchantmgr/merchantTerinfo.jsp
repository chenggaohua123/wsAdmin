<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//Ddl HTML 4.01 pansitional//EN" "http://www.w3.org/p/html4/loose.ddl">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户详细信息</title>
</head>
<body>
	<div class="pageContent" layoutH="0">
		<div class="pageFormContent" currentIndex="0" eventType="click"
			style="width: 100%">
			<dl>
				<dt>商户号</dt>
				<dd>${terInfo.merNo }</dd>
			</dl>
			<dl>
				<dt>终端号</dt>
				<dd>${terInfo.terNo }</dd>
			</dl>
			<dl>
				<dt>终端名称</dt>
				<dd>${terInfo.terName}</dd>
			</dl>
			<dl>
				<dt>结算币种</dt>
				<dd>${terInfo.settleCurrency}</dd>
			</dl>
			<dl>
				<dt>交易币种</dt>
				<dd>${terInfo.transCurrency}</dd>
			</dl>
			<dl></dl>
			<dl>
				<dt>是否有效</dt>
				<dd>${funcs:getStringColumnKey('TER_STATUS',terInfo.enabled,'未知状态')}</dd>
			</dl>
			<dl>
				<dt>收款银行</dt>
				<dd>${terInfo.accountAddress }</dd>
			</dl>
			<dl>
				<dt>收款账户名</dt>
				<dd>${terInfo.accountName}</dd>
			</dl>
			<dl>
				<dt>银行账号</dt>
				<dd>${terInfo.accountNo }</dd>
			</dl>
			<dl>
				<dt>账户省份</dt>
				<dd>${terInfo.accountState}</dd>
			</dl>
			<dl>
				<dt>账户城市</dt>
				<dd>${terInfo.accountCity}</dd>
			</dl>
			<dl>
				<dt>账户国家</dt>
				<dd>${terInfo.accountContryCode }</dd>
			</dl>
			<dl>
				<dt>交易安全码</dt>
				<dd>${terInfo.shaKey }</dd>
			</dl>
			<dl>
				<dt>商户渠道</dt>
				<dd>${funcs:getStringColumnKey('MERCHANT_CHANNEL',terInfo.merchantChannel,'未知状态')}</dd>
			</dl>
			<dl>
				<dt>备注</dt>
				<dd>${terInfo.remark }</dd>
			</dl>
		</div>
	</div>
</body>
</html>