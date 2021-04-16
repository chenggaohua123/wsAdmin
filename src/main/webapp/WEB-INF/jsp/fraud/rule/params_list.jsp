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
<form id="pagerForm" method="post" action="fraud/rule/params_list">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="fraud/rule/params_list" method="post" rel="pagerForm" class="pageForm required-validate">
		<div class="searchBar">
			<ul class="searchContent">
			
						<li><label>参数ID：</label>
						<input type="text" name="paramId"
							value="${param.paramId}" />
						</li>
						<li>
						<label>参数名称：</label>
						<input type="text" name="paramName"
							value="${param.paramName}" />
						</li>
						<li>
						<label>参数显示名称：</label> <input type="text" name="paramDescName"
							value="${param.paramDescName}" />
						
						</li>
						<li>
						<label>创建时间：</label>
						<input type="text" name="createDateStart"
							value="${param.createDateStart}" class="date"
							dateFmt="yyyy-MM-dd" />
						<input type="text" name="createDateEnd"
							value="${param.createDateEnd}" class="date" dateFmt="yyyy-MM-dd" />
						
						</li>
					</ul>
			
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
			<li><a class="add" href="fraud/rule/goAddParams" target="dialog" width="500" mask="true"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="fraud/rule/goUpdateParams?id={paramId}" target="dialog" width="500" mask="true"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="fraud/rule/delParamByParamId?paramId={paramId}" target="ajaxTodo" title="请慎重删除,确定删除该参数吗?" mask="true"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="fraud/rule/goParams_setParamValue?paramId={paramId}" target="dialog" mask="true"  width="600" height="380"><span>设置参数值</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="116" style="text-align: center;">
		<thead>
			<tr>
				<th>参数ID</th>
				<th>参数名称</th>
				<th>参数显示名称</th>
				<th>参数类型</th>
				<th>参数状态</th>
				<th>状态</th>
				<th>创建日期</th>
				<th>创建人</th>
				<th>备注</th>
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
						<c:if test="${r.comFrom == '2' }">
							<a href="fraud/rule/params_Value?paramId=${r.paramId}" target="dialog" mask="true" target="dialog" style="color: blue;" minable="false" atl="查看参数的值" width="500" height="400">${r.type}</a>
						</c:if>
						<c:if test="${r.comFrom == '1' }">${r.type}</c:if>
					</td>
					<td>
						${r.status == '1' ? '生效':'待审核'}
					</td>
					<td>${r.comFrom == '1'?'程序':'常量'}</td>
					<td>${funcs:formatDate(r.createDate,'yyyy-MM-dd')}</td>
					<td>${r.createBy }</td>
					<td>${r.remark }</td>
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