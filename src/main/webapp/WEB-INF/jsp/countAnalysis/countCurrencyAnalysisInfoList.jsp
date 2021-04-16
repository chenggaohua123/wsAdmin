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
<title>通道统计分析</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/queryCurrencyCountAnalysisInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/queryCurrencyCountAnalysisInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
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
			<li>
				<label>通道所属</label>
				<input type="text" name="currencyBelongs" value="${param.currencyBelongs }">
			</li>
			</ul>
			<ul  class="searchContent">
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
		 	<li>
				<label>通道状态</label>
				<select name="enabled" class="combox">
					<option value="" ${param.enabled==''?'selected':'' }>所有</option>
					<option value="1" ${param.enabled=='1'?'selected':'' }>开通</option>
					<option value="0" ${param.enabled=='0'?'selected':'' }>关闭</option>
					<option value="2" ${param.enabled=='2'?'selected':'' }>暂停</option>
				</select>
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
		<li><a class="add" href="<%=path %>/countAnalysis/exportCurrencyCountAnalysisInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		<li>
		<c:forEach items="${total }" var="info">
		<c:if test="${info.successCount>0 }">
		<b>总成功条数:${info.successCount } 金额:${info.currency} ${info.successAmount}</b>
		</c:if>
		</c:forEach>
		</li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="150" style="text-align:center;" >
		<thead>
			<tr>
				  <th>银行</th>
				  <th>通道名称</th>
                  <th>通道所属</th>
                  <th>通道状态</th>
                  <th>交易完成笔数</th>
                  <th>交易金额</th>
                  <th>成功笔数</th>
                  <th>成功金额</th>
                  <th>失败笔数</th>
                  <th>失败重复笔数</th>
                  <th>风险单笔数</th>
                  <th>拒付笔数</th>
                  <th>退单笔数</th>
                  <th >投诉笔数</th>
                  <th >成功率</th>
                  <th >拒付率</th>
                  <th >退单率</th>
                  <th >投诉率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.bankName }</td>
					<td>${info.currencyName }</td>
					<td>${info.currencyBelongs }</td>
					<td>${info.currencyEnabled==1?'开通':'关闭' }</td>
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