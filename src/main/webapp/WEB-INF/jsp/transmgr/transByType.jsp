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
<title>根据类型查询交易数据</title>
</head>
<body>
<style type="text/css">
span{
 font-weight:bold;
}
</style>

<div class="pageHeader" >

</div>

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/transmgr/exportTransByType?ids=${ids}" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>交易导出</span></a></li>
		</ul>
	</div>
	

	<div class="pageContent"  layoutH="0">
<div class="tabs" currentIndex="0" eventType="click" style="width:100%">
		<table class="list" width="100%" targetType="navTab" layoutH="40" style="text-align: center;">
		<thead>
					  <%-- <c:forEach items="${Totallist}" var="totallist">
					   <c:if test="${totallist.total != 0}">
						   	 ${funcs:getStringColumnKey('gw_transtype_info',totallist.transType ,'未知状态')}:${totallist.total} 
						   	 
					   	</c:if>
					  </c:forEach> --%>
			<tr>
				<th>流水号</th>
				<th>订单号</th>
				<th>商户号</th>
				<th>交易时间</th>
				<th>商户交易金额</th>
				<th>返回码</th>
				<th>欺诈分数</th>
				<th>卡号</th>
				<th>IP</th>
				<th>邮箱</th>
				<th>网站</th>
				<th>支付通道</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="trans">
				<tr target="tradeNo" rel="${trans.tradeNo }">
					
					<td>${trans.tradeNo }</td>
					<td>${trans.orderNo}</td>
					<td>${trans.merNo }</td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.merBusCurrency }  ${trans.merTransAmount }</td>
					<td>${trans.respMsg}</td>
					<td>${trans.riskScore}</td>
					<td>${trans.sixAndFourCardNo}</td>
					<td>${trans.ipAddress}</td>
					<td>${trans.email}</td>
					<td>${trans.payWebSite}</td>
					<td>${trans.currencyName }</td>
					
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
         </div>
  </div>
</div>

</body>
</html>