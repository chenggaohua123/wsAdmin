<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Risk Element Config</title>
</head>
<body>
<form id="pagerForm" method="post" action="fraud/risk/riskElement_list">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="fraud/risk/riskElement_list" method="post" rel="pagerForm" class="pageForm required-validate">
		<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>元素ID：</td>
						<td><input type="text" name="elementId"
							value="${param.elementId}" /></td>
						<td>元素类型：</td>
						<td style="width: 100px;">
							<select name="elementType" " class="required combox">
								<option value="" >全部</option>
								<option value="IP" ${param.elementType=='IP'?'selected':'' }>IP</option>
								<option value="CARDNO"
									${param.elementType=='CARDNO'?'selected':'' }>卡号</option>
								<option value="EMAIL"
									${param.elementType=='EMAIL'?'selected':'' }>邮箱</option>
								<option ${param.elementType=='UID'?'selected':'' } value="UID">UID</option>
					            <option ${param.elementType=='POLICE1'?'selected':'' } value="POLICE1">POLICE1</option>
					            <option ${param.elementType=='POLICE2'?'selected':'' } value="POLICE2">POLICE2</option>
					            <option ${param.elementType=='POLICE3'?'selected':'' } value="POLICE3">POLICE3</option>
					            <option ${param.elementType=='POLICE4'?'selected':'' } value="POLICE4">POLICE4</option>
							</select></td>
						<td>元素值：</td>
						<td><input type="text" name="elementValue"
							value="${param.elementValue}" /></td>
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
			<li><a class="add" href="fraud/risk/goAddRiskElement" mask="true" target="dialog" width="500"><span>添加</span></a></li>
			<li><a class="add" href="fraud/risk/goBatchAddRiskElementInfo" mask="true" target="navTab" width="500"><span>批量添加</span></a></li>
			<li><a class="delete" href="fraud/risk/delRiskElementById?elementId={elementId}" target="ajaxTodo" title="确定删除该元素吗?"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="98%" layoutH="138">
		<thead>
			<tr>
				<th>元素ID</th>
				<th>元素类型</th>
				<th>元素值</th>
				<th>创建日期</th>
				<th>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r"
				varStatus="var">
				<tr target="elementId" rel="${r.elementId}">
					<td>${r.elementId}</td>
					<td>${r.elementType}</td>
					<td>${r.elementValue}</td>
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