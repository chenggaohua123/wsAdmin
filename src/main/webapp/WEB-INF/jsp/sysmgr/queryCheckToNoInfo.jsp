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
<title>虚拟银行卡信息</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/sysmgr/queryCheckToNoInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sysmgr/queryCheckToNoInfo" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>卡号：</label>
				<input type="text" name="checkToNo" value="${param.checkToNo}"/>
		 	</li>
		 	<li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
		 	<li>
		 		<label>商户号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
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
		<li><a class="add" href="<%=path %>/sysmgr/goAddCheckToNoInfo" target="dialog" width="500" height="600" mask="true"><span>添加</span></a></li>
		<li><a class="edit" href="<%=path %>/sysmgr/goAddCheckToNoInfo?id={id}" target="dialog" width="500" height="600" mask="true" warn="请选择要修改的内容"><span>修改</span></a></li>
		<li><a class="edit" href="<%=path %>/sysmgr/goBatchUpdateCardPayLimit" target="dialog" width="500" height="600" mask="true" ><span>批量修改支付次数</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>卡号</th>
				<th>币种</th>
				<th>消费最低金额</th>
				<th>消费最高金额</th>
				<th>当前余额</th>
				<th>交易次数</th>
				<th>成功交易次数</th>
				<th>每日支付次数</th>
				<th>有效</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="id" rel="${trans.id }">
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${trans.cardSixAndFour }</td>
					<td>${trans.currency }</td>
					<td>${trans.floor }</td>
					<td>${trans.ceil }</td>
					<td>${trans.balance }</td>
					<td>${trans.count }次</td>
					<td>${trans.successCount }次</td>
					<td>${trans.countLimit }次</td>
					<td>${trans.enabled=='0'?'无效':'有效' }</td>
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