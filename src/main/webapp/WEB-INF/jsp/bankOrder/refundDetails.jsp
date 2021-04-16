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
<title>退款详情信息</title>
</head>
<body>
<style type="text/css">
span{
 font-weight:bold;
}
</style>

<div class="pageHeader" >

</div>

	<%-- <div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/transmgr/exportTransByType?ids=${ids}" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>交易导出</span></a></li>
		</ul>
	</div> --%>
	

	<div class="pageContent"  layoutH="0">
		<div class="tabs" currentIndex="0" eventType="click" style="width:100%">
		<table class="list" width="100%" targetType="navTab" layoutH="40" style="text-align: center;">
		<thead>
			<tr>
				<th>退款流水号</th>
				<th>退款请求号</th>
				<th>支付状态</th>
				<th>退款银行订单号</th>
				<th>退款金额</th>
				<th>退款提交时间</th>
				<th>退款完成时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bankRefundDetails}" var="bankRefundDetail">
				<tr target="bankRefundDetail" rel="${bankRefundDetail.refundId }">
					
					<td>${bankRefundDetail.refundId }</td>
					<td>${bankRefundDetail.requestId}</td>
					<td>
						<c:choose>
				     		<c:when test="${bankRefundDetail.status=='INIT' }">初始化</c:when>
				     		<c:when test="${bankRefundDetail.status=='SUCCESS' }">成功</c:when>
				     		<c:when test="${bankRefundDetail.status=='FAILED' }">失败</c:when>
				     		<c:when test="${bankRefundDetail.status=='PROCESSING' }">处理中</c:when>
				     		<c:otherwise>-</c:otherwise>
				     	</c:choose>
					</td>
					<td>${bankRefundDetail.bankNumber}</td>
					<td>${bankRefundDetail.refundAmount}</td>
					<td>${bankRefundDetail.refundSubmissionTime}</td>
					<td>${bankRefundDetail.refundCompleteTime}</td>
					
					
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
         </div>
  </div>
</div>

</body>
</html>