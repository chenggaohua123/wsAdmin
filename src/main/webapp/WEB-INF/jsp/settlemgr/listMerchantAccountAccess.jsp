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
<title>商户出入帐</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/listMerchantAccountAccess">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/listMerchantAccountAccess" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
             <li>
				<label>提现审核状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=ACCESSSTATUS" selectedValue="${param.status }"  relValue="columnKey" relText="columnvalue" name="status">
			      <option value="">所有</option>
			    </select>
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
				<label>审核人：</label>
				<input type="text" name="checkBy" value="${param.checkBy }"/>
			</li>
             <li>
				<label>复核人：</label>
				<input type="text" name="reCheckBy" value="${param.reCheckBy }"/>
			</li>
			 <li>
				<label>出款人：</label>
				<input type="text" name="moneyBy" value="${param.moneyBy }"/>
			</li>
		</ul>
		<ul class="searchContent">
		 <li  class="dateRange">
			  <label>复核时间</label>
			  <input type="text" name="dateStart"  id = "dateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.dateStart }"/>
		       <span class="limit">-</span>
			  <input type="text" name="dateEnd"  id = "dateEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${param.dateEnd }"/>		
			</li>
			<li >
            	<label>扣款类型</label>
            	<select name="deductionType"  class="combox">
            	<option value="" ${param.deductionType==''?'selected':'' }>所有</option>
            		<c:forEach items="${dtList }" var="dt">
            			<option value="${dt.deductionType }"  ${param.deductionType==dt.deductionType ?'selected':''} >${dt.deductionType}</option>
            		</c:forEach>
            	</select>
            </li>
             <li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo }"/>
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
	<li><a class="edit" href="<%=path %>/settlemgr/goCheckMerchantAccountAccess?id={accessID}" target="dialog" width="550" height="350" mask="true"  rel="settleType"  warn="请选择一个商户账户"><span>审核</span></a></li>
	<li class="line">line</li>
	<li><a class="edit" href="<%=path %>/settlemgr/goReCheckMerchantAccountAccess?id={accessID}" target="dialog" width="550" height="350" mask="true"  rel="settleType"  warn="请选择一个商户账户"><span>复核</span></a></li>
	<li class="line">line</li>
	<li><a class="edit" href="<%=path %>/settlemgr/goCancelReCheckMerchantAccountAccess?id={accessID}" target="dialog" width="550" height="350" mask="true"  rel="settleType"  warn="请选择一个商户账户"><span>取消复核</span></a></li>
	<li class="line">line</li>
	<li><a class="edit" href="<%=path %>/settlemgr/goMoneyMerchantAccountAccess?id={accessID}" target="dialog" width="550" height="350" mask="true"  rel="settleType"  warn="请选择一个商户账户"><span>出款</span></a></li>
	<li class="line">line</li>
	<li><a class="add" href="<%=path %>/settlemgr/exportInfos" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
	<li class="line">line</li>
	<li><a class="add" href="<%=path %>/settlemgr/goBatchMoneyInfo" target="dialog" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>批量出款</span></a></li>
	</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="185" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>账户类型</th>
				<th>操作类型</th>
				<th>扣款类型</th>
				<th>提现金额</th>
				<th>申请时间</th>
				<th>申请人</th>
				<th>备注</th>
				<th>审核状态</th>
				<th>审核意见</th>
				<th>审核人</th>
				<th>审核时间</th>
				<th>复核人</th>
				<th>复核意见</th>
				<th>复核时间</th>
				<th>出款人</th>
				<th>出款时间</th>
				<th>出款说明</th>
				<th>商户渠道</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="access">
				<tr target="accessID" rel="${access.id }">
					<td>${access.merNo }</td>
					<td>${access.terNo }</td>
					<td>${access.accountType==0?'交易账户':'保证金账户' }</td>
					<td>${funcs:getStringColumnKey('CASHTYPE',access.cashType,'未知状态')}</td>
					<td>${access.deductionType }</td>
					<td>${access.currency }&nbsp;${access.amount }</td>
					<td>${funcs:formatDate(access.createDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${access.createBy }</td>
					<td>${access.remark }</td>
					<td>${funcs:getStringColumnKey('ACCESSSTATUS',access.status,access.status)}</td>
					<td>${access.checkRemark }</td>
					<td>${access.checkBy }</td>
					<td>${funcs:formatDate(access.checkDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${access.reCheckBy }</td>
					<td>${access.reCheckRemark }</td>
					<td>${funcs:formatDate(access.reCheckDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${access.moneyBy }</td>
					<td>${funcs:formatDate(access.moneyDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${access.moneyRemark }</td>
					<td>${funcs:getStringColumnKey('MERCHANT_CHANNEL',access.merchantChannel,'未知状态')}</td>
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