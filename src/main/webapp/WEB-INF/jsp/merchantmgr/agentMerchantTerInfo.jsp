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
<title>商户列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/goAgentMerchantInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dialogSearch(this);" action="<%=path %>/merchantmgr/goAgentMerchantInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>商户名称：</label>
				<input type="text" name="merchantName" value="${param.merchantName }"/>
			</li>
			<li>
			<label>商户联系人：</label>
				<input type="text" name="linkName" value="${param.linkName }"/>
			</li>
			<li>
			<label>商户电话：</label>
				<input type="text" name="phoneNo" value="${param.phoneNo }"/>
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
			<li><a class="edit" href="<%=path %>/merchantmgr/saveAgentMerchantInfo?agentNo=${agentInfo.agentNo }" target="selectedTodo" targetType="dialog"  rel="merchantNo"><span>保存</span></a></li>
		</ul>
	</div>
	<%-- <div id="w_list_print">targetType="navTab" --%>
	<table class="list" width="100%" targetType="dialogTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>商户名称</th> 
				<th>开户状态</th>
				<th>登录状态</th>
				<th>直连状态</th>
				<th>注册日期</th>
				<th>合同生效日期</th>
				<th>合同失效信息</th>
				<th>销售员</th>
				<th>OA业务单号</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="merchant">
				<tr target="sid_role" rel="${merchant.id }" align="center">
					<td>${merchant.merNo }</td>
					<td>${merchant.merchantName }</td>
					<td>${funcs:getStringColumnKey('MERCHANTSTATUS',merchant.enabled,merchant.enabled)}</td>
					<td>${funcs:getStringColumnKey('MERCHANT_ACCOUNT_STATUS',merchant.accountStatus,merchant.accountStatus)}</td>
					<td>${funcs:getStringColumnKey('MERCHANT_DIR_STATUS',merchant.dirStatus,merchant.dirStatus)}</td>
					<td>${funcs:formatDate(merchant.regDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${funcs:formatDate(merchant.activationDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${funcs:formatDate(merchant.expireDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${merchant.sales }</td>
					<td>${merchant.oaOrderNo }</td>
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({merchantNo:'${merchant.merNo }'})" title="查找带回">选择</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%-- </div> --%>
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total }条</span>
		</div><%-- targetType="navTab" --%>
		<div class="pagination" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>

</div>
</body>
</html>