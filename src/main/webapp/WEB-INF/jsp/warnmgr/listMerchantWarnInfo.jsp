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
<title>商户监控管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/warnmgr/listMerchantWarnInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/warnmgr/listMerchantWarnInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }">
		 	</li>
			 <li  class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startDate'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['endDate']}"/>		
			</li>
			<li>
				<label>商户类别</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${param.type }" relText="columnvalue" name="type">
					<option value="">所有</option>
				</select>
			</li>
		 	</ul>
		<ul>
		<li><font color="red">日平均交易金额=成功交易金额/实际交易天数<br>
							     单笔平均交易金额=成功交易金额/交易成功笔数</font>
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
		<li><a class="add" href="<%=path %>/warnmgr/exportMerchantWarnInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="135" style="text-align:center;" >
		<thead>
			<tr>
				  <th>商户号</th>
				  <th>查询交易天数</th>
                  <th>实际交易天数</th>
                  <th>交易成功金额</th>
                  <th>交易成功笔数</th>
                  <th>日平均交易金额</th>
                  <th>单笔成功交易金额 </th>
                  <th>最后一笔交易时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.merNo }</td>
					<td>${info.dateCount }</td>
					<td>${info.transDateCount }</td>
					<td>${info.bankCurrency } ${info.successAmount }</td>
					<td>${info.successCount }</td>
					<td>
					<c:choose>
						<c:when test="${info.transDateCount == 0  }">
							${info.bankCurrency } 0.00
						</c:when>
						<c:otherwise>
							${info.bankCurrency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.successAmount/info.transDateCount }" />
						</c:otherwise>
					</c:choose>
					</td>
					<td>
					<c:choose>
						<c:when test="${info.successCount == 0  }">
							${info.bankCurrency } 0.00
						</c:when>
						<c:otherwise>
							${info.bankCurrency } <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.successAmount/info.successCount }" />
						</c:otherwise>
					</c:choose>
					</td>
					<td>${funcs:formatDate(info.lastTransDate,'yyyy-MM-dd HH:mm:ss')}</td>
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