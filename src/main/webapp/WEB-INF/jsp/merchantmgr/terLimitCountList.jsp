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
<title>商户终端限额管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/terLimitInfoCountList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantmgr/terLimitInfoCountList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo }"/>
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
			<li><a class="add" href="<%=path %>/merchantmgr/addTerLimitCountGo" target="dialog" mask="true" width="650" height="500" title="增加终端配额信息"  rel="agentMerchant" ><span>增加配额</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/updateTerNoCountGo?id={sid_role}" target="dialog" mask="true" width="650" height="500" title="修改终端配额信息" ><span>修改配额</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/exportTerLimitCountInfo" target="dwzExport" mask="true" width="650" height="500"><span>导出配额信息</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/goLmitInfoLog?id={sid_role}" target="dialog" mask="true" width="750" height="500" title="终端配额历史信息"><span>查看历史记录</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="<%=path %>/merchantmgr/deleteTerLimitCountInfo?id={sid_role}" target="ajaxTodo" title="请慎重删除,确定删除该参数吗?" mask="true"><span>删除</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="116" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>卡种</th>
				<th>商户名称</th>
				<th>日限成功笔数</th>
				<th>日交易笔数</th>
				<th>日使用比例</th>
				<th>月限成功笔数</th>
				<th>月交易笔数</th>
				<th>月使用比例</th>
				<th>月限额总笔数</th>
				<th>月总笔数</th>
				<th>月总笔数比例</th>
				<th>操作人</th>
				<th>操作时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="limit">
				<tr target="sid_role" rel="${limit.id }" align="center">
					<td>${limit.merNo }</td>
					<td>${limit.terNo }</td>
					<td>${empty limit.cardType?'所有':limit.cardType }</td>
					<td>${limit.merchantName }</td>
					<td>${limit.dayTransAmountLimit }</td>
					<td>${limit.dayAmount }</td>
					<td>${limit.dayRate }</td>
					<td>${limit.monthTransAmountLimit }</td>
					<td>${limit.monthAmount }</td>
					<td>${limit.monthRate }</td>
					<td>${limit.monthCountLimit }</td>
					<td>${limit.monthCount }</td>
					<td>${limit.monthCountRate }</td>
					<td>${limit.upby }</td>
					<td>${funcs:formatDate(limit.updateDate,'yyyy-MM-dd HH:mm')} </td>
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