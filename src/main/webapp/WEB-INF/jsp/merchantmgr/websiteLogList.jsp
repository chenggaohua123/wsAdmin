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
<title>操作历史记录</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/queryWebsiteLogList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);"  ID="AAASDAS" action="<%=path %>/merchantmgr/queryWebsiteLogList" method="post">
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
			<label>状态：</label>
			<select name="status">
				<option value="" ${param.status==''?'selected':'' }>--所有--</option>
				<option value="0" ${param.status=='0'?'selected':'' }>待审批</option>
				<option value="1" ${param.status=='1'?'selected':'' }>审批通过</option>
				<option value="2" ${param.status=='2'?'selected':'' }>审批驳回</option>
				<option value="3" ${param.status=='3'?'selected':'' }>审批取消</option>
			</select>
			</li>
			<li>
				<label>MCC：</label>
				<input type="text" name="MCC" value="${param.MCC }"/>
			</li>
			<li>
				<label>类型：</label>
				<select name="type">
					<option value="" ${param.type==''?'selected':'' }>--所有--</option>
					<option value="add" ${param.type=='add'?'selected':'' }>--添加--</option>
					<option value="update" ${param.type=='update'?'selected':'' }>--修改--</option>
					<option value="delete" ${param.type=='delete'?'selected':'' }>--删除--</option>
					<option value="check" ${param.type=='check'?'selected':'' }>--审核--</option>
				</select>
			</li>
		</ul>
		<ul  class="searchContent">
		    <li>
				<label>网址：</label>
				<input type="text" name="merWebSite" value="${param.merWebSite }"/>
			</li>
			 <li   class="dateRange">
			  <label>申请时间</label>
			  <input type="text" name="createDateStart"  id = "createDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="createDateEnd"  id = "createDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateEnd}"/>		
			</li>
			<li   class="dateRange">
			  <label>审批时间</label>
			  <input type="text" name="appDateStart"  id = "appDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.appDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="appDateEnd"  id = "appDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.appDateEnd}"/>		
			</li>
			<li   class="dateRange">
			  <label>操作时间</label>
			  <input type="text" name="operationStart"  id = "operationStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.operationStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="operationEnd"  id = "operationEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.operationEnd}"/>		
			</li>
		</ul>
		<ul  class="searchContent">
			<li>
				<label>网站语言：</label>
				<input type="text" name="merWebLanguage" value="${param.merWebLanguage }"/>
			</li>
			<li>
				<label>网站程序：</label>
				<input type="text" name="merWebProgram" value="${param.merWebProgram }"/>
			</li>
			<li>
				<label>品牌：</label>
				<input type="text" name="brand" value="${param.brand }"/>
			</li>
			<li>
				<label>产品：</label>
				<input type="text" name="product" value="${param.product }"/>
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
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="142" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>网址</th>
				<th>网站程序</th>
				<th>网站程序语言</th>
				<th>MCC</th>
				<th>品牌</th>
				<th>产品</th>
				<th>状态</th>
				<th>申请时间</th>
				<th>审批意见</th>
				<th>审批时间</th>
				<th>审批人</th>
				<th>备注</th>
				<th>操作类型</th>
				<th>操作人</th>
				<th>操作时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="merWeb">
				<tr target="sid_role" rel="${merWeb.id }"    align="center">
					<td>${merWeb.merNo }</td>
					<td>${merWeb.terNo }</td>
					<td>${merWeb.merWebSite }</td>
					<td>${merWeb.merWebProgram }</td>
					<td>${merWeb.merWebLanguage }</td>
					<td>${merWeb.MCC }</td>
					<td>${merWeb.brand }</td>
					<td>${merWeb.product }</td>
					<td>${funcs:getStringColumnKey('MERCHANTWEBSITESTATUS',merWeb.status,'未知状态')}</td>
					<td>${merWeb.createDate }</td>
					<td>${merWeb.message }</td>
					<td>${merWeb.appDate }</td>
					<td>${merWeb.appBy }</td>
					<td>${merWeb.remark }</td>
					<td>
						<c:if test="${merWeb.type == 'add'}">
							添加
						</c:if>
						<c:if test="${merWeb.type == 'update'}">
							修改
						</c:if>
						<c:if test="${merWeb.type == 'check'}">
							审核
						</c:if>
						<c:if test="${merWeb.type == 'delete'}">
							删除
						</c:if>
					</td>
					<td>${merWeb.operationBy }</td>
					<td>${merWeb.operationDate }</td>
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