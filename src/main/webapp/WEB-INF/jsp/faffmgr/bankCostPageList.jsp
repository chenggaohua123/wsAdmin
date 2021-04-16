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
<title>银行成本录入管理</title>
<script type="text/javascript">
	
</script>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/faffmgr/searchBankCoseList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/faffmgr/searchBankCoseList" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>成本类型：</label>
				<input type="text" name="costType" value="${param.costType}"/>
		 	</li>
		 		<li>
		 		<label>录入人：</label>
				<input type="text" name="createBy" value="${param.createBy}"/>
		 	</li>
		</ul>
			<ul class="searchContent">
			<li>
				<label>银行：</label>
				<select class="combox" selectedValue="${param.bankName }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankName" relText="bankName" name="bankName">
				</select>
			</li>
		   <li >
			  <label>通道</label>
               <input name="currencyName" type="text"  id="currency.currencyName" value="${param.currencyName }" />
                <input name="currencyId" type="hidden" id="currency.currencyId" value="${param.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>收取类型：</label>
				<select name="type">
					<option value="" <c:if test="${param.type=='' }">selected<</c:if>>所有</option>
                	<option value="按金额" <c:if test="${param.type=='按金额' }">selected</c:if>>按金额</option>
                	<option value="按笔数" <c:if test="${param.type=='按笔数' }">selected</c:if>>按笔数</option>
                </select>
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
			<li><a class="add" href="<%=path %>/faffmgr/exportBankCostInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出银行成本录入信息</span></a></li>
			<li><a class="add" href="<%=path %>/faffmgr/showUpdateBankCostInfoPage" target="dialog" width="550" height="380" mask="true" rel="add"><span>添加银行成本录入信息</span></a></li>
			<li><a class="edit" href="<%=path %>/faffmgr/showUpdateBankCostInfoPage?id={id}" target="dialog" width="550" height="380" mask="true" rel="add"><span>修改银行成本录入信息</span></a></li>
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
	<table class="list" id="tableList"   width="100%" targetType="navTab" layoutH="170" style="text-align: center;">
		<thead>
			<tr>
				<th>成本类型</th>
				<th>银行</th>
				<th>通道</th>
				<th>收取类型</th>
				<th>笔数</th>
				<th>费用</th>
				<th>备注</th>
				<th>扣款时间</th>
				<th>录入人</th>
				<th>录入日期</th>
				<th>修改人</th>
				<th>修改日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="id" rel="${info.id }" >
					<td>${info.costType}</td>
					<td>${info.bankName }</td>
					<td>${info.currencyName }</td>
					<td>${info.type }</td>
					<td>${info.count }</td>
					<td>${info.currency }  ${info.amount }</td>
					<td>${info.remark }</td>
					<td>${funcs:formatDate(info.settleDate,'yyyy-MM-dd HH:mm:ss')}</td>
					<td>${info.createBy }</td>
					<td>${funcs:formatDate(info.createDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${info.lastModifyBy }</td>
					<td>${funcs:formatDate(info.lastModifyDate,'yyyy-MM-dd HH:mm:ss')} </td>
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