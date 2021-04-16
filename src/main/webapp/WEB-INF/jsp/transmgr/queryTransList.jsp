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
<title>交易修复</title>
</head>
<body>
<style type="text/css">
span{
 font-weight:bold;
}
</style>

<form id="pagerForm" method="post" action="<%=path %>/transmgr/queryTransList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transmgr/queryTransList" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<%-- <li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}" />
		 	</li> --%>
		 	<li>
				<label>流水号：</label>
				<input id="tradeNo" type="text" name="tradeNo" value="${param.tradeNo }" class="required"/>
			</li>
		 </ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" href="<%=path %>/transmgr/updateTrans?tradeNo={sid_tradeNo_orderRespCode}" target="dialog" width="500" height="300"  title="修改交易授权码"  mask="true" warn="请选择一个商户号"><span>修改交易授权码</span></a></li>
			
			<li class="line">line</li>
		</ul>
	</div>
	
	<div id="w_list_print">
	<table class="list" id="tableList"   width="100%" targetType="navTab" layoutH="50" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>订单号</th>
				<th>商户号</th>
				<th>商户交易金额</th>
				<th>商户结算金额</th>
				<th>交易状态</th>
				<th>返回原因</th>
				<th>授权码</th>
				<th>交易时间</th>
				<th>卡种</th>
				<th>卡类型</th>
				<th>卡号</th>
				<th>IP</th>
				<th>邮箱</th>
				<th>交易通道</th>
				<th>是否勾兑</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="sid_tradeNo_orderRespCode" rel="${trans.tradeNo }" >
					<td>${trans.tradeNo }</td>
					<td>${trans.orderNo}</td>
					<td>${trans.merNo }</td>
					<td>${trans.merBusCurrency }  ${trans.merTransAmount }</td>
					<td>${trans.merSettleCurrency } ${trans.merSettleAmount }</td>
					<td >
					<c:if test="${trans.respCode=='00'}">
						<font color="green">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if>
					<c:if test="${trans.respCode=='0000'}">
						<font color="blue">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if>
					<c:if test="${trans.respCode!='00' && trans.respCode!='0000'}">
						<font color="red">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if>
					</td>
					<td>${trans.respMsg }</td>
					<td><span style="color:red;">${trans.autoCode }</span></td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.cardType}</td>
					<td>${trans.cbInfo.card_type}</td>
					<td>${trans.sixAndFourCardNo}</td>
					<td>${trans.ipAddress}</td>
					<td>${trans.email}</td>
					<td>${trans.currencyName }</td>
					<td>
					<c:if test="${trans.ischecked==0 }">
						<font color="black">未勾兑</font>
					</c:if>
					<c:if test="${trans.ischecked==1 }">
						<font color="green">已勾兑</font>
					</c:if>
					<c:if test="${trans.ischecked==2 }">
						<font color="red">勾兑异常</font>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	
	<div class="panelBar" style="height:30px">
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total }条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
</body>
</html>