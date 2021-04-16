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
<title>邮箱附带信息列表</title>
</head>
<script type="text/javascript">
function onclickinfo(){
	var list= $('input:radio[name="sendId"]:checked').val();
	if(!list){
	    alert("请选中一条邮件附带信息");
	    return false;
	}
	var url = "<%=path %>/emailmgr/bindingEmailInfo?emailSubId="+list+"&id=${typeId}";
	 $.ajax({
         type: "POST",
         url: url,
         dateYtpe: "json",
         data: "",
         success: function(data){
         	alert("关联成功");
         	$.pdialog.closeCurrent();
         },
         error: function(data){
        	 alert("关联失败");
         }
      });
}
</script>
<body>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="delete" href="#" onclick="onclickinfo()"><span>关联邮件附带信息</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="35" style="text-align:center;" >
		<thead>
			<tr>
				<th></th>
				<th>tel</th>
				<th>fax</th>
				<th>Email</th>
				<th>replyEmail</th>
				<th>helpWebsite</th>
				<th>website</th>
				<th>有效性</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="info">
				<tr target="sid_user" rel="${info.id }">
					<td><input type="radio" name="sendId" value="${info.id }" ${emailSubId == info.id?'checked':'' }></td>
					<td>${info.tel }</td>
					<td>${info.fax }</td>
					<td>${info.email }</td>
					<td>${info.replyEmail}</td>
					<td>${info.helpWebsite }</td>
					<td>${info.website }</td>
					<td>${info.enabled==1?'有效':'无效'}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>