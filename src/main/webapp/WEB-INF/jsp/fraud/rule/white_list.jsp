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
<title>白名单管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/whiteListMagage/queryWhiteList">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>

<div class="pageHeader">
	<form id="whiteForm" onsubmit="return navTabSearch(this);" action="<%=path %>/whiteListMagage/queryWhiteList" method="post" rel="pagerForm" class="pageForm required-validate">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
			   <label>商户号：</label>
			   <input type="text" name="merNo" value="${param.merNo}" /> 
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}" />
			</li>
			
			<li>
	     		<label>规则类型：</label>
	              <select name="blackType" class="required combox">
	              		<option value="" ${param.blackType==''?'selected':'' }>全部</option>
			           <option value="IP" ${param.blackType=='IP'?'selected':'' }>IP</option>
			           <option value="email" ${param.blackType=='email'?'selected':'' }>邮箱</option>
			           <option value="cardNo" ${param.blackType=='cardNo'?'selected':'' }>卡号</option>
			     </select>
	         </li>
		</ul>
		<ul class="searchContent">
			
			<li>
	     		<label>处理方式：</label>
	              <select name="type" class="required combox">
	              			<option value="" ${param.type==''?'selected':'' }>全部</option>
			           <option value="0" ${param.type=='0'?'selected':'' }>例外</option>
			           <option value="1" ${param.type=='1'?'selected':'' }>只接受</option>
			     </select>
	         </li>
	         <li>
				<label>内容：</label>
				<input type="text" name="blackText" value="${param.blackText}" />
			</li>
			 <li>
				<label>创建人：</label>
				<input type="text" name="createdBy" value="${param.createdBy}" />
			</li>
		</ul>
		<ul>
		<li><font color="red">
		处理方式：<br>
		0. 例外 设置商户号白名单元素后，有白名单元素的订单不过风控，其他没有白名单元素的订单正常过风控<br>
		1. 只接受 设置商户号白名单元素后，只接受白名单元素，其他一律阻挡<br><br>
		前六后四卡号格式： 411111******1111</font>
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
			<li><a class="add" href="<%=path %>/whiteListMagage/goAddWhiteList" target="dialog" target="dialog" rel="addrule"  mask="true" width="600" ><span>添加</span></a></li>
			<li><a class="edit" href="<%=path %>/whiteListMagage/goUpdateWhiteList?id={id}" target="dialog" width="550" height="350" mask="true" warn="请选择修改的数据"><span>修改白名单</span></a></li>
			<li><a class="delete" href="<%=path %>/whiteListMagage/delWhiteList?id={id}" target="ajaxTodo" mask="true" rel="whiteListMagage/delWhiteList" title="确定删除该元素吗?"><span>删除</span></a></li>
			<li><a class="add" href="<%=path %>/whiteListMagage/showWhiteUpload" target="dialog" target="dialog" rel="addrule"  mask="true" width="600" height="400" ><span>批量添加</span></a></li>
			<li><a class="delete" href="javaScript:deleteWhiteInfo()" mask="true" title="确定删除元素吗?"><span>批量删除</span></a></li>
			<li><a class="add" href="<%=path %>/whiteListMagage/exportWhiteInfoList" target="dwzExport" targetType="navTab"rel="exportWhite" width="550" height="300" mask="true"><span>白名单信息导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="175" style="text-align: center;">
		<thead>
			<tr>
				<th><input type="checkbox" name="white_selectAll" id="white_selectAll" onclick="white_selectAll()"></th>
				<th>序号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>规则类型</th>
				<th>处理方式</th>
				<th>规则内容</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>修改人</th>
				<th>修改时间</th>
				<th>备注</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.result.data}" var="r">
				<tr target="id" rel="${r.id}">
					<td><input type="checkbox" name="white_ids" value="${r.id }" id="white_ids"></td>
					<td>${r.id}</td>
					<td>${r.merNo}</td>
					<td>
						${r.terNo}
					</td>
					<td>${r.blackType}</td>
					<td> ${r.type=='1'?'<font color=red>只接受</font>':'例外'}</td>
					<td>${r.blackText}</td>
					<td>${r.createdBy}</td>
					<td>${r.createDate}</td>
					<td>${r.lastUpdateBy}</td>
					<td>${r.lastUpdateDate}</td>
					<td>${r.remark}</td>
             		<td>${funcs:getStringColumnKey('RATESTATUS',r.enableFlag,'未知状态')}</td>
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
<script type="text/javascript">
function white_selectAll(){
	if ($("#white_selectAll").attr("checked")) {  
    	$("input[name='white_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='white_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
};

function deleteWhiteInfo(){
	if(confirm("是否删除！")){
		var str=""; 
		 $("input[name='white_ids']:checked").each(function(){ 
			str+="ids="+$(this).val()+"&"; 
		});
		if(!str){
			alert("请选择删除数据");
		}else{
			var url="<%=path %>"+"/whiteListMagage/delWhiteInfoList";
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:str,
				success:function(data){
					alert(data.message);
					$("#whiteForm").submit();
				}
			});
		}
	}
	
};
</script>
</body>
</html>