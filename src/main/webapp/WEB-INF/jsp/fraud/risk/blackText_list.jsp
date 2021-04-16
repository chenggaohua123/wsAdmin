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
<form id="pagerForm" method="post" action="fraud/rule/queryBlackInfoList">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="fraud/rule/queryBlackInfoList" method="post" rel="pagerForm" class="pageForm required-validate">
		<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>黑名单ID：</td>
						<td><input type="text" name="blackId"
							value="${param.blackId}" /></td>
						<td>黑名单类型：</td>
						<td>
							<select name="blackType"  class="required combox">
								<option value="" >全部</option>
								<option value="IP" ${param.blackType=='IP'?'selected':'' }>IP</option>
								<option value="CARDNO"
									${param.blackType=='CARDNO'?'selected':'' }>卡号</option>
								<option value="EMAIL"
									${param.blackType=='EMAIL'?'selected':'' }>邮箱</option>
								<option value="sixAndFourCardNo"
									${param.blackType=='sixAndFourCardNo'?'selected':'' }>卡前六后四</option>
								<option ${param.blackType=='WEBSITE'?'selected':'' } value="WEBSITE">WEBSITE</option>
								<option ${param.blackType=='BANK'?'selected':'' } value="BANK">发卡行</option>
								<option ${param.blackType=='exactID'?'selected':'' } value="exactID">设备ID</option>
							</select>
						</td>
						<td>黑名单内容：</td>
						<td>
							<input type="text" name="blackText" value="${param.blackText}" />
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
						<td>商户号</td>
						<td><input type="text" name="merNo"
							value="${param.merNo}" /></td>
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
			<li><a class="add" href="fraud/rule/goAddBlackText" mask="true" target="dialog" width="500"><span>添加</span></a></li>
			<!-- <li><a class="add" href="fraud/rule/goBatchAddBlackText" mask="true" target="navTab" width="500"><span>批量添加</span></a></li> -->
			<li><a class="delete" href="fraud/rule/delBlackTextById?blackId={blackId}" target="ajaxTodo" title="确定删除该元素吗?"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="101%" layoutH="160">
		<thead>
			<tr>
				<th>黑名单ID</th>
				<th>黑名单类型</th>
				<th>黑名单内容</th>
				<th>创建日期</th>
				<th>创建人</th>
				<th>商户号</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r"
				varStatus="var">
				<tr target="blackId" rel="${r.blackId}">
					<td>${r.blackId}</td>
					<td>${r.blackType}</td>
					<td>${r.blackText}</td>
					<td>${r.createDate}</td>
					<td>${r.createBy }</td>
					<td>${r.merNo }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
          <div class="pages">
          <span>显示</span>
          <select class="combox"  name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10" ${requestScope.result.pageSize == 20?'selected':''}>10</option>
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