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
<title>退拒付详情信息</title>
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
				<th>拒付流水号</th>
				<th>拒付银行订单号</th>
				<th>拒付金额</th>
				<th>拒付完成时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bankRefuseDetails}" var="bankRefuseDetail">
				<tr target="bankRefuseDetails" rel="${bankRefuseDetail.refuseId }">
					<td>${bankRefuseDetail.refuseId}</td>
					<td>${bankRefuseDetail.bankNumber}</td>
					<td>${bankRefuseDetail.refuseAmount}</td>
					<td>${bankRefuseDetail.refuseCompleteTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
         </div>
  </div>
</div>

</body>
</html>