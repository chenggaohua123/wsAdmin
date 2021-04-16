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
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/getListMerchant">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantmgr/getListMerchant" method="post">
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
			  <label>销售员</label>
			  <input type="text" name="sales" value="${param.sales }"/>
			</li>
		</ul>
		<ul  class="searchContent">
			<li>
				<label>商户邮箱：</label>
				<input type="text" name="email" value="${param.email }"/>
			</li>
			 <li>
			  <label>qq号</label>
			  <input type="text" name="qq" value="${param.qq }"/>		
			</li>
			<li>
			   <label>商户地址：</label>
			   <input type="text" name="address" value="${param.address }"/>
			</li>
			
		</ul>
		<ul  class="searchContent">
			
			
			<li class="dateRange">
			   <label >商户注册日期：</label>
			   <input type="text" name="createDateStart"  id = "createDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateStart}"/>
			 	 <span class="limit">-</span>
			    <input type="text" name="createDateEnd"  id = "createDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateEnd}"/>
			</li>
				<li class="dateRange">
			  <label>合同失效日期</label>
			  <input type="text" name="expireDateStart"  id = "createDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.expireDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="expireDateEnd"  id = "createDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.expireDateEnd}"/>		
			</li>
			<li class="dateRange">
				<label>合同生效日期：</label>
				<input type="text" name="activationDateStart"  id = "createDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.activationDateStart}"/>
			 	 <span class="limit">-</span>
			    <input type="text" name="activationDateEnd"  id = "createDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.activationDateEnd}"/>
			</li>
		</ul>
		<ul  class="searchContent">
			 <li>
			   <label>开户状态：</label>
			   <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTSTATUS" relValue="columnKey" selectedValue="${param.accountStatus }" relText="columnvalue" name="enabled">
					<option value="">所有</option>
				</select>
			</li>
			<li>
			   <label>商户类别：</label>
			    <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${param.type }" relText="columnvalue" name="type">
					<option value="">所有</option>
				</select>
			</li>
			<li>
				<label>通道类型：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHNAT_CURRENCY_TYPE" relValue="columnKey" selectedValue="${param.merCurrencyType }" relText="columnvalue" name="merCurrencyType">
				<option value="">所有</option>
			   </select>
			</li>
			<li>
			
		</ul>
		<ul  class="searchContent">
			 <li   class="dateRange">
			  <label>行业类型</label>
			  <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=INDUSTRYTYPE" relValue="columnKey" selectedValue="${param.industry }" relText="columnvalue" name="industry">
				<option value="">所有</option>
			   </select>
			</li>
			<li  class="dateRange">
			<label>直连状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANT_DIR_STATUS" relValue="columnKey" selectedValue="${param.dirStatus }" relText="columnvalue" name="dirStatus">
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
			<li><a class="add" href="<%=path %>/merchantmgr/goAddTerInfo?id={sid_role}" target="dialog" mask="true" width="1000" height="450" warn="请选择一个商户" ><span>增加终端</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/goUpdateMerchant?id={sid_role}" target="dialog" width="850" height="400" warn="请选择一个商户" mask="true"><span>资料修改</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/queryMerchantById?id={sid_role}" target="dialog" width="1200" height="400" warn="请选择一个商户" mask="true"><span>详情查询</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/queryMerchantLogByMerNoList?id={sid_role}" target="dialog" width="1100" height="550" mask="true" warn="请选择一个商户"><span>查看历史修改数据</span></a></li>
			<li class="line"></li>
			<li><a class="icon" href="javascript:$.printBox('w_list_print')"><span>打印信息</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/merchantExportTrans" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>商户导出</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/goReSetPass?id={sid_role}" target="dialog" mask="true" width="650" height="300" warn="请选择一个商户" ><span>密码重置</span></a></li>
			<li class="line">line</li>
			
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="215" style="text-align: center;">
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
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="merchant">
				<tr target="sid_role" rel="${merchant.id }"    align="center">
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