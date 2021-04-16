<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资金流核算</title>
<script type="text/javascript">
	
</script>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/faffmgr/searchCapitalFlowList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/faffmgr/searchCapitalFlowList" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
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
					<option value="" ${form['month']==''?'selected':'' }>所有</option>
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
		 
		 
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
			<!-- li><a class="add" href="<%=path %>/faffmgr/exportBankCostInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li-->
		</ul>
	</div>
	<%--
		<div class="pageHeader" id="pageHeaderx" style="display: none;">
			<div class="searchBar">
				<ul class="searchContent">
					<li><span>收入金额: <c:forEach items="${list }" var="info">
								<c:if test="${info.type==0 }">
					${info.currency }&nbsp;${info.incomeTotal } <font color="red">
										| </font>
								</c:if>
							</c:forEach>
					</span></li>
					<li><span>支出金额: <c:forEach items="${list }" var="info">
								<c:if test="${info.type==1 }">
					${info.currency }&nbsp;${info.incomeTotal }<font color="red">
										| </font>
								</c:if>
							</c:forEach></span></li>
				</ul>
			</div>
		</div>
	 --%>
	<div id="w_list_print">
	<table class="list" id="tableList"   width="100%" targetType="navTab" layoutH="105" style="text-align: center;">
		<thead>
			<tr>
				<th>时间</th>
				<th>收入</th>
				<th>支出</th>
				<th>剩余资金</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr>
					<td>${info.cashDate}</td>
					<td>${info.currency } ${info.incomeAmount }</td>
					<td>${info.currency } ${info.outAmount }</td>
					<td>${info.incomeAmount+info.outAmount }</td>
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