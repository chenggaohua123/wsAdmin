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
<title>异常信息管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/faffmgr/searchIncomeInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/faffmgr/searchIncomeInfo" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>通道：</label>
		 		<input name="currencyName" type="text"  id="currency.currencyName" value="${param.currencyName }" />
                <input name="currencyId" type="hidden" id="currency.currencyId" value="${param.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
		 	</li>
		 		<li>
		 		<label>收支类型：</label>
				<select name="incomeType">
					<option value="" <c:if test="${param.incomeType=='' }">selected</c:if>>所有</option>
					<option value="0" <c:if test="${param.incomeType=='0' }">selected</c:if>>收入</option>
					<option value="1" <c:if test="${param.incomeType=='1' }">selected</c:if>>支出</option>
				</select>
		 	</li>
		</ul>
			<ul class="searchContent">
			<li>
				<label>录入人：</label>
				<input type="text" name="createBy" value="${param.createBy }"/>
			</li>
		   <li >
			  <label>收支日期</label>
			  <input type="text" name="startIncomeDate"  id = "startIncomeDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date" value="${param.startIncomeDate}" />
			</li>
			<li>
		      <span class="limit"> -- </span>
		      <input type="text" name="endIncomeDate"  id = "endIncomeDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss"  class="date" value="${param.endIncomeDate}"/>
			</li>
		
			
		</ul>
		<ul class="searchContent">
		 	<li >
			  <label>录入日期</label>
			  <input type="text" name="startCreateDate"  id = "startCreateDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date" value="${param.startCreateDate}" />
			</li>
			<li>
			  <span class="limit"> -- </span>
			  <input type="text" name="endCreateDate"  id = "endCreateDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss"  class="date" value="${param.endCreateDate}"/>
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
			<li><a class="add" href="<%=path %>/faffmgr/exportIncomeExcel" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>异常信息导出</span></a></li>
			<li><a class="add" href="<%=path %>/faffmgr/showUpdateIncomePage" target="dialog" width="550" height="380" mask="true" rel="add"><span>添加异常数据</span></a></li>
			<li><a class="edit" href="<%=path %>/faffmgr/showUpdateIncomePage?id={id}" target="dialog" width="550" height="380" mask="true" rel="add"><span>修改异常数据</span></a></li>
			<!-- 
				<li><a class="add" id="btnx1" href="#"   width="550" height="300" mask="true"><span>隐藏/显示查询条件</span></a></li>
				<li><a class="add" id="btnx" href="#"   width="550" height="300" mask="true"><span>隐藏/显示统计汇总</span></a></li>
			 -->
		</ul>
	</div>
	<div class="pageHeader" id="pageHeaderx" style="display: block;">
	<div class="searchBar">
				<ul class="searchContent">
					<li>
			<span>收入金额: 
			<c:forEach items="${list }" var="info">
				<c:if test="${info.type==0 }">
					${info.currency }&nbsp;${info.incomeTotal } <font color="red"> | </font>
				</c:if>
			</c:forEach>
			</span> 
			</li>
			<li>
			<span>支出金额: <c:forEach items="${list }" var="info">
				<c:if test="${info.type==1 }">
					${info.currency }&nbsp;${info.incomeTotal }<font color="red"> | </font>
				</c:if>
			</c:forEach></span>
			</li>
				</ul>
			</div>
	</div>
	<div id="w_list_print">
	<table class="list" id="tableList"   width="100%" targetType="navTab" layoutH="200" style="text-align: center;">
		<thead>
			<tr>
				<th>银行</th>
				<th>收支类型</th>
				<th>收支名目</th>
				<th>收支金额</th>
				<th>收支日期</th>
				<th>备注</th>
				<th>录入人</th>
				<th>录入日期</th>
				<th>修改人</th>
				<th>修改日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="income">
				<tr target="id" rel="${income.id }" >
				<!-- 
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${income.id }"> ${income.id }</a></td>
				 -->
					<td>${income.bankName}</td>
					<td>
						<c:if test="${income.type==0 }">收入</c:if>
						<c:if test="${income.type==1 }">支出</c:if>
					</td>
					<td>${income.incomeDesc }</td>
					<td>${income.currency }  ${income.amount }</td>
					<td>${funcs:formatDate(income.incomeDate,'yyyy-MM-dd HH:mm:ss')}</td>
					<td>${income.remark }</td>
					<td>${income.createBy }</td>
					<td>${funcs:formatDate(income.createDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${income.lastModifyBy }</td>
					<td>${funcs:formatDate(income.lastmodifyDate,'yyyy-MM-dd HH:mm:ss')} </td>
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