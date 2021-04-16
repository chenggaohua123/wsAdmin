<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fraud Record</title>
</head>
<body>
<form id="pagerForm" method="post" action="fraud/account/queryFraudRecordList">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="fraud/account/queryFraudRecordList" rel="pagerForm" method="post" class="pageForm required-validate">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						流水号：
					</td>
					<td>
						<input type="text" name="txId" value="${param.txId}" />
					</td>
					<td>
						订单号：
					</td>
					<td>
						<input type="text" name="orderNo" value="${param.orderNo}" />
					</td>
					<td>
						风控账号：
					</td>
					<td>
						<input type="text" name="accountNo" value="${param.accountNo}" />
					</td>					
					<td>
						返回码：
					</td>
					<td>
						<input type="text" name="ret" value="${param.ret}" />
					</td>
				</tr>
				<tr>
					<td>
						 交易时间：
					</td>
					<td>
						<input type="text" name="createDateStart" value="${param.createDateStart}" class="date" dateFmt="yyyy-MM-dd" />
					</td>
					<td>
						<input type="text" name="createDateEnd"
						value="${param.createDateEnd}" class="date" dateFmt="yyyy-MM-dd" />
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
	<ul class="toolBar">
		<li><a title="确定要接受这些记录吗?" target="selectedTodo"  rel="txId" href="fraud/account/reviewRecord/0000" class="add"><span>接受交易</span></a></li>
		<li><a title="确定要拒绝这些记录吗?" target="selectedTodo"  rel="txId" href="fraud/account/reviewRecord/0002" class="add"><span>拒绝交易</span></a></li>
	</ul>
	</div>
	<table class="table" width="100%" layoutH="168">
		<thead>
			<tr>
				<th width="10px"><input type="checkbox" group="txId" class="checkboxCtrl"></th>
				<th>流水号</th>
				<th>订单号</th>
				<th>账号</th>
				<th width="100">交易金额</th>
				<th>交易时间</th>
				<th>返回码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r" varStatus="var">
				<tr target="txId" rel="${r.txId}">
					<td>
						<c:if test="${r.ret == '0001'}">
							<input type="checkbox" name="txId" value="${r.txId}">
						</c:if>
						<c:if test="${r.ret != '0001'}"><input type="checkbox" name="txId" value="${r.txId}" disabled="disabled"></c:if>
					</td>
					<td><a href="fraud/account/queryFraudDetailByTxId?txId=${r.txId}" target="dialog" mask="true" target="dialog" style="color: blue;" minable="false" title="详细信息" width="850" height="550">${r.txId}</a></td>
					<td>${r.orderNo}</td>
					<td>${r.accountNo}</td>
					<td>${r.sourceCurrency} &nbsp; ${r.sourceAmount}</td>
					<td>${funcs:formatDateTime(r.createDate,'yyyy-MM-dd HH:mm:ss')}</td>
					<td>${r.ret}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
          <div class="pages">
          <span>显示</span>
          <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
            <span>共${requestScope.result.total}条</span>
          </div>
        <div class="pagination" targetType="navTab" totalCount="${requestScope.result.total}" numPerPage="${requestScope.result.pageSize}" currentPage="${requestScope.result.nowPage}"></div>
    </div>
</div>
</body>
