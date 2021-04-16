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
<title>银行订单查询</title>

</head>
<!-- <script type="text/javascript">

	function getBankId(){
		var currencyId=$("#currencyId").val();
		if(currencyId=="973"){
			$("#bankId option[value='52']").attr("selected", false);
			$("#bankId option[value='97']").attr("selected", true);
			$("#combox_bankId a").attr("value","97");
			$("#combox_bankId a").html("通联47通道-USD");
		}else {
			$("#bankId option[value='52']").attr("selected", true);
			$("#bankId option[value='97']").attr("selected", false);
			$("#combox_bankId a").attr("value","52");
			$("#combox_bankId a").html("通联2通道");
		}
		
	}
</script> -->
<body>
<form id="pagerForm" method="post" action="<%=path %>/bankOrder/getBankOrderList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/bankOrder/getBankOrderList" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
			<li style="width: 250px;">
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
		 	
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/bankOrder/uploadRefundOrderFile" target="dwzExport" targetType="navTab" rel="uploadRefundFile" width="550" height="300" mask="true"><span>退款订单导出</span></a></li>
			
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>订单号</th>
				<th>商户号</th>
				<th>支付状态</th>
				<th>支付方式</th>
				<th>订单币种</th>
				<th>订单金额</th>
				<th>银行订单号</th>
				<th>订单提交时间</th>
				<th>订单扣款时间</th>
				<th>退款总笔数</th>
				<th>拒付总笔数</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${data!=null }">
			<tr>
				<td>
					<a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${param.tradeNo }"> ${param.tradeNo }</a>
				</td>
				<td>${data.requestId }</td>
				<td>${data.merchantId }</td>
				<td>
					<c:choose>
						<c:when test="${data.pstatus=='INIT'}">初始化</c:when>
						<c:when test="${data.pstatus=='SUCCESS'}">成功</c:when>
						<c:when test="${data.pstatus=='FAILED'}">失败</c:when>
						<c:when test="${data.pstatus=='PROCESSING'}">处理中</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</td>
				<td>${data.paymentModeAlias}</td>
				<td>${data.orderCurrency}</td>
				<td>${data.orderAmount}</td>
				<td>${data.bankNumber}</td>
				<td>${data.submissionTime}</td>
				<td>${data.completeTime}</td>
				<td>
					<c:choose>
						<c:when test="${data.totalRefundCount!='0'}">
							<a target="dialog" title="退款明细"  mask="true" href="<%=path %>/bankOrder/refundDetails"> ${data.totalRefundCount}笔(点击查看详情)</a>
						</c:when>
						<c:otherwise>${data.totalRefundCount}</c:otherwise>
					</c:choose>
				</td>
				<%-- <td>
					<c:choose>
						<c:when test="${data.refundDetails==null}">-</c:when>
						<c:otherwise>
							${data.refundDetails.refundId}
							<a target="dialog" title="退款明细"  mask="true" href="<%=path %>/bankOrder/refundDetails"> ${data.refundDetails[0].refundId}</a>
						</c:otherwise>
					</c:choose>
				</td> --%>
				<td>
					<c:choose>
						<c:when test="${data.totalRefuseCount!='0'}">
							<a target="dialog" title="拒付明细"  mask="true" href="<%=path %>/bankOrder/refuseDetails"> ${data.totalRefuseCount}笔(点击查看详情)</a>
						</c:when>
						<c:otherwise>${data.totalRefuseCount}</c:otherwise>
					</c:choose>
				</td>
				<%-- <td>
					<c:choose>
						<c:when test="${data.refuseDetails==null}">-</c:when>
						<c:otherwise>
							${data.refuseDetails.refuseId}
							<a target="dialog" title="拒付明细"  mask="true" href="<%=path %>/bankOrder/refuseDetails"> ${data.refuseDetails[0].refuseId}</a>
						</c:otherwise>
					</c:choose>
				</td> --%>
			</tr>
			</c:if>
			<%-- <c:forEach items="${data }" var="bankOrder">
				<tr target="tradeNo" rel="${bankOrder.transactionId }">
					<td>${bankOrder.transactionId }</td>
					<td>${bankOrder.orderId }</td>
					<td>${bankOrder.merchantId }</td>
					<td>${bankOrder.amountOrder }</td>
					<td>${bankOrder.amount }</td>
					<td>
					<c:choose>
						<c:when test="${bankOrder.status=='Pending' }">等待处理</c:when>
						<c:when test="${bankOrder.status=='Success' }">退款成功</c:when>
						<c:when test="${bankOrder.status=='Rejected' }">已拒绝</c:when>
					</c:choose>
					</td>
					<td>${bankOrder.reason }</td>
					<td>${bankOrder.dateCreated }</td>
				</tr>
			</c:forEach> --%>
		</tbody>
	</table>
	</div>
	<%-- <div class="panelBar" style="height:30px">
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
	</div> --%>
</div>

</body>
</html>