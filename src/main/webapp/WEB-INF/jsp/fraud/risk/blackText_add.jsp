<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Rule</title>
</head>
<body>
	 <form method="post" action="fraud/rule/addBlackTextInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	     <div class="pageFormContent" layoutH="56">
	         <p>
	             <label>黑名单类型：</label>
	              <select name="blackType" class="required combox">
			           <option value="IP">IP</option>
			           <option value="CARDNO">卡号</option>
			           <option value="EMAIL">邮箱</option>
			           <option value="sixAndFourCardNo">卡前六后四</option>
			           <option value="WEBSITE">WEBSITE</option>
			           <option value="BANK">发卡行</option>
			           <option value="exactID">设备ID</option>
			     </select>
	         </p>
	         <p>
	             <label>黑名单内容：</label>
	             <input name="blackText" type="text" size="30" class="required"/>
	         </p>
	         <p style="margin-left:150px;"><font color="red">卡前六后四示例:123456***0000</font></p>
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