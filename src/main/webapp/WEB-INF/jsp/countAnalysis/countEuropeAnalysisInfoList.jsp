<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/queryEuropeChannelInfoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/queryEuropeChannelInfoList" method="post">
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
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li  class="dateRange">
				<label>交易日期</label>
				<input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startDate'] }"/>
				<span class="limit">-</span>
				<input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['endDate']}"/>		
			</li>
			<li>
				<label>风险单过滤：</label>
				<select name="riskFilter" class="combox">
					<option value="" <c:if test="${param.riskFilter==''}">selected</c:if>>过滤</option>
					<option value="1" <c:if test="${param.riskFilter=='1'}">selected</c:if>>不过滤</option>
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

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		<li><a class="add" href="<%=path %>/countAnalysis/exportEuropeChannelInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="140" style="text-align:center;" >
		<thead>
			<tr>
				  <th>交易流水号</th>
				  <th>商户号</th>
				  <th>终端号</th>
				  <%-- 
                  <th>订单号</th>
                  <th>商户交易金额</th>
				   --%>
                  <th>交易状态</th>
                  <th>返回原因</th>
                  <%-- 
                  <th>卡种</th>
                  <th>卡号</th>
                   --%>
                  <th>交易日期</th>
                  <th>z2</th>
                  <th>z5</th>
                  <th>z6</th>
                  <th>z14</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.tradeNo }</td>
					<td>${info.merNo }</td>
					<td>${info.terNo }</td>
					<%-- 
					<td>${info.orderNo }</td>
					<td>${info.merBusCurrency } ${info.merTransAmount }</td>
					 --%>
					<td>
						<c:if test="${info.respCode=='00' }"><font color="green">成功</font></c:if>
						<c:if test="${info.respCode!='00' }"><font color="red">失败</font></c:if>
					</td>
					<td>${info.respMsg }</td>
					<%--
					<td>${info.cardType }</td>
					<td>${info.sixAndFour }</td>
					 --%>
					<td>${funcs:formatDate(info.transDate,'yyyy-MM-dd HH:mm:ss')}</td>
					<%-- 
					<td><a target="dialog" title="关联欧洲通道信息" width="900" height="520" mask="true" href="<%=path %>/countAnalysis/queryEuropeMessage?tradeNo=${info.tradeNo}">关联欧洲通道信息</a></td>
					 --%>
					 <td>${info.z2Code } : ${info.z2Description }</td>
					 <td>${info.z5Description }</td>
					 <td>${info.z6Code } : ${info.z6Description }</td>
					 <td>${info.z14Code } : ${info.z14Description }</td>
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