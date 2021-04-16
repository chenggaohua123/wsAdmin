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
<title>工作管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
</head>
<body>
 <form id="pagerForm" method="post" action="<%=path %>/taskmgr/queryTaskInfoLogList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
 </form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/taskmgr/queryTaskInfoLogList" method="post">
	  <div class="searchBar">
		<ul class="searchContent">
		 <li>
			<label>工作名称</label>
			<input type="text" name="jobName" value="${param.jobName }"/>
		 </li>
		 <li>
			<label>工作组</label>
			<input type="text" name="jobGroup" value="${param.jobGroup }"/>
		 </li>
		  <li>
			<label>触发器名字</label>
			<input type="text" name="triggerName" value="${param.triggerName }"/>
		  </li>
		</ul>
		<ul  class="searchContent">
		 
		  <li>
			<label>触发器组名字</label>
			<input type="text" name="triggerGroupName" value="${param.triggerGroupName }"/>
		 </li>
		 <li>
			<label>处理类型</label>
			<input type="text" name="processClass" value="${param.processClass }"/>
		 </li>
		 <li>
		   <label>状态码</label>
			<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=TASKSTAUTS" relValue="columnKey" selectedValue="${param.status }" relText="columnvalue" name="status">
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
	<div id="w_list_print">
	   <table class="list" width="100%" targetType="navTab" layoutH="115">
		<thead>
			<tr>
				<th>工作ID</th>
				<th>工作名称</th>
				<th>工作组</th>
				<th>触发器名字</th>
				<th>触发器组名字</th>
				<th>处理类型</th>
				<th>状态码</th>
				<th>上次执行时间</th>
				<th>备注</th>
				<th>cron表达式</th>
			</tr>
		</thead>
			<tbody>
				<c:forEach items="${page.data }" var="taskInfo">
						<tr align="center">
							<td>${taskInfo.id }</td>
							<td>${taskInfo.jobName }</td>
							<td>${taskInfo.jobGroup }</td>
							<td>${taskInfo.triggerName }</td>
							<td>${taskInfo.triggerGroupName }</td>
							<td>${taskInfo.processClass }</td>
							<td>${funcs:getStringColumnKey('TASKSTAUTS',taskInfo.status,'未知状态')}</td>
							<td>${taskInfo.lastexcutime }</td>
							<td>${taskInfo.remark }</td>
							<td>${taskInfo.cronExpression }</td>
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