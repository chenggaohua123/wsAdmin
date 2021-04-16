<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>update</title>
</head>
<body>
<div class="pageContent">
<form method="post" action="fraud/account/updateAccountRefProfileInfoById" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${info.id}">
	     <div class="pageFormContent" layoutH="56">
	        <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" readonly="readonly" value="${info.merNo }"/>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo"  id="terNo" type="text" size="30" class="required number" readonly="readonly" value="${info.terNo }"/>
            </p>
            <p>
	             <label>规则集合：</label>
	             <input name="info.proFileId" type="hidden" id="info.proFileId" value="${info.proFileId }"/>
	             <input name="info.proFileName" id="info.proFileName" type="text" size="30" class="required" value="${info.proFileName }" readonly="readonly"/>
	             <a class="btnLook" href="fraud/account/queryProfileList" target="dialog" lookupGroup="info" lookupPk="info.proFileId"  mask="true"  width="650" height="400">查找带回</a>
	         </p>
	         <p>
	             <label>状态：</label>
	             <select name="status" class="required combox">
			           <option value="1" ${info.status=='1'?'selected':'' }>有效</option>
			           <option value="0" ${info.status=='0'?'selected':'' }>无效</option>
			     </select>
	         </p>
	         
	     </div>
	     <div class="formBar">
	         <ul>
	             <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
	             <li>
	                 <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
	             </li>
	         </ul>
	     </div>
	 </form>
	 </div>
</body>
</html>