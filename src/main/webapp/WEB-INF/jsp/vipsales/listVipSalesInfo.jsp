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
<form id="pagerForm" method="post" action="<%=path %>/vipSales/listVipSalesInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/vipSales/listVipSalesInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
			</li>
			<li>
				<label>卡号前六位：</label>
				<input type="text" name="cardSix" value="${param.cardSix}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>卡号后四位：</label>
				<input type="text" name="cardfour" value="${param.cardfour}"/>
			</li>
			<li>
				<label>姓名：</label>
				<input type="text" name="name" value="${param.name}"/>
			</li>
			<li>
				<label>邮箱：</label>
				<input type="text" name="email" value="${param.email}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>ID号：</label>
				<input type="text" name="uniqueID" value="${param.uniqueID}"/>
			</li>
			<li>
			 <label>创建日期</label>
			 <input type="text" name="createdDateStart"  id = "createdDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['createdDateStart'] }"/>
			 -
			 </li>
			 <li>
			 	<input type="text" name="createdDateEnd"  id = "createdDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['createdDateEnd'] }"/>
			 </li>
		</ul>
		<ul class="searchContent">
			 <li>
				<label>更新日期：</label>
				<input type="text" name="updateDateStart"  id = "updateDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['updateDateStart'] }"/>
			 	-
			 </li>
			 <li>
			 	<input type="text" name="updateDateEnd"  id = "updateDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['updateDateEnd'] }"/>
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
			<li><a class="add" href="<%=path %>/vipSales/exportVipSalesInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="190" style="text-align:center;" >
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>卡号</th>
				<th>姓名</th>
				<th>邮箱</th>
				<th>ID号</th>
				<th>金额</th>
				<th>扣款周期</th>
				<th>创建日期</th>
				<th>更新日期</th>
				<th>历史变更记录</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="uniqueID" rel="${info.uniqueID }">
					<td>${info.merNo }</td>
					<td>${info.terNo }</td>
					<td>${info.start }****${info.end }</td>
					<td>${info.name }</td>
					<td>${info.email }</td>
					<td>${info.uniqueID }</td>
					<td>${info.currency } ${info.amount }</td>
					<td>
						<c:if test="${info.salesCycle==0 }">
							非周期扣费
						</c:if>
						<c:if test="${info.salesCycle!=0 }">
							${info.salesCycle }天
						</c:if>
					</td>
					<td>${funcs:formatDate(info.createDate,'yyyy-MM-dd HH:mm:ss') }</td>
					<td>${funcs:formatDate(info.lastModifyDate,'yyyy-MM-dd HH:mm:ss') }</td>
					<td>
					<a title="历史变更记录" target="navTab" width="1600" height="800" mask="true" rel="vipsales" href="<%=path %>/vipSales/listVipSalesHistoryInfo?uniqueID=${info.uniqueID}"> 历史变更记录</a>
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