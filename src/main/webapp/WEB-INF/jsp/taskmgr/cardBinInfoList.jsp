<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>   

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>银行卡管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
</head>
<body>
 <form id="pagerForm" method="post" action="<%=path %>/taskmgr/queryCardBin">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
 </form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/taskmgr/queryCardBin" method="post">
	  <div class="searchBar">
		<ul class="searchContent">
		 <li>
			<label>发卡行名称</label>
			<input type="text" name="bankName" value="${param.bankName }"/>
		 </li>
		 <li>
			<label>卡名称</label>
			<input type="text" name="cardName" value="${param.cardName }"/>
		 </li>
		  <li>
			<label>卡类型</label>
			<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CARDBINTYPE" relValue="columnKey" selectedValue="${param.cardType }" relText="columnvalue" name="cardType">
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
		    <li><a class="add" href="<%=path %>/taskmgr/goAddCardInfo" target="dialog" width="450" height="350" mask="true"  mask="true"  ><span>添加银行卡信息</span></a></li>
		    <li><a class="add" href="<%=path %>/taskmgr/goUpdateCardInfo?id={id}" target="dialog" width="450" height="350" mask="true"><span>修改银行卡信息</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	   <table class="list" width="100%" targetType="navTab" layoutH="115">
		     <thead>
			      <tr>
						<th>发卡行名称</th>
						<th>卡名称</th>
						<th>卡bin</th>
						<th>卡类型</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="card">
						<tr  target="id" rel="${card.id }"    align="center">
							<td>${card.bankName }</td>
							<td>${card.cardName }</td>
							<td>${card.cardBin }</td>
							<td>${funcs:getStringColumnKey('CARDBINTYPE',card.cardType,'未知状态')}</td>
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