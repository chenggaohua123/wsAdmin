<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置参数值</title>
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
	         	<label>存储名称</label>
				 <input name="tableName" type="text" size="30" value="${info.tableName}" class="required"/>
	         </p>
	         <p>
	         	<label>存储列名1</label>
	         	<select class="combox" name="colKeyName" size="30">
	         		<option value="day" <c:if test="${info.colKeyName=='day' }">selected</c:if>>day</option>
	         	</select>
	         </p>
	         <p>
	         	<label>存储值1</label>
				 <input name="colKeyValue" type="text" size="30" value="${info.colKeyValue}" class="required number textInput"/>
	         </p>
	         <p>
	         	<label>存储列名2</label>
	         	<select class="combox" name="colValueName" size="30">
	         		<option value="time" <c:if test="${info.colValueName=='time' }">selected</c:if>>time</option>
	         		<option value="amount" <c:if test="${info.colValueName=='amount' }">selected</c:if>>amount</option>
	         	</select>
	         </p>
	         <p>
	         	<label>存储值2</label>
				 <input name="colValue" type="text" size="30" value="${info.colValue}" class="required number textInput"/>
	         </p>
	         <p>
	             <label>备注：</label>
	              <textarea cols="20" readonly="readonly" name="remark" class="textInput">${result.remark }</textarea>
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