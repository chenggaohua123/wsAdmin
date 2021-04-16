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
<title>状态变更记录</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/ratemgr/getListRateInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/ratemgr/getListRateInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
			</li>
			<li>
				<label>银行名称 ：</label>
				<input type="text" name="bankName" value="${param.bankName}"/>
			</li>
			<li>
			    <label>状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=RATESTATUS" relValue="columnKey" selectedValue="${param.status }" relText="columnvalue" name="status">
					<option value="">所有</option>
				</select>
			</li>
		</ul>
		<ul  class="searchContent">
		   <li   class="dateRange">
			  <label>创建时间</label>
			  <input type="text" name="createDateStart"  id = "createDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="createDateEnd"  id = "createDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateEnd}"/>		
			</li>
			<li>
				<label>创建人：</label>
				<input type="text" name="createBy" value="${param.createBy}"/>
			</li>
			<li>
				 <label>是否欧元区：</label>
			               <select class="combox" name="isEUR">
			               <option value="" ${param.isEUR==''?'selected':'' }>所有</option>
			               <option value="0" ${param.isEUR=='0'?'selected':'' }>不是</option>
			               <option value="1" ${param.isEUR=='1'?'selected':'' }>是</option>
			              </select>
			</li>
			<br>
			<font color='red'>注:单笔手续费币种默认以结算币种为准</font>
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
			<li><a class="add" href="<%=path %>/ratemgr/goAddRateInfo" target="dialog" rel="addCurrency" width="550" height="400" mask="true" rel="rateRelMerchant"><span>添加费率信息</span></a></li>
			<li><a class="edit" href="<%=path %>/ratemgr/goUpdateRateInfo?id={rateId}" target="dialog" width="550" height="400" mask="true" warn="请选择一个通道" rel="updateRelMerchantRate"><span>修改费率信息</span></a></li>
			<li><a class="add" href="<%=path %>/ratemgr/queryRateInfoLogById?rateId={rateId}"  target="dialog" width="1050" height="500"  mask="true" warn="请选择一个费率"><span>查看历史信息</span></a></li>
			<li><a class="add" href="<%=path %>/ratemgr/exportRateInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>费率信息导出</span></a></li>
		</ul>
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="142" style="text-align: center;">
			<thead>
				<tr>
					<th>商户号</th>
					<th>终端号</th>
					<th>卡种</th>
					<th>国家</th>
					<th>是否欧元区</th>
					<th>银行名称</th>
					<th>手续费费率</th>
					<th>保证金费率</th>
					<th>单笔手续费</th>
					<th>操作人</th>
					<th>操作时间</th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="rate">
					<tr  target="rateId" rel="${rate.id }"   >
						<td>${rate.merNo }</td>
						<td>${rate.terNo }</td>
						<td>${rate.cardType }</td>
						<td>${empty rate.countrys||rate.countrys==''?'所有':rate.countrys }</td>
					<td>${empty rate.isEUR||rate.isEUR=='0'?'不是':'是' }</td>
						<td>${empty rate.bankName?'所有':rate.bankName}</td>
						<td>${rate.merRate }</td>
						<td>${rate.bondRate }</td>
						<td>${rate.singleFee }</td>
						<td>${rate.createBy}</td>
						<td>${funcs:formatDate(rate.createDate,'yyyy-MM-dd HH:mm')} </td>
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