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
<title>通道列表</title>
</head>
<body>
<style type="text/css">
span{
 font-weight:bold;
}
</style>
<form id="pagerForm" method="post" action="<%=path %>/transchangemgr/listTransHighRiskRerunInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transchangemgr/listTransHighRiskRerunInfo" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
		 	<li>
		 		<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
		 	</li>
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
		</ul>
		<ul class="searchContent">
		 	<li>
		 		<label>返回码：</label>
				<select class="combox"  name="respCode" >
				    <option value="">请选择</option>
					<option value="00" <c:if test="${param.respCode=='00'}">selected</c:if>>成功</option>
					<option value="01" <c:if test="${param.respCode=='01'}">selected</c:if>>失败</option>
				</select>
		 	</li>
			<li>
			    <label>消费类型：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=gw_transtype_info" relValue="columnKey" selectedValue="${param.transType==null ?'sales':param.transType }" relText="columnvalue" name="transType" >
					<option value="">所有</option>
				</select>
			</li>
			<li>
			    <label>是否对账：</label>
				<select name="checkedBatchNo"  class="combox">
				   <option value="">请选择</option>
				   <option value="1" <c:if test="${param.checkedBatchNo=='1'}">selected</c:if>>已对账</option>
				   <option value="2" <c:if test="${param.checkedBatchNo=='2'}">selected</c:if>>未对账</option>
				</select>
			</li>
		</ul> 
		<ul class="searchContent">
		   <li  class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateEnd}"/>		
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
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="170" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>商户号</th>
				<th>商户名称</th>
				<th>终端号</th>
				<th>交易类型</th>
				<th>商户交易金额</th>
				<th>银行交易金额</th>
				<th>商户结算金额</th>
				<th>商户手续费</th>
				<th>保证金</th>
				<th>返回码</th>
				<th>交易时间</th>
				<th>交易通道</th>
				<th>失败原因</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.tradeNo }">
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${trans.tradeNo }"> ${trans.tradeNo }</a></td>
					<td>${trans.merNo }</td>
					<td>${trans.merchantName }</td>
					<td>${trans.terNo }</td>
					<td>${funcs:getStringColumnKey('gw_transtype_info',trans.transType,'未知状态')}</td>
					<td>${trans.merBusCurrency }  ${trans.merTransAmount }</td>
					<td>${trans.bankCurrency } ${trans.bankTransAmount }</td>
					<td>${trans.merSettleCurrency } ${trans.merSettleAmount }</td>
					<td>${trans.merForAmount }</td>
					<td>${trans.bondAmount}</td>
					<td>
					<c:if test="${trans.respCode=='00'}">
						<font color="green">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if><c:if test="${trans.respCode!='00'}">
						<font color="red">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if>
					</td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.currencyName }</td>
					<td>${trans.respMsg }</td>
					<td>
						<c:if test="${trans.reRunStatus==1 }">
							<a class="edit" href="<%=path %>/transchangemgr/goRerunTransHighRiskRerunInfo?tradeNo=${trans.tradeNo}" target="dialog" width="550" height="350" mask="true"  rel="settleType"  warn="请选择修改的数据"><span>再次支付</span></a>
						</c:if>
						<c:if test="${trans.reRunStatus==0 }">
						<font color="red">超时</font>
						</c:if>
					</td>
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