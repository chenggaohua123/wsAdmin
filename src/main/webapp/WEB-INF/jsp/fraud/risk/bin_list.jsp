<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Blacktext Element Config</title>
</head>
<body>
<form id="pagerForm" method="post" action="fraud/rule/queryBinList">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="fraud/rule/queryBinList" method="post" rel="pagerForm" class="pageForm required-validate">
		<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>Bin ID：</td>
						<td>
							<input type="text" name="id" value="${param.id}" />
						</td>
						<td>Bin 类型：</td>
						<td>
							<input type="text" name="type" value="${param.type}"/>
						</td>
						<td>开始字段：</td>
						<td>
							<input type="text" name="startNum" value="${param.startNum}" />
						</td>
						<td>结束字段：</td>
						<td>
							<input type="text" name="endNum" value="${param.endNum}" />
						</td>
					</tr>
					<tr>
						<td>创建时间：</td>
						<td><input type="text" name="createDateStart"
							value="${param.createDateStart}" class="date" dateFmt="yyyy-MM-dd" />
						</td>
						<td>
							<input type="text" name="createDateEnd"
							value="${param.createDateEnd}" class="date" dateFmt="yyyy-MM-dd" />
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
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
			<li><a class="add" href="fraud/rule/goAddBinInfo" mask="true" target="dialog" width="500"><span>添加</span></a></li>
			<!-- <li><a class="add" href="fraud/rule/goBatchAddBinInfo" mask="true" target="navTab" width="500"><span>批量添加</span></a></li> -->
			<li><a class="delete" href="fraud/rule/delBinInfotById?id={id}" target="ajaxTodo" mask="true" rel="123" title="确定删除该元素吗?"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="101%" layoutH="160">
		<thead>
			<tr>
				<th>Bin ID</th>
				<th>开始字段</th>
				<th>结束字段</th>
				<th>Bin类型</th>
				<th>创建日期</th>
				<th>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r"
				varStatus="var">
				<tr target="id" rel="${r.id}">
					<td>${r.id}</td>
					<td>${r.startNum}</td>
					<td>${r.endNum}</td>
					<td>${r.type}</td>
					<td>${r.createDate}</td>
					<td>${r.createBy }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
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