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
<title>银行真实返回原因</title>
</head>
<body>
<script type="text/javascript">
	function initialization(){
		$(".pageHeader form div ul li:not(.dateRange) input").val('');
		$(".pageHeader form div ul li:not(.dateRange) select[name='bankId']").val("0");
		$(".pageHeader form div ul li:not(.dateRange)  a[name='bankId']").attr("value","77");
		$(".pageHeader form div ul li:not(.dateRange)  a[name='bankId']").html("所有");
	}
</script>
<form id="pagerForm" method="post" action="<%=path %>/sysmgr/queryBankRealRespMsg">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sysmgr/queryBankRealRespMsg" method="post">
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
		 		<label>商户订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo}"/>
		 	</li>
		 	<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
		 	
		</ul>
		<ul class="searchContent">
			<li>
		 		<label>银行：</label>
				 <select  class="combox" selectedValue="${param.bankId }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
				</select>
		 	</li>
			<li>
		 		<label>通道：</label>
		 		<input name="currencyName" type="text"  id="currency.currencyName" value="${param.currencyName }" />
                <input name="currencyId" type="hidden" id="currency.currencyId" value="${param.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="820" height="555"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
		 	</li>
			<li  class="dateRange">
			  <label>交易时间</label>
			  <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startDate']}"/>
		       <span class="limit">-</span>
			  <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['endDate']}"/>		
			</li>
		</ul> 
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="initialization()">重置</button></div></div></li>
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
	<table class="list" width="100%" targetType="navTab" layoutH="145" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>交易通道</th>
				<th>交易银行</th>
				<th>商户订单号</th>
				<th>交易时间</th>
				<th>金额 </th>
				<th>交易状态</th>
				<th>银行返回结果</th>
				<th>自定义结果</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.tradeNo }">
					<td>${trans.tradeNo }</td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${trans.currencyName}</td>
					<td>${trans.bankName}</td>
					<td>${trans.orderNo }</td>
					<td>${trans.transDate }</td>
					<td>${trans.merBusCurrency } ${trans.merTransAmount }</td>
					<%-- <td>${trans.respCode=='00'?'交易成功':'交易失败' }</td> --%>
					<td>
						<c:if test="${trans.respCode=='00'}">交易成功</c:if>
						<c:if test="${trans.respCode=='0000'}">预支付成功</c:if>
						<c:if test="${trans.respCode!='00' && trans.respCode!='0000'}">交易失败</c:if>
					</td>
					<td>${trans.bankRespMsg }</td>
					<td>${trans.respMsg }</td>
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