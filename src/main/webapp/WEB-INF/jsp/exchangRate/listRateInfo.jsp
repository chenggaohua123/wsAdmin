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
<title>状态变更记录</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/ratemgr/getListRateInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/ratemgr/getListRateInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
			</li>
			<li>
			    <label>状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=RATESTATUS" relValue="columnKey" selectedValue="${param.status }" relText="columnvalue" name="status">
					<option value="">所有</option>
				</select>
			</li>
		</ul>
		<ul  class="searchContent">
		   <li   class="dateRange">
			  <label>创建时间</label>
			  <input type="text" name="createDateStart"  id = "createDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="createDateEnd"  id = "createDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateEnd}"/>		
			</li>
			<li>
				<label>创建人：</label>
				<input type="text" name="createBy" value="${param.createBy}"/>
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
			<li><a class="add" href="<%=path %>/ratemgr/goAddRateInfo" target="dialog" rel="addCurrency" width="850" height="400" mask="true" rel="rateRelMerchant"><span>添加费率信息</span></a></li>
			<li><a class="add" href="<%=path %>/ratemgr/goUpdateRateInfo?id={rateId}" target="dialog" width="850" height="400" mask="true" warn="请选择一个通道"><span>修改费率信息</span></a></li>
			<li><a class="add" href="<%=path %>/ratemgr/queryRateInfoLogById?rateId={rateId}"  target="dialog" width="1050" height="500"  mask="true" warn="请选择一个费率"><span>查看历史信息</span></a></li>
		</ul>
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="142" style="text-align: center;">
			<thead>
				<tr >
					<th colspan="5">商户信息</th>
					<th colspan="3">银行费率</th>
					<th colspan="2">商户费率</th>
					<th colspan="3">代理商费率</th>
					<th colspan="3">父级代理商费率</th>
					<th colspan="4">创建信息</th>
				</tr>
				<tr>
					<th>商户号</th>
					<th>商户名称</th>
					<th>终端号</th>
					<th>状态</th>
					<th>最低手续费</th>
					<th>银行费率</th>
					<th>封顶金额</th>
					<th>银行分成比例</th>
					<th>商户费率</th>
					<th>封顶金额</th>
					<th>代理商费率</th>
					<th>封顶金额</th>
					<th>代理商分成比例</th>
					<th>父级代理费率</th>
					<th>封顶金额</th>
					<th>父级分成比例</th>
					<th>创建时间</th>
					<th>创建人</th>
					<th>最后修改时间</th>
					<th>最后修改人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="rate">
					<tr  target="rateId" rel="${rate.id }"   >
						<td>${rate.merNo }</td>
						<td>${rate.merchantName }</td>
						<td>${rate.terNo }</td>
						<td>${funcs:getStringColumnKey('RATESTATUS',rate.status,'未知状态')}</td>
						<td>${rate.minRate}</td>
						<td>${rate.bankRate }</td>
						<td>${rate.bankMaxAmount }</td>
						<td>${rate.bankSplitRate }</td>
						<td>${rate.merRate }</td>
						<td>${rate.merMaxAmount }</td>
						<td>${rate.agentRate }</td>
						<td>${rate.agentMaxAmount }</td>
						<td>${rate.agentSplitRate }</td>
						<td>${rate.parentAgentRate }</td>
						<td>${rate.parentAgentMaxAmount }</td>
						<td>${rate.parentAgentSplitRate }</td>
						<td>${funcs:formatDate(rate.createDate,'yyyy-MM-dd HH:mm')} </td>
						<td>${rate.createBy }</td>
						<td>${funcs:formatDate(rate.lastUpdateDate,'yyyy-MM-dd HH:mm')} </td>
						<td>${rate.lastUpdateBy }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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