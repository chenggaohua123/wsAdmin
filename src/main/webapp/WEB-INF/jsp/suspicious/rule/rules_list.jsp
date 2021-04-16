<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path=request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rule Config</title>
</head>
<body>
<form id="pagerForm" method="post" action="suspicious/rules_list">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="suspicious/rules_list" method="post" rel="pagerForm" class="pageForm required-validate">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
			   <label>规则ID：</label>
			   <input type="text" name="ruleId" value="${param.ruleId}" /> 
			</li>
			<li>
				<label>规则名称：</label>
				<input type="text" name="ruleName" value="${param.ruleName}" />
			</li>
			<li>
				<label>状态：</label>
				<select name="status" class="combox">
					<option value="">全部</option>
					<option value="1" ${param.status == '1'?'selected':'' }>有效</option>
					<option value="0" ${param.status == '0'?'selected':'' }>无效</option>
				</select>
			</li>
			<li>
				<label>参数描述</label>
				<input type="text" name="paramName" value="${param.paramName}" />
			<li>
			 <li>
					<label>规则匹配参数描述</label>
					<input type="text" name="ruleParamName" value="${param.ruleParamName}" />
					</li>
					
					<li>
						<label>处理方式</label>
						<input type="text" name="processClass" value="${param.processClass}" />
					</li>
					<!-- 
					<li>
						<label>返回状态</label>
						<select name="action" class="required combox">
						   <option value="">全部</option>
				           <option value="2" ${param.action == '2'?'selected':'' }>拒绝交易</option>
				           <option value="1" ${param.action == '1'?'selected':'' }>接受交易</option>
				           <option value="3" ${param.action == '3'?'selected':'' }>Review</option>
			    		 </select>
					</li>
					 -->
					
					<li>
						<label>创建时间：</label>
						<input type="text" name="createDateStart" value="${param.createDateStart}" class="date" dateFmt="yyyy-MM-dd" /></li>
					<li>
					<input type="text" name="createDateEnd"
						value="${param.createDateEnd}" class="date" dateFmt="yyyy-MM-dd" />
					</li>
		</ul>
		<%-- <ul class="searchContent">
			<li>
				<label>汇率类型：</label>
				<select name="type">
					<option value="" ${param.type==''?'selected':'' }>所有</option>
					<option value="bus" ${param.type=='bus'?'selected':'' }>交易汇率</option>
					<option value="settle" ${param.type=='settle'?'selected':'' }>结算汇率</option>
				</select>
			</li>
			<li>
			 <label>是否有效：</label>
              <select name="enabled">
                <option value="" ${param.enabled==''?'selected':'' }>所有</option>
				<option value="1" ${param.enabled=='1'?'selected':'' }>有效</option>
				<option value="0" ${param.enabled=='0'?'selected':'' }>无效</option>
				</select>
			</li>
			
		</ul> --%>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>



<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/suspicious/goAddRules" target="dialog" target="dialog" rel="addrule"  mask="true" width="600" ><span>添加</span></a></li>
			<li><a class="edit" href="<%=path %>/suspicious/goUpdateRules?id={ruleId}" target="dialog" rel="updateRule" width="500" height="370" mask="true" ><span>修改</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="116" style="text-align: center;">
		<thead>
			<tr>
				<th>规则ID</th>
				<th>规则名字</th>
				<th>参数描述</th>
				<th>规则匹配参数描述</th>
				<th>处理方式描述</th>
				<th>状态</th>
				<!-- 
				<th>返回状态</th>
				 -->
				<th>创建日期</th>
				<th>创建人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r"
				varStatus="var">
				<tr target="ruleId" rel="${r.ruleId}">
					<td>${r.ruleId}</td>
					<td>${r.ruleName}</td>
					<td>
						<a href="suspicious/params_Value?paramId=${r.paramId}" target="dialog" mask="true" target="dialog" style="color: blue;" minable="false" atl="查看参数的值" width="500" height="400">${r.paramDescName}</a>
					</td>
					<td>
						<a href="suspicious/params_Value?paramId=${r.ruleParamValueId}" target="dialog" mask="true" target="dialog" style="color: blue;" minable="false" atl="查看参数的值" width="500" height="400">${r.ruleParamValueDescName}</a>
					</td>
					<td>
						<a href="suspicious/queryProcessClass?processClassId=${r.processClassId}" target="dialog" mask="true" target="dialog" style="color: blue;" minable="false" atl="查看参数的值" width="500" height="400">${r.processClassName}</a>
					</td>
					<td>${r.status=='1'?'生效':'失效'}</td>
					<!-- 
					<td>
						${r.action=='1'?'接受':'' }
						${r.action=='2'?'拒绝交易':'' }
						${r.action=='3'?'ReView':'' }
					</td>
					 -->
					<td>${funcs:formatDate(r.createDate,'yyyy-MM-dd')}</td>
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
</div>
</body>
</html>