<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Params</title>
</head>
<body>
<div class="pageContent">
	 <form method="post" action="suspicious/updateParams" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	     <div class="pageFormContent" layoutH="56">
	     	<input name="paramId" type="hidden" value="${result.paramId }">
	         <p>
	             <label>参数名称：</label>
	             <input name="paramName" value="${result.paramName }" type="text" size="30" class="required"/>
	         </p>
	         <p>
	             <label>参数显示名称：</label>
	             <input name="paramDescName" value="${result.paramDescName }" type="text" size="30" class="required"/>
	         </p>
	         <p>
	         	<label>参数类型：</label>
	              <select name="type" class="required combox">
			           <option value="String" ${result.type == 'String'?'selected':'' }>String</option>
			           <option value="List" ${result.type == 'List'?'selected':'' }>List</option>
			           <option value="Table" ${result.type == 'Table'?'selected':'' }>Table</option>
			     </select>
	         </p>
	         <p>
	         	<label>参数来源：</label>
	              <select name="comFrom" class="required combox">
			           <option value="1" ${result.comFrom == '1'?'selected':'' }>程序</option>
			           <option value="2" ${result.comFrom == '2'?'selected':'' }>常量</option>
			     </select>
	         </p>
	         <p>
	             <label>备注：</label>
	             <textarea cols="20" name="remark" class="textInput">${result.remark }</textarea>
	         </p>
	     </div>
	     <div class="formBar">
	         <ul>
	             <li>
	             	<div class="buttonActive"><div class="buttonContent"><button type="submit">更新</button></div></div>
	             </li>
	             <li>
	                 <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
	             </li>
	         </ul>
	     </div>
	 </form>
</div>

</body>
</html>