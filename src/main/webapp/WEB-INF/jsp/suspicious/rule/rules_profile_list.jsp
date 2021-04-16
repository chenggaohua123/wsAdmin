<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>规则集合列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="suspicious/queryRuleProfile">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="suspicious/queryRuleProfile" method="post" rel="pagerForm" class="pageForm required-validate">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
						<td>集合ID：</td>
						<td><input type="text" name="profileId"
							value="${param.profileId}" /></td>
						<td>集合名称：</td>
						<td><input type="text" name="profileName"
							value="${param.profileName}" /></td>
						
						<td>创建时间：</td>
						<td><input type="text" name="createDateStart"
							value="${param.createDateStart}" class="date"
							dateFmt="yyyy-MM-dd" /></td>
						<td><input type="text" name="createDateEnd"
							value="${param.createDateEnd}" class="date" dateFmt="yyyy-MM-dd" />
						</td>
						<td>状态：</td>
						<td><select name="status" class="combox">
								<option value="">全部</option>
								<option value="1" ${param.status == '1'?'selected':'' }>有效</option>
								<option value="0" ${param.status == '0'?'selected':'' }>无效</option>
						</select></td>
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
			<li><a class="add" href="suspicious/goAddRulesProFile" target="dialog" mask="true" width="600" ><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="suspicious/goUpdateRulesProFile?profileId={profileId}" target="dialog" width="500" height="370" mask="true" ><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="suspicious/goRefRuleProfileLits?profileId={profileId}" target="dialog" width="800" height="500" mask="true" ><span>添加规则到集合</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="suspicious/queryRefRuleProfileLits?profileId={profileId}" target="dialog" width="800" height="500" mask="true" ><span>查询集合关联规则</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="116" style="text-align: center;">
		<thead>
			<tr>
				<th>集合ID</th>
				<th>集合名字</th>
				<th>状态</th>
				<th>创建日期</th>
				<th>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r"
				varStatus="var">
				<tr target="profileId" rel="${r.proFileId}">
					<td>${r.proFileId}</td>
					<td>${r.proFileName}</td>
					<td>${r.status=='1'?'生效':'失效'}</td>
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