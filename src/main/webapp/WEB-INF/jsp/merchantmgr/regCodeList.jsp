<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
function regCodeListSelectAll(){
	if ($("#regCodeListSelectAll").attr("checked")) {  
    	$("input[name='regCodeListSelectAll_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='regCodeListSelectAll_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
};

function deleteRegCodeInfo(){
	var str=""; 
	 $("input[name='regCodeListSelectAll_ids']:checked").each(function(){ 
		str+="ids="+$(this).val()+"&"; 
	});
	if(!str){
		alert("请选择需要删除的数据");
	}else{
		var flag = confirm("确定删除？");
		if(flag){
			var url="<%=path %>"+"/merchantmgr/deleteRegCodeInfo?"+str;
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:'text',
				success:function(data){
					alert(data.message);
					$("#regCodeForm").submit();
				}
			});
		}
	}
}

</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推荐码管理</title>
</head>

<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/regCodeMgrList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="regCodeForm" onsubmit="return navTabSearch(this);" action="<%=path %>/merchantmgr/regCodeMgrList" method="post">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>邮箱：</label>
					<input type="text" name="email" value="${param.email }"/>
				</li>
				<li>
					<label>推荐码：</label>
					<input type="text" name="regCode" value="${param.regCode }"/>
				</li>
			</ul>
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
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/goAddRegCode" target="dialog" width="500" height="250"  title="添加推荐码"  mask="true"><span>添加推荐码</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="javaScript:deleteRegCodeInfo()"><span>删除推荐码</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th><input type="checkbox" id="regCodeListSelectAll" onclick="regCodeListSelectAll()"></th>
				<th>邮箱</th>
				<th>推荐码</th>
				<th>是否已注册</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>商户号</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="regCode">
				<tr>
					<td>
						<input type="checkbox" name="regCodeListSelectAll_ids" value="${regCode.id}" id="ids">
					</td>
					<td>${regCode.email }</td>
					<td>${regCode.regCode }</td>
					<td>${regCode.status == '1'?'已使用':'未使用' }</td>
					<td>${regCode.createBy }</td>
					<td>${funcs:formatDate(regCode.createDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${regCode.merNo }</td>
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
</body>
</html>