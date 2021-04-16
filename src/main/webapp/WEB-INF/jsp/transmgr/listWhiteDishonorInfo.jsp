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
<title>白名单拒付信息列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/transmgr/whiteTransDishonor">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" id="fromInfo" action="<%=path %>/transmgr/whiteTransDishonor" method="post">
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
			<li>
				<label>规则内容：</label>
				<input type="hidden" name="type" value="1"/>
				<input type="text" name="blackText" value="${param.blackText}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
			</li>
			<li  class="dateRange">
			  <label>交易日期:</label>
			  <input type="text" name="dateStart"  id = "dateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['dateStart']}"/>
		      <span class="limit">-</span>
			  <input type="text" name="dateEnd"  id = "dateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['dateEnd']}"/>		
			</li>
		</ul>
		<ul class="searchContent">
			<li  class="dateRange">
			  <label>拒付通知日期:</label>
			  <input type="text" name="disCreateDateStart"  id = "disCreateDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['disCreateDateStart']}"/>
		      <span class="limit">-</span>
			  <input type="text" name="disCreateDateEnd"  id = "disCreateDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['disCreateDateEnd']}"/>		
			</li>
			<li  class="dateRange">
			  <label>伪冒通知日期:</label>
			  <input type="text" name="fakeCreateDateStart"  id = "fakeCreateDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['fakeCreateDateStart']}"/>
		      <span class="limit">-</span>
			  <input type="text" name="fakeCreateDateEnd"  id = "fakeCreateDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['fakeCreateDateEnd']}"/>		
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
			<li><a class="add" href="<%=path %>/transmgr/exprotWhiteTransDishonor" target="dwzExport" width="550" height="380" mask="true" rel="add"><span>导出</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="165" style="text-align:center;" >
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>规则内容</th>
				<th>流水号</th>
				<th>交易状态</th>
				<th>交易返回原因</th>
				<th>拒付录入日期</th>
				<th>伪冒录入日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr>
					<td>${info.merNo }</td>
					<td>${info.terNo }</td>
					<td><a target="dialog" title="规则内容详情" width="400" height="300" mask="true" href="<%=path %>/transmgr/queryWhiteInfo?whiteIds=${info.whiteIds }"> 规则内容详情</a></td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${info.tradeNo }"> ${info.tradeNo }</a></td>
					<td>
						<c:if test="${info.respCode=='00'}">
						<font color="green">${funcs:getStringColumnKey('RESP_INFO',info.respCode,info.respCode)}</font>
					</c:if><c:if test="${info.respCode!='00'}">
						<font color="red">${funcs:getStringColumnKey('RESP_INFO',info.respCode,info.respCode)}</font>
					</c:if>
					</td>
					<td>${info.respMsg }</td>
					<td>${funcs:formatDate(info.disCreatedDate, 'yyyy-MM-dd HH:mm:ss') }</td>
					<td>${funcs:formatDate(info.fakeCreatedDate, 'yyyy-MM-dd HH:mm:ss') }</td>
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