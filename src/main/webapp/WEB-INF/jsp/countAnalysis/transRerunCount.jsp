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
<title>交易重跑分析</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/transRerunCount">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/transRerunCount" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			 <li  class="dateRange">
			  <label>重跑日期</label>
			  <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startDate'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['endDate']}"/>		
			</li>
				<li>
		 		<label>银行：</label>
				 <select class="combox" selectedValue="${param.bankId }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
				</select>
		 	</li>
		 	<li>
		 		<label>通道：</label>
		 		<input name="currencyName" type="text" readonly="readonly" id="currency.currencyName" value="${param.currencyName }" />
                <input name="currencyId" type="hidden" id="currency.currencyId" value="${param.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
		 	</li>
		 	</ul>
		<ul>
		<li><font color="red">重跑成功率=重跑成功笔数/重跑笔数*100%<br>
							     重跑拒付转换率=重跑拒付笔数/重跑成功笔数*100%</font>
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
		<li><a class="add" href="<%=path %>/countAnalysis/exportTransRerunCount" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="135" style="text-align:center;" >
		<thead>
			<tr>
				  <th>重跑通道号</th>
				  <th>重跑通道名称</th>
                  <th>重跑笔数</th>
                  <th>重跑成功笔数</th>
                  <th>重跑成功率</th>
                  <th>重跑拒付笔数</th>
                  <th>重跑拒付转换率 </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.currencyId }</td>
					<td>${info.currencyName }</td>
					<td>${info.transCount }</td>
					<td>${info.successCount }</td>
					<td>
					<c:choose>
						<c:when test="${info.transCount == 0  }">
							0.00%
						</c:when>
						<c:otherwise>
							<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.successCount/info.transCount*100 }" />%
						</c:otherwise>
					</c:choose>
					</td>
					<td>${info.disCount }</td>
					<td>
					<c:choose>
						<c:when test="${info.successCount == 0  }">
							0.00%
						</c:when>
						<c:otherwise>
							<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.disCount/info.successCount*100 }" />%
						</c:otherwise>
					</c:choose>
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