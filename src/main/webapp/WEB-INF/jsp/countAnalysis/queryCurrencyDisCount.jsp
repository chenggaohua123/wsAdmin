<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通道拒付统计</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/queryCurrencyDisCount">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/queryCurrencyDisCount" method="post">
	<div class="searchBar">
		<ul class="searchContent">
		<li>
		 		<label>银行：</label>
				 <select class="combox" selectedValue="${param.bankId }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
				</select>
		 	</li>
		 	<li>
		 		<label>通道：</label>
		 		<input name="currencyName" type="text"  id="currency.currencyName" value="${param.currencyName }" />
                <input name="currencyId" type="hidden" id="currency.currencyId" value="${param.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
		 	</li>
			<li>
			    <label>状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTCONFIG" relValue="columnKey" selectedValue="${param.enabled }" relText="columnvalue" name="enabled">
					<option value="">所有</option>
				</select>
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
			<li>
				<label>拒付统计类型：</label>
				<select name="countType" class="combox">
					<option value="" ${form['countType']==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${form['countType']=='0'}"> selected</c:if>>总拒付率</option>
					<option value="1" <c:if test="${form['countType']=='1'}">selected</c:if>>月拒付率</option>
				</select>
			</li>
				<li>
				<label>特殊拒付：</label>
				<select name="isSp" class="combox">
					<option value='' ${form['isSp']==''?'selected':''}>所有</option>
					<option value='0' ${form['isSp']=='0'?'selected':''}>不是</option>
					<option value='1' ${form['isSp']=='1'?'selected':''}>是</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>年：</label>
				<select name="countYear" class="combox">
					<option value="" ${form['countYear']==''?'selected':'' }>所有</option>
					<option value="2015" ${form['countYear']=='2015'?'selected':'' }>2015年</option>
					<option value="2016" ${form['countYear']=='2016'?'selected':'' }>2016年</option>
					<option value="2017" ${form['countYear']=='2017'?'selected':'' }>2017年</option>
					<option value="2018" ${form['countYear']=='2018'?'selected':'' }>2018年</option>
					<option value="2019" ${form['countYear']=='2019'?'selected':'' }>2019年</option>
					<option value="2020" ${form['countYear']=='2020'?'selected':'' }>2020年</option>
					<option value="2021" ${form['countYear']=='2021'?'selected':'' }>2021年</option>
					<option value="2022" ${form['countYear']=='2022'?'selected':'' }>2022年</option>
					<option value="2023" ${form['countYear']=='2023'?'selected':'' }>2023年</option>
					<option value="2024" ${form['countYear']=='2024'?'selected':'' }>2024年</option>
				</select>
			</li>
			<li>
				<label>月：</label>
				<select name="countMonth" class="combox">
					<option value="" ${form['countMonth']==''?'selected':'' }>所有</option>
					<option value="1" <c:if test="${form['countMonth']=='1'}">selected</c:if>>1</option>
					<option value="2" <c:if test="${form['countMonth']=='2'}">selected</c:if>>2</option>
					<option value="3" <c:if test="${form['countMonth']=='3'}">selected</c:if>>3</option>
					<option value="4" <c:if test="${form['countMonth']=='4'}">selected</c:if>>4</option>
					<option value="5" <c:if test="${form['countMonth']=='5'}">selected</c:if>>5</option>
					<option value="6" <c:if test="${form['countMonth']=='6'}">selected</c:if>>6</option>
					<option value="7" <c:if test="${form['countMonth']=='7'}">selected</c:if>>7</option>
					<option value="8" <c:if test="${form['countMonth']=='8'}">selected</c:if>>8</option>
					<option value="9" <c:if test="${form['countMonth']=='9'}">selected</c:if>>9</option>
					<option value="10" <c:if test="${form['countMonth']=='10'}">selected</c:if>>10</option>
					<option value="11" <c:if test="${form['countMonth']=='11'}">selected</c:if>>11</option>
					<option value="12" <c:if test="${form['countMonth']=='12'}">selected</c:if>>12</option>
				</select>
			</li>
			<li>
				<label>商户是否可见：</label>
				<select name="isMerchantSee" class="combox">
					<option value='' ${form['isMerchantSee']==''?'selected':''}>所有</option>
					<option value='0' ${form['isMerchantSee']=='0'?'selected':''}>可见</option>
					<option value='1' ${form['isMerchantSee']=='1'?'selected':''}>不可见</option>
				</select>
			</li>
		
		</ul>
		<ul>
		<li><font color="red">总拒付率=总拒付通知笔数/总交易成功笔数*100%<br>
月拒付率=当月通知拒付笔数（以CPD时间为准）/本月交易成功笔数*100%<br>
伪冒占比=伪冒笔数/拒付笔数*100%</font>
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
		<li><a class="add" href="<%=path %>/countAnalysis/exportCurrencyDisCount" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="200" style="text-align:center;" >
		<thead>
			<tr>
				  <th>银行名称</th>
				  <th>通道名称</th>
				  <th>开通状态</th>
                  <th>通道使用时间</th>
                  <th>银行商户号</th>
                  <th>英文账单名称</th>
                  <th>统计方式</th>
                  <th>当月成功笔数</th>
                  <!--c:if test="${form['countYear'] !='' && form['countMonth'] !='' }"-->
							<th>上月成功笔数</th>
							<th>上上月成功笔数</th>
					<!--/c:if-->
                   <th>拒付笔数</th>
                  <th>拒付率</th>
                  <th>伪冒笔数</th>
                  <th>伪冒占比</th>
                  <th>详情</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.bankName }</td>
					<td>${info.currencyName }</td>
					<td>${funcs:getStringColumnKey('MERCHANTCONFIG',info.enabled,'未知状态')}</td>
					<td>${fn:replace(info.tdsj,',','<br>') }</td>
					<td>${info.merchantNo}</td>
					<td>${info.acquirer}</td>
					<td>
						<c:choose>
							<c:when test="${empty info.countYear }">N/A</c:when>
							<c:when test="${info.countYear=='0' }">总拒付率</c:when>
							<c:when test="${info.countYear!='0' }">${info.countYear }-${info.countMonth} </c:when>
							<c:otherwise>N/A</c:otherwise>
						</c:choose>
					</td>
					<td>${info.successCount }</td>
					<!--  c:if test="${form['countYear'] !='' && form['countMonth'] !='' }"-->
							<td>${info.lastSuccessCount }</td>
							<td>${info.lastLastSuccessCount }</td>
					<!--/c:if-->
					<td>${info.disCount }</td>
					<td>
					<c:choose>
						<c:when test="${info.successCount==0 }">0.00%</c:when>
						<c:otherwise>
						<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.disCount/info.successCount*100 }"></fmt:formatNumber>%
						</c:otherwise>
					</c:choose>
					</td>
					<td>${info.fakeCount }</td>
					<td>
					<c:choose>
						<c:when test="${info.disCount==0 }">0.00%</c:when>
						<c:otherwise>
						<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.fakeCount/info.disCount*100 }"></fmt:formatNumber>%
						</c:otherwise>
					</c:choose>
					</td>
					<td><a class="add" href="<%=path %>/countAnalysis/listCurrencyDisCountDesc?currencyId=${info.currencyId}&countYear=${info.countYear}&countMonth=${info.countMonth}&cardType=${param.cardType}&isSp=${param.isSp}&isMerchantSee=${param.isMerchantSee}" target="dialog" rel="addCurren11cy" width="850" height="400" mask="true" rel="rateRelMerc11hant"><span>详情</span></a></td>
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