<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	function exportSelectTransInfoLog(){
		var param='?';
		$("#selectTransInfoLogListForm input,select").each(function(){
			if($(this).attr("name")!=undefined){
				param+=$(this).attr("name")+"="+$(this).val()+"&";
			}
		});
		window.location.href="<%=path %>/transInfoLog/exportTransLogList"+param;
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易变更</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/transInfoLog/selectTransInfoLogList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="selectTransInfoLogListForm">
	<form rel="pagerForm" id="refundFromId" onsubmit="return navTabSearch(this);" action="<%=path %>/transInfoLog/selectTransInfoLogList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
			</li>
			<li>
				<label>商户订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo }"/>
			</li>
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li class="dateRange">
				<label>申请时间：</label>
				<input type="text" name="startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.startDate}"/>
			       <span class="limit">-</span>
				  <input type="text" name="endDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.endDate}"/>	
			</li>
			<li class="dateRange">
				<label>变更时间：</label>
				<input type="text" name="transDateLogStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateLogStart']}"/>
			       <span class="limit">-</span>
				  <input type="text" name="transDateLogEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateLogEnd']}"/>	
			</li>
			<li>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>异常类型：</label>
				<select name="transType" class="combox">
					<option value="" ${param.transType==''?'selected':'' }>-- 全部 --</option>
					<option value="refund" ${param.transType=='refund'?'selected':'' }>退款</option>
					<option value="thaw" ${param.transType=='thaw'?'selected':'' }>解冻</option>
					<option value="frozen" ${param.transType=='frozen'?'selected':'' }>冻结</option>
					<option value="dishonor" ${param.transType=='dishonor'?'selected':'' }>拒付</option>
				</select>
			</li>
			<li>
				<label>异常状态：</label>
				<select name="status" class="combox">
					<option value="" ${param.status==''?'selected':'' }>-- 全部 --</option>
					<option value="0" ${param.status=='0'?'selected':'' }>待审核</option>
					<option value="1" ${param.status=='1'?'selected':'' }>审核驳回</option>
					<option value="2" ${param.status=='2'?'selected':'' }>审核通过</option>
					<option value="3" ${param.status=='3'?'selected':'' }>提交退款管理</option>
				</select>
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
			<li><a class="add" href="javascript:exportSelectTransInfoLog()" ><span>异常单导出</span></a></li>
				<li><a class="add" href="<%=path %>/transInfoLog/goUploadTradeNo" target="dialog" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>拒付单导出</span></a></li>
			</ul>
		</div>
		<div id="w_list_print">
			<table class="list" width="100%" targetType="navTab" layoutH="166" style="text-align: center;">
				<thead>
					<tr>
						<th>流水号</th>
						
						<th>商户号</th>
						<th>商户交易金额</th>
						<th>交易时间</th>
						<th>异常状态</th>
						
						<th>异常金额</th>
						<th>异常申请人</th>
						<th>异常申请时间</th>
						<th>异常处理人</th>
						<th>异常处理时间</th>
						<th>退款状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data}" var="order">
						<tr target="tradeNo" rel="${order.tradeNo }" align="center">
							<td>${order.tradeNo}</td>
							
							<td>${order.merNo}</td>
							<td>${order.merBusCurrency }  ${order.merTransAmount }</td>
							<td>${order.transDate }</td>
							<td>${funcs:getStringColumnKey('TRANS_LOG_TYPE',order.transType,'未知状态')}${funcs:getStringColumnKey('CHECK_STATUS',order.status,'未知状态')}</td>
						
							<td>${order.transCurrency } ${order.transMoney }</td>
							<td>${order.createBy }</td>
							<td>${order.createDate }</td>
							<td>${order.checkBy }</td>
							<td>${order.checkDate }</td>
							<td>${order.refundStatus }</td>
							<td title="${order.refundStatus }">
								<c:if test="${fn:contains(order.refundStatus,'P000')||fn:contains(order.refundStatus,'0001')||fn:contains(order.refundStatus,'退款失败')||fn:contains(order.refundStatus,'提交银行失败') }">
								<a  href="<%=path %>/transInfoLog/goDoRefundAgain?tradeNo=${order.tradeNo}&id=${order.id}&refundAmount=${order.transMoney }&transAmount=${order.merTransAmount}&currency=${order.merBusCurrency}&tradeNewNo=${order.tradeNewNo}&bankTransAmount=${order.bankTransAmount }&bankRefundTransAmount=${order.transMoney*order.bankTransAmount*1.0/order.merTransAmount}" target="dialog" targetType="navTab"rel="dorefundagain" width="550" height="300" mask="true">再次退款</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
	
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' }>100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
	</body>
</html>