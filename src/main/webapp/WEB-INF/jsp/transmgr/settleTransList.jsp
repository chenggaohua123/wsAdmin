<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已结算交易查询</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/transmgr/getSettleTransList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transmgr/getSettleTransList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			<li>
				<label>参考号：</label>
				<input type="text" name="relNo" value="${param.relNo}"/>
			</li>
			<li>
			    <label>消费类型：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=gw_transtype_info" relValue="columnKey" selectedValue="${param.transType }" relText="columnvalue" name="transType">
					<option value="">所有</option>
				</select>
			</li>
			
			<li>
			   <label>商户号</label>
			   <input type="text" name="merNo" value="${param.merNo}"/>
			</li>
		</ul>
		<ul class="searchContent">
		   <li>
			   <label>终端号</label>
			   <input type="text" name="terNo" value="${param.terNo}"/>
			</li>
		   <li class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateEnd}"/>		
			</li>
			<li  class="dateRange">
			  <label>结算日期</label>
			  <input type="text" name="settleDateStart"  id = "settleDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.settleDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="settleDateEnd"  id = "settleDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.settleDateEnd}"/>		
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
			<li><a class="add" href="<%=path %>/transmgr/exportSettleTrans" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>交易导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="143" style="text-align: center;">
		<thead>
				<tr >
					<th align="left" colspan="18">
						  <c:forEach items="${settleTransAmount}" var="settleTransAmount">
						   <c:if test="${settleTransAmount.total != 0 }">
							   	 ${funcs:getStringColumnKey('gw_transtype_info',settleTransAmount.transType ,'未知状态')}:
							   	 ${settleTransAmount.total}
							</c:if>
						  </c:forEach>
						</th>
					</tr>
			    <tr>
				<th>流水号</th>
				<th>参考号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>交易类型</th>
				<th>商户交易币种</th>
				<th>商户交易金额</th>
				<th>银行交易币种</th>
				<th>银行交易金额</th>
				<th>商户结算币种</th>
				<th>商户结算金额</th>
				<th>商户手续费</th>
				<!-- <th>代理商手续费</th>
				<th>父级代理商手续费</th> -->
				<th>返回码</th>
				<th>交易时间</th>
				<th>交易通道</th>
				<th>收单机构</th>
				<th>卡类型</th>
				<th>发卡行</th>
				<th>卡名称</th>
				<th>结算日期</th>
				<th>结算批次号</th>
				<!-- <th>明细导出</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.tradeNo }">
					<td><a target="dialog" title="已结算交易明细" width="900" height="550" mask="true" href="<%=path %>/transmgr/querySettleTransDetailByTradeNo?tradeNo=${trans.tradeNo }"> ${trans.tradeNo }</a></td>
					<td><a target="dialog" title="已结算历史状态变更" width="1150" height="500" mask="true" href="<%=path %>/transmgr/querySettleTransDetailByRelNo?relNo=${trans.relNo }">${trans.relNo }</a></td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${funcs:getStringColumnKey('gw_transtype_info',trans.transType,'未知状态')}</td>
					<td>${trans.merBusCurrency }</td>
					<td>${trans.merTransAmount }</td>
					<td>${trans.bankCurrency }</td>
					<td>${trans.bankTransAmount }</td>
					<td>${trans.merSettleCurrency }</td>
					<td>${trans.merSettleAmount }</td>
					<td>${trans.merForAmount }</td>
					<%-- <td>${trans.agentForAmount }</td>
					<td>${trans.parentAgentForAmount }</td> --%>
					<td>${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.currencyName }</td>
					<td>${trans.acquiringBank }</td>
					<td>${trans.cardType }</td>
					<td>${trans.bankName }</td>
					<td>${trans.cardName }</td>
					<td>${trans.settleDate }</td>
					<td>${trans.settleBatchNo}</td>
				<%-- 	<td><a href="<%=path %>/transmgr/exportTransInfo?merNo=${trans.merNo}&terNo=${trans.terNo}&settleBatchNo=${trans.settleBatchNo}" rel="addCurrency">明细结算导出</a></td>
			 --%>	</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" >
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