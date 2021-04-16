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
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/queryTransAnalysisInfoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/queryTransAnalysisInfoList" method="post">
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
			 <li  class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="startTransDate"  id = "startTransDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startTransDate'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="endTransDate"  id = "endTransDate" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['endTransDate']}"/>		
			</li>
		</ul>
		<ul  class="searchContent">
			<li>
				<label>商户类别</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${param.type }" relText="columnvalue" name="type">
					<option value="">所有</option>
				</select>
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
				<input name="currencyName" type="hidden"  id="currency.currencyName" value="${param.currencyName }" />
                <input name="currencyId" type="text" id="currency.currencyId" value="${param.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
		 	</li>
		</ul>
		<ul  class="searchContent">
			<li>
				<label>来源：</label>
				<select name="resourceType">
					<option value="">所有</option>
					<option value="电脑" <c:if test="${param.resourceType=='电脑' }">selected</c:if> >电脑</option>
					<option value="手机" <c:if test="${param.resourceType=='手机' }">selected</c:if> >手机</option>
					<option value="平板" <c:if test="${param.resourceType=='平板' }">selected</c:if> >平板</option>
				</select>
			</li>
			<li>
				<label>网址：</label>
				<input type="text" name="payWebSite" value="${param.payWebSite}"/>
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
		<li><a class="add" href="<%=path %>/countAnalysis/exportTransAnalysisInfoList" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="165" style="text-align:center;" >
		<thead>
			<tr>
				  <th>商户号</th>
				  <th>终端号</th>
				  <th>订单来源</th>
				  <th>总笔数</th>
                  <th>交易完成笔数</th>
                  <th>成功笔数</th>
                  <th>失败笔数</th>
                  <th>失败重复笔数</th>
                  <th>风险单笔数</th>
                  <th>支付转换率</th>
                  <th >成功率</th>
                  <th >失败原因构成</th>
                  <th >导出详情</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.merNo }</td>
					<td>${info.terNo }</td>
					<td>${info.resourceType }</td>
					<td>${info.totalCount }</td>
					<td>${info.transCount }</td>
					<td>${info.transSuccessCount }</td>
					<td>${info.transFailureCount }</td>
					<td>${info.duplicateCount }</td>
					<td>${info.transRiskCount }</td>
					<td>
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${(info.transCount)/(info.totalCount==0?1:info.totalCount)*100 }"></fmt:formatNumber>%</td>
					<td>${info.successRate }</td>
					<td><a target="dialog" title="失败原因构成" width="900" height="520" mask="true" href="<%=path %>/countAnalysis/queryFaildTransAnalysisInfoList?merNo=${info.merNo }&terNo=${info.terNo }&resourceType=${info.resourceType }&startDate=${form['startTransDate'] }&endDate=${form['endTransDate']}&type=${param.type }&bankId=${param.bankId }&currencyId=${param.currencyId }&payWebSite=${param.payWebSite}">失败原因构成</a></td>
					<td><a class="add" href="javascript:exportInfos('${info.merNo }','${info.terNo }','${info.resourceType }','${form['startTransDate'] }','${form['endTransDate']}','${param.type }','${param.bankId }','${param.currencyId }','${param.payWebSite}')"><span>导出</span></a></td>
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
<script type="text/javascript">
function exportInfos(merNo, terNo, resourceType, startTransDate, endTransDate, type, bankId, currencyId, payWebSite){
	var param = "?merNo="+merNo+"&terNo="+terNo+"&resourceType="+resourceType+"&startTransDate="+startTransDate+"&endTransDate="+endTransDate+"&type="+type+"&bankId="+bankId+"&currencyId="+currencyId+"&payWebSite="+payWebSite;
	window.location.href="<%=path%>/countAnalysis/exportTransDetailInfo"+param;
}
</script>
</body>
</html>