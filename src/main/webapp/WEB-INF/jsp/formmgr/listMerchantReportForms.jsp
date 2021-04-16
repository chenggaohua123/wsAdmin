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
<title>商户报表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/formmgr/listMerchantReportForms">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/formmgr/listMerchantReportForms" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
			</li>
			<li>
				<label>报表类型：</label>
				<select name="formType" class="combox">
					<option value="1" ${param.formType=='1'?'selected':'' }>月报表</option>
					<option value="2" ${param.formType=='2'?'selected':'' }>周报表</option>
					<option value="3" ${param.formType=='3'?'selected':'' }>日报表</option>
				</select>
			</li>
			 <li  class="dateRange">
			  <label>报表生成时间</label>
			  <input type="text" name="formDate"  id = "formDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['formDate'] }"/>
			 </li>
		</ul>
		<ul>
		<li><font color="red">1.月报表查询为上个自然月的报表 2.周报表查询为上一自然周 3.日报表为当前天上一自然天</font>
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
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="125" style="text-align:center;" >
		<thead>
			<tr>
				<th>商户号</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>报表类型</th>
				<th>导出报表</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.merNo }</td>
					<td>${info.startDate }</td>
					<td>${info.endDate }</td>
					<td>
						<c:choose>
							<c:when test="${info.formType==1 }">月报表</c:when>
							<c:when test="${info.formType==2 }">周报表</c:when>
							<c:when test="${info.formType==3 }">日报表</c:when>
						</c:choose>
					</td>
					<td>
					<a  href="<%=path %>/formmgr/exportMerchantReportForms?merNo=${info.merNo}&startDate=${info.startDate}&endDate=${info.endDate}&formType=${info.formType}"  targetType="navTab"rel="addCurre111ncy" width="550" height="300" mask="true">导出报表</a>
					</td>
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