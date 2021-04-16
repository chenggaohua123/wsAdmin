<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rule List</title>
</head>
<body>
<form id="pagerForm" method="post" action="fraud/rule/queryRefRuleProfileLits?profileId=${param.profileId }">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return dialogSearch(this);" action="fraud/rule/queryRefRuleProfileLits?profileId=${param.profileId }" rel="pagerForm"  method="post" class="pageForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					规则ID：<input type="text" name="ruleId" value="${param.ruleId}" /> 
					参数名称：<input type="text" name="ruleName" value="${param.ruleName}" />
					创建时间：<input type="text" name="statusDate" value="${param.statusDate}" class="date"  dateFmt="yyyy-MM-dd"/>
					-<input type="text" name="endDate" value="${param.endDate}" class="date"  dateFmt="yyyy-MM-dd"/>
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
			<li><a title="确实要添加这些记录吗?" target="selectedTodo" targetType="dialog" rel="rulesId" href="fraud/rule/delRulesFromRulesProFile?profileId=${param.profileId }" class="delete"><span>批量删除</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="140" style="text-align: center;">
		<thead>
			<tr>
				<th><input type="checkbox" group="rulesId" class="checkboxCtrl"></th>
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
			<c:forEach items="${requestScope.result.data}" var="r" varStatus="var">
				<tr>
					<td>
						<input type="checkbox" name="rulesId" value="${r.ruleId}">
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
	</div>
	<div class="panelBar">
          <div class="pages">
          <span>显示</span>
          <select class="combox"  name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
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