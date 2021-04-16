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
<title>商户费用录入</title>
<script type="text/javascript">
	
</script>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/faffmgr/searchMerchantFeeInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/faffmgr/searchMerchantFeeInfo" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
			<li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
		 	<li>
		 		<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
		 	</li>
		 	<li>
		 		<label>成本类型：</label>
				<select name="incomeType" class="combox">
					<option value="" ${param.incomeType==''?'selected':'' }>所有</option>
					<option value="0" ${param.incomeType=='0'?'selected':'' }>收入</option>
					<option value="1" ${param.incomeType=='1'?'selected':'' }>支出</option>
				</select>
		 	</li>
		</ul>
		<ul class="searchContent">
		 	<li>
		 		<label>录入人：</label>
				<input type="text" name="createBy" value="${param.createBy}"/>
		 	</li>
		 	<li>
		 		<label>费用类型：</label>
				<input type="text" name="feeType" value="${param.feeType}"/>
		 	</li>
		</ul>
		<ul class="searchContent">
		 	<li >
			  <label>录入日期</label>
			  <input type="text" name="settleDateStart"  id = "settleDateStart" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date" value="${param.settleDateStart}" />
			</li>
			<li>
			  <span class="limit"> -- </span>
			  <input type="text" name="settleDateEnd"  id = "settleDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss"  class="date" value="${param.settleDateEnd}"/>
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
			<li><a class="add" href="<%=path %>/faffmgr/exportMerchantFeeInfoInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
			<li><a class="add" href="<%=path %>/faffmgr/showUpdateMerchantFeeInfoPage" target="dialog" width="550" height="380" mask="true" rel="add"><span>添加</span></a></li>
			<li><a class="edit" href="<%=path %>/faffmgr/showUpdateMerchantFeeInfoPage?id={id}" target="dialog" width="550" height="380" mask="true" rel="add"><span>修改</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" id="tableList"   width="100%" targetType="navTab" layoutH="170" style="text-align: center;">
		<thead>
			<tr>
				<th>费用类型</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>收支类型</th>
				<th>费用金额</th>
				<th>备注</th>
				<th>收入时间</th>
				<th>录入人</th>
				<th>录入时间</th>
				<th>修改人</th>
				<th>修改日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="id" rel="${info.id }" >
					<td>${info.feeType}</td>
					<td>${info.merNo }</td>
					<td>${info.terNo }</td>
					<td>${info.incomeType==0?'收入':'支出' }</td>
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