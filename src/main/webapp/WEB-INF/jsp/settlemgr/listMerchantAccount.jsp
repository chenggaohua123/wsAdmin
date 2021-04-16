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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>商户虚拟账户列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/listMerchantAccount">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/listMerchantAccount" method="post">
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
			<li>
				<label>账户类型：</label>
				<select name="accountType" class="combox">
				<option value="" ${param.accountType==''?'selected':'' }>所有</option>
					<option value="0" ${param.accountType=='0'?'selected':'' }>交易账户</option>
					<option value="1" ${param.accountType=='1'?'selected':'' }>保证金账户</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>商户渠道：</label>
				 <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANT_CHANNEL" relValue="columnKey" selectedValue="${param.merchantChannel}"  relText="columnvalue"  name="merchantChannel">
             		<option value="" ${param.merchantChannel==''?'selected':'' }>所有</option>
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
	<ul  class="toolBar">
	<li><a class="edit" href="<%=path %>/settlemgr/goUpdateMerchantAccount?id={accountID}" target="dialog" width="550" height="350" mask="true"  rel="settleType"  warn="请选择一个商户账户"><span>提现|冻结|解冻|扣款|返款</span></a></li>
	<li><a class="add" href="<%=path %>/settlemgr/exportMerchantAccount" target="dwzExport" targetType="navTab"rel="addCurrency111" width="550" height="300" mask="true"><span>导出</span></a></li>
	<li><a class="add" href="<%=path %>/settlemgr/goAddDeductionType" target="dialog" width="450" height="300" mask="true"><span>添加扣款类型</span></a></li>
			<li><a class="add" href="<%=path %>/settlemgr/listDeductionTypeInfo" rel="queryConfig11"  target="dialog" width="650" height="300" mask="true"><span>查看扣款类型</span></a></li>
	</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="135" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>账户类型</th>
				<th>账户总金额</th>
				<th>可提现金额</th>
				<th>冻结金额</th>
				<th>商户渠道</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="account">
				<tr target="accountID" rel="${account.id },${account.accountType }" ${account.status>0?'bgcolor=yellow':'' } title="有${account.status }笔待审核的提现记录">
					<td>${account.merNo }</td>
					<td>${account.terNo }</td>
					<td>${account.accountType=='0'?'交易账户':'保证金账户'}</td>
					<td>${account.currency }&nbsp;${account.totalAmount }</td>
					<td>${account.currency }&nbsp;${account.cashAmount }</td>
					<td style="color:red;">${account.currency }&nbsp;${account.frozenAmount }</td>
					<td>${funcs:getStringColumnKey('MERCHANT_CHANNEL',account.merchantChannel,'未知状态')}</td>
				</tr>
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