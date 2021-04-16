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
<title>交易统计</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/transReport/transTotalList">
    <input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transReport/transTotalList" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		   <li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
		 	
			<li>
		 	<label>分类统计：</label>
		 	  <input type="radio" name="radType" value="" ${(param.radType == null or param.radType == '')?'checked=checked':'' }/>所有
			  <input type="radio" name="radType" value="1" ${param.radType == '1'?'checked=checked':'' }/>商户
		    </li>
		</ul>
		<ul class="searchContent">
		 <li>
		 		<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
		 	</li>
		 	<li>
		 		<label>卡类型：</label>
				<input type="text" name="cardType" value="${param.cardType}"/>
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
				<c:if test="${param.radType==null or param.radType==''}">
				<th>商户号</th>
				<th>终端号</th>
				</c:if>
				<th>卡类型</th>
				<th>笔数</th>
				<th>交易金额</th>
				<th>交易时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data}" var="total">
				<tr>
				    <c:if test="${param.radType=='1'}">
					<td>${total.merNo }</td>
					</c:if>
					<c:if test="${param.radType==null or param.radType==''}">
					<td>${total.merNo }</td>
					<td>${total.terNo }</td>
					</c:if>
					<td>${total.cardType }</td>
					<td>${total.count }</td>
					<td>${total.transAmount }</td>
					<td>${funcs:formatDate(total.transDate,'yyyy-MM-dd')} </td>
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