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
<title>卡bin信息</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/sysmgr/queryCardBinInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sysmgr/queryCardBinInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>卡bin(前六)：</label>
				<input type="text" name="bin" value="${param.bin }"/>
			</li>
			<li>
				<label>卡种：</label>
				<select class="combox" name="brand"> 
					<option value="" ${param.brand==''?'selected':'' }>所有</option>
					<option value="VISA" ${param.brand=='VISA'?'selected':'' }>VISA</option>
					<option value="MASTERCARD" ${param.brand=='MASTERCARD'?'selected':'' }>MASTERCARD</option>
					<option value="JCB" ${param.brand=='JCB'?'selected':'' }>JCB</option>
					<option value="DISCOVER" ${param.brand=='DISCOVER'?'selected':'' }>DISCOVER</option>
					<option value="AMEX" ${param.brand=='AMEX'?'selected':'' }>AMEX</option>
				</select>
			</li>
			<li>
				<label>国家编码：</label>
				<input type="text" name="country_code" value="${param.country_code }"/>
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
			<li><a class="add" href="<%=path %>/sysmgr/goAddCardBinInfo" target="dialog" width="550" height="550" mask="true"><span>添加</span></a></li>
			<li><a class="edit" href="<%=path %>/sysmgr/goAddCardBinInfo?id={sid_role}" target="dialog" width="550" height="550" mask="true" warn="请选择一条记录"><span>修改</span></a></li>
		<li><a class="add" href="<%=path %>/sysmgr/goBatchUpdateCardBinInfo" target="dialog" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>批量修改</span></a></li>
		</ul>
		
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
		<tr>
				<th></th>
				<th colspan="5">binlist.net</th>
				<th colspan="5">bins.pro</th>
			</tr>
			<tr>
				<th>bin</th>
				<th>卡种</th>
				<th>国家编码</th>
				<th>国家</th>
				<th>发卡行</th>
				<th>卡类型</th>
				<th>国家</th>
				<th>卡种</th>
				<th>卡类型</th>
				<th>Level</th>
				<th>发卡行</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="role">
				<tr target="sid_role" rel="${role.id }">
					<td>${role.bin }</td>
					<td>${role.brand }</td>
					<td>${role.country_code }</td>
					<td>${role.country_name }</td>
					<td>${role.bank }</td>
					<td>${role.card_type }</td>
					<td>${role.country }</td>
					<td>${role.vendor }</td>
					<td>${role.type }</td>
					<td>${role.level }</td>
					<td>${role.proBank }</td>
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