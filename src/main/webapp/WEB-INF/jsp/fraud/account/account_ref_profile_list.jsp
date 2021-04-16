<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Account ref ruleprofile</title>
</head>
<body>
<form id="pagerForm" method="post" action="fraud/account/queryAccountRefProfileList">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="fraud/account/queryAccountRefProfileList" method="post" rel="pagerForm" class="pageForm required-validate">
		<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>商户号：</td>
						<td>
							<input type="text" name="merNo" value="${param.merNo}" />
						</td>
					
				
						<td>终端号:</td>
						<td>
							<input type="text" name="terNo" value="${param.terNo}" />
						</td>
				
				
						<td>创建时间：</td>
						<td><input type="text" name="createDateStart"
							value="${param.createDateStart}" class="date" dateFmt="yyyy-MM-dd" />
						</td>
						<td>
							<input type="text" name="createDateEnd"
							value="${param.createDateEnd}" class="date" dateFmt="yyyy-MM-dd" />
						</td>
					</tr>
				</table>
				<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="fraud/account/goAddProfileToAccount" rel="addProfileToAccount" target="dialog" width="500" height="370" mask="true"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="fraud/account/delAccountFromList?id={id}" target="ajaxTodo" title="确定删除该关联信息吗?"><span>删除</span></a></li>
			
			<li class="line">line</li>
			<li><a class="edit" href="fraud/account/goUpdateAccountRefProfileInfoById?id={id}" rel = "updateProfileToAccount" target="dialog" width="500" height="370" mask="true" ><span>修改</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="116" style="text-align: center;">
		<thead>
			<tr>
				<th>关联ID</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>集合名称</th>
				<th>状态</th>
				<th>创建日期</th>
				<th>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r"
				varStatus="var">
				<tr target="id" rel="${r.id}">
					<td>${r.id}</td>
					<td>${r.merNo}</td>
					<td>${r.terNo}</td>
					<td>${r.proFileName}</td>
					<td>${r.status == '1'?'有效':'无效'}</td>
					<td>${funcs:formatDate(r.createDate,'yyyy-MM-dd')}</td>
					<td>${r.createBy }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar">
          <div class="pages">
          <span>显示</span>
          <select class="combox"  name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" ${requestScope.result.pageSize == 20?'selected':''}>20</option>
				<option value="50" ${requestScope.result.pageSize == 50?'selected':''}>50</option>
				<option value="100" ${requestScope.result.pageSize == 100?'selected':''}>100</option>
				<option value="200" ${requestScope.result.pageSize == 200?'selected':''}>200</option>
			</select>
            <span>共${requestScope.result.total}条</span>
          </div>
        <div class="pagination" targetType="navTab" totalCount="${requestScope.result.total}" numPerPage="${requestScope.result.pageSize}" currentPage="${requestScope.result.nowPage}">
        </div>

    </div>
</div>
</body>
</html>