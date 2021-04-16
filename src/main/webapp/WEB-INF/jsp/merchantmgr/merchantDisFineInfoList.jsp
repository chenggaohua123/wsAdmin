<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.ruleList{
	width: 100%;
	clear: both;
	border: 0px solid black;
}
.chirld{
	float: left;
	width: 32.3%;
	padding-bottom: 5px;
	padding-top: 5px;
	border: 1px solid black;
}
</style>
<title>商户拒付扣款限制</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/queryMerchantDisFineList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantmgr/queryMerchantDisFineList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo }"/>
			</li>
			
			<li>
				<label>状态：</label>
				<select name="enabled">
					<option value="">所有</option>
					<option ${param.enabled=='1'?'selected':'' } value="1">有效</option>
					<option ${param.enabled=='0'?'selected':'' } value="0">无效</option>
				</select>
			</li>
		</ul>
		<font color='red'>
			注：1.显示规则中“ [ ”表示大于等于, “ ) ”表示小于。
			<br/>
			<br/>  2.显示规则中的币种为商户结算币种。
			<br/>
			<br/>  3.[x - ∞)表示条数大于等于x。
		</font>
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
			<li><a class="add" href="<%=path %>/merchantmgr/goAddMerchantDisFineInfo" target="dialog" width="470" height="500" mask="true" rel="disfine"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/goAddMerchantDisFineInfo?{mer}" target="dialog" width="470" height="500" warn="请选择一条记录" mask="true" rel="disfine"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="<%=path %>/merchantmgr/delMerchantDisFineInfo?{mer}" target="ajaxTodo" title="请慎重删除,确定删除该参数吗?" mask="true"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="175" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>规则</th>
				<th>状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<%--
				<th>最后修改人</th>
				<th>最后修改时间</th>
				 --%>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="mer" rel="id=${info.id }&merNo=${info.merNo }&terNo=${info.terNo}" align="center">
					<td>${info.merNo }</td>
					<td>${info.terNo }</td>
					<td>
						<div class="ruleList">
							<c:forEach items="${info.ruleList }" var="rule">
								<div class="ruleList">
									<div class="chirld">[${rule.start }&nbsp;-&nbsp;${empty rule.end?'∞':rule.end })</div>
									<div class="chirld">${rule.currency }</div>
									<div class="chirld">${rule.amount }</div>
								</div>
							</c:forEach>
						</div>
					</td>
					<td>
						${empty info.enabled?'':(info.enabled=='1'?'有效':'无效') }
					</td>
					<td>${info.createBy }</td>
					<td>${funcs:formatDate(info.createDate,'yyyy-MM-dd HH:mm:ss')}</td>
					<%--
					<td>${info.modify }</td>
					<td>${funcs:formatDate(info.modifyDate,'yyyy-MM-dd HH:mm:ss')}</td>
					 --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total }条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>

</div>
<script type="text/javascript">
</script>
</body>
</html>