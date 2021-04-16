<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易查询</title>
</head>
<body>
<style type="text/css">
span{
 font-weight:bold;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	  $("#btnx").click(function(){
			  $("#pageHeaderx").slideToggle();
		  });
	$("#btnx1").click(function(){
		  $("#pageHeader").slideToggle();
	  });
});

</script>
<form id="pagerForm" method="post" action="<%=path %>/faffmgr/listTransCheckedInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/faffmgr/listTransCheckedInfo" method="post">
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
		   <li  class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateStart']}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['transDateEnd']}"/>		
			</li>
			<li  class="dateRange">
			  <label>交易核对日期</label>
			  <input type="text" name="transSettleStart"  id = "transSettleStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transSettleStart']}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transSettleEnd"  id = "transSettleEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['transSettleEnd']}"/>		
			</li>
				<li>
			    <label>勾兑状态：</label>
				<select name="isChecked"  class="combox">
				   <option value="" <c:if test="${param.isChecked==''}">selected</c:if>>所有</option>
				   <option value="0" <c:if test="${param.isChecked=='0'}">selected</c:if>>未勾兑</option>
				   <option value="1" <c:if test="${param.isChecked=='1'}">selected</c:if>>已勾兑</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
			    <label>入账状态：</label>
				<select name="access"  class="combox">
				   <option value="" <c:if test="${param.access==''}">selected</c:if>>所有</option>
				   <option value="0" <c:if test="${param.access=='0'}">selected</c:if>>未入账</option>
				   <option value="1" <c:if test="${param.access=='1'}">selected</c:if>>已入账</option>
				</select>
			</li>
		 	<li>
		 		<label>退款状态：</label>
				<select name="transRefund" class="combox">
					<option value="" ${param.transRefund==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${param.transRefund=='0'}">selected</c:if>>未退款</option>
					<option value="1" <c:if test="${param.transRefund=='1'}">selected</c:if>>已退款</option>
					
				</select>
		 	</li>
		 	<li>
		 		<label>退款核对状态：</label>
				<select name="transRefundCheck" class="combox">
					<option value="" ${param.transRefundCheck==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${param.transRefundCheck=='0'}">selected</c:if>>未核对</option>
					<option value="1" <c:if test="${param.transRefundCheck=='1'}">selected</c:if>>已核对</option>
					
				</select>
		 	</li>
		</ul> 
		
		 <ul class="searchContent">
		 <li>
		 		<label>交易核对状态：</label>
				<select name="transCheck" class="combox">
					<option value="" ${param.transCheck==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${param.transCheck=='0'}">selected</c:if>>未核对</option>
					<option value="1" <c:if test="${param.transCheck=='1'}">selected</c:if>>已核对</option>
					
				</select>
		 	</li>
		 		
		 	<li>
		 		<label>拒付状态：</label>
				<select name="transDishonor" class="combox">
					<option value="" ${param.transDishonor==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${param.transDishonor=='0'}">selected</c:if>>未拒付</option>
					<option value="1" <c:if test="${param.transDishonor=='1'}">selected</c:if>>已拒付</option>
					
				</select>
		 	</li>
		 	<li>
		 		<label>拒付核对状态：</label>
				<select name="transDishonorCheck" class="combox">
					<option value="" ${param.transDishonorCheck==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${param.transDishonorCheck=='0'}">selected</c:if>>未核对</option>
					<option value="1" <c:if test="${param.transDishonorCheck=='1'}">selected</c:if>>已核对</option>
					
				</select>
		 	</li>
		 </ul>
		 	<ul class="searchContent">
			<li>
			    <label>拒付申诉状态：</label>
				<select name="isComp"  class="combox">
				   <option value="" <c:if test="${param.isComp==''}">selected</c:if>>所有</option>
				   <option value="0" <c:if test="${param.isComp=='0'}">selected</c:if>>未申诉</option>
				   <option value="1" <c:if test="${param.isComp=='1'}">selected</c:if>>已申诉</option>
				</select>
			</li>
		 	<li>
		 		<label>特殊拒付状态：</label>
				<select name="isSp" class="combox">
					<option value="" ${param.isSp==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${param.isSp=='0'}">selected</c:if>>非特殊拒付</option>
					<option value="1" <c:if test="${param.isSp=='1'}">selected</c:if>>特殊拒付</option>
					
				</select>
		 	</li>
		</ul>
		 <ul class="searchContent">
		 	<li>
		 		<label>银行：</label>
				 <select class="combox" selectedValue="${param.bankId }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
				</select>
		 	</li>
		 	<li>
		 		<label>通道：</label>
		 		<input name="currencyName" type="text"  id="currency.currencyName" value="${param.currencyName }" />
                <input name="currencyId" type="hidden" id="currency.currencyId" value="${param.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
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
		<li><a class="add" href="<%=path %>/faffmgr/exportTransCheckedInfo" target="dwzExport" targetType="navTab"rel="addCurrency11111" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
	<div class="pageHeader" id="pageHeaderx" style="display: block;">
	<div class="searchBar" >
		<ul class="searchContent" >
			<li>
			<span>银行交易金额: 
			<c:forEach items="${list }" var="info">
			${info.currency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.totalAmount }"/> <font color="red"> | </font>
			</c:forEach>
			</span> 
			</li>
			<li>
			<span>银行结算金额: <c:forEach items="${list }" var="info">
			${info.currency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.salesTotalAmount }"/><font color="red"> | </font>
			</c:forEach></span>
			</li>
			<li>
			<span>银行手续费金额:<c:forEach items="${list }" var="info">
			${info.currency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.salesTotalFee }"/><font color="red"> | </font>
			</c:forEach></span>
			</li>
						
		</ul>
	</div>
	
	
		<div class="searchBar" >
				<ul class="searchContent" >
				<li><span>银行退款金额: 	
					<c:forEach items="${list }" var="info">
					${info.currency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.refundSettleAmount }"/><font color="red"> | </font>
					</c:forEach>
				</span> 
				<li><span>银行退款未扣款金额: 	
						<c:forEach items="${list }" var="info">
						${info.currency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.refundAmount+info.refundSettleAmount }"/><font color="red"> | </font>
						</c:forEach>
				</span> 
			</ul>
		</div>
		<div class="searchBar" >
				<ul class="searchContent" >
				<li><span>银行拒付金额: 	
					<c:forEach items="${list }" var="info">
					${info.currency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.disSettleAmount }"/><font color="red"> | </font>
					</c:forEach>
				</span> 
				<li><span>银行拒付未扣款金额: 	
					<c:forEach items="${list }" var="info">
					${info.currency }&nbsp;<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.disAmount+info.disSettleAmount }"/><font color="red"> | </font>
					</c:forEach>
				</span> 
			</ul>
		</div>
	</div>
	<div id="w_list_print">
	<table class="list"  width="100%" targetType="navTab" layoutH="330" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>流水号</th>
				<th>交易时间</th>
				<th>交易状态</th>
				<th>银行交易金额</th>
				<th>银行结算金额</th>
				<th>银行手续费</th>
				<th>银行到账日期</th>
				<th>退款状态</th>
				<th>退款详情</th>
				<th>退款数据核对状态</th>
				<th>银行退款扣款详情</th>
				<th>拒付状态</th>
				<th>拒付详情</th>
				<th>拒付数据核对状态</th>
				<th>银行拒付扣款详情</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.tradeNo }" >
					<td>${trans.merNo }</td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${trans.tradeNo }"> ${trans.tradeNo }</a></td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td >
					<c:if test="${trans.respCode=='00'}">
						<font color="green">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if><c:if test="${trans.respCode!='00'}">
						<font color="red">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if>
					</td>
					<td>${trans.bankCurrency }  ${trans.bankTransAmount }</td>
					<td>${trans.bankSettleCurrency } ${trans.salesSettleAmount }</td>
					<td>${trans.bankSettleCurrency } ${trans.salesSettleFee }</td>
					<td>${funcs:formatDate(trans.settleDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.transRefund=='0'?'未退款':'已退款' }</td>
					<td>
					<c:choose>
					<c:when test="${trans.transRefund!='0'}">
					<a target="dialog" title="退款明细" width="900" height="520" mask="true" href="<%=path %>/faffmgr/refundDescInfo?tradeNo=${trans.tradeNo }&transType=refund">退款详情</a>
					</c:when>
					<c:otherwise></c:otherwise>
					</c:choose>
					</td>
					<td>${(!(empty trans.refundSettleAmount))&&trans.refundSettleAmount != 0.0 ?'已核对':'未核对'}</td>
					<td> 
					<c:choose>
					<c:when test="${(!(empty trans.refundSettleAmount))&&trans.refundSettleAmount != 0.0}">
					<a target="dialog" title="银行退款扣款详情" width="900" height="520" mask="true" href="<%=path %>/faffmgr/bankCutPaymentDescInfo?tradeNo=${trans.tradeNo }&settleType=refund">银行退款扣款详情</a>
					</c:when>
					<c:otherwise></c:otherwise>
					</c:choose>
					</td>
					<td> ${trans.transDishonor==0.0?'未拒付':'已拒付'}</td>
					<td>
					<c:choose>
					<c:when test="${trans.transDishonor !=0}">
					<a target="dialog" title="拒付详情" width="900" height="520" mask="true" href="<%=path %>/faffmgr/refundDescInfo?tradeNo=${trans.tradeNo }&transType=dishonor">拒付详情</a>
					</c:when>
					<c:otherwise></c:otherwise>
					</c:choose>
					</td>
					<td>${(!(empty trans.disSettleAmount))&&trans.disSettleAmount != 0.0 ?'已核对':'未核对'}</td>
					<td> 
					<c:choose>
					<c:when test="${(!(empty trans.disSettleAmount))&&trans.disSettleAmount != 0.0}">
					<a target="dialog" title="银行拒付扣款详情" width="900" height="520" mask="true" href="<%=path %>/faffmgr/bankCutPaymentDescInfo?tradeNo=${trans.tradeNo }&settleType=dis">银行拒付扣款详情</a>
					</c:when>
					<c:otherwise></c:otherwise>
					</c:choose>
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