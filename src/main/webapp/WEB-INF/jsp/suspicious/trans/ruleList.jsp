<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%
	String path = request.getContextPath();
%>  
<body>
<form id="pagerForm" method="post" action="<%=path %>/suspicious/goRuleLits">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return dialogSearch(this);" action="<%=path %>/suspicious/goRuleLits" rel="pagerForm"  method="post" class="pageForm	">
	<div class="searchBar">
		<ul class="searchContent">
		 	<li>
		 		<label>规则ID：</label>
				<input type="text" name="ruleId" value="${param.ruleId}" />
		 	</li>
		</ul>
		<ul class="searchContent">
		 	<li>
		 		<label>参数名称：</label>
				<input type="text" name="ruleName" value="${param.ruleName}" />
		 	</li>
		</ul>
		<!-- 
		<label>创建时间: </label>
		<input type="text" name="statusDate" value="${param.statusDate}" class="date"  dateFmt="yyyy-MM-dd"/>
		<span class="limit">-</span>
		<input type="text" name="endDate" value="${param.endDate}" class="date"  dateFmt="yyyy-MM-dd"/>
		 -->
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">查询</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<div class="toolBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" multLookup="rules" lookupGroup="rule" warn="请选择规则">确定</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</div>
	<table class="table" width="125%" layoutH="190">
		<thead>
			<tr>
				<th>
					 <input type="checkbox" class="checkboxCtrl" group="rules" />
				</th>
				<th>规则ID</th>
				<th>规则名字</th>
				<th>参数描述</th>
				<th>规则匹配参数描述</th>
				<th>处理方式描述</th>
				<th>状态</th>
				<th>返回状态</th>
				<th>创建日期</th>
				<th>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.ruleList.data}" var="r" varStatus="var">
				<tr>
					<td>
						<input type="checkbox" name="rules" value="{id:'${r.ruleId}', name:'${r.ruleName }'}" />
					</td>
					<td>${r.ruleId}</td>
					<td>${r.ruleName}</td>
					<td>
						${r.paramDescName}
					</td>
					<td>
						${r.ruleParamValueDescName}
					</td>
					<td>
						${r.processClassName}
					</td>
					<td>${r.status=='1'?'生效':'失效'}</td>
					<td>
						${r.action=='1'?'接受':'' }
						${r.action=='2'?'拒绝交易':'' }
						${r.action=='3'?'ReView':'' }
					</td>
					<td>${funcs:formatDate(r.createDate,'yyyy-MM-dd')}</td>
					<td>${r.createBy }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
          <div class="pages">
          <span>显示</span>
          <select class="combox"  name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="20" ${requestScope.ruleList.pageSize == 20?'selected':''}>20</option>
				<option value="50" ${requestScope.ruleList.pageSize == 50?'selected':''}>50</option>
				<option value="100" ${requestScope.ruleList.pageSize == 100?'selected':''}>100</option>
				<option value="200" ${requestScope.ruleList.pageSize == 200?'selected':''}>200</option>
			</select>
            <span>共${requestScope.ruleList.total}条</span>
          </div>
        <div class="pagination" targetType="dialog" totalCount="${requestScope.ruleList.total}" numPerPage="${requestScope.ruleList.pageSize}" currentPage="${requestScope.ruleList.nowPage}">
        </div>
    </div>
</body>
</html>