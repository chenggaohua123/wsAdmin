<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置参数的值</title>
</head>
<body>
<div class="pageContent">
<form method="post" action="suspicious/setParamValue" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	     <div class="pageFormContent" layoutH="56">
	     	<input type="hidden" name="paramId" value="${info.paramId }">
	         <p>
	             <label>参数名称：</label>
	             <input name="paramName" readonly="readonly" type="text" size="30" value="${info.paramName}" class="required"/>
	         </p>
	         <p>
	             <label>参数显示名称：</label>
	             <input name="paramDescName" readonly="readonly"  type="text" size="30" value="${info.paramDescName}" class="required"/>
	         </p>
	         <p>
	         	<label>列表值：</label>
	            <input name="stringValue" size="40"  alt="多个值请用英文逗号隔开。" class="required" value="${funcs:listToString(info.listValue)}"></input>
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