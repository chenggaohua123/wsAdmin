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
<title>商户通道绑定</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/getMerchnatRelCurrencyList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantmgr/getMerchnatRelCurrencyList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
			    <label>终端号</label>
			    <input type="text" name="terNo" value="${param.terNo }">
			</li>
			<li>
				<label>状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=AGENT_STATUS" relValue="columnKey" selectedValue="${param.enabled }" relText="columnvalue"  name="enabled">
					<option value="">所有</option>
				</select>
			</li>
			<li>
				<label>自动切换：</label>
				<select class="combox" name="autoChange">
					<option value="">所有</option>
					<option value="0" ${param.autoChange=='0'?'selected':'' }>不切换</option>
					<option value="1" ${param.autoChange=='1'?'selected':'' }>切换</option>
				</select>
			</li>
		</ul>
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
			<li><a class="add" href="<%=path %>/merchantmgr/goAddCurrencyToMerchant" target="dialog" width="750" height="450" rel="addCurrencyToMerchan" mask="true"  rel="agentMerchant" ><span>添加商户通道绑定记录</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/goUpdateCurrencyToMerchnat?id={sid_role}" target="dialog" width="750" height="450"  rel="addCurrencyToMerchan" mask="true" warn="请选择一个商户号"><span>修改绑定通道</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="<%=path %>/merchantmgr/getCurrencyToMerchnatHisList?id={sid_role}" target="dialog" width="1100" height="450"  mask="true" warn="请选择一个商户号"><span>查看修改历史</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="<%=path %>/merchantmgr/goBatchUpdateCurrencyToMerchant" target="dialog" width="800" height="550" rel="addCurrencyToMerchan"  mask="true"><span>批量修改</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/exportMerchantRefCurrency" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>商户导出</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/goAddOrUpdateMerchantRefCurrencySpecial?id={sid_role}" target="dialog" width="900" height="450"  rel="addCurrencyToMerchan" mask="true" warn="请选择一个商户号"><span>绑定自动切换规则</span></a></li>
			
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="145" style="text-align: center;">
		<thead>
			<tr>
				<th colspan="2">商户信息</th>
				<th colspan="5">绑定银行信息</th>
				<th colspan="2">通道限额切换</th>
				<th colspan="4">其他</th>
			</tr>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>绑定银行名称</th>
				<th>绑定通道名称</th>
				<th>英文账单</th>
				<th>待切换通道名称</th>
				<th>是否自动切换</th>
				<th>通道日限额备用</th>
				<th>切换规则</th>
				<th>欧元通道名称</th>
				<th>绑定卡种</th>
				<th>状态</th>
				<th>绑定时间</th>
				<th>绑定人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="merchant">
				<tr target="sid_role" rel="${merchant.id }"    align="center">
					<td>${merchant.merNo }</td>
					<td>${merchant.terNo == '0'?'所有':merchant.terNo}</td>
					<td>${merchant.bankName}</td>
					<td>${merchant.currencyName}</td>
					<td>${merchant.acquirer}</td>
					<td>${merchant.currencyName2}</td>
					<td>${merchant.autoChange=='0'?'<font color=green>不切换</font>':'<font color=green>切换</font>'}</td>
					<td>${merchant.currencyDayAmountNames}</td>
					<c:if test="${merchant.currencyDayAmountType==0}">
						<td><font color=green>不切换</font></td>
					</c:if>
					<c:if test="${merchant.currencyDayAmountType==1}">
						<td><font color=green>顺序切换</font></td>
					</c:if>
					<c:if test="${merchant.currencyDayAmountType==2}">
						<td><font color=green>最少切换</font></td>
					</c:if>
					<td>${merchant.currencyName3}</td>
					<td>${funcs:getStringColumnKey('CARDTYPE',merchant.cardType,'未知状态')}</td>
					<td>${funcs:getStringColumnKey('AGENT_STATUS',merchant.enabled,'未知状态')}</td>
					<td>${funcs:formatDate(merchant.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${merchant.createBy }</td>
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