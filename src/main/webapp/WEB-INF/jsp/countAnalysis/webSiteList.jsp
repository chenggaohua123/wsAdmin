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
<title>网站交易统计</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/webSiteList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/webSiteList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
			</li>
			<li>
				<label>卡种：</label>
				<select class="combox"
								loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CARDTYPE"
								relValue="columnKey" relText="columnvalue" name="cardType" selectedValue="${param.cardType }">
								<option value="">所有</option>
							</select>
			</li>
			 <li  class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startDate'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['endDate']}"/>		
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>网站：</label>
				<input type="text" name="payWebsite" value="${param.payWebsite}"/>
			</li>
		</ul>
		<ul>
		<li><font color="red">成功率=成功笔数/（交易完成笔数-风险单笔数-失败重复笔数）*100%</font>
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
		<li><a class="add" href="<%=path %>/countAnalysis/uploadWebSiteList" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="150" style="text-align:center;" >
		<thead>
			<tr>
				 <th >网站</th>
                  <th >商户号</th>
                   <th >终端号</th>
                  <th >交易完成笔数</th>
                  <th >交易金额</th>
                  <th >成功笔数</th>
                  <th >成功金额</th>
                  <th >失败笔数</th>
                  <th >重复笔数</th>
                  <th >风险单笔数</th>
                  <th >拒付笔数</th>
                  <th >退单笔数</th>
                  <th >投诉笔数</th>
                  <th >成功率</th>
                  <th >拒付率</th>
                  <th >退单率</th>
                  <th >投诉率</th>
                  <th >交易占比</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.payWebsite }</td>
					<td>${info.merNo }</td>
					<td>${info.terNo }</td>
					<td>${info.transCount }</td>
					<td>${info.transCurrency} ${info.transAmount  }</td>
					<td>${info.transSuccessCount }</td>
					<td>${info.transCurrency} ${info.transSuccessAmount }</td>
					<td>${info.transFailureCount }</td>
					<td>${info.duplicateCount }</td>
					<td>${info.transRiskCount }</td>
					<td>${info.dishonorCount }</td>
					<td>${info.refundCount }</td>
					<td>${info.complaintCount }</td>
					<td>${info.successRate }</td>
					<td>${info.dishonorRate }</td>
					<td>${info.refundRate }</td>
					<td>${info.complaintRate }</td>
					<td>${info.transRate }</td>
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