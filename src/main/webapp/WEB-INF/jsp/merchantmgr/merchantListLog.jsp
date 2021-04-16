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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户历史记录列表</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="0">
		<thead>
			<tr>
				<th>商户号</th>
				<th>商户名称</th>
				<th>商户联系人</th>
				<th>商户电话</th>
				<th>商户邮箱</th>
				<th>QQ号</th>
				<th>商户地址</th>
				<th>开户状态</th>
				<th>激活状态</th>
				<th>直连状态</th>
				<th>通道类型</th>
				<th>商户类别</th>
				<th>合同生效日期</th>
				<th>合同失效信息</th>
				<th>销售员</th>
				<th>OA业务单号</th>
				<th>实收历史费用</th>
				<th>备注信息</th>
				<th>修改人</th>
				<th>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="merchant">
				<tr target="sid_role" rel="${merchant.id }"    align="center">
					<td>${merchant.merNo }</td>
					<td>${merchant.merchantName }</td>
					<td>${merchant.linkName }(${merchant.linkPhoneNo })</td>
					<td>${merchant.phoneNo }</td>
					<td>${merchant.email }</td>
					<td>${merchant.qq }</td>
					<td>${merchant.address }</td>
					<td>${funcs:getStringColumnKey('MERCHANTSTATUS',merchant.enabled,merchant.enabled)}</td>
					<td>${funcs:getStringColumnKey('MERCHANT_ACCOUNT_STATUS',merchant.accountStatus,merchant.accountStatus)}</td>
					<td>${funcs:getStringColumnKey('MERCHANT_DIR_STATUS',merchant.dirStatus,merchant.dirStatus)}</td>
					<td>${funcs:getStringColumnKey('MERCHNAT_CURRENCY_TYPE',merchant.merCurrencyType,merchant.merCurrencyType)}</td>
					<td>${funcs:getStringColumnKey('MERCHANTTYPE',merchant.type,merchant.type)}</td>
					<td>${funcs:formatDate(merchant.activationDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${funcs:formatDate(merchant.expireDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${merchant.sales }</td>
					<td>${merchant.oaOrderNo }</td>
					<td>${merchant.topRate }</td>
					<td>${merchant.remark }</td>
					<td>${merchant.updatePerson }</td>
					<td>${funcs:formatDate(merchant.updateDate,'yyyy-MM-dd HH:mm')} </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>