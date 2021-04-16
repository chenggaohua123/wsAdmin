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
<title>商户终端管理</title>
</head>
<style> 
</style> 
<script type="text/javascript">

</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/getMerchantTerList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantmgr/getMerchantTerList" method="post">
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
			<label>是否禁用：</label>
			<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=TER_STATUS" relValue="columnKey" selectedValue="${param.enabled }" relText="columnvalue"  name="enabled">
				<option value="">所有</option>
			</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>收款账户名：</label>
				<input type="text" name="accountName" value="${param.accountName }"/>
			</li>
			<li>
				<label>收款账户号：</label>
				<input type="text" name="accountNo" value="${param.accountNo }"/>
			</li>
			<li>
			<label>收款银行：</label>
			<input type="text" name="accountAddress" value="${param.accountAddress }"/>
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
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/goUpdateTerInfo?id={sid_role}" target="dialog" width="1000" height="550"  title="修改商户终端状态"  mask="true"><span>修改终端</span></a></li>
			<li class="line">line</li>
			<%-- <li><a class="edit" href="<%=path %>/merchantmgr/goMerchantRelAgent?id={sid_role}" target="dialog" width="450" height="300" mask="true" warn="请选择商户" rel="agentRelMerchant"><span>绑定代理商</span></a></li>
			<li class="line">line</li> --%>
			<!-- li><a class="add" href="<%=path %>/merchantmgr/goAddMerchantConfig?id={sid_role}" target="dialog" title="增加商户配置" mask="true"><span>增加配置信息</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/queryMerchantConfigList?id={sid_role}" target="dialog" width="1050" height="450" rel="queryConfig" mask="true" warn="请选择一个终端信息"><span>查看配置信息</span></a></li-->
			<li><a class="edit" href="<%=path %>/merchantmgr/queryTerInfoHis?id={sid_role}" target="dialog" width="1050" height="500" mask="true" warn="请选择一个终端信息"><span>查看历史修改数据</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/exportMerchant" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/goCopyMerchantTerInfo?id={sid_role}" target="dialog" mask="true" width="650" height="450" rel="asdasdsa" warn="请选择一个商户" ><span>一键开通</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/goUpdateSpareTerNo?id={sid_role}" target="dialog" width="900" height="450"  rel="updateSpareTerNo" mask="true" warn="请选择一个商户号"><span>终端切换</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/queryShopify?id={sid_role}" target="dialog" width="500" height="300"  title="shopify开关"  mask="true" warn="请选择一个商户号"><span>shopify开关</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="135" >
		<thead>
			<tr>
				<th style="word-break:break-all">商户号</th>
				<th style="word-break:break-all">终端号</th>
				<th style="word-break:break-all">备用终端号</th>
				<th style="word-break:break-all">使用备用终端号</th>
				<th style="word-break:break-all">商户名称</th>
				<th style="word-break:break-all">交易安全码</th>
				<th style="word-break:break-all">结算币种</th>
				<th style="word-break:break-all">产品类型</th>
				<th style="word-break:break-all">shopify开关</th>
				<th style="word-break:break-all">是否有效</th>
				<th style="word-break:break-all"> 创建时间</th>
				<th style="word-break:break-all">创建人</th>
				<th style="word-break:break-all">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="terInfo">
				<tr target="sid_role" rel="${terInfo.id }" >
					<td><a  href="<%=path %>/merchantmgr/queryTerInfo?id=${terInfo.id }" target="dialog" width="850" height="350" mask="true" title="终端详细信息">${terInfo.merNo }</a></td>
					<td>${terInfo.terNo }</td>
					<td>${terInfo.spareTerNo }</td>
					<td>${terInfo.isChangeToSpareTerNo=='0'?'<font color=red>否</font>':'<font color=green>是</font>'}</td>
					<td>${terInfo.merchantName }</td>
					<td>${terInfo.shaKey }</td>
					<td>${terInfo.settleCurrency }</td>
					<td>null</td>
					<%-- <td>${funcs:getStringColumnKey('TER_PRODUCT_TYPE',terInfo.productType ,terInfo.productType)}</td> --%>
					<td>null</td>
					<%-- <td>${funcs:getStringColumnKey('TER_SHOPIFY_ON_OFF',terInfo.shopifyOnOff,terInfo.shopifyOnOff)}</td> --%>
					<td>${funcs:getStringColumnKey('TER_STATUS',terInfo.enabled,terInfo.enabled)}</td>
					<td>${funcs:formatDate(terInfo.createDate,'yyyy-MM-dd HH:mm')} </td>
					<td>${terInfo.createby } </td>
					<td>${terInfo.remark } </td>
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