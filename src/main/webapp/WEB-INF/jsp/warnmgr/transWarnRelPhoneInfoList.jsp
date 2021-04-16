<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>  

<%
	String path = request.getContextPath();
%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rule List</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/warnmgr/goTransWarnRelPhone">
	<input type="hidden" name="pageNum" value="${requestScope.result.nowPage}" /> 
	<input type="hidden" name="numPerPage" value="${requestScope.reslut.pageSize}" /> 
	<input type="hidden" name="type" value="${requestScope.type}" />
</form>
<div class="pageHeader">
	<form onsubmit="return dialogSearch(this);" action="<%=path %>/warnmgr/goTransWarnRelPhone" rel="pagerForm"  method="post" class="pageForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户名: </td>
					<td>
						<input type="text" id="userName" name="userName" value="${param.userName }" />
						<input type="hidden" id="systemId" name="systemId" value="1" />
						<%--
						<input type="hidden" name="warnId" value="${param.warnId }" />
						 --%>
						<input type="hidden" id="warnId" name="warnId" value="${param.warnId }" />
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
			 <li><a class="add" href="javascript:addPhoneInfo()" id="addPhoneInfo" mask="true"  rel="dels" ><span>批量添加</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="140" style="text-align: center;">
		<thead>
			<tr>
				<th><input type="checkbox" name="phone_selectAll" id="phone_selectAll" onclick="phone_selectAll()"></th>
				<th>姓名</th>
				<th>邮箱</th>
				<th>电话</th>
				<%--
				<th>关联预警</th>
				 --%>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="phone">
				<tr  target="id" rel="${phone.id }" align="center">
					<td><input type="checkbox" name="phone_ids" value="${phone.id }" id="phone_ids" <c:if test="${phone.checked==true }">checked</c:if> ></td>
					<td>${phone.userName }</td>
					<td>${phone.email }</td>
					<td>${phone.phoneNo }</td>
					<%--
					<td>
						<a target="dialog" title="交易明细" width="500" height="300" mask="true" href="<%=path%>/warnmgr/queryWarnTranInfo?warnIds=${phone.warnId }">查看交易预警信息</a>
					</td>
					 --%>
					<td>${phone.createDate }</td>
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
<script type="text/javascript">
 function phone_selectAll(){
	if ($("#phone_selectAll").attr("checked")) {  
    	$("input[name='phone_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='phone_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
};

function addPhoneInfo(){
	if(confirm("是否添加?")){
		var str="";
		var warnId=$('#warnId').val();
		if(warnId!=undefined && warnId!=null && warnId!=''){
			str += ('warnId='+warnId+'&');
		}
		$("input[name='phone_ids']:checked").each(function(){ 
			str+="ids="+$(this).val()+"&"; 
		});
		if(!str){
			alert("请选择删除数据");
		}else{
			var url="<%=path %>"+"/warnmgr/addTransWarnRelPhone?"+str;
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:'text',
				success:function(data){
					alert(data.message);
					$("#PhoneForm").submit();
				}
			});
		}
	}
	
};
</script>
</body>
</html>