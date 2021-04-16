<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Params Config</title>
</head>
<body>
<form id="pagerForm" method="post" action="suspicious/queryParamList">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return dialogSearch(this);" action="suspicious/queryParamList" method="post" rel="pagerForm" class="pageForm required-validate">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
						<td>参数ID：</td>
						<td><input type="text" name="paramId"
							value="${param.paramId}" /></td>
						<td>参数名称：</td>
				
						<td> <input type="text" name="paramName" value="${param.paramName}" /></td>
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
	
	<table class="table" width="100%" layoutH="120">
		<thead>
			<tr>
				<th>参数ID</th>
				<th>参数名字</th>
				<th>参数显示名称</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r"
				varStatus="var">
				<tr target="paramId" rel="${r.paramId}">
					<td>${r.paramId}</td>
					<td>${r.paramName}</td>
					<td>${r.paramDescName}</td>
					<td>
					<a class="btnSelect" href="javascript:$.bringBack({paramId:'${r.paramId}', paramDescName:'${r.paramDescName }'})" title="查找带回">选择</a>
					</td>
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
        <div class="pagination" targetType="dialog" totalCount="${requestScope.result.total}" numPerPage="${requestScope.result.pageSize}" currentPage="${requestScope.result.nowPage}">
        </div>

    </div>
</div>
</body>
</html>