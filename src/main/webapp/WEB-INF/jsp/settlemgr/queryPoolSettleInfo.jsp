<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易报告</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/queryPoolSettleInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="reSearch">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/queryPoolSettleInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo }"/>
			</li>
			</ul>
		<font color='red'>
			注：1.每日余额为商户 未入账金额+当前账号余额<br>
			2.交易额，手续费 ，单笔手续费，保证金为当日实际成功交易<br>
			3.退款包含：退款处理费、退款交易手续费返还、交易退款<br>
			4.拒付包含：交易拒付、拒付处理费、拒付罚金<br>
			5.冻结包含：交易冻结、结算条件变更冻结、保证金账户冻结<br>
			6.解冻包含：交易解冻、结算条件变更解冻、保证金账户解冻<br>
			7.提现包含：交易提现、保证金提现<br>
			8.其他费用包含：交易账户扣款、交易账户返款、保证金账户扣款、保证金账户返款、失败订单处理费
		</font>
		<div class="subBar">
			<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="search">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/settlemgr/exportPoolSettleInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
			<li>
			<font color="red">
			总余额：${total.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${total.totalAmount }"/>
			</font>
			</li>
		</ul>
	</div>
	<div id="w_list_print">
	<form id="canHandTransAdd">
	<table class="list" width="100%" targetType="navTab" layoutH="210" style="text-align: center;" id="listCanHandTransInfo">	
		<thead>
			<tr>
				<th>日期</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>交易额</th>
				<th>手续费</th>
				<th>单笔手续费</th>
				<th>保证金</th>
				<th>退款</th>
				<th>拒付</th>
				<th>冻结</th>
				<th>解冻</th>
				<th>提现</th>
				<th>其他费用</th>
				<th>余额</th>
				<th>明细</th>
			</tr>
		</thead>
		<tbody >
			<c:forEach items="${page.data }" var="st">
				<tr  target="stId"   >
					<td>${st.dateStr }</td>
					<td>${st.merNo }</td>
					<td>${st.terNo }</td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.settleAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.feeAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.singleAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.bondAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.refundAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.disAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.thawAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.froznAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.cashAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.otherAmount }"/></td>
					<td>${st.currency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${st.settleAmount-st.feeAmount-st.singleAmount+st.refundAmount+st.disAmount+st.thawAmount+st.froznAmount+st.cashAmount+st.otherAmount }"/></td>
					<td>
					<a class="add" href="<%=path %>/settlemgr/exportPoolSettleInfoDesc?merNo=${st.merNo}&terNo=${st.terNo}&dateStr=${st.dateStr}" rel="addCurren11cy" width="850" height="400" mask="true" rel="rateRelMerc11ha11nt"><span>详情</span></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
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