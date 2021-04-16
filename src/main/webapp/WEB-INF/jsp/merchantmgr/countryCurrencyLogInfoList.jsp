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
<title>商户结算周期</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/queryCountryCurrencyInfoLogList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantmgr/queryCountryCurrencyInfoLogList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input name="merNo" id="merNo" type="text" value="${param.merNo }"/>
	            <a class="btnLook" href="<%=path %>/merchantmgr/queryMerchantMerAndTerNoList?type=1" target="dialog" lookupGroup="" lookupPk="merNo"  mask="true"  width="750" height="400">查找带回</a>
			</li>
			<li>
				<label>终端号：</label>
				<input name="terNo" id="terNo" type="text" value="${param.terNo }"/>
	            <a class="btnLook" href="<%=path %>/merchantmgr/queryMerchantMerAndTerNoList?type=2" target="dialog" lookupGroup="" lookupPk="terNo"  mask="true"  width="750" height="400">查找带回</a>
			</li>
			<li>
				<label>卡种：</label>
				<select name="cardType">
					<option ${param.cardType==''?'selected':''} value="">所有</option>
            		<option value="visa" <c:if test="${param.cardType=='visa'}">selected</c:if>>VISA</option>
					<option value="master" <c:if test="${param.cardType=='master'}">selected</c:if>>Master</option>
					<option value="jcb" <c:if test="${param.cardType=='jcb'}">selected</c:if>>JCB</option>
            	</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>国家：</label>
				<input name="countryCode" type="text" id="countryCode" value="${param.countryCode }"/>
                <a class="btnLook" href="<%=path %>/merchantmgr/getCountryInfoList" width="1100" height="350" target="dialog" lookupGroup="" lookupPk="countryCode"  mask="true">查找带回</a>
			</li>
			<li>
				<label>通道：</label>
				<input name="currencyName" type="text"  id="currency.currencyName" value="${param.currencyName }" />
                <input name="currencyId" type="hidden" id="currency.currencyId" value="${param.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
			</li>
			<li>
				<label>操作类型：</label>
				<select name="operateType">
					<option <c:if test="${param.operateType =='' }">selected</c:if> value="">所有</option>
					<option <c:if test="${param.operateType =='1' }">selected</c:if> value="1">添加</option>
					<option <c:if test="${param.operateType =='2' }">selected</c:if> value="2">修改</option>
					<option <c:if test="${param.operateType =='3' }">selected</c:if> value="3">删除</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>品牌：</label>
				<input name="brand" type="text" id="brand" value="${param.brand }"/>
                <a class="btnLook" href="<%=path %>/merchantmgr/getBrandInfoList" width="1100" height="350" target="dialog" lookupGroup="" lookupPk="brand"  mask="true">查找带回</a>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>操作日期</label>
			 	<input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startDate']}" />
				--
			</li>
			<li >
			 	<input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['endDate']}" />
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
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="190" style="text-align: center;">
		<thead>
			<tr>
				<th>商户类型</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>操作类型</th>
				<th>卡种</th>
				<th>国家</th>
				<th>品牌</th>
				<th>通道</th>
				<th>是否生效</th>
				<th>操作人</th>
				<th>操作时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="id" rel="${info.id }" align="center">
					<td>${info.merType }</td>
					<td>${info.merNo }</td>
					<td>${info.terNo }</td>
					<td>
						<c:if test="${info.operateType=='1' }">添加</c:if>
						<c:if test="${info.operateType=='2' }">修改</c:if>
						<c:if test="${info.operateType=='3' }">删除</c:if>
					</td>
					<td>${info.cardType}</td>
					<td>${info.countryCode }</td>
					<td>${info.brand }</td>
					<td>${info.currencyName}</td>
					<td>${info.changeEnable}</td>
					<td>${info.modifyBy }</td>
					<td>${info.modifyDate }</td>
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