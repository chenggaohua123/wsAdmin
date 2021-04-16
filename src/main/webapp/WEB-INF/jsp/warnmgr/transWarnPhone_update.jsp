<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改电话信息</title>
</head>
<body>
<div class="pageContent">
	 <form method="post" action="warnmgr/updateWarnPhoneInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	     <div class="pageFormContent" layoutH="56">
	          <p>
	             <label>用户名：</label>
	             <input type="text" id="userName" name="userName" size="30" class="required" value="${info.userName }">
	             <input type="hidden" id="systemId" name="systemId" value="1"/>
	             <input type="hidden" id="id" name="id" value="${info.id }"/>
	         </p>
	         <p>
	             <label>邮箱：</label>
	             <input type="text" id="email" name="email" size="30" class="required" value="${info.email }">
	         </p>
	         <p>
	             <label>电话：</label>
	             <input type="text" id="phoneNo" name="phoneNo" size="30" class="required number textInput"/ value="${info.phoneNo }">
	         </p>
	     </div>
	     <div class="formBar">
	         <ul>
	             <li>
	             	<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
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