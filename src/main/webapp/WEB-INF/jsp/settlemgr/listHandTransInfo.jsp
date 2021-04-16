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
<title>手工结算状态</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/queryHandTransInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="reSearch">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/queryHandTransInfo" method="post">
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
				<label>结算状态：</label>
				<select name="isAccess">
					<option value="" ${param.isAccess==''?'selected':'' }>所有</option>
					<option value="0" ${param.isAccess=='0'?'selected':'' }>未结算</option>
					<option value="1" ${param.isAccess=='1'?'selected':'' }>已结算</option>
				</select>
			</li>
			</ul>
			<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			<li class="dateRange"> 
				<label>创建时间：</label>
				 <input type="text" name="dateStart"  id = "dateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.dateStart}"/>
				  <span class="limit">-</span>
				 <input type="text" name="dateEnd"  id = "dateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.dateEnd}"/>
			</li>
			</ul>
		<font color='red'>
			注：1.当选择提交之后，次日早上八点结算。
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
			<li>
			</li>
		</ul>
	</div>
	<div id="w_list_print">
	<form id="canHandTransAdd">
	<table class="list" width="100%" targetType="navTab" layoutH="150" style="text-align: center;" id="listCanHandTransInfo">	
		<thead>
			<tr>
				<th>流水号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>结算状态</th>
				<th>结算时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody >
			<c:forEach items="${page.data }" var="st">
				<tr  target="stId" rel="${st.tradeNo }"   >
					<td>${st.tradeNo }</td>
					<td>${st.merNo }</td>
					<td>${st.terNo }</td>
					<td>${st.createBy }</td>
					<td>${st.createDate }</td>
					<td>${st.isAccess==0?'未结算':'<font color=red>已结算</font>' }</td>
					<td>${st.moneyDate}</td>
					<td>${st.remark}</td>
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