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
<title>商户结算周期</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/getMerchantSettleCycleList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantmgr/getMerchantSettleCycleList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
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
			<li><a class="add" href="<%=path %>/merchantmgr/goAddMerchantSettleCycle" target="dialog" width="850" height="400" mask="true"><span>添加商户结算周期</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/goUpdateMerchantSettleCycle?id={sid_role}" target="dialog" width="1100" height="550" mask="true" warn="请选择一个用户"><span>修改商户结算记录</span></a></li>
			<li class="line"></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>结算次数</th>
				<th>首次结算周期T</th>
				<th>常规结算周期</th>
				<th>每月固定结算日</th>
				<th>划款币种</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>最后修改人</th>
				<th>最后修改时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="msc">
				<tr target="sid_role" rel="${msc.id }"    align="center">
					<td>${msc.merNo }</td>
					<td>${msc.settleCount }</td>
					<td>${msc.firstSettleT}</td>
					<td>${msc.commonSettleT }</td>
					<td>${msc.settleDay}</td>
					<td>${msc.settleCurrency }</td>
					<td>${msc.createBy }</td>
					<td>${msc.createDate }</td>
					<td>${msc.modifyBy }</td>
					<td>${msc.modifyDate }</td>
					<td>${msc.remark }</td>
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