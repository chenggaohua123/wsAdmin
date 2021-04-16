<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>风险资金池统计</title>
</head>
<body>

<form id="pagerForm" method="post" action="<%=path %>/faffmgr/listRiskCapitalPoolCount">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/faffmgr/listRiskCapitalPoolCount" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>年份：</label>
				<select class="combox" name="year">
					<option value="" ${param.year==''?'selected':'' }>所有</option>
					<option value="2015" ${param.year=='2015'?'selected':'' }>2015</option>
					<option value="2016" ${param.year=='2016'?'selected':'' }>2016</option>
					<option value="2017" ${param.year=='2017'?'selected':'' }>2017</option>
					<option value="2018" ${param.year=='2018'?'selected':'' }>2018</option>
					<option value="2019" ${param.year=='2019'?'selected':'' }>2019</option>
					<option value="2020" ${param.year=='2020'?'selected':'' }>2020</option>
					<option value="2021" ${param.year=='2021'?'selected':'' }>2021</option>
					<option value="2022" ${param.year=='2022'?'selected':'' }>2022</option>
					<option value="2023" ${param.year=='2023'?'selected':'' }>2023</option>
					<option value="2024" ${param.year=='2024'?'selected':'' }>2024</option>
					<option value="2025" ${param.year=='2025'?'selected':'' }>2025</option>
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
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
		</ul>
	</div>
	<div class="pageHeader" id="pageHeaderx" style="display: block;">
	<div class="searchBar" >
		<ul class="searchContent" >
			<li>
			<span>风险池资金合计盈余金额: 
				<c:forEach items="${total }" var="info">
					${info.currency } ${info.totalAmount } <font color='red'>|</font>
				</c:forEach>
			</span> 
			</li>
		</ul>
	</div>
	</div>
	<div id="w_list_print">
	<table class="list" id="tableList"   width="100%" targetType="navTab" layoutH="155" style="text-align: center;">
		<thead>
			<tr>
				<th>月份</th>
				<th>异常收入金额</th>
				<th>异常支出金额</th>
				<th>合计盈余金额</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr>
					<td>${info.moneyDate}</td>
					<td>${info.currency} ${info.incomeAmount}</td>
					<td>${info.currency} ${info.outAmount}</td>
					<td>${info.currency} ${info.totalAmount}</td>
					<td>
						<a href="<%=path %>/faffmgr/exportRiskCapitalPoolInfos?moneyDate=${info.moneyDate}">导出</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" style="height:30px">
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