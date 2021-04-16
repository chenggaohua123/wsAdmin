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
<title>交易拒付趋势</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/queryTransDisPercent">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/queryTransDisPercent" method="post">
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
			<li  class="dateRange">
			  <label>日期</label>
			  <input type="text" name="dateStart"  id = "dateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['dateStart'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="dateEnd"  id = "dateEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['dateEnd']}"/>		
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>卡种：</label>
				<select class="combox"
								loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CARDTYPE"
								relValue="columnKey" relText="columnvalue" name="cardType" selectedValue="${param.cardType }">
								<option value="">所有</option>
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
		<li><a class="add" href="<%=path %>/countAnalysis/exportTransDisPercent" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="140" style="text-align: center;">
			<thead>
				<tr>
					<th>时间</th>
					<th>成功笔数</th>
					<th>拒付总笔数</th>
					<th>当月</th>
					<th>第一月</th>
					<th>第二月</th>
					<th>第三月</th>
					<th>第四月</th>
					<th>第五月</th>
					<th>第六月</th>
					<th>第七月</th>
					<th>第八月</th>
					<th>第九月</th>
					<th>第十月</th>
					<th>第十一月</th>
					<th>第十二月</th>
					<th>其他</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="info">
					<tr  >
						<td>${info.transDate }</td>
						<td>${info.successCount }</td>
						<td>${info.totalDisCount }</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_0/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_1/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_2/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_3/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_4/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_5/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_6/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_7/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_8/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_9/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_10/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_11/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_12/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
							<c:when test="${info.totalDisCount==0 }">0.00%</c:when>
							<c:otherwise>
								<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2">${info.month_other/info.totalDisCount*100}</fmt:formatNumber>%
							</c:otherwise>
						</c:choose>
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