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
<title>风险码交易监控</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/riskReport/queryRiskTransLoseCount">
    <input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/riskReport/queryRiskTransLoseCount" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 <li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 </li>
		 <li>
		 		<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
		 </li>
		 
		</ul>
		<ul  class="searchContent">
		  <li>
		 	<label>分类查询：</label>
			<input type="radio" name="radType" value="" ${(param.radType == null or param.radType == '')?'checked=checked':'' } />全部
			<input type="radio" name="radType" value="1" ${param.radType == '1' ?'checked=checked':''}/>商户号
			<input type="radio" name="radType" value="2" ${param.radType == '2'?'checked=checked':'' } />终端号
		 </li>
		 <li  class="dateRange">
			  <label>查询日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateStart}"/>
			     <span class="limit">-</span>
			   <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateEnd}"/>		
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
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="145" style="text-align: center;">
		<thead>
			<tr>
			    <c:if test="${param.radType=='1'}">
				<th>商户号</th>
				</c:if>
				<c:if test="${param.radType=='2'}">
				<th>终端号</th>
				</c:if>
				<th>失败笔数</th>
				<th>失败比率</th>
				<th>失败原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data}" var="total">
				<tr>
				    <c:if test="${param.radType=='1'}">
					<td>${total.merNo }</td>
					</c:if>
					<c:if test="${param.radType=='2'}">
				    <td>${total.terNo }</td>
				    </c:if>
				  
					<td>${total.riskCount }</td>
					<td>${total.riskRate }</td>
					<td>${funcs:getStringColumnKey('RESP_INFO',total.respCode,total.respCode)}</td>
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