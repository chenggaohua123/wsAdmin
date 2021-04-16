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
<title>综合信息查询</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/transmgr/listMulTransInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transmgr/listMulTransInfo" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
		 	<li>
		 		<label>交易流水号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
		 	</li>
		 	<li>
		 		<label>商户订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo}"/>
		 	</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>支付状态：</label>
				<select class="combox"  name="respCode" >
				    <option value="">所有</option>
					<option value="00" <c:if test="${param.respCode=='00'}">selected</c:if>>成功</option>
					<option value="01" <c:if test="${param.respCode=='01'}">selected</c:if>>失败</option>
				</select>
			</li>
			<li  class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateEnd}"/>		
			</li>
			<li>
				<label>商户网站：</label>
				<input type="text" name="merURL" value="${param.merURL}"/>
			</li>
		</ul> 
		<ul class="searchContent">
			
				<li  >
			  <label>email</label>
			  <input type="text" name="email" value="${param.email}"/>
			</li>
			<li>
				<label>卡号前六：</label>
				<input type="text" name="cardStart" value="${param.cardStart}"/>
			</li>
			<li>
				<label>卡号后四：</label>
				<input type="text" name="cardEnd" value="${param.cardEnd}"/>
			</li>	
		</ul> 
		<ul class="searchContent">
		<li>
				<label>通道名称：</label>
				<select name="currencyName" class="combox">
				<option value="" ${param.currencyName==''?'selected':'' }>所有</option>
				<c:forEach items="${currencyInfos }" var="currencyInfo">
					<option value="${currencyInfo.id }" ${param.currencyName==currencyInfo.id?'selected':'' }>${currencyInfo.currencyName}</option>
				</c:forEach>
				</select>
			</li>
			<li>
				<label>银行名称：</label>
				<select name="bankName" class="combox">
				<option value="" ${param.bankName==''?'selected':'' }>所有</option>
				<c:forEach items="${bankInfos }" var="bankInfo">
					<option value="${bankInfo.id }" ${param.bankName==bankInfo.id?'selected':'' }>${bankInfo.bankName}</option>
				</c:forEach>
				</select>
			</li>
		
			<li  >
			  <label>支付IP</label>
			  <input type="text" name="ipAddress" value="${param.ipAddress}"/>
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
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="190" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>商户号</th>
				<th>商户订单号</th>
				<th>前六后四卡号</th>
				<th>支付IP</th>
				<th>交易时间</th>
				<th>支付状态</th>
				<th>失败原因</th>
				<th>标签金额</th>
				<th>支付银行</th>
				<th>网站</th>
				<th>email</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.tradeNo }">
					<td>${trans.tradeNo }</td>
					<td>${trans.merNo }</td>
					<td>${trans.orderNo }</td>
					<td>${funcs:decrypt(trans.checkNo) }***${funcs:decrypt(trans.last)}</td>
					<td>${trans.ipAddress }</td>
					<td>${trans.transDate }</td>
					<td>${trans.respCode=='00'?'支付成功':'支付失败'}</td>
					<td>${trans.respMsg }</td>
					<td>${trans.merBusCurrency }&nbsp;${trans.merTransAmount }</td>
					<td>${trans.currencyName }</td>
					<td>${trans.merURL }</td>
					<td>${trans.email }</td>
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