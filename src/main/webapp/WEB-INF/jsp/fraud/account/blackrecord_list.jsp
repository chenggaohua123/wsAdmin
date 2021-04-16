<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BlackText Query  Record</title>
</head>
<body>
<form id="pagerForm" method="post" action="fraud/account/queryBlackTextLimitList">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="fraud/account/queryBlackTextLimitList" rel="pagerForm" method="post" class="pageForm required-validate">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						流水号：
					</td>
					<td>
						<input type="text" name="tradeNo" value="${param.tradeNo}" />
					</td>
					<td>
						风控账号：
					</td>
					<td>
						<input type="text" name="accountNo" value="${param.accountNo}" />
					</td>					
					<td>
						返回码：
					</td>
					<td>
						<input type="text" name="respCode" value="${param.respCode}" />
					</td>
				</tr>
				<tr>
					<td>
						shaCardNo：
					</td>
					<td>
						<input type="text" name="shaCardNo" value="${param.shaCardNo}" />
					</td>
					<td>
						Email：
					</td>
					<td>
						<input type="text" name="email" value="${param.email}" />
					</td>					
					<td>
						IP
					</td>
					<td>
						<input type="text" name="ipAddress" value="${param.ipAddress}" />
					</td>
				</tr>
				<tr>
					<td>
						 查询时间：
					</td>
					<td>
						<input type="text" name="createDateStart" value="${param.createDateStart}" class="date" dateFmt="yyyy-MM-dd" />
					</td>
					<td>
						<input type="text" name="createDateEnd"
						value="${param.createDateEnd}" class="date" dateFmt="yyyy-MM-dd" />
					</td>
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
	<table class="table" width="100%" layoutH="168">
		<thead>
			<tr>
				<th>流水号</th>
				<th>账号</th>
				<th>IPAddress</th>
				<th>SHACARDNO</th>
				<th>EMAIL</th>
				<th>WEBSITE</th>
				<th>UID</th>
				<th>police1</th>
				<th>police2</th>
				<th>police3</th>
				<th>police4</th>
				<th>查询时间</th>
				<th>返回码</th>
				<th>返回信息</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r" varStatus="var">
				<tr>
					<td>
						${r.tradeNo }
					</td>
					<td>${r.accountNo}</td>
					
					<td>${r.ipAddress}</td>
					<td>${r.shaCardNo}</td>
					<td>${r.email}</td>
					<td>${r.webSite}</td>
					<td>${r.uid}</td>
					<td>${r.police1}</td>
					<td>${r.police2}</td>
					<td>${r.police3}</td>
					<td>${r.police4}</td>
					<td>${funcs:formatDateTime(r.queryTime,'yyyy-MM-dd HH:mm:ss')}</td>
					<td>${r.respCode}</td>
					<td>${r.respMsg}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
          <div class="pages">
          <span>显示</span>
          <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
            <span>共${requestScope.result.total}条</span>
          </div>
        <div class="pagination" targetType="navTab" totalCount="${requestScope.result.total}" numPerPage="${requestScope.result.pageSize}" currentPage="${requestScope.result.nowPage}"></div>
    </div>
</div>
</body>
