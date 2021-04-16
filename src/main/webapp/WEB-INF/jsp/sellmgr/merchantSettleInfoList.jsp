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
<title>商户提现查询</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/sellmgr/queryMerchantSettlerInfoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sellmgr/queryMerchantSettlerInfoList" method="post">
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
		 <li  class="dateRange">
			  <label>提现时间</label>
			  <input type="text" name="dateStart"  id = "dateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['dateStart']}"/>
		       <span class="limit">-</span>
			  <input type="text" name="dateEnd"  id = "dateEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['dateEnd']}"/>		
			</li>
		</ul>
		<ul class="searchContent">
			<li  class="dateRange">
				<label>出款时间</label>
				<input type="text" name="sellterDateStart"  id = "sellterDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['sellterDateStart']}"/>
			    <span class="limit">-</span>
				<input type="text" name="sellterDateEnd"  id = "sellterDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['sellterDateEnd']}"/>		
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
	<li><a class="add" href="<%=path %>/sellmgr/exportMerchantSettleInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
	</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>账户类型</th>
				<th>操作类型</th>
				<%--
				<th>扣款类型</th>
				 --%>
				<th>提现金额</th>
				<th>提现时间</th>
				<%--
				<th>申请人</th>
				--%>
				<th>提现审核状态</th>
				<%--
				<th>审核意见</th>
				<th>审核人</th>
				<th>审核时间</th>
				<th>复核人</th>
				<th>复核意见</th>
				<th>复核时间</th>
				<th>出款人</th>
				--%>
				<th>出款时间</th>
				<th>出款说明</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="access">
				<tr target="accessID" rel="${access.id }">
					<td>${access.merNo }</td>
					<td>${access.terNo }</td>
					<td>${access.accountType==0?'交易账户':'保证金账户' }</td>
					<td>${funcs:getStringColumnKey('CASHTYPE',access.cashType,'未知状态')}</td>
					<%--
					<td>${access.deductionType }</td>
					 --%>
					<td>${access.currency }&nbsp;${access.amount }</td>
					<td>${funcs:formatDate(access.createDate,'yyyy-MM-dd HH:mm')}</td>
					<%--
					<td>${access.createBy }</td>
					 --%>
					<td>${funcs:getStringColumnKey('ACCESSSTATUS',access.status,access.status)}</td>
					<%--
					<td>${access.checkRemark }</td>
					<td>${access.checkBy }</td>
					<td>${funcs:formatDate(access.checkDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${access.reCheckBy }</td>
					<td>${access.reCheckRemark }</td>
					<td>${funcs:formatDate(access.reCheckDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${access.moneyBy }</td>
					 --%>
					<td>${funcs:formatDate(access.moneyDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${access.moneyRemark }</td>
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