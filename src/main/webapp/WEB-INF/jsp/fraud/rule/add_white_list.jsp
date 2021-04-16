<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>白名单添加</title>
<style type="text/css">
	#p_enableFlag{
		margin-top:25px;
	}
</style>
</head>
<body>
<div class="pageContent">
	 <form method="post" action="whiteListMagage/addWhiteList" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	     <div class="pageFormContent" layoutH="56">
	     	   <p>
	             <label>商户号：</label>
	             <input type="text" name="merNo" id="merNo" size="30" class="required">
	            
	         </p>
	          <p>
	             <label>终端号：</label>
	             <input type="text" name="terNo" id="terNo" size="30" class="required">
	             
	         </p>
	     	<p>
	     		<label>规则类型：</label>
	              <select name="blackType" class="required combox">
			           <option value="IP">IP</option>
			           <option value="email">邮箱</option>
			           <option value="cardNo">卡号</option>
			     </select>
	         </p>
	         <p>
	         	<label>处理方式：</label>
	              <select name="type" class="required combox">
			           <option value="0" ${param.type=='0'?'selected':'' }>例外</option>
			           <option value="1" ${param.type=='1'?'selected':'' }>只接受</option>
			     </select>
	         </p>
	         <p>
	             <label>规则信息：</label>
	             <input name="blackText" type="text" size="30" class="required"/>
	         </p>
	         
	     	<p>
                <label>备注：</label>
               
           		<textarea name="remark"  style="width:22%;" rows="2" cols="100" ></textarea>
            </p>
	  		<p id="p_enableFlag">
             <label>状态：</label>
              <select name="enableFlag" id="enableFlag">
					<option value="1" >开通</option>
					<option value="0" >关闭</option>
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