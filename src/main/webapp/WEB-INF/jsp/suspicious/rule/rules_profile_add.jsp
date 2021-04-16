<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加规则集合</title>
</head>
<body>
<div class="pageContent">
<form method="post" action="suspicious/addRuleProfile" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	     <div class="pageFormContent" layoutH="56">
	         <p>
	             <label>集合名称：</label>
	             <input name="proFileName" type="text" size="30" class="required"/>
	         </p>
	         <p>
	             <label>状态：</label>
	             <select name="status" class="required combox">
			           <option value="1" >有效</option>
			           <option value="0" >无效</option>
			     </select>
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