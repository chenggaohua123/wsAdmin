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
<title>汇率查询</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/exchangRate/queryMerchantExchangRateList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/exchangRate/queryMerchantExchangRateList" method="post">
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
			<li>
				<label>汇率类型：</label>
				<select name="type" class="combox">
					<option value="" ${param.type==''?'selected':'' }>所有</option>
					<option value="bus" ${param.type=='bus'?'selected':'' }>交易汇率</option>
					<option value="settle" ${param.type=='settle'?'selected':'' }>结算汇率</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>组名：</label>
				<input type="text" name="groupName" value="${param.groupName}"/>
			</li>
			<li>
			 <label>是否有效：</label>
              <select name="enabled" class="combox">
                <option value="" ${param.enabled==''?'selected':'' }>所有</option>
				<option value="1" ${param.enabled=='1'?'selected':'' }>有效</option>
				<option value="0" ${param.enabled=='0'?'selected':'' }>无效</option>
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
			<li><a class="add" href="<%=path %>/exchangRate/goAddMerchantExchangRate" target="dialog" width="550" height="400" mask="true" rel="settleType"><span>添加商户定制汇率</span></a></li>
			<li><a class="edit" href="<%=path %>/exchangRate/goUpdateMerchantExchangRate?id={rateId}" target="dialog" width="550" height="350" mask="true" warn="请选择修改的数据"><span>修改商户定制汇率</span></a></li>
			<li><a class="delete" href="<%=path %>/exchangRate/deleteMerchantExchangeRate?id={rateId}" target="ajaxTodo" warn="请选择删除的数据"><span>删除汇率</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="140" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>组名</th>
				<th>汇率类型</th>
				<th>修改人</th>
				<th>修改时间</th>
				<th>是否有效</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="merExchangRate">
				<tr  target="rateId" rel="${merExchangRate.id }"   >
					<td>${merExchangRate.merNo }</td>
					<td>${merExchangRate.terNo }</td>
					<td>${merExchangRate.groupName }</td>
					<td>${merExchangRate.type=='settle'?'结算汇率':'交易汇率'}</td>
					<td>${merExchangRate.createBy}</td>
					<td>${merExchangRate.updateDate}</td>
					<td>${merExchangRate.enabled==1?'有效':'无效'}</td>
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