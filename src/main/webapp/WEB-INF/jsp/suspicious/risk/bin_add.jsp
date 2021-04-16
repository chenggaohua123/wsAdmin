<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Rule</title>
</head>
<body>
	 <form method="post" action="suspicious/addBinInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	     <div class="pageFormContent" layoutH="56">
	         <p>
	             <label>BIN 类型：</label>
	              <select name="type" class="required combox">
			           <option value="IP">IP</option>
			           <option value="CARDNO">卡Bin</option>
			     </select>
	         </p>
	         <p>
	             <label>起始段：</label>
	             <input name="startNum" type="text" size="30" class="required"/>
	         </p>
	         <p>
	             <label>结束段：</label>
	             <input name="endNum" type="text" size="30" class="required"/>
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
</body>
</html>