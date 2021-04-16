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
<title>查看终端历史记录</title>
</head>
<body>
<div class="pageContent">
	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" layoutH="20">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>商户名称</th>
				<th>交易安全码</th>
				<th>交易币种</th>
				<th>结算币种</th>
				<th>收款账号</th>
				<th>收款账户名</th>
				<th>收款银行</th>
				<th>银行所在省</th>
				<th>银行所在市</th>
				<th>是否有效</th>
				<th>商户渠道</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>修改人</th>
				<th>修改时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="terInfo">
				<tr align="center">
					<td><a  href="<%=path %>/merchantmgr/queryTerInfo?id=${terInfo.id }" target="dialog" width="850" height="350" mask="true" title="终端详细信息">${terInfo.merNo }</a></td>
					<td>${terInfo.terNo }</td>
					<td>${terInfo.merchantName }</td>
					<td>${terInfo.shaKey }</td>
					<td>${terInfo.transCurrency }</td>
					<td>${terInfo.settleCurrency }</td>
					<td>${terInfo.accountNo }</td>
					<td>${terInfo.accountName }</td>
					<td>${terInfo.accountAddress }</td>
					<td>${terInfo.accountState }</td>
					<td>${terInfo.accountCity }</td>
					<td>${funcs:getStringColumnKey('TER_STATUS',terInfo.enabled,terInfo.enabled)}</td>
					<td>${funcs:getStringColumnKey('MERCHANT_CHANNEL',terInfo.merchantChannel,terInfo.merchantChannel)}</td>
					<td>${funcs:formatDate(terInfo.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${terInfo.createby } </td>
					<td>${terInfo.updateBy } </td>
					<td>${funcs:formatDate(terInfo.updateDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${terInfo.remark } </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
  </div>
</div>
</body>
</html>