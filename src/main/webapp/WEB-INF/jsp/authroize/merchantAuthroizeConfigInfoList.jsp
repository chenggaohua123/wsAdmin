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
<title>商户预授权配置信息列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantAuthroizeConfigInfo/getMerchantAuthroizeConfigInfoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantAuthroizeConfigInfo/getMerchantAuthroizeConfigInfoList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
			    <label>终端号：</label>
			    <input type="text" name="terNo" value="${param.terNo }">
			</li>
			<li>
				<label>状态：</label>
				<select class="combox" name="enable">
					<option value="">所有</option>
					<option value="0" ${param.enable=='0'?'selected':'' }>无效</option>
					<option value="1" ${param.enable=='1'?'selected':'' }>有效</option>
				</select>
			</li>
		</ul>
		<div class="subBar">
		<font color='red'>
			<br>注：如果没有对商户终端进行定制化配置，默认4天后对预授权成功的订单进行自动确认。
		</font>
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
			<li><a class="add" href="<%=path %>/merchantAuthroizeConfigInfo/goAddMerchantAuthroizeConfigInfo" target="dialog" width="450" height="250"  mask="true"  rel="agentMerchant" ><span>新增</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantAuthroizeConfigInfo/goUpdateMerchantAuthroizeConfigInfo?id={sid_role}" target="dialog" width="450" height="250"   mask="true" warn="请选择一个商户号"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="<%=path %>/merchantAuthroizeConfigInfo/deleteMerchantAuthroizeConfigInfo?id={sid_role}" target="ajaxTodo" width="450" height="250"  mask="true" warn="请选择一个商户号"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="145" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>预授权自动确认时间/天</th>
				<th>状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>修改人</th>
				<th>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="merchantAuthroizeConfigInfo">
				<tr target="sid_role" rel="${merchantAuthroizeConfigInfo.id }"    align="center">
					<td>${merchantAuthroizeConfigInfo.merNo }</td>
					<td>${merchantAuthroizeConfigInfo.terNo == '0'?'所有':merchantAuthroizeConfigInfo.terNo}</td>
					<td>${merchantAuthroizeConfigInfo.day}</td>
					<td>${merchantAuthroizeConfigInfo.enable=='0'?'<font color=red>无效</font>':'<font color=green>有效</font>'}</td>
					<td>${merchantAuthroizeConfigInfo.createBy }</td>
					<td>${funcs:formatDate(merchantAuthroizeConfigInfo.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${merchantAuthroizeConfigInfo.updateBy }</td>
					<td>${funcs:formatDate(merchantAuthroizeConfigInfo.updateDate,'yyyy-MM-dd HH:mm')} </td>
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