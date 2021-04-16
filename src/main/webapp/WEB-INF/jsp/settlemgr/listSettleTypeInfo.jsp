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
<title>结算方式管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/listSettleTypeInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/listSettleTypeInfo" method="post">
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
			<ul  class="searchContent">
			<li>
				<label>结算类型：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=SETTLETYPE" relValue="columnKey" selectedValue="${param.settleType }" relText="columnvalue" name="settleType">
					<option value="">所有</option>
				</select>
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
			<li><a class="add" href="<%=path %>/settlemgr/goAddSettleTypeInfo" target="dialog" width="550" height="400" mask="true"  rel="settleType" ><span>添加商户结算方式</span></a></li>
			<li><a class="edit" href="<%=path %>/settlemgr/goUpdateSettleTypeInfo?id={stId}" target="dialog" width="550" height="350" mask="true"  rel="settleType"  warn="请选择修改的数据"><span>修改商户结算方式</span></a></li>
			<li><a class="add" href="<%=path %>/settlemgr/listSettleTypeInfoLog" target="dialog" width="1000" height="600" mask="true"  rel="settleType1" ><span>查看历史记录</span></a></li>
			<li><a class="add" href="<%=path %>/settlemgr/exportSettleTypeInfoList" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="140" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>结算周期(天)</th>
				<th>结算类型</th>
				<th>保证金结算周期(天)</th>
				<th>是否有效</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>最后修改人</th>
				<th>最后修改时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="st">
				<tr  target="stId" rel="${st.id }"   >
					<td>${st.merNo }</td>
					<td>${st.terNo }</td>
					<td>${st.settleCycle}</td>
					<td>${funcs:getStringColumnKey('SETTLETYPE',st.settleType,'未知状态')}</td>
					<td>${st.bondCycle}</td>
					<td>${st.enabled==1?'有效':'无效'}</td>
					<td>${st.createBy}</td>
					<td>${st.createDate}</td>
					<td>${st.lastModify}</td>
					<td>${st.lastModifyDate}</td>
					<td>${st.remark}</td>
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