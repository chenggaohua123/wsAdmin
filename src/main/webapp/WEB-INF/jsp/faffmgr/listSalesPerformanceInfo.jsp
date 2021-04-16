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
<title>销售业绩</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/faffmgr/salesPerformanceInfoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/faffmgr/salesPerformanceInfoList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>销售员：</label>
				<input type="text" name="userName" value="${param.userName }"/>
			</li>
			<li>
				<label>年：</label>
				<select name="year" class="combox">
					<option value="" ${form['year']==''?'selected':'' }>所有</option>
					<option value="2015" ${form['year']=='2015'?'selected':'' }>2015年</option>
					<option value="2016" ${form['year']=='2016'?'selected':'' }>2016年</option>
					<option value="2017" ${form['year']=='2017'?'selected':'' }>2017年</option>
					<option value="2018" ${form['year']=='2018'?'selected':'' }>2018年</option>
					<option value="2019" ${form['year']=='2019'?'selected':'' }>2019年</option>
					<option value="2020" ${form['year']=='2020'?'selected':'' }>2020年</option>
					<option value="2021" ${form['year']=='2021'?'selected':'' }>2021年</option>
					<option value="2022" ${form['year']=='2022'?'selected':'' }>2022年</option>
					<option value="2023" ${form['year']=='2023'?'selected':'' }>2023年</option>
					<option value="2024" ${form['year']=='2024'?'selected':'' }>2024年</option>
				</select>
			</li>
			<li>
				<label>月：</label>
				<select name="month" class="combox">
					<option value=""${form['month']==''?'selected':'' }>所有</option>
					<option value="1" <c:if test="${form['month']=='1'}">selected</c:if>>1</option>
					<option value="2" <c:if test="${form['month']=='2'}">selected</c:if>>2</option>
					<option value="3" <c:if test="${form['month']=='3'}">selected</c:if>>3</option>
					<option value="4" <c:if test="${form['month']=='4'}">selected</c:if>>4</option>
					<option value="5" <c:if test="${form['month']=='5'}">selected</c:if>>5</option>
					<option value="6" <c:if test="${form['month']=='6'}">selected</c:if>>6</option>
					<option value="7" <c:if test="${form['month']=='7'}">selected</c:if>>7</option>
					<option value="8" <c:if test="${form['month']=='8'}">selected</c:if>>8</option>
					<option value="9" <c:if test="${form['month']=='9'}">selected</c:if>>9</option>
					<option value="10" <c:if test="${form['month']=='10'}">selected</c:if>>10</option>
					<option value="11" <c:if test="${form['month']=='11'}">selected</c:if>>11</option>
					<option value="12" <c:if test="${form['month']=='12'}">selected</c:if>>12</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>欧元区</label>
				<select class="combox" name="isEur">
					<option value="" ${param.isEur==''?'selected':''}>所有</option>
					<option value="0" ${param.isEur=='0'?'selected':''}>是</option>
					<option value="1" ${param.isEur=='1'?'selected':''}>非</option>
				</select>
			</li>
		</ul>
		<ul>
		<li>
			<font color="red">
			拒付率=查询时间段内通知拒付笔数/查询时间段内成功笔数*100%<br>
			退款率=查询时间段内已退款笔数/查询时间段内成功笔数*100%<br>
			</font>
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
		<li><a class="add" href="<%=path %>/faffmgr/exportsalesPerformanceInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出明细</span></a></li>
		</ul>
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
			<thead>
				<tr>
					<th>销售员</th>
					<th>查询日期</th>
					<th>成功金额</th>
					<th>拒付金额</th>
					<th>拒付率</th>
					<th>退款金额</th>
					<th>退款率</th>
					<th>导出详情</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="info">
					<tr>
						<td>${info.sellName }</td>
						<td>${info.queryDate }</td>
						<td>${info.currency} ${info.successAmount }</td>
						<td>${info.currency} ${info.disAmount }</td>
						<td>${info.disRate }</td>
						<td>${info.currency} ${info.refundAmount }</td>
						<td>${info.refundRate }</td>
						<td>
						<a href="<%=path %>/faffmgr/exportsalesPerformanceInfo?userName=${info.sellName}&year=${info.comYear}&month=${info.comMonth}&currency=${info.currency}" target="dwzExport" targetType="navTab"rel="querySalesPerformance" mask="true"><span>导出</span></a>
						</td>
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