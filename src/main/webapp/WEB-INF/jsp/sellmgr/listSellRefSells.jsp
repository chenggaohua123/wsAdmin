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
function selectSellRefSellsAll(){  
    if ($("#selectSellRefSellsAll").attr("checked")) {  
    	$("input[name='sellMgrs']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='sellMgrs']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
}  

function deleteSellRefSells(){
	var str=""; 
	 $("input[name='sellMgrs']:checked").each(function(){ 
		str+="sellMgrs="+$(this).val()+"&"; 
	});
	if(!str){
		alert("请选择删除数据");
	}else if(confirm("确定要删除吗？")){
		var url="<%=path %>"+"/sellmgr/deleteSellRefSellsInfo";
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:str,
			success:function(data){
				alert(data.message);
				$("#sellRefSellsFromId").submit();
			}
		});
	}
}
</script>
<style type="text/css">

table
        {
            table-layout: fixed;
            width: 100%;
        }
        
        td
        {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            
        }
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询销售员绑定商户</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/sellmgr/listSellRefSells">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="sellRefSellsFromId" onsubmit="return navTabSearch(this);" action="<%=path %>/sellmgr/listSellRefSells" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>主管：</label>
				<input type="text" name="sellMgr" value="${param.sellMgr }"/>
			</li>
			<li>
				<label>员工：</label>
				<input type="text" name="sell" value="${param.sell }"/>
			</li>
			
			<!-- ${SESSION_AUTHENTICATION.userRefMerNo } -->
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
			<li><a class="add" href="<%=path %>/sellmgr/goAddSellRefSells" target="dialog" width="800" hight="600" title="添加" mask="true"><span>添加</span></a></li>
			<li><a class="edit" href="<%=path %>/sellmgr/goUpdateSellRefSells?sellMgr={id}" target="dialog" width="800" hight="600" title="修改" mask="true"><span>修改</span></a></li>
			<li><a class="edit" href="javascript:deleteSellRefSells()" ><span>批量删除</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th><input type="checkbox" name="selectSellRefSellsAll" id="selectSellRefSellsAll" onclick="selectSellRefSellsAll()"></th>
				<th>主管</th>
				<th>员工</th>
				<th>创建人</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr  target="id" rel="${info.sellMgr }">
					<td>
							<input type="checkbox" name="sellMgrs" value="${info.sellMgr}" id="ids">
					</td>
					<td>${info.sellMgrRealName }</td>
					<td>${info.sellsRealName }</td>
					<td>${info.createBy }</td>
					<td>${info.createDate }</td>
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