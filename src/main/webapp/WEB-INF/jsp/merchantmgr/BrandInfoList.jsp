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
<title>产品带回</title>
</head>
<script type="text/javascript">
	function submitForm(){
		if($("#BrandInfobpname").val().replace(/\s/g,"")!=''){
			$("#getBrandInfo").submit();
		}
	}
</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/getBrandInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>


<div class="pageHeader">
	<form rel="pagerForm" id="getBrandInfo" method="post" action="<%=path %>/merchantmgr/getBrandInfo" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>品牌名称：</label>
				<input type="text" name="bpname" id="BrandInfobpname" value="${param.bpname}" />
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" multLookup="orgId" warn="请选择产品">选择带回</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>


<div class="pageContent">
	<div id="w_list_print">
	
	
	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="orgId" /></th>
				<th>品牌名称</th>
				<th>创建人</th>
				<th>创建时间</th>
			</tr>
		</thead>
		
		
		<tbody>
			<c:forEach items="${page.data }" var="terInfo">
				<tr>
				    <td><input type="checkbox" name="orgId"  value="{ brand:'${terInfo.bpname }'}"/></td>
					<td>${terInfo.bpname }</td>
					<td>${terInfo.createBy }</td>
					<td>${terInfo.createDate }</td>
					
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	</div>
	
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
				<option value="1000" ${param.numPerPage==1000?'selected':'' }>1000</option>
			</select>
			<span>条，共${page.total }条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
</body>
</html>